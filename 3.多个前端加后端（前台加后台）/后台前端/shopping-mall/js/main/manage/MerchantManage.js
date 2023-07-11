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
import { MerchantUrls } from "../../api/ApiConstant.js";
import { CustomTableControl } from "../../components/CustomTableControl.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
import Ajax from "../../utils/Ajax.js";
import ParentMain from "../ParentMain.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
var MerchantManage = (function (_super) {
    __extends(MerchantManage, _super);
    function MerchantManage() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    MerchantManage.prototype.getEvent = function () {
        var _this = this;
        return function () {
            var mainHtml = document.querySelector('main');
            var merchantModifyBoxEl = document.querySelector('.merchant-modify-component-box');
            var merchantIdValueEl = document.querySelector('.merchant-modify-box .merchan-text-value');
            var merchantModifyInputEls = document.querySelectorAll('.merchant-modify-box .merchant-input > input');
            var merchantModifyAreaEls = document.querySelectorAll('.merchant-modify-box .merchant-text-area > textarea');
            var merchantModifySelectEl = document.querySelector('.merchant-modify-box .select-check-value');
            var merchantModifyButtonEl = document.querySelector('.merchant-modify-box .merchant-modify-button');
            var merchantAddBoxEl = document.querySelector('.merchant-add-component-box');
            var merchantAddInputEls = document.querySelectorAll('.merchant-add-box .merchant-input > input');
            var merchantAddAreaEls = document.querySelectorAll('.merchant-add-box .merchant-text-area > textarea');
            var merchantAddSelectEl = document.querySelector('.merchant-add-box .select-check-value');
            var merchantAddButtonEl = document.querySelector('.merchant-add-box .merchant-add-button');
            merchantModifyButtonEl.addEventListener('click', function () {
                _this.modifyMerchant(merchantIdValueEl, merchantModifySelectEl, merchantModifyInputEls, merchantModifyAreaEls);
            });
            merchantAddButtonEl.addEventListener('click', function () {
                _this.addMerchant(merchantAddSelectEl, merchantAddInputEls, merchantAddAreaEls);
            });
            _this.getMerchantData(__spreadArray(__spreadArray(__spreadArray(__spreadArray(__spreadArray(__spreadArray([mainHtml,
                merchantModifyBoxEl, merchantIdValueEl], merchantModifyInputEls, true), merchantModifyAreaEls, true), [merchantModifySelectEl,
                merchantAddBoxEl], false), merchantAddInputEls, true), merchantAddAreaEls, true), [merchantAddSelectEl], false));
        };
    };
    MerchantManage.prototype.getMerchantData = function (els) {
        var _this = this;
        new Ajax(MerchantUrls.adminAllDataUrl).config().sendEmpty().result(function (data) {
            _this.createTable(els, data);
        }, function () { }, this.parentFailHandel);
    };
    MerchantManage.prototype.createTable = function (els, data, index, value, currentPageNum) {
        var _this = this;
        if (index === void 0) { index = -1; }
        if (value === void 0) { value = ''; }
        if (currentPageNum === void 0) { currentPageNum = 1; }
        var tableTitleData = ['商户id', '商户号', '商户名字', '商户类型', '平台地址', '支付平台应用id', '商户公钥', '商户私钥', '内容加密密码', '回调地址'];
        var tableContentData = [];
        data.forEach(function (merchantData) {
            tableContentData.push([merchantData.merchantId, merchantData.merchantCode, merchantData.merchantName, merchantData.merchantType, merchantData.serverUrl, merchantData.payPlatformAppId, merchantData.publicKey, merchantData.privateKey, merchantData.encryptPassword, merchantData.callbackUrl]);
        });
        var tableContentDataBak = [];
        if (index != -1 && value != '') {
            tableContentData.forEach(function (merchantData) {
                if (merchantData[index].indexOf(value) != -1) {
                    tableContentDataBak.push(merchantData);
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
            CustomComponentBoxControl.open(els[12], function () {
                for (var i = 13; i <= 20; i++) {
                    els[i].value = '';
                }
            });
        }, true, function (index, value) {
            _this.setInputIndex(index);
            _this.setInputValue(value);
            _this.createTable(els, data, index, value);
        }, 1, 3, 4)
            .configUpdateAndDelete(true, function (data) {
            els[2].innerText = data[0];
            els[3].value = data[1];
            els[4].value = data[2];
            els[11].innerText = data[3];
            els[5].value = data[4];
            els[6].value = data[5];
            els[9].value = data[6];
            els[10].value = data[7];
            els[7].value = data[8];
            els[8].value = data[9];
            CustomComponentBoxControl.open(els[1]);
        }, true, function (data) {
            DelTipsBoxControl.open("确定要删除此商户吗？", function () {
                _this.deleteMerchan(data[0]);
            });
        }).create(els[0], null, tableData, [], currentPageNum);
    };
    MerchantManage.prototype.modifyMerchant = function (merchantIdEl, merchantTypeEl, inputEls, textAreaEls) {
        var _this = this;
        if (ChatCheck.isEmpty(merchantIdEl.innerText) ||
            ChatCheck.isEmpty(merchantTypeEl.innerText) ||
            ChatCheck.isEmpty(inputEls[1].value) ||
            ChatCheck.isEmpty(inputEls[2].value) ||
            ChatCheck.isEmpty(inputEls[5].value)) {
            MessageControl.open("必要信息【商户id、商户类型、商户名字、平台地址、回调地址】不得为空！", MessageType.WARNING);
        }
        else {
            new Ajax(MerchantUrls.modifyMerchantUrl).config().sendJson({
                merchantId: merchantIdEl.innerText,
                merchantCode: inputEls[0].value,
                merchantName: inputEls[1].value,
                merchantType: merchantTypeEl.innerText,
                serverUrl: inputEls[2].value,
                payPlatformAppId: inputEls[3].value,
                publicKey: textAreaEls[0].value,
                privateKey: textAreaEls[1].value,
                encryptPassword: inputEls[4].value,
                callbackUrl: inputEls[5].value
            }).result(function () { _this.responseSuccessRefresh('修改商户成功！'); }, function () { }, this.parentFailHandel);
        }
    };
    MerchantManage.prototype.addMerchant = function (merchantTypeEl, inputEls, textAreaEls) {
        var _this = this;
        if (ChatCheck.isEmpty(merchantTypeEl.innerText) ||
            ChatCheck.isEmpty(inputEls[1].value) ||
            ChatCheck.isEmpty(inputEls[2].value) ||
            ChatCheck.isEmpty(inputEls[5].value)) {
            MessageControl.open("必要信息【商户类型、商户名字、平台地址、回调地址】不得为空！", MessageType.WARNING);
        }
        else {
            new Ajax(MerchantUrls.addMerchantUrl).config().sendJson({
                merchantCode: inputEls[0].value,
                merchantName: inputEls[1].value,
                merchantType: merchantTypeEl.innerText,
                serverUrl: inputEls[2].value,
                payPlatformAppId: inputEls[3].value,
                publicKey: textAreaEls[0].value,
                privateKey: textAreaEls[1].value,
                encryptPassword: inputEls[4].value,
                callbackUrl: inputEls[5].value
            }).result(function () { _this.responseSuccessRefresh('添加商户成功！'); }, function () { }, this.parentFailHandel);
        }
    };
    MerchantManage.prototype.deleteMerchan = function (merchantId) {
        var _this = this;
        if (merchantId.trim().length <= 0) {
            MessageControl.open("商户id不得为空！", MessageType.WARNING);
        }
        else {
            new Ajax(MerchantUrls.delMerchantByIdUrl).config().sendJson({
                merchantId: merchantId
            }).result(function () { _this.responseSuccessRefresh('删除商户成功！', false); }, function () { }, this.parentFailHandel);
        }
    };
    return MerchantManage;
}(ParentMain));
PublicMainView.run(new MerchantManage(6));
