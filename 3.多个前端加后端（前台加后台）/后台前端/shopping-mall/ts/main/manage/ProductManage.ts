import { PublicMainView } from "../../components/PublicMainView.js";
import Ajax from "../../utils/Ajax.js";
import { CustomTableControl } from "../../components/CustomTableControl.js";
import { GoodsUrls } from "../../api/ApiConstant.js";
import { GoodsType } from "../../interface/Types.js";
import ParentMain from "../ParentMain.js";
import CustomComponentBoxControl from "../../components/CustomComponentBoxControl.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { SrcToFile } from "../../utils/SrcToFile.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
//商品管理
class ProductManage extends ParentMain {
    //每个管理类，只需要处理自己的事件即可
    getEvent(): Function {
        return () => {
            const mainHtml = document.querySelector('main') as HTMLElement;
            //添加
            const productAddBoxEl = document.querySelector('.product-manage-add-component-box') as HTMLElement;
            const productAddInputEls = document.querySelectorAll('.product-manage-add-box .product-manage-input-box input') as unknown as Array<HTMLInputElement>;
            const productAddareaEl = document.querySelector('.product-manage-add-box textarea') as HTMLTextAreaElement;
            const productAddImgEls = document.querySelectorAll('.product-manage-add-box .file-update-box-img') as unknown as Array<HTMLImageElement>;
            const productAddBthEl = document.querySelector('.product-manage-add-button') as HTMLButtonElement;
            //添加事件
            productAddBthEl.addEventListener('click', () => {
                this.addGoods(productAddInputEls, productAddareaEl, productAddImgEls);
            });
            //修改
            const productModifyBoxEl = document.querySelector('.product-manage-modify-component-box') as HTMLElement;
            const productModifyIdEl = document.querySelector('.product-manage-modify-box .product-manage-text-value') as HTMLElement;
            const productModifyInputEls = document.querySelectorAll('.product-manage-modify-box .product-manage-input-box input') as unknown as Array<HTMLInputElement>
            const productModifyareaEl = document.querySelector('.product-manage-modify-box textarea') as HTMLTextAreaElement;
            const productModifyImgEls = document.querySelectorAll('.product-manage-modify-box .file-update-box-img') as unknown as Array<HTMLImageElement>;
            const productModifyBthEl = document.querySelector('.product-manage-modify-button') as HTMLButtonElement;
            //修改事件
            productModifyBthEl.addEventListener('click', () => {
                this.modifyGoods(productModifyIdEl, productModifyInputEls, productModifyareaEl, productModifyImgEls);
            });
            this.getGoodsData([mainHtml, productAddBoxEl, ...productAddInputEls, productAddareaEl, ...productAddImgEls,
                productModifyBoxEl, productModifyIdEl, ...productModifyInputEls, productModifyareaEl, ...productModifyImgEls]);
        };
    }
    //获取商品数据
    private getGoodsData(els: Array<HTMLElement>) {
        new Ajax(GoodsUrls.allDataUrl).config().sendEmpty().result(
            (data: Array<GoodsType>) => {
                this.createTable(els, data);
            },
            () => { },
            this.parentFailHandel
        );
    }
    //生成表格数据
    public createTable(els: Array<HTMLElement>, data: Array<GoodsType>, index: number = -1, value: string = '', currentPageNum: number = 1) {
        const tableTitleData = ['商品id', '商品封面', '商品名字', '商品描述', '商品价格', '库存'];
        const tableContentData = [] as Array<Array<string>>;
        let maxImgNum = 0;
        data.forEach(goodsData => {
            const contentData: Array<string> = [goodsData.goodsId, goodsData.imgUrl, goodsData.goodsName, goodsData.goodsDescribe, goodsData.goodsPrice.toString(), goodsData.maxBuyNum.toString()];
            if (goodsData.rotationGoodsImgs.length > maxImgNum) {
                maxImgNum = goodsData.rotationGoodsImgs.length;
            }
            goodsData.rotationGoodsImgs.forEach(img => contentData.push(img));
            tableContentData.push(contentData);
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
        //需要显示的图片下标
        const imgIndexs = [];
        for (let i = 1; i <= maxImgNum; i++) {
            imgIndexs.push(5 + i);
            tableTitleData.push(`详情图片${i}`);
        }
        const tableData = {
            title: tableTitleData,
            content: (index != -1 && value != '') ? tableContentDataBak : tableContentData
        };
        //生成表格
        CustomTableControl
            .configValue(index, value)
            .configHaveImgOrHavepageNum(true, true, (currentPageNum: number) => {
                //页码事件
                this.createTable(els, data, this.getInputIndex(), this.getInputValue(), currentPageNum);
            })
            .configQueryAndAdd(true, () => {
                //添加
                CustomComponentBoxControl.open(els[1], () => {
                    //还原
                    for (let i = 2; i <= 10; i++) {
                        if (i <= 5) {
                            (els[i] as HTMLInputElement).value = '';
                        } else {
                            (els[i].previousElementSibling as HTMLInputElement).value = '';
                            (els[i] as HTMLImageElement).src = '../src/bak-img.jpg';
                        }
                    }
                });
            }, true, (index: number, value: string) => {
                //查询事件，得到下标和文本框的值,然后重新生成表格
                this.setInputIndex(index);
                this.setInputValue(value);
                this.createTable(els, data, index, value);
            }, 1, 3)
            .configUpdateAndDelete(true, (data: Array<string>) => {
                //更新点击回调
                els[12].innerText = data[0];
                (els[13] as HTMLInputElement).value = data[2];
                (els[14] as HTMLInputElement).value = data[4];
                (els[15] as HTMLInputElement).value = data[5];
                (els[16] as HTMLInputElement).value = data[3];
                (els[17] as HTMLImageElement).src = data[1];
                for (let i = 6; i < data.length - 1; i++) {
                    if (ChatCheck.isEmpty(data[i])) {
                        (els[12 + i] as HTMLImageElement).src = '../src/bak-img.jpg';
                    } else {
                        (els[12 + i] as HTMLImageElement).src = data[i];
                    }
                }
                CustomComponentBoxControl.open(els[11]);
            }, true, (data: Array<string>) => {
                //删除点击回调
                DelTipsBoxControl.open('确定要删除此商品吗？<br /> <p style="color:red;font-size:.9em;margin:1em;">（注：删除商品也会删除与之关联的购物车、订单、图片数据以及可能会影响界面！)</p>', () => {
                    this.delGoods(data[0]);
                });
            }).create(els[0], null, tableData, [1, ...imgIndexs], currentPageNum);
    }
    //添加商品
    addGoods(productAddInputEls: Array<HTMLInputElement>, productAddareaEl: HTMLTextAreaElement, productAddImgEls: Array<HTMLImageElement>) {
        const goodsName = productAddInputEls[0].value;
        const goodsDescribe = productAddareaEl.value;
        const goodsPrice = productAddInputEls[1].value;
        const maxBuyNum = productAddInputEls[2].value;
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
        const mainImgUrl = productAddImgEls[0].src;
        if (!SrcToFile.check(mainImgUrl)) {
            MessageControl.open("商品封面图片必须上传！", MessageType.WARNING);
            return;
        }
        const imgSrcs = [] as Array<string>;
        productAddImgEls.forEach(img => {
            if (SrcToFile.check(img.src)) {
                imgSrcs.push(img.src);
            }
        });
        if (imgSrcs.length < 2) {
            MessageControl.open("商品详情图片最少上传一个！", MessageType.WARNING);
            return;
        }
        const formData = new FormData();
        const imgFiles = [] as Array<File>;
        formData.append('goodsName', goodsName);
        formData.append('goodsDescribe', goodsDescribe);
        formData.append('goodsPrice', goodsPrice);
        formData.append('maxBuyNum', maxBuyNum);
        SrcToFile.moreSrcToFile(imgSrcs, imgFiles, () => {
            imgFiles.forEach(imgFile => {
                formData.append('files', imgFile);
            });
            //提交
            this.addGoodsSubmit(formData);
        }, () => {
            MessageControl.open("文件上传错误，请检查文件类型！", MessageType.WARNING);
        });
    }
    //添加商品提交
    private addGoodsSubmit(formData: FormData) {
        new Ajax(GoodsUrls.addGoodsUrl).config(false).sendFormData(formData).result(
            () => { this.responseSuccessRefresh('添加商品成功！') },
            () => { },
            this.parentFailHandel
        );
    }
    //修改商品
    modifyGoods(productModifyIdEl: HTMLElement, productModifyInputEls: HTMLInputElement[], productModifyareaEl: HTMLTextAreaElement, productModifyImgEls: HTMLImageElement[]) {
        const goodsId = productModifyIdEl.innerText;
        const goodsName = productModifyInputEls[0].value;
        const goodsDescribe = productModifyareaEl.value;
        const goodsPrice = productModifyInputEls[1].value;
        const maxBuyNum = productModifyInputEls[2].value;
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
        const mainImgUrl = productModifyImgEls[0].src;
        if (!SrcToFile.check(mainImgUrl)) {
            MessageControl.open("商品封面图片必须上传！", MessageType.WARNING);
            return;
        }
        const imgSrcs = [] as Array<string>;
        productModifyImgEls.forEach(img => {
            if (SrcToFile.check(img.src)) {
                imgSrcs.push(img.src);
            }
        });
        if (imgSrcs.length < 2) {
            MessageControl.open("商品详情图片最少上传一个！", MessageType.WARNING);
            return;
        }
        const formData = new FormData();
        const imgFiles = [] as Array<File>;
        formData.append('goodsId', goodsId);
        formData.append('goodsName', goodsName);
        formData.append('goodsDescribe', goodsDescribe);
        formData.append('goodsPrice', goodsPrice);
        formData.append('maxBuyNum', maxBuyNum);
        SrcToFile.moreSrcToFile(imgSrcs, imgFiles, () => {
            imgFiles.forEach(imgFile => {
                formData.append('files', imgFile);
            });
            //提交
            this.modifyGoodsSubmit(formData);
        }, () => {
            MessageControl.open("文件上传错误，请检查文件类型！", MessageType.WARNING);
        });
    }
    //修改商品提交事件
    private modifyGoodsSubmit(formData: FormData) {
        new Ajax(GoodsUrls.modifyGoodsUrl).config(false).sendFormData(formData).result(
            () => { this.responseSuccessRefresh('修改商品成功！') },
            () => { },
            this.parentFailHandel
        );
    }
    //删除商品
    delGoods(id: string) {
        if (ChatCheck.isEmpty(id)) {
            MessageControl.open("商品id不能为空！", MessageType.ERROR);
            return;
        }
        new Ajax(GoodsUrls.delGoodsUrl).config().sendJson({
            id
        }).result(
            () => { this.responseSuccessRefresh('删除商品成功！') },
            () => { /*默认有处理*/ },
            this.parentFailHandel
        );
    }
}
PublicMainView.run(new ProductManage(1));