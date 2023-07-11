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
import { PublicMainView } from "../../components/PublicMainView.js";
import Ajax from "../../utils/Ajax.js";
import { OrderUrls } from "../../api/ApiConstant.js";
import { CustomTableControl } from "../../components/CustomTableControl.js";
import ParentMain from "../ParentMain.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
var OrderManage = (function (_super) {
    __extends(OrderManage, _super);
    function OrderManage() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    OrderManage.prototype.getEvent = function () {
        var _this = this;
        return function () {
            var mainEl = document.querySelector('main');
            var orderModifyDhipStatusEl = document.querySelector('.order-modify-ship-status-box');
            var orderIdValueEl = document.querySelector('.order-id-value');
            var selectCheckValueEl = document.querySelector('.select-check-value');
            var submitButtonEl = document.querySelector('.order-modify-status-button');
            submitButtonEl.addEventListener('click', function () {
                _this.modifyOrderShipStatus(orderIdValueEl, selectCheckValueEl);
            });
            _this.getOrderData([mainEl, orderModifyDhipStatusEl, orderIdValueEl, selectCheckValueEl]);
        };
    };
    OrderManage.prototype.getOrderData = function (els) {
        var _this = this;
        new Ajax(OrderUrls.allDataUrl).config().sendEmpty().result(function (data) {
            _this.createTable(els, data);
        }, function () { }, this.parentFailHandel);
    };
    OrderManage.prototype.createTable = function (els, data, index, value, currentPageNum) {
        var _this = this;
        if (index === void 0) { index = -1; }
        if (value === void 0) { value = ''; }
        if (currentPageNum === void 0) { currentPageNum = 1; }
        var tableTitleData = ['订单id', '用户id', '商品名字', '商户名字', '商品数量', '运送状态', '总金额', '支付状态'];
        var tableContentData = [];
        data.forEach(function (orderData) {
            var payStatus = '未支付';
            if (orderData.orderState == 'COMPLET') {
                payStatus = '已支付';
            }
            var shipStatus = '未发货';
            if (orderData.shipStatus == 2) {
                shipStatus = '运送中';
            }
            else if (orderData.shipStatus == 3) {
                shipStatus = '已送达';
            }
            tableContentData.push([orderData.orderId, orderData.userId, orderData.goodsName, orderData.merchantName, orderData.goodsNum.toString(), shipStatus, orderData.totalPrice.toString(), payStatus]);
        });
        var tableContentDataBak = [];
        if (index != -1 && value != '') {
            tableContentData.forEach(function (goodsData) {
                if (goodsData[index].indexOf(value) != -1) {
                    tableContentDataBak.push(goodsData);
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
            .configQueryAndAdd(false, function () { }, true, function (index, value) {
            _this.setInputIndex(index);
            _this.setInputValue(value);
            _this.createTable(els, data, index, value);
        }, 1, 2, 3)
            .configUpdateAndDelete(true, function (data) {
            els[2].innerText = data[0];
            els[3].innerText = data[5];
            CustomComponentBoxControl.open(els[1]);
        }, false, function () { }).create(els[0], null, tableData, [], currentPageNum);
    };
    OrderManage.prototype.modifyOrderShipStatus = function (orderIdValueEl, selectCheckValueEl) {
        var _this = this;
        var orderId = orderIdValueEl.innerText;
        var shipStatus = 1;
        var shipStatusName = selectCheckValueEl.innerText;
        switch (shipStatusName) {
            case '运送中':
                shipStatus = 2;
                break;
            case '已送达':
                shipStatus = 3;
                break;
            default:
                shipStatus = 1;
        }
        new Ajax(OrderUrls.modifyOrderShipStatusUrl).config().sendJson({
            orderId: orderId,
            shipStatus: shipStatus
        }).result(function () { _this.responseSuccessRefresh('修改订单运送状态成功！'); }, function () { }, this.parentFailHandel);
    };
    return OrderManage;
}(ParentMain));
PublicMainView.run(new OrderManage(5));
