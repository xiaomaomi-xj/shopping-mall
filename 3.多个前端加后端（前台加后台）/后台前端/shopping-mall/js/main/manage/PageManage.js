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
import { GoodsUrls, PageManageUrls } from "../../api/ApiConstant.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { PublicMainView } from "../../components/PublicMainView.js";
import Ajax from "../../utils/Ajax.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
import ParentMain from "../ParentMain.js";
var PageManage = (function (_super) {
    __extends(PageManage, _super);
    function PageManage() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.currentSelectInputEl = document.createElement('div');
        _this.goodsEls = [];
        _this.selectPublicInputBoxEl = document.createElement('div');
        _this.publicSelectBoxEl = document.createElement('div');
        return _this;
    }
    PageManage.prototype.getEvent = function () {
        var _this = this;
        return function () {
            var tabBthEls = document.querySelectorAll('.page-manage-tab-button-box-btn .page-manage-tab-button');
            var tabViewEl = document.querySelector('.page-manage-box-more-box');
            var tabPageBoxEls = document.querySelectorAll('.page-manage-box-more-box-page');
            _this.handelTabHeight(tabPageBoxEls);
            _this.handelTabEvent(tabBthEls, tabViewEl, tabPageBoxEls);
            var pageToggleEl = document.querySelector('.page-toggle-manage-box');
            var pageRotationEl = document.querySelector('.page-rotation-manage-box');
            var pageAdEl = document.querySelector('.page-ad-manage-box');
            var pageContainerEl = document.querySelector('.page-container-manage-box');
            var goodsBoxEls = document.querySelectorAll('.page-manage-goods-box');
            _this.initGoodsView(goodsBoxEls);
            _this.initViewEvent(pageToggleEl, pageRotationEl, pageAdEl, pageContainerEl);
            _this.selectPublicInputBoxEl = document.querySelector('.page-manage-select');
            _this.publicSelectBoxEl = document.querySelector('.page-manage-select .page-manage-goods-box');
            var publicGoodsBoxBthEl = document.querySelector('.page-manage-select .page-manage-add-box-btn');
            publicGoodsBoxBthEl.addEventListener('click', function () {
                var selectBoxElChildEls = _this.publicSelectBoxEl.children;
                var goodsIds = [];
                for (var i = 0; i < selectBoxElChildEls.length; i++) {
                    if (selectBoxElChildEls.item(i).className.indexOf('page-manage-goods-check') != -1) {
                        goodsIds.push(selectBoxElChildEls.item(i).firstElementChild.innerText);
                    }
                }
                _this.currentSelectInputEl.innerHTML = _this.selectCheckTemplate(goodsIds);
                CustomComponentBoxControl.close();
            });
            var toggleSubmitBtn = document.querySelector('.page-manage-add-toggle-box .page-manage-add-box-btn');
            var containerSubmitBtn = document.querySelector('.page-manage-add-container-box .page-manage-add-box-btn');
            var toggleSubmitInputEl = document.querySelector('.page-manage-add-toggle-box .page-manage-add-box-input-box input');
            var containerSubmitInputEl = document.querySelector('.page-manage-add-container-box .page-manage-add-box-input-box input');
            _this.addSubmitEvent(toggleSubmitBtn, containerSubmitBtn, toggleSubmitInputEl, containerSubmitInputEl);
        };
    };
    PageManage.prototype.addSubmitEvent = function (toggleSubmitBtn, containerSubmitBtn, toggleSubmitInputEl, containerSubmitInputEl) {
        var _this = this;
        var toggleGoodsEls = toggleSubmitBtn.parentElement.previousElementSibling.children;
        var containerGoodsEls = containerSubmitBtn.parentElement.previousElementSibling.children;
        toggleSubmitBtn.addEventListener('click', function () {
            var toggleGoodsIds = [];
            for (var i = 0; i < toggleGoodsEls.length; i++) {
                var goodsEl = toggleGoodsEls.item(i);
                var goodsIdEl = goodsEl.firstElementChild;
                if (goodsEl.className.indexOf(" page-manage-goods-check") != -1) {
                    if (ChatCheck.isEmpty(goodsIdEl.innerText)) {
                        MessageControl.open('商品数据id为空！', MessageType.ERROR);
                        return;
                    }
                    toggleGoodsIds.push(goodsIdEl.innerText);
                }
            }
            if (toggleGoodsIds.length < 1 || toggleGoodsIds.length > 7) {
                MessageControl.open('标题界面选择的商品最少1个最多7个！', MessageType.WARNING);
                return;
            }
            if (ChatCheck.isEmpty(toggleSubmitInputEl.value)) {
                MessageControl.open('标题界面标题不得为空！！', MessageType.WARNING);
                return;
            }
            new Ajax(PageManageUrls.addToggleViewUrl).config().sendJson({
                titleName: toggleSubmitInputEl.value,
                goodsIds: toggleGoodsIds
            }).result(function () { _this.responseSuccessRefresh('添加标题界面数据成功！'); }, function () { }, _this.parentFailHandel);
        });
        containerSubmitBtn.addEventListener('click', function () {
            var containerGoodsIds = [];
            for (var i = 0; i < containerGoodsEls.length; i++) {
                var goodsEl = containerGoodsEls.item(i);
                var goodsIdEl = goodsEl.firstElementChild;
                if (goodsEl.className.indexOf(" page-manage-goods-check") != -1) {
                    if (ChatCheck.isEmpty(goodsIdEl.innerText)) {
                        MessageControl.open('商品数据id为空！', MessageType.ERROR);
                        return;
                    }
                    containerGoodsIds.push(goodsIdEl.innerText);
                }
            }
            if (containerGoodsIds.length != 9) {
                MessageControl.open('主要界面选择的商品必须为9个！', MessageType.WARNING);
                return;
            }
            if (ChatCheck.isEmpty(containerSubmitInputEl.value)) {
                MessageControl.open('主要界面标题不得为空！', MessageType.WARNING);
                return;
            }
            new Ajax(PageManageUrls.addContainerModuleUrl).config().sendJson({
                titleName: containerSubmitInputEl.value,
                goodsIds: containerGoodsIds
            }).result(function () { _this.responseSuccessRefresh('添加主要界面数据成功！'); }, function () { }, _this.parentFailHandel);
        });
    };
    PageManage.prototype.fixToggleView = function (toggleIdEl, fixInputEl, fixSelectCheckValueEls) {
        var _this = this;
        var toggleViewId = toggleIdEl.innerText;
        var titleName = fixInputEl.value;
        var goodsIds = [];
        if (ChatCheck.isEmpty(titleName)) {
            MessageControl.open("标题名字不能为空！", MessageType.WARNING);
            return;
        }
        for (var i = 0; i < fixSelectCheckValueEls.length; i++) {
            goodsIds.push(fixSelectCheckValueEls.item(i).innerText);
        }
        if (goodsIds.length < 1 || goodsIds.length > 7) {
            MessageControl.open('标题界面选择的商品最少1个最多7个！', MessageType.WARNING);
            return;
        }
        new Ajax(PageManageUrls.fixToggleViewUrl).config().sendJson({
            toggleViewId: toggleViewId,
            titleName: titleName,
            goodsIds: goodsIds
        }).result(function () { _this.responseSuccessRefresh('修改标题界面数据成功！', false); }, function () { }, this.parentFailHandel);
    };
    PageManage.prototype.fixRotationChart = function (fixSelectCheckValueEls) {
        var _this = this;
        var goodsIds = [];
        for (var i = 0; i < fixSelectCheckValueEls.length; i++) {
            goodsIds.push(fixSelectCheckValueEls.item(i).innerText);
        }
        if (goodsIds.length < 4 || goodsIds.length > 6) {
            MessageControl.open('轮播图的商品数量最好是4至6个！', MessageType.WARNING);
            return;
        }
        new Ajax(PageManageUrls.fixRotationChattUrl).config().sendJson({
            goodsIds: goodsIds
        }).result(function () { _this.responseSuccessRefresh('修改轮播图界面数据成功！', false); }, function () { }, this.parentFailHandel);
    };
    PageManage.prototype.fixGoodsAd = function (fixSelectCheckValueEls) {
        var _this = this;
        var goodsIds = [];
        for (var i = 0; i < fixSelectCheckValueEls.length; i++) {
            goodsIds.push(fixSelectCheckValueEls.item(i).innerText);
        }
        if (goodsIds.length != 2) {
            MessageControl.open('广告界面的商品数量必须为2个！', MessageType.WARNING);
            return;
        }
        new Ajax(PageManageUrls.fixGoodsAdUrl).config().sendJson({
            goodsIds: goodsIds
        }).result(function () { _this.responseSuccessRefresh('修改广告界面数据成功！', false); }, function () { }, this.parentFailHandel);
    };
    PageManage.prototype.fixContainerModule = function (containerIdEl, fixInputEl, fixSelectLeftValueEls, fixSelectTopValueEls, fixSelectBottomValueEls) {
        var _this = this;
        var containerModuleId = containerIdEl.innerText;
        var titleName = fixInputEl.value;
        var containerSpecialGoodsId = [];
        var containerTopGoodsIds = [];
        var containerBottomGoodsIds = [];
        if (ChatCheck.isEmpty(titleName)) {
            MessageControl.open("标题名字不能为空！", MessageType.WARNING);
            return;
        }
        for (var i = 0; i < fixSelectLeftValueEls.length; i++) {
            containerSpecialGoodsId.push(fixSelectLeftValueEls.item(i).innerText);
        }
        if (containerSpecialGoodsId.length != 1) {
            MessageControl.open("左侧高的商品选择的数量必须为1个！", MessageType.WARNING);
            return;
        }
        for (var i = 0; i < fixSelectTopValueEls.length; i++) {
            containerTopGoodsIds.push(fixSelectTopValueEls.item(i).innerText);
        }
        if (containerTopGoodsIds.length != 4) {
            MessageControl.open("上排商品选择的数量必须4个！", MessageType.WARNING);
            return;
        }
        for (var i = 0; i < fixSelectBottomValueEls.length; i++) {
            containerBottomGoodsIds.push(fixSelectBottomValueEls.item(i).innerText);
        }
        if (containerBottomGoodsIds.length != 4) {
            MessageControl.open("下排商品选择的数量必须4个！", MessageType.WARNING);
            return;
        }
        new Ajax(PageManageUrls.fixContainerModuleUrl).config().sendJson({
            containerModuleId: containerModuleId,
            titleName: titleName,
            containerSpecialGoodsId: containerSpecialGoodsId,
            containerTopGoodsIds: containerTopGoodsIds,
            containerBottomGoodsIds: containerBottomGoodsIds
        }).result(function () { _this.responseSuccessRefresh('修改广告界面数据成功！', false); }, function () { }, this.parentFailHandel);
    };
    PageManage.prototype.delToggleView = function (id) {
        var _this = this;
        new Ajax(PageManageUrls.delToggleViewUrl).config().sendJson({
            id: id
        }).result(function () { _this.responseSuccessRefresh('删除标题界面成功！', false); }, function () { }, this.parentFailHandel);
    };
    PageManage.prototype.delContainerModule = function (id) {
        var _this = this;
        new Ajax(PageManageUrls.delContainerModuleUrl).config().sendJson({
            id: id
        }).result(function () { _this.responseSuccessRefresh('删除主要界面成功！', false); }, function () { }, this.parentFailHandel);
    };
    PageManage.prototype.cancelSelectGoods = function () {
        var inputEls = document.querySelectorAll('.page-manage-add-box-input-box > input');
        inputEls.forEach(function (inputEl) {
            inputEl.value = '';
        });
        this.goodsEls.forEach(function (goodsEl) {
            goodsEl.className = 'page-manage-goods';
        });
    };
    PageManage.prototype.openPublicSelectInput = function (inputBoxEl) {
        var _this = this;
        var checkValuEls = inputBoxEl.children;
        var publiSelectGoodsEls = this.publicSelectBoxEl.children;
        for (var j = 0; j < publiSelectGoodsEls.length; j++) {
            for (var i = 0; i < checkValuEls.length; i++) {
                if (checkValuEls.item(i).innerText == publiSelectGoodsEls.item(j).firstElementChild.innerText) {
                    publiSelectGoodsEls.item(j).className = 'page-manage-goods page-manage-goods-check';
                    break;
                }
            }
        }
        CustomComponentBoxControl.open(this.selectPublicInputBoxEl, function () {
            _this.cancelSelectGoods();
        });
    };
    PageManage.prototype.initGoodsView = function (goodsBoxEls) {
        var _this = this;
        new Ajax(GoodsUrls.allDataUrl).config().sendEmpty().result(function (data) {
            goodsBoxEls.forEach(function (goodsBoxEl) {
                var temp = goodsBoxEl.innerHTML;
                data.forEach(function (goods) {
                    temp += _this.goodsSelectTemplate(goods.goodsId, goods.imgUrl, goods.goodsName);
                });
                goodsBoxEl.innerHTML = temp;
            });
            _this.goodsEls = document.querySelectorAll('.page-manage-goods-box .page-manage-goods');
            _this.goodsEls.forEach(function (goodsEl) {
                goodsEl.addEventListener('click', function () {
                    goodsEl.className.indexOf("page-manage-goods-check") != -1 ? goodsEl.className = 'page-manage-goods' : goodsEl.className = 'page-manage-goods page-manage-goods-check';
                });
            });
        }, function () { }, this.parentFailHandel);
    };
    PageManage.prototype.initViewEvent = function (pageToggleEl, pageRotationEl, pageAdEl, pageContainerEl) {
        var _this = this;
        var pageToggleHtml = pageToggleEl.innerHTML;
        var pageRotationHtml = pageRotationEl.innerHTML;
        var pageAdHtml = pageAdEl.innerHTML;
        var pageContainerHtml = pageContainerEl.innerHTML;
        new Ajax(PageManageUrls.getToggleViewUrl).config().sendEmpty().result(function (data) {
            data.forEach(function (pageToggle) {
                pageToggleHtml += _this.pageToggleTemplate(pageToggle.toggleViewId, pageToggle.titleName, _this.selectCheckTemplate(pageToggle.goodsIds));
            });
            pageToggleEl.innerHTML = pageToggleHtml;
            var toggleAddBth = document.querySelector('.page-toggle-manage-box .page-son-manage-add-bth');
            var toggleAddEl = document.querySelector('.page-manage-add-toggle');
            toggleAddBth.addEventListener('click', function () {
                CustomComponentBoxControl.open(toggleAddEl, function () {
                    _this.cancelSelectGoods();
                });
            });
            var toggleSelectInputBoxEls = document.querySelectorAll('.page-toggle-manage-box .page-select-input-box');
            toggleSelectInputBoxEls.forEach(function (inputBoxEl) {
                inputBoxEl.addEventListener('click', function () {
                    _this.currentSelectInputEl = inputBoxEl;
                    _this.openPublicSelectInput(inputBoxEl);
                });
            });
            var fixSubmitBtnEls = document.querySelectorAll('.page-toggle-manage-box .page-manage-box-handel-fix');
            var fixInputEls = document.querySelectorAll('.page-toggle-manage-box .page-manage-box-content-input > input');
            fixSubmitBtnEls.forEach(function (fixSubmitBtnEl, index) {
                fixSubmitBtnEl.addEventListener('click', function () {
                    DelTipsBoxControl.open('你确定要修改这个标题界面数据吗？', function () {
                        var toggleIdEl = fixSubmitBtnEl.parentElement.parentElement.firstElementChild;
                        var fixInputEl = fixInputEls[index];
                        var fixSelectCheckValueEls = toggleSelectInputBoxEls[index].children;
                        _this.fixToggleView(toggleIdEl, fixInputEl, fixSelectCheckValueEls);
                    });
                });
            });
            var delSubmitBtnEls = document.querySelectorAll('.page-toggle-manage-box .page-manage-box-handel-del');
            delSubmitBtnEls.forEach(function (delSubmitBtnEl) {
                delSubmitBtnEl.addEventListener('click', function () {
                    var toggleIdEl = delSubmitBtnEl.parentElement.parentElement.firstElementChild;
                    DelTipsBoxControl.open('你确定要删除这个标题界面数据吗？', function () {
                        _this.delToggleView(toggleIdEl.innerText);
                    });
                });
            });
        }, function () { }, this.parentFailHandel);
        new Ajax(PageManageUrls.getRotationChartwUrl).config().sendEmpty().result(function (data) {
            pageRotationHtml += _this.pageCheckTemplate(_this.selectCheckTemplate(data.goodsIds));
            pageRotationEl.innerHTML = pageRotationHtml;
            var toggleSelectInputBoxEl = document.querySelector('.page-rotation-manage-box .page-select-input-box');
            toggleSelectInputBoxEl.addEventListener('click', function () {
                _this.currentSelectInputEl = toggleSelectInputBoxEl;
                _this.openPublicSelectInput(toggleSelectInputBoxEl);
            });
            var fixSubmitBtn = document.querySelector('.page-rotation-manage-box .page-manage-box-handel-fix');
            fixSubmitBtn.addEventListener('click', function () {
                DelTipsBoxControl.open('你确定要修改这个轮播图界面数据吗？', function () {
                    _this.fixRotationChart(toggleSelectInputBoxEl.children);
                });
            });
        }, function () { }, this.parentFailHandel);
        new Ajax(PageManageUrls.getGoodsAdUrl).config().sendEmpty().result(function (data) {
            pageAdHtml += _this.pageCheckTemplate(_this.selectCheckTemplate(data.goodsIds));
            pageAdEl.innerHTML = pageAdHtml;
            var toggleSelectInputBoxEl = document.querySelector('.page-ad-manage-box .page-select-input-box');
            toggleSelectInputBoxEl.addEventListener('click', function () {
                _this.currentSelectInputEl = toggleSelectInputBoxEl;
                _this.openPublicSelectInput(toggleSelectInputBoxEl);
            });
            var fixSubmitBtn = document.querySelector('.page-ad-manage-box .page-manage-box-handel-fix');
            fixSubmitBtn.addEventListener('click', function () {
                DelTipsBoxControl.open('你确定要修改这个广告界面数据吗？', function () {
                    _this.fixGoodsAd(toggleSelectInputBoxEl.children);
                });
            });
        }, function () { }, this.parentFailHandel);
        new Ajax(PageManageUrls.getContainerModuleUrl).config().sendEmpty().result(function (data) {
            data.forEach(function (container) {
                pageContainerHtml += _this.pageContainerTemplate(container.containerModuleId, container.titleName, _this.selectCheckTemplate([container.specialGoodsInfo]), _this.selectCheckTemplate(container.topGoodsInfo), _this.selectCheckTemplate(container.bottomGoodsInfo));
            });
            pageContainerEl.innerHTML = pageContainerHtml;
            var containerAddBth = document.querySelector('.page-container-manage-box .page-son-manage-add-bth');
            var containerEl = document.querySelector('.page-manage-add-container');
            containerAddBth.addEventListener('click', function () {
                CustomComponentBoxControl.open(containerEl, function () {
                    _this.cancelSelectGoods();
                });
            });
            var containerSelectInputBoxEls = document.querySelectorAll('.page-container-manage-box .page-select-input-box');
            containerSelectInputBoxEls.forEach(function (inputBoxEl) {
                inputBoxEl.addEventListener('click', function () {
                    _this.currentSelectInputEl = inputBoxEl;
                    _this.openPublicSelectInput(inputBoxEl);
                });
            });
            var fixSubmitBtnEls = document.querySelectorAll('.page-container-manage-box .page-manage-box-handel-fix');
            var fixInputEls = document.querySelectorAll('.page-container-manage-box .page-manage-box-content-input > input');
            fixSubmitBtnEls.forEach(function (fixSubmitBtnEl, index) {
                fixSubmitBtnEl.addEventListener('click', function () {
                    DelTipsBoxControl.open('你确定要修改这个广告界面数据吗？', function () {
                        var containerIdEl = fixSubmitBtnEl.parentElement.parentElement.firstElementChild;
                        var fixInputEl = fixInputEls[index];
                        var selectBoxIndex = index * 3;
                        var fixSelectLeftValueEls = containerSelectInputBoxEls[selectBoxIndex].children;
                        var fixSelectTopValueEls = containerSelectInputBoxEls[selectBoxIndex + 1].children;
                        var fixSelectBottomValueEls = containerSelectInputBoxEls[selectBoxIndex + 2].children;
                        _this.fixContainerModule(containerIdEl, fixInputEl, fixSelectLeftValueEls, fixSelectTopValueEls, fixSelectBottomValueEls);
                    });
                });
            });
            var delSubmitBtnEls = document.querySelectorAll('.page-container-manage-box .page-manage-box-handel-del');
            delSubmitBtnEls.forEach(function (delSubmitBtnEl) {
                delSubmitBtnEl.addEventListener('click', function () {
                    var containerIdEl = delSubmitBtnEl.parentElement.parentElement.firstElementChild;
                    DelTipsBoxControl.open('你确定要删除这个主要界面数据吗？', function () {
                        _this.delContainerModule(containerIdEl.innerText);
                    });
                });
            });
        }, function () { }, this.parentFailHandel);
    };
    PageManage.prototype.handelTabHeight = function (tabPageBoxEls, index) {
        if (index === void 0) { index = 0; }
        tabPageBoxEls.forEach(function (tabPageBoxEl) {
            tabPageBoxEl.style.height = '0';
        });
        tabPageBoxEls[index].style.height = 'auto';
    };
    PageManage.prototype.handelTabEvent = function (tabBthEls, tabViewEl, tabPageBoxEls) {
        var _this = this;
        tabBthEls.forEach(function (tabBthEl, index) {
            tabBthEl.addEventListener('click', function () {
                tabBthEls.forEach(function (v) {
                    v.className = 'page-manage-tab-button';
                });
                _this.handelTabHeight(tabPageBoxEls, index);
                tabBthEl.className = tabBthEl.className + ' page-manage-tab-button-check';
                tabViewEl.style.transform = "translateX(".concat((-25) * index, "%)");
            });
        });
    };
    PageManage.prototype.goodsSelectTemplate = function (goodsId, goodsImg, goodsName, check) {
        if (check === void 0) { check = false; }
        return "\n        <div class=\"page-manage-goods ".concat(check ? 'page-manage-goods-check' : '', "\">\n            <div class=\"page-manage-goods-id\">").concat(goodsId, "</div>\n            <div class=\"page-manage-goods-img\">\n                <img src=\"").concat(goodsImg, "\" alt=\"\u56FE\u7247\u8D44\u6E90\u4E22\u5931...\">\n            </div>\n            <div class=\"page-manage-goods-name\">").concat(goodsName, "</div>\n        </div>\n        ");
    };
    PageManage.prototype.selectCheckTemplate = function (goodsIds) {
        var template = '';
        goodsIds.forEach(function (goodsId) {
            template += "<div class=\"page-select-check-value\">".concat(goodsId, "</div>");
        });
        return template;
    };
    PageManage.prototype.pageToggleTemplate = function (toggleViewId, titleName, selectCheckTemplate) {
        return "\n        <div class=\"page-toggle-manage\">\n            <div class=\"page-manage-box-value-box\">\n                <div class=\"page-manage-hidden-id\">".concat(toggleViewId, "</div>\n                <div class=\"page-manage-box-value\">\n                    <div class=\"page-manage-box-content-title\">\u6807\u9898\u540D\u5B57:</div>\n                    <div class=\"page-manage-box-content-input-box\">\n                        <div class=\"page-manage-box-content-input\">\n                            <input type=\"text\" value='").concat(titleName, "'>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"page-manage-box-value\">\n                    <div class=\"page-manage-box-content-title\">\u9009\u62E9\u7684\u5546\u54C1:</div>\n                    <div class=\"page-manage-box-content-value page-select-input-box\">\n                        ").concat(selectCheckTemplate, "\n                    </div>\n                </div>\n                <div class=\"page-manage-box-handel\">\n                    <button class=\"page-manage-box-handel-fix\">\u4FEE\u6539</button>\n                    <button class=\"page-manage-box-handel-del\">\u5220\u9664</button>\n                </div>\n            </div>\n        </div>\n        ");
    };
    PageManage.prototype.pageContainerTemplate = function (containerModuleId, titleName, leftSelectCheckTemplate, topSelectCheckTemplate, bottomSelectCheckTemplate) {
        return "\n        <div class=\"page-toggle-manage\">\n            <div class=\"page-manage-box-value-box\">\n                <div class=\"page-manage-hidden-id\">".concat(containerModuleId, "</div>\n                <div class=\"page-manage-box-value\">\n                    <div class=\"page-manage-box-content-title\">\u6807\u9898\u540D\u5B57:</div>\n                    <div class=\"page-manage-box-content-input-box\">\n                        <div class=\"page-manage-box-content-input\">\n                            <input type=\"text\" value='").concat(titleName, "'>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"page-manage-box-value\">\n                    <div class=\"page-manage-box-content-title\">\u5DE6\u4FA7\u9AD8\u7684\u5546\u54C1:</div>\n                    <div class=\"page-manage-box-content-value page-select-input-box\">\n                        ").concat(leftSelectCheckTemplate, "\n                    </div>\n                </div>\n                <div class=\"page-manage-box-value\">\n                    <div class=\"page-manage-box-content-title\">\u4E0A\u6392\u5546\u54C1:</div>\n                    <div class=\"page-manage-box-content-value page-select-input-box\">\n                        ").concat(topSelectCheckTemplate, "\n                    </div>\n                </div>\n                <div class=\"page-manage-box-value\">\n                    <div class=\"page-manage-box-content-title\">\u4E0B\u6392\u5546\u54C1:</div>\n                    <div class=\"page-manage-box-content-value page-select-input-box\">\n                        ").concat(bottomSelectCheckTemplate, "\n                    </div>\n                </div>\n                <div class=\"page-manage-box-handel\">\n                    <button class=\"page-manage-box-handel-fix\">\u4FEE\u6539</button>\n                    <button class=\"page-manage-box-handel-del\">\u5220\u9664</button>\n                </div>\n            </div>\n        </div>\n        ");
    };
    PageManage.prototype.pageCheckTemplate = function (selectCheckTemplate) {
        return "\n        <div class=\"page-toggle-manage\">\n            <div class=\"page-manage-box-value-box\">\n                <div class=\"page-manage-box-value\">\n                    <div class=\"page-manage-box-content-title\">\u9009\u62E9\u7684\u5546\u54C1:</div>\n                    <div class=\"page-manage-box-content-value page-select-input-box\">\n                        ".concat(selectCheckTemplate, "\n                    </div>\n                </div>\n                <div class=\"page-manage-box-handel\">\n                    <button class=\"page-manage-box-handel-fix\">\u4FEE\u6539</button>\n                </div>\n            </div>\n        </div>\n        ");
    };
    return PageManage;
}(ParentMain));
PublicMainView.run(new PageManage(3));
