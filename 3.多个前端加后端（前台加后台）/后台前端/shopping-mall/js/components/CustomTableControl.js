var _this = this;
import { ImgComponentBoxControl } from "./ImgComponentBoxControl.js";
var CustomTableControl = (function () {
    function CustomTableControl() {
    }
    CustomTableControl.configValue = function (index, value) {
        if (index === void 0) { index = -1; }
        if (value === void 0) { value = ''; }
        this.inputIndex = index;
        this.inputValue = value;
        return this;
    };
    CustomTableControl.configHaveImgOrHavepageNum = function (haveImg, havePageNum, pageNumFun) {
        if (haveImg === void 0) { haveImg = false; }
        if (havePageNum === void 0) { havePageNum = false; }
        if (pageNumFun === void 0) { pageNumFun = function () { }; }
        this.haveImg = haveImg;
        this.havePageNum = havePageNum;
        this.pageNumFun = pageNumFun;
        return this;
    };
    CustomTableControl.configQueryAndAdd = function (addState, addFun, queryState, queryFun) {
        var queryFileds = [];
        for (var _i = 4; _i < arguments.length; _i++) {
            queryFileds[_i - 4] = arguments[_i];
        }
        this.isAdd = addState;
        this.addFun = addFun;
        this.isQuery = queryState;
        this.queryFun = queryFun;
        var checkFileds = queryFileds.filter(function (item, index) { return item > 0 && queryFileds.indexOf(item) === index; });
        this.queryFiledIndexs = checkFileds;
        return this;
    };
    CustomTableControl.configUpdateAndDelete = function (updateState, updateFun, deleteState, deleteFun) {
        this.isOpenUpdate = updateState;
        this.updateFun = updateFun;
        this.isOpenDelete = deleteState;
        this.deleteFun = deleteFun;
        return this;
    };
    CustomTableControl.create = function (parentEl, beforeEl, data, imgIndexs, currentPageNum) {
        if (currentPageNum === void 0) { currentPageNum = -1; }
        this.tableData = data;
        if (this.haveImg && this.havePageNum) {
            this.createHavePageNum(parentEl, beforeEl, data, imgIndexs, currentPageNum);
            return;
        }
        if (this.havePageNum) {
            this.createHavePageNum(parentEl, beforeEl, data, [], currentPageNum);
            return;
        }
        if (this.haveImg) {
            this.createHaveImg(parentEl, beforeEl, data, imgIndexs);
            return;
        }
        this.createPlain(parentEl, beforeEl, data);
    };
    CustomTableControl.createPlain = function (parentEl, beforeEl, data) {
        this.customTableEl.innerHTML = '';
        this.createTable(data);
        this.parentEl = parentEl;
        this.customTableBakNode = this.customTableEl.cloneNode(true);
        parentEl.insertBefore(this.customTableBakNode, beforeEl);
        this.buttoEventHandel(data.content);
    };
    CustomTableControl.createHaveImg = function (parentEl, beforeEl, data, indexs) {
        this.customTableEl.innerHTML = '';
        this.createTable(data, indexs);
        this.parentEl = parentEl;
        this.customTableBakNode = this.customTableEl.cloneNode(true);
        parentEl.insertBefore(this.customTableBakNode, beforeEl);
        this.buttoEventHandel(data.content);
    };
    CustomTableControl.createHavePageNum = function (parentEl, beforeEl, data, indexs, currentPageNum) {
        if (currentPageNum === void 0) { currentPageNum = 1; }
        var tableData = {
            title: data.title,
            content: data.content.slice(((currentPageNum - 1) * 10), ((currentPageNum - 1) * 10) + 10)
        };
        this.customTableEl.innerHTML = '';
        this.createTable(tableData, indexs, currentPageNum);
        this.parentEl = parentEl;
        this.customTableBakNode = this.customTableEl.cloneNode(true);
        parentEl.insertBefore(this.customTableBakNode, beforeEl);
        this.buttoEventHandel(tableData.content);
    };
    CustomTableControl.createTable = function (data, indexs, currentPageNum) {
        var _this = this;
        if (indexs === void 0) { indexs = []; }
        if (currentPageNum === void 0) { currentPageNum = 1; }
        if (this.isAdd || this.isQuery) {
            var headBox = document.createElement('div');
            headBox.className = 'custom-table-header-box';
            if (this.isAdd) {
                var addButton = document.createElement('div');
                addButton.className = 'custom-table-header-box-add-button';
                addButton.innerHTML = '增加<span class="custom-table-header-box-add-button-span">+</span>';
                headBox.appendChild(addButton);
            }
            if (this.isQuery) {
                var queryBox_1 = document.createElement('div');
                queryBox_1.className = 'custom-table-header-box-query-box';
                this.queryFiledIndexs.forEach(function (v) {
                    var spanEl = document.createElement('span');
                    spanEl.className = 'custom-table-header-box-query-box-title';
                    spanEl.innerText = data.title[v - 1] + ":";
                    var inputBox = document.createElement('div');
                    inputBox.className = 'custom-table-header-box-query-box-input-box';
                    var inputEl = document.createElement('input');
                    inputEl.type = 'text';
                    inputEl.name = v - 1 + '';
                    if (_this.inputIndex != -1 && _this.inputIndex == v - 1) {
                        inputEl.value = _this.inputValue;
                    }
                    inputBox.appendChild(inputEl);
                    var queryBthEl = document.createElement('div');
                    queryBthEl.innerText = '查询';
                    queryBthEl.className = 'custom-table-header-box-query-box-query-button';
                    var resetBthEl = document.createElement('div');
                    resetBthEl.innerText = '重置';
                    resetBthEl.className = 'custom-table-header-box-query-box-reset-button';
                    queryBox_1.appendChild(spanEl);
                    queryBox_1.appendChild(inputBox);
                    queryBox_1.appendChild(queryBthEl);
                    queryBox_1.appendChild(resetBthEl);
                });
                headBox.appendChild(queryBox_1);
            }
            this.customTableEl.appendChild(headBox);
        }
        var titllEl = document.createElement('div');
        titllEl.className = 'custom-table-title';
        var itemNum = document.createElement('div');
        itemNum.className = 'item-num';
        itemNum.innerText = '序号';
        var itemBox = document.createElement('div');
        itemBox.className = 'item-column-box';
        if (this.isOpenDelete || this.isOpenUpdate) {
            data.title.push('操作');
            var maxNum_1 = data.title.length;
            data.content.forEach(function (v) {
                var dValue = maxNum_1 - v.length;
                for (var i = 0; i < dValue - 1; i++) {
                    v.push('');
                }
                if (_this.isOpenDelete || _this.isOpenUpdate) {
                    if (_this.isOpenUpdate && _this.isOpenDelete) {
                        v.push('<button class="custom-table-update-button">修改</button><button class="custom-table-delete-button">删除</button>');
                    }
                    else if (_this.isOpenUpdate) {
                        v.push('<button class="custom-table-update-button">修改</button>');
                    }
                    else {
                        v.push('<button class="custom-table-delete-button">删除</button>');
                    }
                }
            });
        }
        var cloumnNum = data.title.length;
        data.title.forEach(function (v) {
            var itemColumn = document.createElement('div');
            itemColumn.className = 'item-column';
            itemColumn.style.width = Math.floor(100 / cloumnNum) + "%";
            itemColumn.innerText = v;
            itemBox.appendChild(itemColumn);
        });
        titllEl.appendChild(itemNum);
        titllEl.appendChild(itemBox);
        this.customTableEl.appendChild(titllEl);
        data.content.forEach(function (v, k) {
            var rowEl = document.createElement('div');
            rowEl.className = 'custom-table-row';
            var rowNumeL = document.createElement('div');
            rowNumeL.className = 'item-num';
            rowNumeL.innerText = (k + 1).toString();
            var boxEl = document.createElement('div');
            boxEl.className = 'item-column-box';
            v.forEach(function (value, index) {
                var itemEl = document.createElement('div');
                itemEl.className = 'item-column';
                itemEl.style.width = Math.floor(100 / cloumnNum) + "%";
                if (indexs.indexOf(index) == -1) {
                    var span = document.createElement('span');
                    span.innerText = value;
                    itemEl.appendChild(span);
                }
                else {
                    if (value == null || value == undefined || value.trim().length <= 0) {
                        var span = document.createElement('span');
                        span.innerText = value;
                        itemEl.appendChild(span);
                    }
                    else {
                        var img = document.createElement('img');
                        img.src = value;
                        itemEl.appendChild(img);
                    }
                }
                if (index == v.length - 1) {
                    if (_this.isOpenDelete || _this.isOpenUpdate) {
                        itemEl.innerHTML = value;
                    }
                }
                boxEl.appendChild(itemEl);
            });
            rowEl.appendChild(rowNumeL);
            rowEl.appendChild(boxEl);
            _this.customTableEl.appendChild(rowEl);
        });
        if (this.havePageNum) {
            var pageNumberBoxEl = document.createElement('div');
            pageNumberBoxEl.className = 'page-number-box';
            var startPageNumEl = document.createElement('div');
            startPageNumEl.className = 'page-number-start';
            startPageNumEl.innerText = '第一页';
            pageNumberBoxEl.appendChild(startPageNumEl);
            var pageNum = Math.ceil(this.tableData.content.length / 10);
            this.maxPageNum = pageNum;
            if (pageNum < 5) {
                for (var i = 1; i <= pageNum; i++) {
                    var pageNumEl = document.createElement('div');
                    pageNumEl.className = 'page-number ';
                    pageNumEl.innerText = i + '';
                    if (i == currentPageNum) {
                        pageNumEl.className += 'check';
                    }
                    pageNumberBoxEl.appendChild(pageNumEl);
                }
            }
            else {
                if (currentPageNum > 0) {
                    var leaveVl = pageNum - currentPageNum;
                    var startVl = 0;
                    var endVl = 0;
                    if (leaveVl > 3) {
                        startVl = currentPageNum;
                        endVl = currentPageNum + 4;
                    }
                    else {
                        endVl = pageNum;
                        startVl = currentPageNum - (3 - leaveVl + 1);
                    }
                    for (var i = startVl; i <= endVl; i++) {
                        var pageNumEl = document.createElement('div');
                        pageNumEl.className = 'page-number ';
                        pageNumEl.innerText = i + '';
                        if (i == currentPageNum) {
                            pageNumEl.className += 'check';
                        }
                        pageNumberBoxEl.appendChild(pageNumEl);
                    }
                }
                else {
                    for (var i = 1; i <= 5; i++) {
                        var pageNumEl = document.createElement('div');
                        pageNumEl.className = 'page-number';
                        pageNumEl.innerText = i + '';
                        if (i == currentPageNum) {
                            pageNumEl.className += 'check';
                        }
                        pageNumberBoxEl.appendChild(pageNumEl);
                    }
                }
            }
            var endPageNumEl = document.createElement('div');
            endPageNumEl.className = 'page-number-end';
            endPageNumEl.innerText = '最后一页';
            pageNumberBoxEl.appendChild(endPageNumEl);
            this.customTableEl.appendChild(pageNumberBoxEl);
        }
    };
    CustomTableControl.buttoEventHandel = function (data) {
        var _this = this;
        if (this.haveImg) {
            setTimeout(function () {
                var imgs = document.querySelectorAll('.item-column > img');
                imgs.forEach(function (img) {
                    img.addEventListener('click', function () {
                        ImgComponentBoxControl.open(img.src);
                    });
                });
            });
        }
        if (this.isOpenUpdate) {
            setTimeout(function () {
                var updateEl = document.querySelectorAll('.custom-table-update-button');
                updateEl.forEach(function (el, index) {
                    el.addEventListener('click', function () {
                        _this.updateFun(data[index]);
                    });
                });
            });
        }
        if (this.isOpenDelete) {
            setTimeout(function () {
                var deleteEl = document.querySelectorAll('.custom-table-delete-button');
                deleteEl.forEach(function (el, index) {
                    el.addEventListener('click', function () {
                        _this.deleteFun(data[index]);
                    });
                });
            });
        }
        if (this.isAdd) {
            setTimeout(function () {
                var addEl = document.querySelector('.custom-table-header-box-add-button');
                addEl.addEventListener('click', function () {
                    _this.addFun();
                });
            });
        }
        if (this.isQuery) {
            setTimeout(function () {
                var inputs = document.querySelectorAll('.custom-table-header-box-query-box-input-box > input');
                var queryBtns = document.querySelectorAll('.custom-table-header-box-query-box-query-button');
                var resetEls = document.querySelectorAll('.custom-table-header-box-query-box-reset-button');
                queryBtns.forEach(function (v, k) {
                    v.addEventListener('click', function () {
                        if (inputs[k].value.trim().length != 0) {
                            _this.parentEl.removeChild(_this.customTableBakNode);
                            _this.queryFun(inputs[k].name, inputs[k].value);
                        }
                    });
                });
                resetEls.forEach(function (v) {
                    v.addEventListener('click', function () {
                        _this.parentEl.removeChild(_this.customTableBakNode);
                        _this.queryFun(-1, '');
                    });
                });
            });
        }
        if (this.havePageNum) {
            setTimeout(function () {
                var pageNumEls = document.querySelectorAll('.page-number-box > .page-number');
                var startPageNumEl = document.querySelector('.page-number-box > .page-number-start');
                var endPageNumEl = document.querySelector('.page-number-box > .page-number-end');
                startPageNumEl.addEventListener('click', function () {
                    _this.parentEl.removeChild(_this.customTableBakNode);
                    _this.pageNumFun(1);
                });
                endPageNumEl.addEventListener('click', function () {
                    _this.parentEl.removeChild(_this.customTableBakNode);
                    _this.pageNumFun(_this.maxPageNum);
                });
                pageNumEls.forEach(function (v) {
                    v.addEventListener('click', function () {
                        _this.parentEl.removeChild(_this.customTableBakNode);
                        _this.pageNumFun(parseInt(v.innerText));
                    });
                });
            });
        }
    };
    var _a;
    _a = CustomTableControl;
    CustomTableControl.customTableEl = document.createElement('div');
    CustomTableControl.haveImg = false;
    CustomTableControl.havePageNum = false;
    CustomTableControl.inputIndex = -1;
    CustomTableControl.inputValue = '';
    (function () {
        _a.customTableEl.className = 'custom-table';
        _a.isOpenDelete = false;
        _a.isOpenUpdate = false;
    })();
    return CustomTableControl;
}());
export { CustomTableControl };
