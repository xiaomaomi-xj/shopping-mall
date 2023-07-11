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
var __spreadArray = (this && this.__spreadArray) || function (to, from, pack) {
    if (pack || arguments.length === 2) for (var i = 0, l = from.length, ar; i < l; i++) {
        if (ar || !(i in from)) {
            if (!ar) ar = Array.prototype.slice.call(from, 0, i);
            ar[i] = from[i];
        }
    }
    return to.concat(ar || Array.prototype.slice.call(from));
};
import { PublicMainView } from "../../components/PublicMainView.js";
import Ajax from "../../utils/Ajax.js";
import { VipCardUrls } from "../../api/ApiConstant.js";
import { CustomTableControl } from "../../components/CustomTableControl.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import ParentMain from "../ParentMain.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
var VipCardManage = (function (_super) {
    __extends(VipCardManage, _super);
    function VipCardManage() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    VipCardManage.prototype.getEvent = function () {
        var _this = this;
        return function () {
            var mainHtml = document.querySelector('main');
            var vipCardModifyBoxEl = document.querySelector('.vip-card-modify-component-box');
            var vipCardIdModifyEl = document.querySelector('.vip-card-modify-box .vip-card-text-value');
            var vipCardInputModifyEls = document.querySelectorAll('.vip-card-modify-box .vip-card-input > input');
            var vipCardPasswordBtnEl = document.querySelector('.vip-card-modify-box .vip-card-modify-password-button');
            var vipCardBalanceBtnEl = document.querySelector('.vip-card-modify-box .vip-card-modify-balance-button');
            vipCardPasswordBtnEl.addEventListener('click', function () {
                _this.modifyVipCardPassword(vipCardIdModifyEl, vipCardInputModifyEls[0]);
            });
            vipCardBalanceBtnEl.addEventListener('click', function () {
                _this.modifyVipCardBalance(vipCardIdModifyEl, vipCardInputModifyEls[1]);
            });
            var vipCardAddBoxEl = document.querySelector('.vip-card-add-component-box');
            var vipCardInputAddEls = document.querySelectorAll('.vip-card-add-box .vip-card-input > input');
            var vipCardBtnAddEl = document.querySelector('.vip-card-add-box .vip-card-add-button');
            vipCardBtnAddEl.addEventListener('click', function () {
                _this.addVipCard(vipCardInputAddEls);
            });
            _this.getVipCardData(__spreadArray(__spreadArray(__spreadArray([mainHtml, vipCardModifyBoxEl, vipCardIdModifyEl], vipCardInputModifyEls, true), [vipCardAddBoxEl], false), vipCardInputAddEls, true));
        };
    };
    VipCardManage.prototype.getVipCardData = function (els) {
        var _this = this;
        new Ajax(VipCardUrls.getAllVipCardUrl).config().sendEmpty().result(function (data) {
            _this.createTable(els, data);
        }, function () { }, this.parentFailHandel);
    };
    VipCardManage.prototype.createTable = function (els, data, index, value, currentPageNum) {
        var _this = this;
        if (index === void 0) { index = -1; }
        if (value === void 0) { value = ''; }
        if (currentPageNum === void 0) { currentPageNum = 1; }
        var tableTitleData = ['购物卡id', '购物卡账号', '购物卡余额'];
        var tableContentData = [];
        data.forEach(function (vipCard) {
            tableContentData.push([vipCard.vipCardId, vipCard.vipCardAccount, vipCard.vipCardBalance]);
        });
        var tableContentDataBak = [];
        if (index != -1 && value != '') {
            tableContentData.forEach(function (vipCard) {
                if (vipCard[index].indexOf(value) != -1) {
                    tableContentDataBak.push(vipCard);
                }
            });
        }
        var tableData = {
            title: tableTitleData,
            content: (index != -1 && value != '') ? tableContentDataBak : tableContentData
        };
        CustomTableControl
            .configValue(index, value)
            .configHaveImgOrHavepageNum(false, true, function (currentPageNum) {
            _this.createTable(els, data, _this.getInputIndex(), _this.getInputValue(), currentPageNum);
        })
            .configQueryAndAdd(true, function () {
            CustomComponentBoxControl.open(els[5], function () {
                els[6].value = '';
                els[7].value = '';
            });
        }, true, function (index, value) {
            _this.setInputIndex(index);
            _this.setInputValue(value);
            _this.createTable(els, data, index, value);
        }, 1, 2)
            .configUpdateAndDelete(true, function (data) {
            els[2].innerText = data[0];
            els[3].value = '';
            els[4].value = data[2];
            CustomComponentBoxControl.open(els[1]);
        }, true, function (data) {
            DelTipsBoxControl.open("确定要删除此购物卡吗？", function () {
                _this.delVipCard(data[0]);
            });
        }).create(els[0], null, tableData, [], currentPageNum);
    };
    VipCardManage.prototype.modifyVipCardBalance = function (vipCardBtnModifyEl, vipCardBalanceInputEl) {
        var _this = this;
        if (ChatCheck.isEmpty(vipCardBtnModifyEl.innerText) ||
            ChatCheck.isEmpty(vipCardBalanceInputEl.value)) {
            MessageControl.open("必要信息【购物卡id、购物卡余额】不得为空！", MessageType.WARNING);
        }
        else {
            if (window.isNaN(window.parseFloat(vipCardBalanceInputEl.value))) {
                MessageControl.open("请输入正确的金额格式！", MessageType.WARNING);
            }
            else {
                new Ajax(VipCardUrls.modifyVipCardBalanceUrl).config().sendJson({
                    vipCardId: vipCardBtnModifyEl.innerText,
                    vipCardBalance: vipCardBalanceInputEl.value
                }).result(function () { _this.responseSuccessRefresh('修改购物卡余额成功！'); }, function () { }, this.parentFailHandel);
            }
        }
    };
    VipCardManage.prototype.modifyVipCardPassword = function (vipCardBtnModifyEl, vipCardPasswordInputEl) {
        var _this = this;
        if (ChatCheck.isEmpty(vipCardBtnModifyEl.innerText) ||
            ChatCheck.isEmpty(vipCardPasswordInputEl.value)) {
            MessageControl.open("必要信息【购物卡id、购物卡支付密码】不得为空！", MessageType.WARNING);
        }
        else {
            new Ajax(VipCardUrls.modifyVipCardPasswordUrl).config().sendJson({
                vipCardId: vipCardBtnModifyEl.innerText,
                vipCardPassword: vipCardPasswordInputEl.value,
            }).result(function () { _this.responseSuccessRefresh('修改购物卡支付密码成功！'); }, function () { }, this.parentFailHandel);
        }
    };
    VipCardManage.prototype.addVipCard = function (vipCardInputAddEls) {
        var _this = this;
        if (ChatCheck.isEmpty(vipCardInputAddEls[0].value) ||
            ChatCheck.isEmpty(vipCardInputAddEls[1].value)) {
            MessageControl.open("必要信息【购物卡支付密码、购物卡余额】不得为空！", MessageType.WARNING);
        }
        else {
            if (window.isNaN(window.parseFloat(vipCardInputAddEls[1].value))) {
                MessageControl.open("请输入正确的金额格式！", MessageType.WARNING);
            }
            else {
                new Ajax(VipCardUrls.addVipCardUrl).config().sendJson({
                    vipCardPassword: vipCardInputAddEls[0].value,
                    vipCardBalance: vipCardInputAddEls[1].value
                }).result(function () { _this.responseSuccessRefresh('添加购物卡成功！'); }, function () { }, this.parentFailHandel);
            }
        }
    };
    VipCardManage.prototype.delVipCard = function (vipCardId) {
        var _this = this;
        if (ChatCheck.isEmpty(vipCardId)) {
            MessageControl.open("购物卡id不得为空！", MessageType.WARNING);
        }
        else {
            new Ajax(VipCardUrls.delVipCardUrl).config().sendJson({
                vipCardId: vipCardId
            }).result(function () { _this.responseSuccessRefresh('删除购物卡成功！', false); }, function () { }, this.parentFailHandel);
        }
    };
    return VipCardManage;
}(ParentMain));
PublicMainView.run(new VipCardManage(7));
