import LocalStorageToken from "./LocalStorageToken.js";
import LoadingControl from "../components/LoadingControl.js";
import { MessageBoxControl } from "../components/MessageBoxControl.js";
import { MessageControl, MessageType } from "../components/MessageControl.js";
import { loadingWhiteList } from "../api/UrlWhiteList.js";
import { AdminUrls } from "../api/ApiConstant.js";
import Router from "./Router.js";
var DataType;
(function (DataType) {
    DataType["NONE"] = "NONE";
    DataType["OBJECT"] = "OBJECT";
    DataType["FORM_DATA"] = "FORM_DATA";
})(DataType || (DataType = {}));
var Ajax = (function () {
    function Ajax(url, autoMessageHandel, autoMessageBoxHandel) {
        if (autoMessageHandel === void 0) { autoMessageHandel = true; }
        if (autoMessageBoxHandel === void 0) { autoMessageBoxHandel = true; }
        this.xhr = new XMLHttpRequest();
        this.reqInfo = { url: '', data: null, type: DataType.NONE };
        this.reqInfo.url = url;
        this.url = Ajax.baseUrl + url;
        this.method = 'post';
        this.autoMessageHandel = autoMessageHandel;
        this.autoMessageBoxHandel = autoMessageBoxHandel;
        if (this.isOpenLoading()) {
            LoadingControl.open();
        }
    }
    Ajax.prototype.isOpenLoading = function () {
        var _this = this;
        var ifOpen = true;
        loadingWhiteList.map(function (v) {
            if (_this.url.indexOf(v) != -1) {
                ifOpen = false;
            }
        });
        return ifOpen;
    };
    Ajax.prototype.config = function (isJson, headerArray) {
        var _this = this;
        if (isJson === void 0) { isJson = true; }
        if (headerArray === void 0) { headerArray = []; }
        this.xhr.withCredentials = false;
        this.xhr.responseType = "json";
        this.xhr.open(this.method, this.url, true);
        var token = LocalStorageToken.getToken();
        var refreshToken = LocalStorageToken.getRefreshToken();
        if (token == undefined || token == null || refreshToken == undefined || refreshToken == null) {
            token = "";
            refreshToken = "";
        }
        this.xhr.setRequestHeader("admin_token", token);
        this.xhr.setRequestHeader("admin_refresh_token", refreshToken);
        if (headerArray.length <= 0) {
            if (isJson) {
                this.xhr.setRequestHeader("Content-Type", "application/json");
            }
        }
        else {
            headerArray.map(function (v) {
                _this.xhr.setRequestHeader(v['headerKey'], v['headerValue']);
            });
        }
        return this;
    };
    Ajax.prototype.sendEmpty = function () {
        this.reqInfo.type = DataType.NONE;
        this.reqInfo.data = null;
        this.xhr.send();
        return this;
    };
    Ajax.prototype.sendJson = function (content) {
        this.reqInfo.type = DataType.OBJECT;
        this.reqInfo.data = content;
        this.xhr.send(JSON.stringify(content));
        return this;
    };
    Ajax.prototype.sendFormData = function (data) {
        this.reqInfo.type = DataType.FORM_DATA;
        this.reqInfo.data = data;
        this.xhr.send(data);
        return this;
    };
    Ajax.prototype.result = function (successCallbackFun, bizFailCallbackFun, appFailCallbackFun) {
        var _this = this;
        this.xhr.onload = function () {
            if (_this.xhr.readyState == 4 && _this.xhr.status == 200) {
                if (_this.xhr.response['errStatus'] != undefined) {
                    if (_this.xhr.response['errStatus'] == 400) {
                        if (_this.autoMessageHandel) {
                            bizFailCallbackFun();
                            MessageControl.open(_this.xhr.response['errMessage'], MessageType.ERROR);
                        }
                        else {
                            bizFailCallbackFun(_this.xhr.response['errMessage']);
                        }
                    }
                    else if (_this.xhr.response['errStatus'] == 403) {
                        var token = LocalStorageToken.getToken();
                        var refreshToken = LocalStorageToken.getRefreshToken();
                        if (token == undefined || token == null || refreshToken == undefined || refreshToken == null || _this.xhr.response['errMessage'] != 'token过期或错误！') {
                            if (_this.autoMessageBoxHandel) {
                                MessageBoxControl.open(_this.xhr.response['errMessage'], appFailCallbackFun);
                            }
                            else {
                                appFailCallbackFun(_this.xhr.response['errMessage']);
                            }
                        }
                        else {
                            if (_this.url != Ajax.baseUrl + AdminUrls.refreshTokenUrl) {
                                new Ajax(AdminUrls.refreshTokenUrl).config().sendEmpty().result(function (data) {
                                    LocalStorageToken.setToken(data.token);
                                    LocalStorageToken.setRefreshToken(data.refreshToken);
                                    if (DataType.NONE == _this.reqInfo.type) {
                                        new Ajax(_this.reqInfo.url).config().sendEmpty().result(successCallbackFun, bizFailCallbackFun, appFailCallbackFun);
                                    }
                                    else if (DataType.OBJECT == _this.reqInfo.type) {
                                        new Ajax(_this.reqInfo.url).config().sendJson(_this.reqInfo.data).result(successCallbackFun, bizFailCallbackFun, appFailCallbackFun);
                                    }
                                    else {
                                        new Ajax(_this.reqInfo.url).config().sendFormData(_this.reqInfo.data).result(successCallbackFun, bizFailCallbackFun, appFailCallbackFun);
                                    }
                                }, function () {
                                    LocalStorageToken.delToken();
                                    LocalStorageToken.delRefreshToken();
                                    Router.go('/login.html');
                                }, function () {
                                    LocalStorageToken.delToken();
                                    LocalStorageToken.delRefreshToken();
                                    Router.go('/login.html');
                                });
                            }
                            else {
                                if (_this.autoMessageBoxHandel) {
                                    MessageBoxControl.open(_this.xhr.response['errMessage'], appFailCallbackFun);
                                }
                                else {
                                    appFailCallbackFun(_this.xhr.response['errMessage']);
                                }
                            }
                        }
                    }
                    else {
                        if (_this.autoMessageBoxHandel) {
                            MessageBoxControl.open("未知异常！", appFailCallbackFun);
                        }
                        else {
                            appFailCallbackFun("未知异常！");
                        }
                    }
                }
                else {
                    successCallbackFun(_this.xhr.response);
                }
            }
            else {
                if (_this.autoMessageBoxHandel) {
                    MessageBoxControl.open("请求失败，网络异常！", appFailCallbackFun);
                }
                else {
                    appFailCallbackFun("请求失败，网络异常！");
                }
            }
            if (_this.isOpenLoading()) {
                LoadingControl.close();
            }
        };
    };
    Ajax.baseUrl = "http://localhost:8088/shopping-mall";
    return Ajax;
}());
export default Ajax;
