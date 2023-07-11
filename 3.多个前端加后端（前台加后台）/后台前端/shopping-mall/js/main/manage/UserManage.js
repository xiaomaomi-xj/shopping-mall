var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        if (typeof b !== "function" && b !== null)
            throw new TypeError("Class extends value " + String(b) + " is not a constructor or null");
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
import { UserUrls } from "../../api/ApiConstant.js";
import { PublicMainView } from "../../components/PublicMainView.js";
import Ajax from "../../utils/Ajax.js";
import { CustomTableControl } from "../../components/CustomTableControl.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import ParentMain from "../ParentMain.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
var UserManage = (function (_super) {
    __extends(UserManage, _super);
    function UserManage() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    UserManage.prototype.getEvent = function () {
        var _this = this;
        return function () {
            var mainHtml = document.querySelector('main');
            _this.getUserData([mainHtml]);
        };
    };
    UserManage.prototype.getUserData = function (els) {
        var _this = this;
        new Ajax(UserUrls.allUserDataUrl).config().sendEmpty().result(function (data) {
            new Ajax(UserUrls.allWechatDataUrl).config().sendEmpty().result(function (da) {
                _this.createTable(els, data.concat(da));
            }, function () { }, _this.parentFailHandel);
        }, function () { }, this.parentFailHandel);
    };
    UserManage.prototype.createTable = function (els, data, index, value, currentPageNum) {
        var _this = this;
        if (index === void 0) { index = -1; }
        if (value === void 0) { value = ''; }
        if (currentPageNum === void 0) { currentPageNum = 1; }
        var tableTitleData = ['用户id', '头像', '昵称', '账号', '性别', '绑定id', '用户类型'];
        var tableContentData = [];
        data.forEach(function (userData) {
            tableContentData.push([userData.userId, userData.userHeadUrl, userData.userName, userData.userAccount, userData.userSex == 1 ? '男' : '女', userData.bindId, userData.userType == 'email-user' ? '邮箱用户' : '微信用户']);
        });
        var tableContentDataBak = [];
        if (index != -1 && value != '') {
            tableContentData.forEach(function (user) {
                if (user[index].indexOf(value) != -1) {
                    tableContentDataBak.push(user);
                }
            });
        }
        var tableData = {
            title: tableTitleData,
            content: (index != -1 && value != '') ? tableContentDataBak : tableContentData
        };
        CustomTableControl
            .configValue(index, value)
            .configHaveImgOrHavepageNum(true, true, function (currentPageNum) {
            _this.createTable(els, data, _this.getInputIndex(), _this.getInputValue(), currentPageNum);
        })
            .configQueryAndAdd(false, function () { }, true, function (index, value) {
            _this.setInputIndex(index);
            _this.setInputValue(value);
            _this.createTable(els, data, index, value);
        }, 1, 4, 7)
            .configUpdateAndDelete(false, function () { }, true, function (data) {
            DelTipsBoxControl.open('确定要删除此用户吗？', function () {
                var type = '';
                if ('邮箱用户' == data[6]) {
                    type = 'email-user';
                    _this.delUser(data[0], type);
                }
                else if ('微信用户' == data[6]) {
                    type = 'wechat-user';
                    _this.delUser(data[0], type);
                }
                else {
                    MessageControl.open('用户类型错误！', MessageType.ERROR);
                }
            });
        }).create(els[0], null, tableData, [1], currentPageNum);
    };
    UserManage.prototype.delUser = function (id, type) {
        var _this = this;
        if (type == 'email-user') {
            new Ajax(UserUrls.delUserUrl).config().sendJson({ id: id }).result(function () {
                _this.responseSuccessRefresh('删除用户成功！', false);
            }, function () { }, this.parentFailHandel);
        }
        else if (type == 'wechat-user') {
            new Ajax(UserUrls.delWechatUrl).config().sendJson({ id: id }).result(function () {
                _this.responseSuccessRefresh('删除用户成功！', false);
            }, function () { }, this.parentFailHandel);
        }
        else {
            MessageControl.open('用户类型错误！', MessageType.ERROR);
        }
    };
    return UserManage;
}(ParentMain));
PublicMainView.run(new UserManage(4));
