/* 根据img标签的src内容变成file文件对象，方便异步上传文件 */
export class SrcToFile {
    //检验图片是不是初始图片
    public static check(src: string): boolean {
        if (src.indexOf('/src/bak-img.jpg') != -1) {
            return false;
        }
        return true;
    }
    //对于多个异步获取file，提供一个方法
    public static moreSrcToFile(srcs: Array<string>, files: Array<File>, successCallBackFun: Function, failCallBackFun: Function) {
        if (srcs.length > 0) {
            const src = srcs.shift() as string;
            this.srcToFile(src, (file: File) => {
                files.push(file);
                this.moreSrcToFile(srcs, files, successCallBackFun, failCallBackFun);
            }, () => {
                failCallBackFun();
            });
        } else {
            successCallBackFun();
        }
    }
    //根据src链接生成file对象
    private static srcToFile(src: string, successCallBackFun: Function, failCallBackFun: Function) {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', src, true);
        xhr.responseType = 'blob';
        xhr.onload = () => {
            if (xhr.status == 200) {
                const blob = xhr.response;
                if ((blob.type as string).indexOf('image/') == 0) {
                    const fileName = this.createFileName(blob.type);
                    successCallBackFun(new File([blob], fileName, { type: blob.type }));
                } else {
                    failCallBackFun();
                }
            } else {
                failCallBackFun();
            }
        };
        xhr.send();
    }
    //生成文件名字
    private static createFileName(type: string): string {
        //前缀要保证唯一
        const filePrefix = new Date().getTime() + "" + Math.floor(Math.random() * 100000);
        const fileSuffix = '.'.concat(type.split("/")[1]);
        return filePrefix + fileSuffix;
    }
}