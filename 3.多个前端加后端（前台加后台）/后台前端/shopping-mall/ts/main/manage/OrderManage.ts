import { PublicMainView } from "../../components/PublicMainView.js";
import Ajax from "../../utils/Ajax.js";
import { OrderUrls } from "../../api/ApiConstant.js";
import { OrderType } from "../../interface/Types.js";
import { CustomTableControl } from "../../components/CustomTableControl.js";
import ParentMain from "../ParentMain.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
//订单管理
class OrderManage extends ParentMain {
    //每个管理类，只需要处理自己的事件即可
    getEvent(): Function {
        return () => {
            //main组件
            const mainEl = document.querySelector('main') as HTMLElement;
            //获取修改组件(先把组件存到变量中，防止关闭消失)
            const orderModifyDhipStatusEl = document.querySelector('.order-modify-ship-status-box') as HTMLElement
            //id值
            const orderIdValueEl = document.querySelector('.order-id-value') as HTMLElement;
            //选中的值
            const selectCheckValueEl = document.querySelector('.select-check-value') as HTMLElement;
            //获取提交按钮
            const submitButtonEl = document.querySelector('.order-modify-status-button') as HTMLButtonElement;
            //提交事件
            submitButtonEl.addEventListener('click', () => {
                this.modifyOrderShipStatus(orderIdValueEl, selectCheckValueEl);
            });
            this.getOrderData([mainEl, orderModifyDhipStatusEl, orderIdValueEl, selectCheckValueEl]);
        };
    }
    //获取订单数据
    private getOrderData(els: Array<HTMLElement>) {
        new Ajax(OrderUrls.allDataUrl).config().sendEmpty().result(
            (data: Array<OrderType>) => {
                this.createTable(els, data);
            },
            () => { },
            this.parentFailHandel
        );
    }
    private createTable(els: Array<HTMLElement>, data: Array<OrderType>, index: number = -1, value: string = '', currentPageNum: number = 1) {
        const tableTitleData = ['订单id', '用户id', '商品名字', '商户名字', '商品数量', '运送状态', '总金额', '支付状态'];
        const tableContentData = [] as Array<Array<string>>;
        data.forEach(orderData => {
            //支付状态
            let payStatus = '未支付';
            if (orderData.orderState == 'COMPLET') {
                payStatus = '已支付';
            }
            //运送状态
            let shipStatus = '未发货';
            if (orderData.shipStatus == 2) {
                shipStatus = '运送中';
            } else if (orderData.shipStatus == 3) {
                shipStatus = '已送达';
            }
            tableContentData.push([orderData.orderId, orderData.userId, orderData.goodsName, orderData.merchantName, orderData.goodsNum.toString(), shipStatus, orderData.totalPrice.toString(), payStatus]);
        });
        //过滤重组一个新的表
        const tableContentDataBak = [] as Array<Array<string>>;
        if (index != -1 && value != '') {
            tableContentData.forEach(goodsData => {
                if (goodsData[index].indexOf(value) != -1) {
                    tableContentDataBak.push(goodsData);
                }
            });
        }
        const tableData = {
            title: tableTitleData,
            content: (index != -1 && value != '') ? tableContentDataBak : tableContentData
        };
        //生成表格
        CustomTableControl
            .configValue(index, value)
            .configHaveImgOrHavepageNum(false, true, (currentPageNum: number) => {
                //页码事件
                this.createTable(els, data, this.getInputIndex(), this.getInputValue(), currentPageNum);
            })
            .configQueryAndAdd(false, () => { }, true, (index: number, value: string) => {
                //查询事件，得到下标和文本框的值,然后重新生成表格
                this.setInputIndex(index);
                this.setInputValue(value);
                this.createTable(els, data, index, value);
            }, 1, 2, 3)
            .configUpdateAndDelete(true, (data: Array<string>) => {
                //更新点击回调,无需挂载，因为外层已经处理了
                els[2].innerText = data[0];
                els[3].innerText = data[5];
                CustomComponentBoxControl.open(els[1]);
            }, false, () => { }).create(els[0], null, tableData, [], currentPageNum);
    }
    //修改订单运送状态事件
    private modifyOrderShipStatus(orderIdValueEl: HTMLElement, selectCheckValueEl: HTMLElement) {
        const orderId = orderIdValueEl.innerText;
        let shipStatus = 1;
        let shipStatusName = selectCheckValueEl.innerText;
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
            orderId,
            shipStatus
        }).result(
            () => { this.responseSuccessRefresh('修改订单运送状态成功！') },
            () => { /*默认有处理*/ },
            this.parentFailHandel
        );
    }
}
PublicMainView.run(new OrderManage(5));