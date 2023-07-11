import { ImgComponentBoxControl } from "./ImgComponentBoxControl.js";
import { MessageControl, MessageType } from "./MessageControl.js";

//控制上传按钮
export class UploadImgComponentControl {
    //获取的所有对象
    private static fileUpdateEls: Array<HTMLElement>;
    private static acceptFileType = ['.bmp', '.jpg', '.png', '.gif', '.jpeg'];
    //最大2mb
    private static acceptFileSize = 2097152;
    public static run() {
        this.fileUpdateEls = document.querySelectorAll('.file-update-box') as unknown as Array<HTMLElement>;
        this.fileUpdateEls.forEach(fileUpdateEl => {
            this.bindEvent(fileUpdateEl);
        });
    }
    //用于动态添加上传文件按钮后使其正常工作
    public static add(fileUpdateEl: HTMLElement) {
        //加一个微任务延时
        setTimeout(() => {
            this.bindEvent(fileUpdateEl);
        });
    }
    private static bindEvent(fileUpdateEl: HTMLElement) {
        const inputEl = fileUpdateEl.firstElementChild as HTMLInputElement;
        //限制文件类型
        inputEl.accept = this.acceptFileType.join(',');
        const imgEl = inputEl.nextElementSibling as HTMLImageElement;
        const extendEl = imgEl.nextElementSibling as HTMLElement;
        const selectEl = extendEl.nextElementSibling as HTMLElement;
        extendEl.addEventListener('click', () => {
            this.extendImg(imgEl.src);
        });
        selectEl.addEventListener('click', () => {
            this.selectInput(inputEl);
        });
        inputEl.addEventListener('change', () => {
            this.clickSelectImg(inputEl, imgEl);
        });
        fileUpdateEl.addEventListener('drop', e => {
            this.dropSelectImg(e, imgEl);
        });
        fileUpdateEl.addEventListener('dragover', e => {
            this.dragoverEvent(e);
        });
    }
    //放大图片
    private static extendImg(imgSrc: string) {
        ImgComponentBoxControl.open(imgSrc);
    }
    //选择图片
    private static selectInput(inputEl: HTMLInputElement) {
        inputEl.click();
    }
    //点击选择图片事件
    private static clickSelectImg(inputEl: HTMLInputElement, imgEl: HTMLImageElement) {
        const file = (inputEl.files as FileList)[0];
        this.showImg(file, imgEl);
    }
    //拖动选择图片事件
    private static dropSelectImg(e: DragEvent, imgEl: HTMLImageElement) {
        e.preventDefault();
        const file = (e.dataTransfer as DataTransfer).files[0];
        this.showImg(file, imgEl);
    }
    //拖动结束事件
    private static dragoverEvent(e: DragEvent) {
        e.preventDefault();
    }
    //显示图片文件
    private static showImg(file: File, imgEl: HTMLImageElement) {
        const fileName = file.name.toLowerCase();
        if (this.acceptFileType.indexOf(fileName.substring(fileName.lastIndexOf("."))) == -1) {
            MessageControl.open("只支持【" + this.acceptFileType.join(",") + "】类型的文件", MessageType.ERROR);
        } else if (file.size > this.acceptFileSize) {
            MessageControl.open("文件大小不得超过【" + this.acceptFileSize / 1024 / 1024 + "MB】", MessageType.ERROR);
        } else {
            imgEl.src = URL.createObjectURL(file);
        }
    }
}