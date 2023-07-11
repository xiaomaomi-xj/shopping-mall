//异步请求
import LocalStorageToken from "./LocalStorageToken.js";
import LoadingControl from "../components/LoadingControl.js";
import { MessageBoxControl } from "../components/MessageBoxControl.js";
import { MessageControl, MessageType } from "../components/MessageControl.js";
import { loadingWhiteList } from "../api/UrlWhiteList.js";
import { AdminUrls } from "../api/ApiConstant.js";
import { LoginType } from "../interface/Types.js";
import Router from "./Router.js";
//请求头参数
export interface HeaderParam {
    headerKey: string,
    headerValue: string
}
//xhr 请求信息类型
interface ReqInfoType {
    url: string;
    data: object | null | FormData;
    type: DataType
}
//数据方式
enum DataType {
    NONE = 'NONE',
    OBJECT = 'OBJECT',
    FORM_DATA = 'FORM_DATA'
}
class Ajax {
    private static baseUrl = "http://localhost:8088/shopping-mall";
    private url: string;
    private method: string;
    private xhr = new XMLHttpRequest();
    private autoMessageHandel: boolean;
    private autoMessageBoxHandel: boolean;
    //请求信息
    private reqInfo: ReqInfoType = { url: '', data: null, type: DataType.NONE };
    constructor(url: string, autoMessageHandel: boolean = true, autoMessageBoxHandel: boolean = true) {
        this.reqInfo.url = url;
        this.url = Ajax.baseUrl + url;
        this.method = 'post';
        //是否开启message-box和message处理,默认开启
        this.autoMessageHandel = autoMessageHandel;
        this.autoMessageBoxHandel = autoMessageBoxHandel;
        //开启loding
        if (this.isOpenLoading()) {
            LoadingControl.open()
        }
    }
    //是否开启loding组件，有一些请求链接不需要开启
    private isOpenLoading(): boolean {
        let ifOpen = true;
        loadingWhiteList.map(v => {
            if (this.url.indexOf(v) != -1) {
                ifOpen = false;
            }
        });
        return ifOpen;
    }
    //请求配置
    config(isJson: Boolean = true, headerArray: Array<HeaderParam> = []): Ajax {
        this.xhr.withCredentials = false;
        //响应的数据
        this.xhr.responseType = "json";
        this.xhr.open(this.method, this.url, true);
        //所有请求都加上请求头token
        let token = LocalStorageToken.getToken();
        let refreshToken = LocalStorageToken.getRefreshToken();
        if (token == undefined || token == null || refreshToken == undefined || refreshToken == null) {
            token = "";
            refreshToken = "";
        }
        this.xhr.setRequestHeader("admin_token", token);
        this.xhr.setRequestHeader("admin_refresh_token", refreshToken);
        //请求头，要为json
        if (headerArray.length <= 0) {
            if (isJson) {
                //上传formdata的时候不要设置Content-Type
                this.xhr.setRequestHeader("Content-Type", "application/json");
            }
        } else {
            headerArray.map(v => {
                this.xhr.setRequestHeader(v['headerKey'], v['headerValue']);
            });
        }
        return this;
    }
    //发送请求，空
    sendEmpty(): Ajax {
        this.reqInfo.type = DataType.NONE;
        this.reqInfo.data = null;
        this.xhr.send();
        return this;
    }
    //发送请求，json信息
    sendJson(content: object): Ajax {
        this.reqInfo.type = DataType.OBJECT;
        this.reqInfo.data = content;
        this.xhr.send(JSON.stringify(content));
        return this;
    }
    //发送formdata数据（图片带json数据）
    sendFormData(data: FormData): Ajax {
        this.reqInfo.type = DataType.FORM_DATA;
        this.reqInfo.data = data;
        this.xhr.send(data);
        return this;
    }
    //处理结果(利用回调的方式，没有使用promise)
    result(successCallbackFun: Function, bizFailCallbackFun: Function, appFailCallbackFun: Function): void {
        this.xhr.onload = () => {
            if (this.xhr.readyState == 4 && this.xhr.status == 200) {
                if (this.xhr.response['errStatus'] != undefined) {
                    if (this.xhr.response['errStatus'] == 400) {
                        //业务错误
                        if (this.autoMessageHandel) {
                            bizFailCallbackFun();
                            MessageControl.open(this.xhr.response['errMessage'], MessageType.ERROR);
                        } else {
                            bizFailCallbackFun(this.xhr.response['errMessage']);
                        }
                    } else if (this.xhr.response['errStatus'] == 403) {
                        //系统错误，或者无权限,尝试刷新token
                        let token = LocalStorageToken.getToken();
                        let refreshToken = LocalStorageToken.getRefreshToken();
                        if (token == undefined || token == null || refreshToken == undefined || refreshToken == null || this.xhr.response['errMessage'] != 'token过期或错误！') {
                            if (this.autoMessageBoxHandel) {
                                MessageBoxControl.open(this.xhr.response['errMessage'], appFailCallbackFun);
                            } else {
                                appFailCallbackFun(this.xhr.response['errMessage']);
                            }
                        } else {
                            //只有拥有token，才会刷新，但是刷新接口除外
                            if (this.url != Ajax.baseUrl + AdminUrls.refreshTokenUrl) {
                                new Ajax(AdminUrls.refreshTokenUrl).config().sendEmpty().result(
                                    (data: LoginType) => {
                                        LocalStorageToken.setToken(data.token);
                                        LocalStorageToken.setRefreshToken(data.refreshToken);
                                        //重新进行请求
                                        if (DataType.NONE == this.reqInfo.type) {
                                            new Ajax(this.reqInfo.url).config().sendEmpty().result(successCallbackFun, bizFailCallbackFun, appFailCallbackFun);
                                        } else if (DataType.OBJECT == this.reqInfo.type) {
                                            new Ajax(this.reqInfo.url).config().sendJson(this.reqInfo.data as object).result(successCallbackFun, bizFailCallbackFun, appFailCallbackFun);
                                        } else {
                                            new Ajax(this.reqInfo.url).config().sendFormData(this.reqInfo.data as FormData).result(successCallbackFun, bizFailCallbackFun, appFailCallbackFun);
                                        }
                                    },
                                    () => {
                                        //删掉token，跳转登陆页
                                        LocalStorageToken.delToken();
                                        LocalStorageToken.delRefreshToken();
                                        Router.go('/login.html');
                                    },
                                    () => {
                                        //删掉token，跳转登陆页
                                        LocalStorageToken.delToken();
                                        LocalStorageToken.delRefreshToken();
                                        Router.go('/login.html');
                                    }
                                );
                            } else {
                                if (this.autoMessageBoxHandel) {
                                    MessageBoxControl.open(this.xhr.response['errMessage'], appFailCallbackFun);
                                } else {
                                    appFailCallbackFun(this.xhr.response['errMessage']);
                                }
                            }
                        }
                    } else {
                        if (this.autoMessageBoxHandel) {
                            MessageBoxControl.open("未知异常！", appFailCallbackFun);
                        } else {
                            appFailCallbackFun("未知异常！");
                        }
                    }
                } else {
                    //成功响应
                    successCallbackFun(this.xhr.response);
                }
            } else {
                if (this.autoMessageBoxHandel) {
                    MessageBoxControl.open("请求失败，网络异常！", appFailCallbackFun);
                } else {
                    appFailCallbackFun("请求失败，网络异常！");
                }
            }
            if (this.isOpenLoading()) {
                LoadingControl.close();
            }
        }
    }
}
export default Ajax;