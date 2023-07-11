import { PublicMainView } from "../../components/PublicMainView.js";
import { MerchantUrls } from "../../api/ApiConstant.js";
import { MerchantType } from "../../interface/Types.js";
import { CustomTableControl } from "../../components/CustomTableControl.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
import Ajax from "../../utils/Ajax.js";
import ParentMain from "../ParentMain.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
//商户管理
class MerchantManage extends ParentMain {
    //每个管理类，只需要处理自己的事件即可
    getEvent(): Function {
        return () => {
            const mainHtml = document.querySelector('main') as HTMLElement;
            //修改信息的组件
            const merchantModifyBoxEl = document.querySelector('.merchant-modify-component-box') as HTMLElement;
            const merchantIdValueEl = document.querySelector('.merchant-modify-box .merchan-text-value') as HTMLElement;
            const merchantModifyInputEls = document.querySelectorAll('.merchant-modify-box .merchant-input > input') as unknown as Array<HTMLInputElement>;
            const merchantModifyAreaEls = document.querySelectorAll('.merchant-modify-box .merchant-text-area > textarea') as unknown as Array<HTMLTextAreaElement>;
            const merchantModifySelectEl = document.querySelector('.merchant-modify-box .select-check-value') as HTMLElement;
            const merchantModifyButtonEl = document.querySelector('.merchant-modify-box .merchant-modify-button') as HTMLElement;
            //添加信息的组件
            const merchantAddBoxEl = document.querySelector('.merchant-add-component-box') as HTMLElement;
            const merchantAddInputEls = document.querySelectorAll('.merchant-add-box .merchant-input > input') as unknown as Array<HTMLInputElement>;
            const merchantAddAreaEls = document.querySelectorAll('.merchant-add-box .merchant-text-area > textarea') as unknown as Array<HTMLTextAreaElement>;
            const merchantAddSelectEl = document.querySelector('.merchant-add-box .select-check-value') as HTMLElement;
            const merchantAddButtonEl = document.querySelector('.merchant-add-box .merchant-add-button') as HTMLElement;
            //修改信息点击事件
            merchantModifyButtonEl.addEventListener('click', () => {
                this.modifyMerchant(merchantIdValueEl, merchantModifySelectEl, merchantModifyInputEls, merchantModifyAreaEls);
            });
            //新增信息点击事件
            merchantAddButtonEl.addEventListener('click', () => {
                this.addMerchant(merchantAddSelectEl, merchantAddInputEls, merchantAddAreaEls);
            });
            this.getMerchantData([mainHtml,
                merchantModifyBoxEl, merchantIdValueEl, ...merchantModifyInputEls, ...merchantModifyAreaEls, merchantModifySelectEl,
                merchantAddBoxEl, ...merchantAddInputEls, ...merchantAddAreaEls, merchantAddSelectEl
            ]);
        };
    }
    //获取商户数据
    getMerchantData(els: Array<HTMLElement>) {
        new Ajax(MerchantUrls.adminAllDataUrl).config().sendEmpty().result(
            (data: Array<MerchantType>) => {
                this.createTable(els, data);
            },
            () => { },
            this.parentFailHandel
        );
    }
    //创建表格
    createTable(els: HTMLElement[], data: MerchantType[], index: number = -1, value: string = '', currentPageNum: number = 1) {
        const tableTitleData = ['商户id', '商户号', '商户名字', '商户类型', '平台地址', '支付平台应用id', '商户公钥', '商户私钥', '内容加密密码', '回调地址'];
        const tableContentData = [] as Array<Array<string>>;
        data.forEach(merchantData => {
            tableContentData.push([merchantData.merchantId, merchantData.merchantCode, merchantData.merchantName, merchantData.merchantType, merchantData.serverUrl, merchantData.payPlatformAppId, merchantData.publicKey, merchantData.privateKey, merchantData.encryptPassword, merchantData.callbackUrl]);
        });
        //过滤重组一个新的表
        const tableContentDataBak = [] as Array<Array<string>>;
        if (index != -1 && value != '') {
            tableContentData.forEach(merchantData => {
                if (merchantData[index].indexOf(value) != -1) {
                    tableContentDataBak.push(merchantData);
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
                CustomComponentBoxControl.open(els[12], () => {
                    for (let i = 13; i <= 20; i++) {
                        (els[i] as HTMLInputElement).value = '';
                    }
                });
            }, true, (index: number, value: string) => {
                //查询事件，得到下标和文本框的值,然后重新生成表格
                this.setInputIndex(index);
                this.setInputValue(value);
                this.createTable(els, data, index, value);
            }, 1, 3, 4)
            .configUpdateAndDelete(true, (data: Array<string>) => {
                //更新点击回调,无需挂载，因为外层已经处理了
                els[2].innerText = data[0];
                (els[3] as HTMLInputElement).value = data[1];
                (els[4] as HTMLInputElement).value = data[2];
                els[11].innerText = data[3];
                (els[5] as HTMLInputElement).value = data[4];
                (els[6] as HTMLInputElement).value = data[5];
                (els[9] as HTMLTextAreaElement).value = data[6];
                (els[10] as HTMLTextAreaElement).value = data[7];
                (els[7] as HTMLInputElement).value = data[8];
                (els[8] as HTMLInputElement).value = data[9];
                CustomComponentBoxControl.open(els[1]);
            }, true, (data: Array<string>) => {
                DelTipsBoxControl.open("确定要删除此商户吗？", () => {
                    this.deleteMerchan(data[0]);
                });
            }).create(els[0], null, tableData, [], currentPageNum);
    }
    //修改商户
    private modifyMerchant(merchantIdEl: HTMLElement, merchantTypeEl: HTMLElement, inputEls: Array<HTMLInputElement>, textAreaEls: Array<HTMLTextAreaElement>) {
        if (ChatCheck.isEmpty(merchantIdEl.innerText) ||
            ChatCheck.isEmpty(merchantTypeEl.innerText) ||
            ChatCheck.isEmpty(inputEls[1].value) ||
            ChatCheck.isEmpty(inputEls[2].value) ||
            ChatCheck.isEmpty(inputEls[5].value)) {
            MessageControl.open("必要信息【商户id、商户类型、商户名字、平台地址、回调地址】不得为空！", MessageType.WARNING);
        } else {
            new Ajax(MerchantUrls.modifyMerchantUrl).config().sendJson({
                merchantId: merchantIdEl.innerText,
                merchantCode: inputEls[0].value,
                merchantName: inputEls[1].value,
                merchantType: merchantTypeEl.innerText,
                serverUrl: inputEls[2].value,
                payPlatformAppId: inputEls[3].value,
                publicKey: textAreaEls[0].value,
                privateKey: textAreaEls[1].value,
                encryptPassword: inputEls[4].value,
                callbackUrl: inputEls[5].value
            }).result(
                () => { this.responseSuccessRefresh('修改商户成功！') },
                () => { },
                this.parentFailHandel
            );
        }
    }
    //新增商户
    private addMerchant(merchantTypeEl: HTMLElement, inputEls: Array<HTMLInputElement>, textAreaEls: Array<HTMLTextAreaElement>) {
        if (ChatCheck.isEmpty(merchantTypeEl.innerText) ||
            ChatCheck.isEmpty(inputEls[1].value) ||
            ChatCheck.isEmpty(inputEls[2].value) ||
            ChatCheck.isEmpty(inputEls[5].value)) {
            MessageControl.open("必要信息【商户类型、商户名字、平台地址、回调地址】不得为空！", MessageType.WARNING);
        } else {
            new Ajax(MerchantUrls.addMerchantUrl).config().sendJson({
                merchantCode: inputEls[0].value,
                merchantName: inputEls[1].value,
                merchantType: merchantTypeEl.innerText,
                serverUrl: inputEls[2].value,
                payPlatformAppId: inputEls[3].value,
                publicKey: textAreaEls[0].value,
                privateKey: textAreaEls[1].value,
                encryptPassword: inputEls[4].value,
                callbackUrl: inputEls[5].value
            }).result(
                () => { this.responseSuccessRefresh('添加商户成功！') },
                () => { },
                this.parentFailHandel
            );
        }
    }
    //删除商户
    private deleteMerchan(merchantId: string) {
        if (merchantId.trim().length <= 0) {
            MessageControl.open("商户id不得为空！", MessageType.WARNING);
        } else {
            new Ajax(MerchantUrls.delMerchantByIdUrl).config().sendJson({
                merchantId
            }).result(
                () => { this.responseSuccessRefresh('删除商户成功！', false) },
                () => { },
                this.parentFailHandel
            );
        }
    }
}
PublicMainView.run(new MerchantManage(6));