import { ExtraConfigUrls } from "../../api/ApiConstant.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { PublicMainView } from "../../components/PublicMainView.js";
import { ExtraConfigType } from "../../interface/Types.js";
import Ajax from "../../utils/Ajax.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
import { SrcToFile } from "../../utils/SrcToFile.js";
import ParentMain from "../ParentMain.js";
//页面管理
class ExtraConfigManage extends ParentMain {
    //每个管理类，只需要处理自己的事件即可
    getEvent(): Function {
        return () => {
            const extraConfigIdEl = document.querySelector('.extra-config-manage-box-content-text-box-value') as HTMLElement;
            const extraConfigInputEls = document.querySelectorAll('.extra-config-manage-box-content-input-box-input > input') as unknown as Array<HTMLInputElement>;
            const extraConfigSelectEl = document.querySelector('.extra-config-manage-box-content-select-box-select .select-check-value') as HTMLElement;
            const extraConfigImgEls = document.querySelectorAll('.extra-config-manage-box-content-bg-box-bg .file-update-box-img') as unknown as Array<HTMLImageElement>;
            const extraConfigBtnEl = document.querySelector('.extra-config-manage-box-button-box > button') as HTMLButtonElement;
            new Ajax(ExtraConfigUrls.getConfigUrl).config().sendEmpty().result(
                (data: ExtraConfigType) => {
                    extraConfigIdEl.innerText = data.extraId;
                    extraConfigSelectEl.innerText = data.storeNameHaveSpecial ? '含有特殊字' : '不含有特殊字';
                    extraConfigInputEls[0].value = data.passwordTemplateText;
                    extraConfigInputEls[1].value = data.belowPageText;
                    extraConfigInputEls[2].value = data.specialText;
                    extraConfigInputEls[3].value = data.storeName;
                    if (ChatCheck.isEmpty(data.registerBgImgUrl)) {
                        extraConfigImgEls[0].src = '../src/bak-img.jpg';
                    } else {
                        extraConfigImgEls[0].src = data.registerBgImgUrl;
                    }
                    if (ChatCheck.isEmpty(data.loginBgImgUrl)) {
                        extraConfigImgEls[1].src = '../src/bak-img.jpg';
                    } else {
                        extraConfigImgEls[1].src = data.loginBgImgUrl;
                    }
                },
                () => { },
                this.parentFailHandel
            );
            //提交修改
            extraConfigBtnEl.addEventListener('click', () => {
                DelTipsBoxControl.open("确定要进行修改吗？", () => {
                    const extraId = extraConfigIdEl.innerText;
                    const storeNameHaveSpecial = extraConfigSelectEl.innerText == '含有特殊字';
                    const passwordTemplateText = extraConfigInputEls[0].value;
                    if (ChatCheck.check(passwordTemplateText) && passwordTemplateText.length > 2) {
                        MessageControl.open("密码模板只允许一个字符！", MessageType.WARNING);
                        return;
                    } else if (!ChatCheck.check(passwordTemplateText) && passwordTemplateText.length > 1) {
                        MessageControl.open("密码模板只允许一个字符！", MessageType.WARNING);
                        return;
                    }
                    const belowPageText = extraConfigInputEls[1].value;
                    const specialText = extraConfigInputEls[2].value;
                    if (specialText.length > 1) {
                        MessageControl.open("特殊字最多只允许一个字符！", MessageType.WARNING);
                        return;
                    }
                    const storeName = extraConfigInputEls[3].value;
                    const registerBgImgUrl = extraConfigImgEls[0].src;
                    const loginBgImgUrl = extraConfigImgEls[1].src;
                    if (ChatCheck.isEmpty(extraId) ||
                        ChatCheck.isEmpty(passwordTemplateText) ||
                        ChatCheck.isEmpty(belowPageText) ||
                        ChatCheck.isEmpty(specialText) ||
                        ChatCheck.isEmpty(storeName)) {
                        MessageControl.open("必要信息【特殊配置id、密码模板、页脚内容、特殊字，商城名字】不得为空！", MessageType.WARNING);
                        return;
                    }
                    const formData = new FormData();
                    formData.append("extraId", extraId);
                    formData.append("storeNameHaveSpecial", storeNameHaveSpecial + "");
                    formData.append("passwordTemplateText", passwordTemplateText as unknown as string);
                    formData.append("belowPageText", belowPageText);
                    formData.append("specialText", specialText as unknown as string);
                    formData.append("storeName", storeName);
                    if (!SrcToFile.check(registerBgImgUrl) || !SrcToFile.check(loginBgImgUrl)) {
                        MessageControl.open("上传的图片不合格！", MessageType.WARNING);
                        return;
                    }
                    const files = [] as Array<File>;
                    SrcToFile.moreSrcToFile([registerBgImgUrl, loginBgImgUrl], files, () => {
                        files.forEach(file => {
                            formData.append("files", file);
                        });
                        this.modifyExtraConfig(formData);
                    }, () => {
                        MessageControl.open("文件上传错误，请检查文件类型！", MessageType.WARNING);
                    });
                });
            });
        };
    }
    //修改特殊配置
    modifyExtraConfig(formData: FormData) {
        new Ajax(ExtraConfigUrls.fixExtraConfig).config(false).sendFormData(formData).result(
            () => { this.responseSuccessRefresh('修改特殊配置成功！', false) },
            () => { },
            this.parentFailHandel
        );
    }
}
PublicMainView.run(new ExtraConfigManage(8));