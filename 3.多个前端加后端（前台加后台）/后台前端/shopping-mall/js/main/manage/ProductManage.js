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
import { CustomTableControl } from "../../components/CustomTableControl.js";
import { GoodsUrls } from "../../api/ApiConstant.js";
import ParentMain from "../ParentMain.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { SrcToFile } from "../../utils/SrcToFile.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
var ProductManage = (function (_super) {
    __extends(ProductManage, _super);
    function ProductManage() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ProductManage.prototype.getEvent = function () {
        var _this = this;
        return function () {
            var mainHtml = document.querySelector('main');
            var productAddBoxEl = document.querySelector('.product-manage-add-component-box');
            var productAddInputEls = document.querySelectorAll('.product-manage-add-box .product-manage-input-box input');
            var productAddareaEl = document.querySelector('.product-manage-add-box textarea');
            var productAddImgEls = document.querySelectorAll('.product-manage-add-box .file-update-box-img');
            var productAddBthEl = document.querySelector('.product-manage-add-button');
            productAddBthEl.addEventListener('click', function () {
                _this.addGoods(productAddInputEls, productAddareaEl, productAddImgEls);
            });
            var productModifyBoxEl = document.querySelector('.product-manage-modify-component-box');
            var productModifyIdEl = document.querySelector('.product-manage-modify-box .product-manage-text-value');
            var productModifyInputEls = document.querySelectorAll('.product-manage-modify-box .product-manage-input-box input');
            var productModifyareaEl = document.querySelector('.product-manage-modify-box textarea');
            var productModifyImgEls = document.querySelectorAll('.product-manage-modify-box .file-update-box-img');
            var productModifyBthEl = document.querySelector('.product-manage-modify-button');
            productModifyBthEl.addEventListener('click', function () {
                _this.modifyGoods(productModifyIdEl, productModifyInputEls, productModifyareaEl, productModifyImgEls);
            });
            _this.getGoodsData(__spreadArray(__spreadArray(__spreadArray(__spreadArray(__spreadArray(__spreadArray(__spreadArray([mainHtml, productAddBoxEl], productAddInputEls, true), [productAddareaEl], false), productAddImgEls, true), [productModifyBoxEl, productModifyIdEl], false), productModifyInputEls, true), [productModifyareaEl], false), productModifyImgEls, true));
        };
    };
    ProductManage.prototype.getGoodsData = function (els) {
        var _this = this;
        new Ajax(GoodsUrls.allDataUrl).config().sendEmpty().result(function (data) {
            _this.createTable(els, data);
        }, function () { }, this.parentFailHandel);
    };
    ProductManage.prototype.createTable = function (els, data, index, value, currentPageNum) {
        var _this = this;
        if (index === void 0) { index = -1; }
        if (value === void 0) { value = ''; }
        if (currentPageNum === void 0) { currentPageNum = 1; }
        var tableTitleData = ['商品id', '商品封面', '商品名字', '商品描述', '商品价格', '库存'];
        var tableContentData = [];
        var maxImgNum = 0;
        data.forEach(function (goodsData) {
            var contentData = [goodsData.goodsId, goodsData.imgUrl, goodsData.goodsName, goodsData.goodsDescribe, goodsData.goodsPrice.toString(), goodsData.maxBuyNum.toString()];
            if (goodsData.rotationGoodsImgs.length > maxImgNum) {
                maxImgNum = goodsData.rotationGoodsImgs.length;
            }
            goodsData.rotationGoodsImgs.forEach(function (img) { return contentData.push(img); });
            tableContentData.push(contentData);
        });
        var tableContentDataBak = [];
        if (index != -1 && value != '') {
            tableContentData.forEach(function (goodsData) {
                if (goodsData[index].indexOf(value) != -1) {
                    tableContentDataBak.push(goodsData);
                }
            });
        }
        var imgIndexs = [];
        for (var i = 1; i <= maxImgNum; i++) {
            imgIndexs.push(5 + i);
            tableTitleData.push("\u8BE6\u60C5\u56FE\u7247".concat(i));
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
            .configQueryAndAdd(true, function () {
            CustomComponentBoxControl.open(els[1], function () {
                for (var i = 2; i <= 10; i++) {
                    if (i <= 5) {
                        els[i].value = '';
                    }
                    else {
                        els[i].previousElementSibling.value = '';
                        els[i].src = '../src/bak-img.jpg';
                    }
                }
            });
        }, true, function (index, value) {
            _this.setInputIndex(index);
            _this.setInputValue(value);
            _this.createTable(els, data, index, value);
        }, 1, 3)
            .configUpdateAndDelete(true, function (data) {
            els[12].innerText = data[0];
            els[13].value = data[2];
            els[14].value = data[4];
            els[15].value = data[5];
            els[16].value = data[3];
            els[17].src = data[1];
            for (var i = 6; i < data.length - 1; i++) {
                if (ChatCheck.isEmpty(data[i])) {
                    els[12 + i].src = '../src/bak-img.jpg';
                }
                else {
                    els[12 + i].src = data[i];
                }
            }
            CustomComponentBoxControl.open(els[11]);
        }, true, function (data) {
            DelTipsBoxControl.open('确定要删除此商品吗？<br /> <p style="color:red;font-size:.9em;margin:1em;">（注：删除商品也会删除与之关联的购物车、订单、图片数据以及可能会影响界面！)</p>', function () {
                _this.delGoods(data[0]);
            });
        }).create(els[0], null, tableData, __spreadArray([1], imgIndexs, true), currentPageNum);
    };
    ProductManage.prototype.addGoods = function (productAddInputEls, productAddareaEl, productAddImgEls) {
        var _this = this;
        var goodsName = productAddInputEls[0].value;
        var goodsDescribe = productAddareaEl.value;
        var goodsPrice = productAddInputEls[1].value;
        var maxBuyNum = productAddInputEls[2].value;
        if (ChatCheck.isEmpty(goodsName) ||
            ChatCheck.isEmpty(goodsDescribe) ||
            ChatCheck.isEmpty(goodsPrice) ||
            ChatCheck.isEmpty(maxBuyNum)) {
            MessageControl.open("必要信息【商品名字、商品描述、商品价格、库存】不得为空！", MessageType.WARNING);
            return;
        }
        if (window.isNaN(window.parseInt(maxBuyNum))) {
            MessageControl.open("库存必须是正确的数字类型！", MessageType.WARNING);
            return;
        }
        if (window.isNaN(window.parseFloat(goodsPrice))) {
            MessageControl.open("商品价格必须是正确的数字类型！", MessageType.WARNING);
            return;
        }
        var mainImgUrl = productAddImgEls[0].src;
        if (!SrcToFile.check(mainImgUrl)) {
            MessageControl.open("商品封面图片必须上传！", MessageType.WARNING);
            return;
        }
        var imgSrcs = [];
        productAddImgEls.forEach(function (img) {
            if (SrcToFile.check(img.src)) {
                imgSrcs.push(img.src);
            }
        });
        if (imgSrcs.length < 2) {
            MessageControl.open("商品详情图片最少上传一个！", MessageType.WARNING);
            return;
        }
        var formData = new FormData();
        var imgFiles = [];
        formData.append('goodsName', goodsName);
        formData.append('goodsDescribe', goodsDescribe);
        formData.append('goodsPrice', goodsPrice);
        formData.append('maxBuyNum', maxBuyNum);
        SrcToFile.moreSrcToFile(imgSrcs, imgFiles, function () {
            imgFiles.forEach(function (imgFile) {
                formData.append('files', imgFile);
            });
            _this.addGoodsSubmit(formData);
        }, function () {
            MessageControl.open("文件上传错误，请检查文件类型！", MessageType.WARNING);
        });
    };
    ProductManage.prototype.addGoodsSubmit = function (formData) {
        var _this = this;
        new Ajax(GoodsUrls.addGoodsUrl).config(false).sendFormData(formData).result(function () { _this.responseSuccessRefresh('添加商品成功！'); }, function () { }, this.parentFailHandel);
    };
    ProductManage.prototype.modifyGoods = function (productModifyIdEl, productModifyInputEls, productModifyareaEl, productModifyImgEls) {
        var _this = this;
        var goodsId = productModifyIdEl.innerText;
        var goodsName = productModifyInputEls[0].value;
        var goodsDescribe = productModifyareaEl.value;
        var goodsPrice = productModifyInputEls[1].value;
        var maxBuyNum = productModifyInputEls[2].value;
        if (ChatCheck.isEmpty(goodsId) ||
            ChatCheck.isEmpty(goodsName) ||
            ChatCheck.isEmpty(goodsDescribe) ||
            ChatCheck.isEmpty(goodsPrice) ||
            ChatCheck.isEmpty(maxBuyNum)) {
            MessageControl.open("必要信息【商品id、商品名字、商品描述、商品价格、库存】不得为空！", MessageType.WARNING);
            return;
        }
        if (window.isNaN(window.parseInt(maxBuyNum))) {
            MessageControl.open("库存必须是正确的数字类型！", MessageType.WARNING);
            return;
        }
        if (window.isNaN(window.parseFloat(goodsPrice))) {
            MessageControl.open("商品价格必须是正确的数字类型！", MessageType.WARNING);
            return;
        }
        var mainImgUrl = productModifyImgEls[0].src;
        if (!SrcToFile.check(mainImgUrl)) {
            MessageControl.open("商品封面图片必须上传！", MessageType.WARNING);
            return;
        }
        var imgSrcs = [];
        productModifyImgEls.forEach(function (img) {
            if (SrcToFile.check(img.src)) {
                imgSrcs.push(img.src);
            }
        });
        if (imgSrcs.length < 2) {
            MessageControl.open("商品详情图片最少上传一个！", MessageType.WARNING);
            return;
        }
        var formData = new FormData();
        var imgFiles = [];
        formData.append('goodsId', goodsId);
        formData.append('goodsName', goodsName);
        formData.append('goodsDescribe', goodsDescribe);
        formData.append('goodsPrice', goodsPrice);
        formData.append('maxBuyNum', maxBuyNum);
        SrcToFile.moreSrcToFile(imgSrcs, imgFiles, function () {
            imgFiles.forEach(function (imgFile) {
                formData.append('files', imgFile);
            });
            _this.modifyGoodsSubmit(formData);
        }, function () {
            MessageControl.open("文件上传错误，请检查文件类型！", MessageType.WARNING);
        });
    };
    ProductManage.prototype.modifyGoodsSubmit = function (formData) {
        var _this = this;
        new Ajax(GoodsUrls.modifyGoodsUrl).config(false).sendFormData(formData).result(function () { _this.responseSuccessRefresh('修改商品成功！'); }, function () { }, this.parentFailHandel);
    };
    ProductManage.prototype.delGoods = function (id) {
        var _this = this;
        if (ChatCheck.isEmpty(id)) {
            MessageControl.open("商品id不能为空！", MessageType.ERROR);
            return;
        }
        new Ajax(GoodsUrls.delGoodsUrl).config().sendJson({
            id: id
        }).result(function () { _this.responseSuccessRefresh('删除商品成功！'); }, function () { }, this.parentFailHandel);
    };
    return ProductManage;
}(ParentMain));
PublicMainView.run(new ProductManage(1));
