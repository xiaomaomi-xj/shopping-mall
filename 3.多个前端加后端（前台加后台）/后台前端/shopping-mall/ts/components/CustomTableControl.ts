import { ImgComponentBoxControl } from "./ImgComponentBoxControl.js";
//表格数据类型
interface CustomTableType {
    title: Array<string>;
    content: Array<Array<string>>;
}

//自定义表格控制
class CustomTableControl {
    private static parentEl: HTMLElement;
    private static customTableEl = document.createElement('div');
    private static isOpenUpdate: boolean;
    private static updateFun: Function;
    private static isOpenDelete: boolean;
    private static deleteFun: Function;
    private static isAdd: boolean;
    private static addFun: Function;
    private static isQuery: boolean;
    private static queryFun: Function;
    //存放的表格副本
    private static customTableBakNode: Node;
    //需要查询的字段
    private static queryFiledIndexs: Array<number>;
    //表格是否含有图片和页码
    private static haveImg: boolean = false;
    private static havePageNum: boolean = false;
    //页码事件
    private static pageNumFun: Function;
    //记录最大页数
    private static maxPageNum: number;
    //查询框的下标和值
    private static inputIndex: number = -1;
    private static inputValue: string = '';
    //数据
    private static tableData: CustomTableType;
    static {
        this.customTableEl.className = 'custom-table';
        this.isOpenDelete = false;
        this.isOpenUpdate = false;
    }
    //查询重新生成表，值复现
    public static configValue(index: number = -1, value: string = '') {
        this.inputIndex = index;
        this.inputValue = value;
        return this;
    }
    //配置是否带有图片，和页码
    public static configHaveImgOrHavepageNum(haveImg: boolean = false, havePageNum: boolean = false, pageNumFun: Function = () => { }) {
        this.haveImg = haveImg;
        this.havePageNum = havePageNum;
        this.pageNumFun = pageNumFun;
        return this;
    }
    //配置表（是否启用添加和查询），默认都是不打开,接受下标，那些字段当查询条件,除去序号那一列，从1开始数
    public static configQueryAndAdd(addState: boolean, addFun: Function, queryState: boolean, queryFun: Function, ...queryFileds: Array<number>) {
        this.isAdd = addState;
        this.addFun = addFun;
        this.isQuery = queryState;
        this.queryFun = queryFun;
        const checkFileds = queryFileds.filter((item, index) => item > 0 && queryFileds.indexOf(item) === index);
        this.queryFiledIndexs = checkFileds;
        return this;
    }
    //配置表(是否启用修改删除),默认都是不打开
    public static configUpdateAndDelete(updateState: boolean, updateFun: Function, deleteState: boolean, deleteFun: Function) {
        this.isOpenUpdate = updateState;
        this.updateFun = updateFun;
        this.isOpenDelete = deleteState;
        this.deleteFun = deleteFun;
        return this;
    }
    //创建表
    public static create(parentEl: HTMLElement, beforeEl: HTMLElement | null, data: CustomTableType, imgIndexs: Array<number>, currentPageNum: number = -1) {
        this.tableData = data;

        //页码加图片
        if (this.haveImg && this.havePageNum) {
            this.createHavePageNum(parentEl, beforeEl, data, imgIndexs, currentPageNum);
            return;
        }

        //只要页码
        if (this.havePageNum) {
            this.createHavePageNum(parentEl, beforeEl, data, [], currentPageNum);
            return;
        }

        //只要图片
        if (this.haveImg) {
            this.createHaveImg(parentEl, beforeEl, data, imgIndexs);
            return;
        }

        //什么都不要的
        this.createPlain(parentEl, beforeEl, data);
    }
    //创建普通的表
    private static createPlain(parentEl: HTMLElement, beforeEl: HTMLElement | null, data: CustomTableType) {
        //创建之前先清空
        this.customTableEl.innerHTML = '';
        this.createTable(data);
        //每次都复制一份
        this.parentEl = parentEl;
        this.customTableBakNode = this.customTableEl.cloneNode(true)
        parentEl.insertBefore(this.customTableBakNode, beforeEl);
        //启动函数
        this.buttoEventHandel(data.content);
    }
    //创建带图片的表,接受一个下标，表示那一列需要用图片
    private static createHaveImg(parentEl: HTMLElement, beforeEl: HTMLElement | null, data: CustomTableType, indexs: Array<number>) {
        //创建之前先清空
        this.customTableEl.innerHTML = '';
        this.createTable(data, indexs);
        //每次都复制一份
        this.parentEl = parentEl;
        this.customTableBakNode = this.customTableEl.cloneNode(true)
        parentEl.insertBefore(this.customTableBakNode, beforeEl);
        //启动函数
        this.buttoEventHandel(data.content);
    }
    //创建带页码的表
    private static createHavePageNum(parentEl: HTMLElement, beforeEl: HTMLElement | null, data: CustomTableType, indexs: Array<number>, currentPageNum: number = 1) {
        //先处理数据
        const tableData: CustomTableType = {
            title: data.title,
            content: data.content.slice(((currentPageNum - 1) * 10), ((currentPageNum - 1) * 10) + 10)
        };
        //创建之前先清空
        this.customTableEl.innerHTML = '';
        this.createTable(tableData, indexs, currentPageNum);
        //每次都复制一份
        this.parentEl = parentEl;
        this.customTableBakNode = this.customTableEl.cloneNode(true)
        parentEl.insertBefore(this.customTableBakNode, beforeEl);
        //启动函数
        this.buttoEventHandel(tableData.content);
    }
    //创建表格
    private static createTable(data: CustomTableType, indexs: Array<number> = [], currentPageNum: number = 1) {
        if (this.isAdd || this.isQuery) {
            const headBox = document.createElement('div');
            headBox.className = 'custom-table-header-box';
            if (this.isAdd) {
                const addButton = document.createElement('div');
                addButton.className = 'custom-table-header-box-add-button';
                addButton.innerHTML = '增加<span class="custom-table-header-box-add-button-span">+</span>';
                headBox.appendChild(addButton);
            }
            if (this.isQuery) {
                const queryBox = document.createElement('div');
                queryBox.className = 'custom-table-header-box-query-box';
                //字段下标
                this.queryFiledIndexs.forEach(v => {
                    const spanEl = document.createElement('span');
                    spanEl.className = 'custom-table-header-box-query-box-title';
                    spanEl.innerText = data.title[v - 1] + ":";
                    const inputBox = document.createElement('div')
                    inputBox.className = 'custom-table-header-box-query-box-input-box';
                    const inputEl = document.createElement('input');
                    inputEl.type = 'text';
                    inputEl.name = v - 1 + '';
                    if (this.inputIndex != -1 && this.inputIndex == v - 1) {
                        inputEl.value = this.inputValue;
                    }
                    inputBox.appendChild(inputEl);
                    const queryBthEl = document.createElement('div');
                    queryBthEl.innerText = '查询';
                    queryBthEl.className = 'custom-table-header-box-query-box-query-button';
                    const resetBthEl = document.createElement('div');
                    resetBthEl.innerText = '重置';
                    resetBthEl.className = 'custom-table-header-box-query-box-reset-button';
                    queryBox.appendChild(spanEl);
                    queryBox.appendChild(inputBox);
                    queryBox.appendChild(queryBthEl);
                    queryBox.appendChild(resetBthEl);
                });
                headBox.appendChild(queryBox);
            }
            this.customTableEl.appendChild(headBox);
        }
        const titllEl = document.createElement('div');
        titllEl.className = 'custom-table-title';
        const itemNum = document.createElement('div');
        itemNum.className = 'item-num';
        itemNum.innerText = '序号';
        const itemBox = document.createElement('div');
        itemBox.className = 'item-column-box';
        //判断是否开启了修改或删除功能
        if (this.isOpenDelete || this.isOpenUpdate) {
            data.title.push('操作');
            const maxNum = data.title.length;
            data.content.forEach(v => {
                const dValue = maxNum - v.length;
                for (let i = 0; i < dValue - 1; i++) {
                    v.push('');
                }
                if (this.isOpenDelete || this.isOpenUpdate) {
                    if (this.isOpenUpdate && this.isOpenDelete) {
                        v.push('<button class="custom-table-update-button">修改</button><button class="custom-table-delete-button">删除</button>');
                    } else if (this.isOpenUpdate) {
                        v.push('<button class="custom-table-update-button">修改</button>');
                    } else {
                        v.push('<button class="custom-table-delete-button">删除</button>');
                    }
                }
            });
        }
        const cloumnNum = data.title.length;
        //生成表头标题
        data.title.forEach(v => {
            const itemColumn = document.createElement('div');
            itemColumn.className = 'item-column';
            itemColumn.style.width = Math.floor(100 / cloumnNum) + "%";
            itemColumn.innerText = v;
            itemBox.appendChild(itemColumn);
        });
        titllEl.appendChild(itemNum);
        titllEl.appendChild(itemBox);
        this.customTableEl.appendChild(titllEl);
        //生成表数据
        data.content.forEach((v, k) => {
            const rowEl = document.createElement('div');
            rowEl.className = 'custom-table-row';
            const rowNumeL = document.createElement('div');
            rowNumeL.className = 'item-num';
            rowNumeL.innerText = (k + 1).toString();
            const boxEl = document.createElement('div');
            boxEl.className = 'item-column-box';
            v.forEach((value, index) => {
                const itemEl = document.createElement('div');
                itemEl.className = 'item-column';
                itemEl.style.width = Math.floor(100 / cloumnNum) + "%";
                if (indexs.indexOf(index) == -1) {
                    const span = document.createElement('span');
                    span.innerText = value;
                    itemEl.appendChild(span);
                } else {
                    if (value == null || value == undefined || value.trim().length <= 0) {
                        const span = document.createElement('span');
                        span.innerText = value;
                        itemEl.appendChild(span);
                    } else {
                        const img = document.createElement('img') as HTMLImageElement;
                        img.src = value;
                        itemEl.appendChild(img);
                    }
                }
                if (index == v.length - 1) {
                    if (this.isOpenDelete || this.isOpenUpdate) {
                        itemEl.innerHTML = value;
                    }
                }
                boxEl.appendChild(itemEl);
            });
            rowEl.appendChild(rowNumeL);
            rowEl.appendChild(boxEl);
            this.customTableEl.appendChild(rowEl);
        });
        if (this.havePageNum) {
            const pageNumberBoxEl = document.createElement('div');
            pageNumberBoxEl.className = 'page-number-box';
            const startPageNumEl = document.createElement('div');
            startPageNumEl.className = 'page-number-start';
            startPageNumEl.innerText = '第一页';
            pageNumberBoxEl.appendChild(startPageNumEl);
            const pageNum = Math.ceil(this.tableData.content.length / 10);
            this.maxPageNum = pageNum;
            if (pageNum < 5) {
                for (let i = 1; i <= pageNum; i++) {
                    const pageNumEl = document.createElement('div');
                    pageNumEl.className = 'page-number ';
                    pageNumEl.innerText = i + '';
                    if (i == currentPageNum) {
                        pageNumEl.className += 'check';
                    }
                    pageNumberBoxEl.appendChild(pageNumEl);
                }
            } else {
                if (currentPageNum > 0) {
                    //差值
                    let leaveVl = pageNum - currentPageNum;
                    //起始
                    let startVl = 0;
                    //结束
                    let endVl = 0;
                    if (leaveVl > 3) {
                        startVl = currentPageNum;
                        endVl = currentPageNum + 4;
                    } else {
                        endVl = pageNum;
                        startVl = currentPageNum - (3 - leaveVl + 1);
                    }
                    for (let i = startVl; i <= endVl; i++) {
                        const pageNumEl = document.createElement('div');
                        pageNumEl.className = 'page-number ';
                        pageNumEl.innerText = i + '';
                        if (i == currentPageNum) {
                            pageNumEl.className += 'check';
                        }
                        pageNumberBoxEl.appendChild(pageNumEl);
                    }
                } else {
                    for (let i = 1; i <= 5; i++) {
                        const pageNumEl = document.createElement('div');
                        pageNumEl.className = 'page-number';
                        pageNumEl.innerText = i + '';
                        if (i == currentPageNum) {
                            pageNumEl.className += 'check';
                        }
                        pageNumberBoxEl.appendChild(pageNumEl);
                    }
                }
            }
            const endPageNumEl = document.createElement('div');
            endPageNumEl.className = 'page-number-end';
            endPageNumEl.innerText = '最后一页';
            pageNumberBoxEl.appendChild(endPageNumEl);
            this.customTableEl.appendChild(pageNumberBoxEl);
        }
    }
    //触发按钮事件
    private static buttoEventHandel(data: Array<Array<string>>) {
        if (this.haveImg) {
            setTimeout(() => {
                const imgs = document.querySelectorAll('.item-column > img') as unknown as Array<HTMLImageElement>;
                imgs.forEach(img => {
                    img.addEventListener('click', () => {
                        ImgComponentBoxControl.open(img.src);
                    });
                });
            });
        }
        if (this.isOpenUpdate) {
            setTimeout(() => {
                const updateEl = document.querySelectorAll('.custom-table-update-button');
                updateEl.forEach((el, index) => {
                    el.addEventListener('click', () => {
                        this.updateFun(data[index]);
                    });
                });
            });
        }
        if (this.isOpenDelete) {
            setTimeout(() => {
                const deleteEl = document.querySelectorAll('.custom-table-delete-button');
                deleteEl.forEach((el, index) => {
                    el.addEventListener('click', () => {
                        this.deleteFun(data[index]);
                    });
                });
            });
        }
        if (this.isAdd) {
            setTimeout(() => {
                const addEl = document.querySelector('.custom-table-header-box-add-button') as HTMLElement;
                addEl.addEventListener('click', () => {
                    this.addFun();
                });
            });
        }
        if (this.isQuery) {
            setTimeout(() => {
                const inputs = document.querySelectorAll('.custom-table-header-box-query-box-input-box > input') as unknown as Array<HTMLInputElement>;
                const queryBtns = document.querySelectorAll('.custom-table-header-box-query-box-query-button');
                const resetEls = document.querySelectorAll('.custom-table-header-box-query-box-reset-button');
                //查询
                queryBtns.forEach((v, k) => {
                    v.addEventListener('click', () => {
                        if (inputs[k].value.trim().length != 0) {
                            //删掉原先的
                            this.parentEl.removeChild(this.customTableBakNode);
                            this.queryFun(inputs[k].name, inputs[k].value);
                        }
                    });
                });
                //重置
                resetEls.forEach(v => {
                    v.addEventListener('click', () => {
                        //删掉原先的
                        this.parentEl.removeChild(this.customTableBakNode);
                        this.queryFun(-1, '');
                    });
                });
            });
        }
        if (this.havePageNum) {
            setTimeout(() => {
                const pageNumEls = document.querySelectorAll('.page-number-box > .page-number') as unknown as Array<HTMLElement>;
                const startPageNumEl = document.querySelector('.page-number-box > .page-number-start') as HTMLElement;
                const endPageNumEl = document.querySelector('.page-number-box > .page-number-end') as HTMLElement;
                startPageNumEl.addEventListener('click', () => {
                    //删掉原先的
                    this.parentEl.removeChild(this.customTableBakNode);
                    this.pageNumFun(1);
                });
                endPageNumEl.addEventListener('click', () => {
                    //删掉原先的
                    this.parentEl.removeChild(this.customTableBakNode);
                    this.pageNumFun(this.maxPageNum);
                });
                pageNumEls.forEach(v => {
                    v.addEventListener('click', () => {
                        //删掉原先的
                        this.parentEl.removeChild(this.customTableBakNode);
                        this.pageNumFun(parseInt(v.innerText));
                    });
                });
            });
        }
    }
}

export {
    CustomTableControl
};