import { PublicMainView } from "../../components/PublicMainView.js";
import Ajax from "../../utils/Ajax.js";
import { VipCardUrls } from "../../api/ApiConstant.js";
import { CustomTableControl } from "../../components/CustomTableControl.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import ParentMain from "../ParentMain.js";
import { VipCardType } from "../../interface/Types.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
//购物卡管理
class VipCardManage extends ParentMain {
    //每个管理类，只需要处理自己的事件即可
    getEvent(): Function {
        return () => {
            const mainHtml = document.querySelector('main') as HTMLElement;
            //修改
            const vipCardModifyBoxEl = document.querySelector('.vip-card-modify-component-box') as HTMLElement;
            const vipCardIdModifyEl = document.querySelector('.vip-card-modify-box .vip-card-text-value') as HTMLElement;
            const vipCardInputModifyEls = document.querySelectorAll('.vip-card-modify-box .vip-card-input > input') as unknown as Array<HTMLInputElement>;
            const vipCardPasswordBtnEl = document.querySelector('.vip-card-modify-box .vip-card-modify-password-button') as HTMLElement;
            const vipCardBalanceBtnEl = document.querySelector('.vip-card-modify-box .vip-card-modify-balance-button') as HTMLElement;
            vipCardPasswordBtnEl.addEventListener('click', () => {
                this.modifyVipCardPassword(vipCardIdModifyEl, vipCardInputModifyEls[0]);
            });
            vipCardBalanceBtnEl.addEventListener('click', () => {
                this.modifyVipCardBalance(vipCardIdModifyEl, vipCardInputModifyEls[1]);
            });
            //新增
            const vipCardAddBoxEl = document.querySelector('.vip-card-add-component-box') as HTMLElement;
            const vipCardInputAddEls = document.querySelectorAll('.vip-card-add-box .vip-card-input > input') as unknown as Array<HTMLInputElement>;
            const vipCardBtnAddEl = document.querySelector('.vip-card-add-box .vip-card-add-button') as HTMLElement;
            vipCardBtnAddEl.addEventListener('click', () => {
                this.addVipCard(vipCardInputAddEls);
            });
            this.getVipCardData([mainHtml, vipCardModifyBoxEl, vipCardIdModifyEl, ...vipCardInputModifyEls, vipCardAddBoxEl, ...vipCardInputAddEls]);
        };
    }
    //获取购物卡数据
    private getVipCardData(els: Array<HTMLElement>) {
        new Ajax(VipCardUrls.getAllVipCardUrl).config().sendEmpty().result(
            (data: Array<VipCardType>) => {
                this.createTable(els, data);
            },
            () => { },
            this.parentFailHandel
        );
    }
    //创建表格
    createTable(els: HTMLElement[], data: VipCardType[], index: number = -1, value: string = '', currentPageNum: number = 1) {
        const tableTitleData = ['购物卡id', '购物卡账号', '购物卡余额'];
        const tableContentData = [] as Array<Array<string>>;
        data.forEach(vipCard => {
            tableContentData.push([vipCard.vipCardId, vipCard.vipCardAccount, vipCard.vipCardBalance]);
        });
        //过滤重组一个新的表
        const tableContentDataBak = [] as Array<Array<string>>;
        if (index != -1 && value != '') {
            tableContentData.forEach(vipCard => {
                if (vipCard[index].indexOf(value) != -1) {
                    tableContentDataBak.push(vipCard);
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
            .configQueryAndAdd(true, () => {
                CustomComponentBoxControl.open(els[5], () => {
                    (els[6] as HTMLInputElement).value = '';
                    (els[7] as HTMLInputElement).value = '';
                });
            }, true, (index: number, value: string) => {
                //查询事件，得到下标和文本框的值,然后重新生成表格
                this.setInputIndex(index);
                this.setInputValue(value);
                this.createTable(els, data, index, value);
            }, 1, 2)
            .configUpdateAndDelete(true, (data: Array<string>) => {
                //更新点击回调,无需挂载，因为外层已经处理了
                els[2].innerText = data[0];
                (els[3] as HTMLInputElement).value = '';
                (els[4] as HTMLInputElement).value = data[2];
                CustomComponentBoxControl.open(els[1]);
            }, true, (data: Array<string>) => {
                DelTipsBoxControl.open("确定要删除此购物卡吗？", () => {
                    this.delVipCard(data[0]);
                });
            }).create(els[0], null, tableData, [], currentPageNum);
    }
    //修改余额
    private modifyVipCardBalance(vipCardBtnModifyEl: HTMLElement, vipCardBalanceInputEl: HTMLInputElement) {
        if (ChatCheck.isEmpty(vipCardBtnModifyEl.innerText) ||
            ChatCheck.isEmpty(vipCardBalanceInputEl.value)) {
            MessageControl.open("必要信息【购物卡id、购物卡余额】不得为空！", MessageType.WARNING);
        } else {
            if (window.isNaN(window.parseFloat(vipCardBalanceInputEl.value))) {
                MessageControl.open("请输入正确的金额格式！", MessageType.WARNING);
            } else {
                new Ajax(VipCardUrls.modifyVipCardBalanceUrl).config().sendJson({
                    vipCardId: vipCardBtnModifyEl.innerText,
                    vipCardBalance: vipCardBalanceInputEl.value
                }).result(
                    () => { this.responseSuccessRefresh('修改购物卡余额成功！') },
                    () => { },
                    this.parentFailHandel
                );
            }
        }
    }
    //修改支付密码
    private modifyVipCardPassword(vipCardBtnModifyEl: HTMLElement, vipCardPasswordInputEl: HTMLInputElement) {
        if (ChatCheck.isEmpty(vipCardBtnModifyEl.innerText) ||
            ChatCheck.isEmpty(vipCardPasswordInputEl.value)) {
            MessageControl.open("必要信息【购物卡id、购物卡支付密码】不得为空！", MessageType.WARNING);
        } else {
            new Ajax(VipCardUrls.modifyVipCardPasswordUrl).config().sendJson({
                vipCardId: vipCardBtnModifyEl.innerText,
                vipCardPassword: vipCardPasswordInputEl.value,
            }).result(
                () => { this.responseSuccessRefresh('修改购物卡支付密码成功！') },
                () => { },
                this.parentFailHandel
            );
        }
    }
    //新增
    private addVipCard(vipCardInputAddEls: Array<HTMLInputElement>) {
        if (ChatCheck.isEmpty(vipCardInputAddEls[0].value) ||
            ChatCheck.isEmpty(vipCardInputAddEls[1].value)) {
            MessageControl.open("必要信息【购物卡支付密码、购物卡余额】不得为空！", MessageType.WARNING);
        } else {
            if (window.isNaN(window.parseFloat(vipCardInputAddEls[1].value))) {
                MessageControl.open("请输入正确的金额格式！", MessageType.WARNING);
            } else {
                new Ajax(VipCardUrls.addVipCardUrl).config().sendJson({
                    vipCardPassword: vipCardInputAddEls[0].value,
                    vipCardBalance: vipCardInputAddEls[1].value
                }).result(
                    () => { this.responseSuccessRefresh('添加购物卡成功！') },
                    () => { },
                    this.parentFailHandel
                );
            }
        }
    }
    //删除
    private delVipCard(vipCardId: string) {
        if (ChatCheck.isEmpty(vipCardId)) {
            MessageControl.open("购物卡id不得为空！", MessageType.WARNING);
        } else {
            new Ajax(VipCardUrls.delVipCardUrl).config().sendJson({
                vipCardId
            }).result(
                () => { this.responseSuccessRefresh('删除购物卡成功！', false) },
                () => { },
                this.parentFailHandel
            );
        }
    }
}
PublicMainView.run(new VipCardManage(7));