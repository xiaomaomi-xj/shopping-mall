import { PublicMainView } from "../components/PublicMainView.js";
import Ajax from "../utils/Ajax.js";
import { ChatBoosUrls, OrderUrls, UserUrls, GoodsUrls, ExtraConfigUrls } from "../api/ApiConstant.js";
import ParentMain from "./ParentMain.js";
import { ChatBoosType, ExtraConfigType, GoodsType, OrderType, UserType } from "../interface/Types.js";
import { CustomTableControl } from "../components/CustomTableControl.js";
import { ChatCheck } from "../utils/ChatCheck.js";
//首页
class Main extends ParentMain {
    //每个管理类，只需要处理自己的事件即可
    getEvent(): Function {
        return () => {
            const goodsNumEl = document.querySelector('.c-body.goods-num') as HTMLElement;
            const orderNumEl = document.querySelector('.c-body.order-num') as HTMLElement;
            const userNumEl = document.querySelector('.c-body.user-num') as HTMLElement;
            const chatNumEl = document.querySelector('.c-body.chat-num') as HTMLElement;
            //数量有关数据
            this.getData(goodsNumEl, orderNumEl, userNumEl, chatNumEl);
            //特殊配置数据
            this.extraConfigDataHandel();
        };
    }
    //后端获取数据
    private getData(goodsNumEl: HTMLElement, orderNumEl: HTMLElement, userNumEl: HTMLElement, chatNumEl: HTMLElement) {
        //聊天数据
        this.chatDataHandel(chatNumEl);
        //商品数据
        this.goodsDataHandel(goodsNumEl);
        //订单数据
        this.orderDataHandel(orderNumEl);
        //用户比较特殊，两种形式都要考虑
        let sum = 0;
        new Ajax(UserUrls.allUserDataUrl).config().sendEmpty().result(
            (data: Array<any>) => {
                sum += data.length;
                userNumEl.innerText = sum.toString();
            },
            () => { },
            //错误处理统一用父级的
            this.parentFailHandel
        );
        new Ajax(UserUrls.allWechatDataUrl).config().sendEmpty().result(
            (data: Array<UserType>) => {
                data.forEach(wechat=>{
                    //绑定的微信用户不再单独算个数
                    if(ChatCheck.isEmpty(wechat.bindId)){
                        sum += data.length;
                    }
                });
                userNumEl.innerText = sum.toString();
            },
            () => { },
            //错误处理统一用父级的
            this.parentFailHandel
        );
    }
    //订单数据处理
    private orderDataHandel(orderNumEl: HTMLElement) {
        new Ajax(OrderUrls.allDataUrl).config().sendEmpty().result(
            (data: Array<OrderType>) => {
                const amountEl = document.querySelector('.amount-total') as HTMLElement;
                let amounSum = 0;
                data.forEach(v => {
                    amounSum += v.totalPrice;
                });
                amountEl.innerText = amounSum.toString();
                orderNumEl.innerText = data.length.toString();
            },
            () => { },
            //错误处理统一用父级的
            this.parentFailHandel
        );
    }
    //商品数据处理
    private goodsDataHandel(goodsNumEl: HTMLElement) {
        new Ajax(GoodsUrls.allDataUrl).config().sendEmpty().result(
            (data: Array<GoodsType>) => {
                const rightBoxEl = document.querySelector('.among .box') as HTMLElement;
                const tableRowData = [] as Array<Array<string>>;
                data.sort((a, b) => a.maxBuyNum - b.maxBuyNum);
                data.forEach((v, k) => {
                    if (k < 10) {
                        tableRowData.push([v.goodsId, v.imgUrl, v.goodsName, v.goodsPrice.toString(), v.maxBuyNum.toString()]);
                    }
                });
                const tableData = {
                    title: ['商品id', '商品封面', '商品名字', '商品价格', '商品库存'],
                    content: tableRowData
                };
                CustomTableControl.configHaveImgOrHavepageNum(true).create(rightBoxEl, null, tableData, [1]);
                goodsNumEl.innerText = data.length.toString();
            },
            () => { },
            //错误处理统一用父级的
            this.parentFailHandel
        );
    }
    //聊天数据处理
    private chatDataHandel(chatNumEl: HTMLElement) {
        //聊天数据需要特殊处理
        new Ajax(ChatBoosUrls.allDataUrl).config().sendEmpty().result(
            (data: Array<ChatBoosType>) => {
                //生成表格数据
                const rightBoxEl = document.querySelector('.right .box') as HTMLElement;
                const tableRowData = [] as Array<Array<string>>;
                let num = 0;
                data.reverse().forEach((v) => {
                    if (num < 10 && v.fromUser == 1) {
                        //这里不用下标是因为有的数据不符合条件
                        num++;
                        tableRowData.push([v.userId, v.message, v.isUnRead == 1 ? '未读' : '已读']);
                    }
                });
                const tableData = {
                    title: ['发送者', '消息内容', '是否已读'],
                    content: tableRowData
                };
                CustomTableControl.create(rightBoxEl, null, tableData,[]);
                let sum = 0;
                data.forEach(v => {
                    if (v.fromUser == 1 && v.isUnRead == 1) {
                        sum++;
                    }
                });
                chatNumEl.innerText = sum.toString();
            },
            () => { },
            //错误处理统一用父级的
            this.parentFailHandel
        );
    }
    //特殊配置数据
    private extraConfigDataHandel() {
        new Ajax(ExtraConfigUrls.getConfigUrl).config().sendEmpty().result(
            (data: ExtraConfigType) => {
                let storeName: string;
                if (data.storeNameHaveSpecial) {
                    storeName = data.storeName.replace('^', data.specialText);
                } else {
                    storeName = data.storeName;
                }
                this.createStorNameNode(storeName.trim());
            },
            () => { },
            //错误处理统一用父级的
            this.parentFailHandel
        );
    }
    //创建商店名称酷炫节点
    private createStorNameNode(storeName: string) {
        const leftEl = document.querySelector('.bottom .left') as HTMLElement;
        storeName.split('').forEach(v => {
            const windmillEl = document.createElement('div');
            windmillEl.className = 'windmill';
            const spanBoxEl = document.createElement('div');
            spanBoxEl.className = 'span-box';
            const spanEl = document.createElement('span');
            spanEl.innerText = v;
            spanBoxEl.appendChild(spanEl);
            windmillEl.appendChild(spanBoxEl);
            leftEl.appendChild(windmillEl);
        });
    }
}
PublicMainView.run(new Main(0));