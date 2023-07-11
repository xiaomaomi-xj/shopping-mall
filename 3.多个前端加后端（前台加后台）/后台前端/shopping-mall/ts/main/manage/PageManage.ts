import { GoodsUrls, PageManageUrls } from "../../api/ApiConstant.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { PublicMainView } from "../../components/PublicMainView.js";
import { ContainerModuleType, GoodsType, PageCheckType, ToggleViewType } from "../../interface/Types.js";
import Ajax from "../../utils/Ajax.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
import ParentMain from "../ParentMain.js";
//页面管理
class PageManage extends ParentMain {
    private currentSelectInputEl: HTMLElement = document.createElement('div');
    //避免重复获取，使用全局变量
    private goodsEls: Array<HTMLElement> = [];
    private selectPublicInputBoxEl: HTMLElement = document.createElement('div');
    private publicSelectBoxEl: HTMLElement = document.createElement('div');
    //每个管理类，只需要处理自己的事件即可
    getEvent(): Function {
        return () => {
            //拿取tab按钮
            const tabBthEls = document.querySelectorAll('.page-manage-tab-button-box-btn .page-manage-tab-button') as unknown as Array<HTMLElement>;
            //拿取切换页面
            const tabViewEl = document.querySelector('.page-manage-box-more-box') as HTMLElement;
            //四个tab页面
            const tabPageBoxEls = document.querySelectorAll('.page-manage-box-more-box-page') as unknown as Array<HTMLElement>;
            //初始tab页面
            this.handelTabHeight(tabPageBoxEls);
            //tab事件
            this.handelTabEvent(tabBthEls, tabViewEl, tabPageBoxEls);
            //各个配置组件
            const pageToggleEl = document.querySelector('.page-toggle-manage-box') as HTMLElement;
            const pageRotationEl = document.querySelector('.page-rotation-manage-box') as HTMLElement;
            const pageAdEl = document.querySelector('.page-ad-manage-box') as HTMLElement;
            const pageContainerEl = document.querySelector('.page-container-manage-box') as HTMLElement;
            //初始化商品数据选择显示
            const goodsBoxEls = document.querySelectorAll('.page-manage-goods-box') as unknown as Array<HTMLElement>;
            this.initGoodsView(goodsBoxEls);
            //初始化显示事件(因为进行异步了，其他的事件要放到对应的位置)
            this.initViewEvent(pageToggleEl, pageRotationEl, pageAdEl, pageContainerEl);
            //赋值
            this.selectPublicInputBoxEl = document.querySelector('.page-manage-select') as HTMLElement;
            this.publicSelectBoxEl = document.querySelector('.page-manage-select .page-manage-goods-box') as HTMLElement;
            //公共商品选择
            const publicGoodsBoxBthEl = document.querySelector('.page-manage-select .page-manage-add-box-btn') as HTMLElement;
            publicGoodsBoxBthEl.addEventListener('click', () => {
                const selectBoxElChildEls = this.publicSelectBoxEl.children;
                const goodsIds = [] as Array<string>;
                for (let i = 0; i < selectBoxElChildEls.length; i++) {
                    if ((selectBoxElChildEls.item(i) as HTMLElement).className.indexOf('page-manage-goods-check') != -1) {
                        goodsIds.push(((selectBoxElChildEls.item(i) as HTMLElement).firstElementChild as HTMLElement).innerText);
                    }
                }
                //因为是共用的，所以需要借助全局变量来解决不知道把结果给谁
                this.currentSelectInputEl.innerHTML = this.selectCheckTemplate(goodsIds);
                CustomComponentBoxControl.close();
            });
            //添加提交事件
            const toggleSubmitBtn = document.querySelector('.page-manage-add-toggle-box .page-manage-add-box-btn') as HTMLElement;
            const containerSubmitBtn = document.querySelector('.page-manage-add-container-box .page-manage-add-box-btn') as HTMLElement;
            const toggleSubmitInputEl = document.querySelector('.page-manage-add-toggle-box .page-manage-add-box-input-box input') as HTMLInputElement;
            const containerSubmitInputEl = document.querySelector('.page-manage-add-container-box .page-manage-add-box-input-box input') as HTMLInputElement;
            this.addSubmitEvent(toggleSubmitBtn, containerSubmitBtn, toggleSubmitInputEl, containerSubmitInputEl);
        };
    }
    //标题界面和主要界面的添加提交事件
    addSubmitEvent(toggleSubmitBtn: HTMLElement, containerSubmitBtn: HTMLElement, toggleSubmitInputEl: HTMLInputElement, containerSubmitInputEl: HTMLInputElement) {
        const toggleGoodsEls = ((toggleSubmitBtn.parentElement as HTMLElement).previousElementSibling as HTMLElement).children;
        //他本来该是三个位置，但是太拥挤了，直接一个位置选，先添加，后面还可以修改
        const containerGoodsEls = ((containerSubmitBtn.parentElement as HTMLElement).previousElementSibling as HTMLElement).children;
        toggleSubmitBtn.addEventListener('click', () => {
            const toggleGoodsIds = [] as Array<string>;
            for (let i = 0; i < toggleGoodsEls.length; i++) {
                const goodsEl = (toggleGoodsEls.item(i) as HTMLElement);
                const goodsIdEl = goodsEl.firstElementChild as HTMLElement;
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
            //提交
            new Ajax(PageManageUrls.addToggleViewUrl).config().sendJson({
                titleName: toggleSubmitInputEl.value,
                goodsIds: toggleGoodsIds
            }).result(
                () => { this.responseSuccessRefresh('添加标题界面数据成功！') },
                () => { /*默认有处理*/ },
                this.parentFailHandel
            );
        });
        containerSubmitBtn.addEventListener('click', () => {
            const containerGoodsIds = [] as Array<string>;
            for (let i = 0; i < containerGoodsEls.length; i++) {
                const goodsEl = (containerGoodsEls.item(i) as HTMLElement);
                const goodsIdEl = goodsEl.firstElementChild as HTMLElement;
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
            //提交
            new Ajax(PageManageUrls.addContainerModuleUrl).config().sendJson({
                titleName: containerSubmitInputEl.value,
                goodsIds: containerGoodsIds
            }).result(
                () => { this.responseSuccessRefresh('添加主要界面数据成功！') },
                () => { /*默认有处理*/ },
                this.parentFailHandel
            );
        });
    }
    //修改标题界面
    fixToggleView(toggleIdEl: HTMLElement, fixInputEl: HTMLInputElement, fixSelectCheckValueEls: HTMLCollection) {
        const toggleViewId = toggleIdEl.innerText;
        const titleName = fixInputEl.value;
        const goodsIds = [] as Array<string>;
        if (ChatCheck.isEmpty(titleName)) {
            MessageControl.open("标题名字不能为空！", MessageType.WARNING);
            return;
        }
        for (let i = 0; i < fixSelectCheckValueEls.length; i++) {
            goodsIds.push((fixSelectCheckValueEls.item(i) as HTMLElement).innerText);
        }
        if (goodsIds.length < 1 || goodsIds.length > 7) {
            MessageControl.open('标题界面选择的商品最少1个最多7个！', MessageType.WARNING);
            return;
        }
        //提交
        new Ajax(PageManageUrls.fixToggleViewUrl).config().sendJson({
            toggleViewId,
            titleName,
            goodsIds
        }).result(
            () => { this.responseSuccessRefresh('修改标题界面数据成功！', false) },
            () => { /*默认有处理*/ },
            this.parentFailHandel
        );
    }
    //修改轮播图界面
    fixRotationChart(fixSelectCheckValueEls: HTMLCollection) {
        const goodsIds = [] as Array<string>;
        for (let i = 0; i < fixSelectCheckValueEls.length; i++) {
            goodsIds.push((fixSelectCheckValueEls.item(i) as HTMLElement).innerText);
        }
        if (goodsIds.length < 4 || goodsIds.length > 6) {
            MessageControl.open('轮播图的商品数量最好是4至6个！', MessageType.WARNING);
            return;
        }
        //提交
        new Ajax(PageManageUrls.fixRotationChattUrl).config().sendJson({
            goodsIds
        }).result(
            () => { this.responseSuccessRefresh('修改轮播图界面数据成功！', false) },
            () => { /*默认有处理*/ },
            this.parentFailHandel
        );
    }
    //修改广告界面
    fixGoodsAd(fixSelectCheckValueEls: HTMLCollection) {
        const goodsIds = [] as Array<string>;
        for (let i = 0; i < fixSelectCheckValueEls.length; i++) {
            goodsIds.push((fixSelectCheckValueEls.item(i) as HTMLElement).innerText);
        }
        if (goodsIds.length != 2) {
            MessageControl.open('广告界面的商品数量必须为2个！', MessageType.WARNING);
            return;
        }
        //提交
        new Ajax(PageManageUrls.fixGoodsAdUrl).config().sendJson({
            goodsIds
        }).result(
            () => { this.responseSuccessRefresh('修改广告界面数据成功！', false) },
            () => { /*默认有处理*/ },
            this.parentFailHandel
        );
    }
    //修改主要界面
    fixContainerModule(containerIdEl: HTMLElement, fixInputEl: HTMLInputElement, fixSelectLeftValueEls: HTMLCollection, fixSelectTopValueEls: HTMLCollection, fixSelectBottomValueEls: HTMLCollection) {
        const containerModuleId = containerIdEl.innerText;
        const titleName = fixInputEl.value;
        const containerSpecialGoodsId = [] as Array<string>;
        const containerTopGoodsIds = [] as Array<string>;
        const containerBottomGoodsIds = [] as Array<string>;
        if (ChatCheck.isEmpty(titleName)) {
            MessageControl.open("标题名字不能为空！", MessageType.WARNING);
            return;
        }
        for (let i = 0; i < fixSelectLeftValueEls.length; i++) {
            containerSpecialGoodsId.push((fixSelectLeftValueEls.item(i) as HTMLElement).innerText);
        }
        if (containerSpecialGoodsId.length != 1) {
            MessageControl.open("左侧高的商品选择的数量必须为1个！", MessageType.WARNING);
            return;
        }
        for (let i = 0; i < fixSelectTopValueEls.length; i++) {
            containerTopGoodsIds.push((fixSelectTopValueEls.item(i) as HTMLElement).innerText);
        }
        if (containerTopGoodsIds.length != 4) {
            MessageControl.open("上排商品选择的数量必须4个！", MessageType.WARNING);
            return;
        }
        for (let i = 0; i < fixSelectBottomValueEls.length; i++) {
            containerBottomGoodsIds.push((fixSelectBottomValueEls.item(i) as HTMLElement).innerText);
        }
        if (containerBottomGoodsIds.length != 4) {
            MessageControl.open("下排商品选择的数量必须4个！", MessageType.WARNING);
            return;
        }
        //提交
        new Ajax(PageManageUrls.fixContainerModuleUrl).config().sendJson({
            containerModuleId,
            titleName,
            containerSpecialGoodsId,
            containerTopGoodsIds,
            containerBottomGoodsIds
        }).result(
            () => { this.responseSuccessRefresh('修改广告界面数据成功！', false) },
            () => { /*默认有处理*/ },
            this.parentFailHandel
        );
    }
    //删除标题界面
    delToggleView(id: string) {
        new Ajax(PageManageUrls.delToggleViewUrl).config().sendJson({
            id
        }).result(
            () => { this.responseSuccessRefresh('删除标题界面成功！', false) },
            () => { /*默认有处理*/ },
            this.parentFailHandel
        );
    }
    //删除主要界面
    delContainerModule(id: string) {
        new Ajax(PageManageUrls.delContainerModuleUrl).config().sendJson({
            id
        }).result(
            () => { this.responseSuccessRefresh('删除主要界面成功！', false) },
            () => { /*默认有处理*/ },
            this.parentFailHandel
        );
    }
    //关闭弹窗，取消商品选择
    cancelSelectGoods() {
        //顺带着把输入框也清空
        const inputEls = document.querySelectorAll('.page-manage-add-box-input-box > input') as unknown as Array<HTMLInputElement>;
        inputEls.forEach(inputEl => {
            inputEl.value = '';
        });
        this.goodsEls.forEach(goodsEl => {
            goodsEl.className = 'page-manage-goods';
        });
    }
    //开启公共弹窗图片选择
    openPublicSelectInput(inputBoxEl: HTMLElement) {
        const checkValuEls = inputBoxEl.children;
        const publiSelectGoodsEls = this.publicSelectBoxEl.children;
        for (let j = 0; j < publiSelectGoodsEls.length; j++) {
            for (let i = 0; i < checkValuEls.length; i++) {
                if ((checkValuEls.item(i) as HTMLElement).innerText == ((publiSelectGoodsEls.item(j) as HTMLElement).firstElementChild as HTMLElement).innerText) {
                    (publiSelectGoodsEls.item(j) as HTMLElement).className = 'page-manage-goods page-manage-goods-check';
                    break;
                }
            }

        }
        CustomComponentBoxControl.open(this.selectPublicInputBoxEl, () => {
            this.cancelSelectGoods();
        });
    }
    //初始化商品事件
    initGoodsView(goodsBoxEls: Array<HTMLElement>) {
        new Ajax(GoodsUrls.allDataUrl).config().sendEmpty().result(
            (data: Array<GoodsType>) => {
                goodsBoxEls.forEach(goodsBoxEl => {
                    let temp = goodsBoxEl.innerHTML;
                    data.forEach(goods => {
                        temp += this.goodsSelectTemplate(goods.goodsId, goods.imgUrl, goods.goodsName);
                    });
                    goodsBoxEl.innerHTML = temp;
                });
                //处理所有商品选择点击事件
                this.goodsEls = document.querySelectorAll('.page-manage-goods-box .page-manage-goods') as unknown as Array<HTMLElement>;
                this.goodsEls.forEach(goodsEl => {
                    goodsEl.addEventListener('click', () => {
                        goodsEl.className.indexOf("page-manage-goods-check") != -1 ? goodsEl.className = 'page-manage-goods' : goodsEl.className = 'page-manage-goods page-manage-goods-check';
                    });
                });
            },
            () => { },
            this.parentFailHandel
        );

    }
    //初始化显示事件
    initViewEvent(pageToggleEl: HTMLElement, pageRotationEl: HTMLElement, pageAdEl: HTMLElement, pageContainerEl: HTMLElement) {
        let pageToggleHtml = pageToggleEl.innerHTML;
        let pageRotationHtml = pageRotationEl.innerHTML;
        let pageAdHtml = pageAdEl.innerHTML;
        let pageContainerHtml = pageContainerEl.innerHTML;
        new Ajax(PageManageUrls.getToggleViewUrl).config().sendEmpty().result(
            (data: Array<ToggleViewType>) => {
                data.forEach(pageToggle => {
                    pageToggleHtml += this.pageToggleTemplate(pageToggle.toggleViewId, pageToggle.titleName, this.selectCheckTemplate(pageToggle.goodsIds));
                });
                //赋值
                pageToggleEl.innerHTML = pageToggleHtml;
                //从这里进行操作dom
                const toggleAddBth = document.querySelector('.page-toggle-manage-box .page-son-manage-add-bth') as HTMLElement;
                const toggleAddEl = document.querySelector('.page-manage-add-toggle') as HTMLElement;
                toggleAddBth.addEventListener('click', () => {
                    CustomComponentBoxControl.open(toggleAddEl, () => {
                        this.cancelSelectGoods();
                    });
                });
                //选择框点击
                const toggleSelectInputBoxEls = document.querySelectorAll('.page-toggle-manage-box .page-select-input-box') as unknown as Array<HTMLElement>;
                toggleSelectInputBoxEls.forEach(inputBoxEl => {
                    inputBoxEl.addEventListener('click', () => {
                        this.currentSelectInputEl = inputBoxEl;
                        this.openPublicSelectInput(inputBoxEl);
                    });
                });
                //修改按钮点击
                const fixSubmitBtnEls = document.querySelectorAll('.page-toggle-manage-box .page-manage-box-handel-fix') as unknown as Array<HTMLElement>;
                const fixInputEls = document.querySelectorAll('.page-toggle-manage-box .page-manage-box-content-input > input') as unknown as Array<HTMLInputElement>;
                fixSubmitBtnEls.forEach((fixSubmitBtnEl, index) => {
                    fixSubmitBtnEl.addEventListener('click', () => {
                        DelTipsBoxControl.open('你确定要修改这个标题界面数据吗？', () => {
                            const toggleIdEl = ((fixSubmitBtnEl.parentElement as HTMLElement).parentElement as HTMLElement).firstElementChild as HTMLElement;
                            const fixInputEl = fixInputEls[index];
                            const fixSelectCheckValueEls = toggleSelectInputBoxEls[index].children;
                            this.fixToggleView(toggleIdEl, fixInputEl, fixSelectCheckValueEls);
                        });
                    });
                });
                //删除按钮点击
                const delSubmitBtnEls = document.querySelectorAll('.page-toggle-manage-box .page-manage-box-handel-del') as unknown as Array<HTMLElement>;
                delSubmitBtnEls.forEach(delSubmitBtnEl => {
                    delSubmitBtnEl.addEventListener('click', () => {
                        const toggleIdEl = ((delSubmitBtnEl.parentElement as HTMLElement).parentElement as HTMLElement).firstElementChild as HTMLElement
                        DelTipsBoxControl.open('你确定要删除这个标题界面数据吗？', () => {
                            this.delToggleView(toggleIdEl.innerText);
                        });
                    });
                });
            },
            () => { },
            this.parentFailHandel
        );
        new Ajax(PageManageUrls.getRotationChartwUrl).config().sendEmpty().result(
            (data: PageCheckType) => {
                pageRotationHtml += this.pageCheckTemplate(this.selectCheckTemplate(data.goodsIds));
                //赋值
                pageRotationEl.innerHTML = pageRotationHtml;
                //选择框点击
                const toggleSelectInputBoxEl = document.querySelector('.page-rotation-manage-box .page-select-input-box') as HTMLElement;
                toggleSelectInputBoxEl.addEventListener('click', () => {
                    this.currentSelectInputEl = toggleSelectInputBoxEl;
                    this.openPublicSelectInput(toggleSelectInputBoxEl);
                });
                //修改按钮点击
                const fixSubmitBtn = document.querySelector('.page-rotation-manage-box .page-manage-box-handel-fix') as HTMLElement;
                fixSubmitBtn.addEventListener('click', () => {
                    DelTipsBoxControl.open('你确定要修改这个轮播图界面数据吗？', () => {
                        this.fixRotationChart(toggleSelectInputBoxEl.children);
                    });
                });
            },
            () => { },
            this.parentFailHandel
        );
        new Ajax(PageManageUrls.getGoodsAdUrl).config().sendEmpty().result(
            (data: PageCheckType) => {
                pageAdHtml += this.pageCheckTemplate(this.selectCheckTemplate(data.goodsIds));
                //赋值
                pageAdEl.innerHTML = pageAdHtml;
                //选择框点击
                const toggleSelectInputBoxEl = document.querySelector('.page-ad-manage-box .page-select-input-box') as HTMLElement;
                toggleSelectInputBoxEl.addEventListener('click', () => {
                    this.currentSelectInputEl = toggleSelectInputBoxEl;
                    this.openPublicSelectInput(toggleSelectInputBoxEl);
                });
                //修改按钮点击
                const fixSubmitBtn = document.querySelector('.page-ad-manage-box .page-manage-box-handel-fix') as HTMLElement;
                fixSubmitBtn.addEventListener('click', () => {
                    DelTipsBoxControl.open('你确定要修改这个广告界面数据吗？', () => {
                        this.fixGoodsAd(toggleSelectInputBoxEl.children);
                    });
                });
            },
            () => { },
            this.parentFailHandel
        );
        new Ajax(PageManageUrls.getContainerModuleUrl).config().sendEmpty().result(
            (data: Array<ContainerModuleType>) => {
                data.forEach(container => {
                    pageContainerHtml += this.pageContainerTemplate(container.containerModuleId, container.titleName,
                        this.selectCheckTemplate([container.specialGoodsInfo]),
                        this.selectCheckTemplate(container.topGoodsInfo),
                        this.selectCheckTemplate(container.bottomGoodsInfo));
                });
                //赋值
                pageContainerEl.innerHTML = pageContainerHtml;
                //这里操作dom
                const containerAddBth = document.querySelector('.page-container-manage-box .page-son-manage-add-bth') as HTMLElement;
                const containerEl = document.querySelector('.page-manage-add-container') as HTMLElement;
                containerAddBth.addEventListener('click', () => {
                    CustomComponentBoxControl.open(containerEl, () => {
                        this.cancelSelectGoods();
                    });
                });
                //选择框点击
                const containerSelectInputBoxEls = document.querySelectorAll('.page-container-manage-box .page-select-input-box') as unknown as Array<HTMLElement>;
                containerSelectInputBoxEls.forEach(inputBoxEl => {
                    inputBoxEl.addEventListener('click', () => {
                        this.currentSelectInputEl = inputBoxEl;
                        this.openPublicSelectInput(inputBoxEl);
                    });
                });
                //修改按钮点击
                const fixSubmitBtnEls = document.querySelectorAll('.page-container-manage-box .page-manage-box-handel-fix') as unknown as Array<HTMLElement>;
                const fixInputEls = document.querySelectorAll('.page-container-manage-box .page-manage-box-content-input > input') as unknown as Array<HTMLInputElement>;
                fixSubmitBtnEls.forEach((fixSubmitBtnEl, index) => {
                    fixSubmitBtnEl.addEventListener('click', () => {
                        DelTipsBoxControl.open('你确定要修改这个广告界面数据吗？', () => {
                            const containerIdEl = ((fixSubmitBtnEl.parentElement as HTMLElement).parentElement as HTMLElement).firstElementChild as HTMLElement;
                            const fixInputEl = fixInputEls[index];
                            let selectBoxIndex = index * 3;
                            const fixSelectLeftValueEls = containerSelectInputBoxEls[selectBoxIndex].children;
                            const fixSelectTopValueEls = containerSelectInputBoxEls[selectBoxIndex + 1].children;
                            const fixSelectBottomValueEls = containerSelectInputBoxEls[selectBoxIndex + 2].children;
                            this.fixContainerModule(containerIdEl, fixInputEl, fixSelectLeftValueEls, fixSelectTopValueEls, fixSelectBottomValueEls);
                        });
                    });
                });
                //删除按钮点击
                const delSubmitBtnEls = document.querySelectorAll('.page-container-manage-box .page-manage-box-handel-del') as unknown as Array<HTMLElement>;
                delSubmitBtnEls.forEach(delSubmitBtnEl => {
                    delSubmitBtnEl.addEventListener('click', () => {
                        const containerIdEl = ((delSubmitBtnEl.parentElement as HTMLElement).parentElement as HTMLElement).firstElementChild as HTMLElement
                        DelTipsBoxControl.open('你确定要删除这个主要界面数据吗？', () => {
                            this.delContainerModule(containerIdEl.innerText);
                        });
                    });
                });
            },
            () => { },
            this.parentFailHandel
        );
    }
    //tab页面高度
    handelTabHeight(tabPageBoxEls: Array<HTMLElement>, index: number = 0) {
        tabPageBoxEls.forEach(tabPageBoxEl => {
            tabPageBoxEl.style.height = '0';
        });
        tabPageBoxEls[index].style.height = 'auto';
    }
    //tab切换事件
    handelTabEvent(tabBthEls: Array<HTMLElement>, tabViewEl: HTMLElement, tabPageBoxEls: Array<HTMLElement>) {
        tabBthEls.forEach((tabBthEl, index) => {
            tabBthEl.addEventListener('click', () => {
                tabBthEls.forEach(v => {
                    v.className = 'page-manage-tab-button';
                });
                this.handelTabHeight(tabPageBoxEls, index);
                tabBthEl.className = tabBthEl.className + ' page-manage-tab-button-check';
                tabViewEl.style.transform = `translateX(${(-25) * index}%)`;
            });
        });
    }
    //商品选择模板
    private goodsSelectTemplate(goodsId: string, goodsImg: string, goodsName: string, check: boolean = false): string {
        return `
        <div class="page-manage-goods ${check ? 'page-manage-goods-check' : ''}">
            <div class="page-manage-goods-id">${goodsId}</div>
            <div class="page-manage-goods-img">
                <img src="${goodsImg}" alt="图片资源丢失...">
            </div>
            <div class="page-manage-goods-name">${goodsName}</div>
        </div>
        `;
    }
    //生成选择商品模板
    private selectCheckTemplate(goodsIds: Array<string>) {
        let template = '';
        goodsIds.forEach(goodsId => {
            template += `<div class="page-select-check-value">${goodsId}</div>`;
        });
        return template;
    }
    //标题界面模板
    private pageToggleTemplate(toggleViewId: string, titleName: string, selectCheckTemplate: string): string {
        return `
        <div class="page-toggle-manage">
            <div class="page-manage-box-value-box">
                <div class="page-manage-hidden-id">${toggleViewId}</div>
                <div class="page-manage-box-value">
                    <div class="page-manage-box-content-title">标题名字:</div>
                    <div class="page-manage-box-content-input-box">
                        <div class="page-manage-box-content-input">
                            <input type="text" value='${titleName}'>
                        </div>
                    </div>
                </div>
                <div class="page-manage-box-value">
                    <div class="page-manage-box-content-title">选择的商品:</div>
                    <div class="page-manage-box-content-value page-select-input-box">
                        ${selectCheckTemplate}
                    </div>
                </div>
                <div class="page-manage-box-handel">
                    <button class="page-manage-box-handel-fix">修改</button>
                    <button class="page-manage-box-handel-del">删除</button>
                </div>
            </div>
        </div>
        `;
    }
    //主要界面模板
    private pageContainerTemplate(containerModuleId: string, titleName: string, leftSelectCheckTemplate: string, topSelectCheckTemplate: string, bottomSelectCheckTemplate: string): string {
        return `
        <div class="page-toggle-manage">
            <div class="page-manage-box-value-box">
                <div class="page-manage-hidden-id">${containerModuleId}</div>
                <div class="page-manage-box-value">
                    <div class="page-manage-box-content-title">标题名字:</div>
                    <div class="page-manage-box-content-input-box">
                        <div class="page-manage-box-content-input">
                            <input type="text" value='${titleName}'>
                        </div>
                    </div>
                </div>
                <div class="page-manage-box-value">
                    <div class="page-manage-box-content-title">左侧高的商品:</div>
                    <div class="page-manage-box-content-value page-select-input-box">
                        ${leftSelectCheckTemplate}
                    </div>
                </div>
                <div class="page-manage-box-value">
                    <div class="page-manage-box-content-title">上排商品:</div>
                    <div class="page-manage-box-content-value page-select-input-box">
                        ${topSelectCheckTemplate}
                    </div>
                </div>
                <div class="page-manage-box-value">
                    <div class="page-manage-box-content-title">下排商品:</div>
                    <div class="page-manage-box-content-value page-select-input-box">
                        ${bottomSelectCheckTemplate}
                    </div>
                </div>
                <div class="page-manage-box-handel">
                    <button class="page-manage-box-handel-fix">修改</button>
                    <button class="page-manage-box-handel-del">删除</button>
                </div>
            </div>
        </div>
        `;
    }
    //轮播图和广告界面模板
    private pageCheckTemplate(selectCheckTemplate: string): string {
        return `
        <div class="page-toggle-manage">
            <div class="page-manage-box-value-box">
                <div class="page-manage-box-value">
                    <div class="page-manage-box-content-title">选择的商品:</div>
                    <div class="page-manage-box-content-value page-select-input-box">
                        ${selectCheckTemplate}
                    </div>
                </div>
                <div class="page-manage-box-handel">
                    <button class="page-manage-box-handel-fix">修改</button>
                </div>
            </div>
        </div>
        `;
    }
}
PublicMainView.run(new PageManage(3));