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
import { PublicMainView } from "../components/PublicMainView.js";
import Ajax from "../utils/Ajax.js";
import { ChatBoosUrls, OrderUrls, UserUrls, GoodsUrls, ExtraConfigUrls } from "../api/ApiConstant.js";
import ParentMain from "./ParentMain.js";
import { CustomTableControl } from "../components/CustomTableControl.js";
import { ChatCheck } from "../utils/ChatCheck.js";
var Main = (function (_super) {
    __extends(Main, _super);
    function Main() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    Main.prototype.getEvent = function () {
        var _this = this;
        return function () {
            var goodsNumEl = document.querySelector('.c-body.goods-num');
            var orderNumEl = document.querySelector('.c-body.order-num');
            var userNumEl = document.querySelector('.c-body.user-num');
            var chatNumEl = document.querySelector('.c-body.chat-num');
            _this.getData(goodsNumEl, orderNumEl, userNumEl, chatNumEl);
            _this.extraConfigDataHandel();
        };
    };
    Main.prototype.getData = function (goodsNumEl, orderNumEl, userNumEl, chatNumEl) {
        this.chatDataHandel(chatNumEl);
        this.goodsDataHandel(goodsNumEl);
        this.orderDataHandel(orderNumEl);
        var sum = 0;
        new Ajax(UserUrls.allUserDataUrl).config().sendEmpty().result(function (data) {
            sum += data.length;
            userNumEl.innerText = sum.toString();
        }, function () { }, this.parentFailHandel);
        new Ajax(UserUrls.allWechatDataUrl).config().sendEmpty().result(function (data) {
            data.forEach(function (wechat) {
                if (ChatCheck.isEmpty(wechat.bindId)) {
                    sum += data.length;
                }
            });
            userNumEl.innerText = sum.toString();
        }, function () { }, this.parentFailHandel);
    };
    Main.prototype.orderDataHandel = function (orderNumEl) {
        new Ajax(OrderUrls.allDataUrl).config().sendEmpty().result(function (data) {
            var amountEl = document.querySelector('.amount-total');
            var amounSum = 0;
            data.forEach(function (v) {
                amounSum += v.totalPrice;
            });
            amountEl.innerText = amounSum.toString();
            orderNumEl.innerText = data.length.toString();
        }, function () { }, this.parentFailHandel);
    };
    Main.prototype.goodsDataHandel = function (goodsNumEl) {
        new Ajax(GoodsUrls.allDataUrl).config().sendEmpty().result(function (data) {
            var rightBoxEl = document.querySelector('.among .box');
            var tableRowData = [];
            data.sort(function (a, b) { return a.maxBuyNum - b.maxBuyNum; });
            data.forEach(function (v, k) {
                if (k < 10) {
                    tableRowData.push([v.goodsId, v.imgUrl, v.goodsName, v.goodsPrice.toString(), v.maxBuyNum.toString()]);
                }
            });
            var tableData = {
                title: ['商品id', '商品封面', '商品名字', '商品价格', '商品库存'],
                content: tableRowData
            };
            CustomTableControl.configHaveImgOrHavepageNum(true).create(rightBoxEl, null, tableData, [1]);
            goodsNumEl.innerText = data.length.toString();
        }, function () { }, this.parentFailHandel);
    };
    Main.prototype.chatDataHandel = function (chatNumEl) {
        new Ajax(ChatBoosUrls.allDataUrl).config().sendEmpty().result(function (data) {
            var rightBoxEl = document.querySelector('.right .box');
            var tableRowData = [];
            var num = 0;
            data.reverse().forEach(function (v) {
                if (num < 10 && v.fromUser == 1) {
                    num++;
                    tableRowData.push([v.userId, v.message, v.isUnRead == 1 ? '未读' : '已读']);
                }
            });
            var tableData = {
                title: ['发送者', '消息内容', '是否已读'],
                content: tableRowData
            };
            CustomTableControl.create(rightBoxEl, null, tableData, []);
            var sum = 0;
            data.forEach(function (v) {
                if (v.fromUser == 1 && v.isUnRead == 1) {
                    sum++;
                }
            });
            chatNumEl.innerText = sum.toString();
        }, function () { }, this.parentFailHandel);
    };
    Main.prototype.extraConfigDataHandel = function () {
        var _this = this;
        new Ajax(ExtraConfigUrls.getConfigUrl).config().sendEmpty().result(function (data) {
            var storeName;
            if (data.storeNameHaveSpecial) {
                storeName = data.storeName.replace('^', data.specialText);
            }
            else {
                storeName = data.storeName;
            }
            _this.createStorNameNode(storeName.trim());
        }, function () { }, this.parentFailHandel);
    };
    Main.prototype.createStorNameNode = function (storeName) {
        var leftEl = document.querySelector('.bottom .left');
        storeName.split('').forEach(function (v) {
            var windmillEl = document.createElement('div');
            windmillEl.className = 'windmill';
            var spanBoxEl = document.createElement('div');
            spanBoxEl.className = 'span-box';
            var spanEl = document.createElement('span');
            spanEl.innerText = v;
            spanBoxEl.appendChild(spanEl);
            windmillEl.appendChild(spanBoxEl);
            leftEl.appendChild(windmillEl);
        });
    };
    return Main;
}(ParentMain));
PublicMainView.run(new Main(0));
