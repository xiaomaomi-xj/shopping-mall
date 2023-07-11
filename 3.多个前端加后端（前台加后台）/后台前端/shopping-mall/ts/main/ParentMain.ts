import { PublicMain } from "../components/PublicMainView";
import LocalStorageToken from "../utils/LocalStorageToken.js";
import { MessageControl, MessageType } from "../components/MessageControl.js";
import CustomComponentBoxControl from "../components/CustomComponentBoxControl.js";
import Router from "../utils/Router.js";
//用于主页面的继承,减少重复代码的编写
abstract class ParentMain implements PublicMain {
    private mainEl: HTMLElement;
    private index: number;
    private inputIndex: number;
    private inputValue: string;
    constructor(index: number = 0) {
        //规定，最外层必须是main标签，所以，直接把这件事情做了
        const mainEl = document.querySelector('main') as HTMLElement;
        this.mainEl = mainEl;
        this.index = index;
        this.inputIndex = -1;
        this.inputValue = '';
        //给每个管理界面加上图标
        const iconLinkEl = document.createElement('link');
        iconLinkEl.setAttribute('rel', 'icon');
        iconLinkEl.setAttribute('href', '../src/favicon.ico');
        document.head.appendChild(iconLinkEl);
    }
    //表格页码使用
    public setInputIndex(index: number) {
        this.inputIndex = index;
    }
    public getInputIndex(): number {
        return this.inputIndex;
    }
    //表格页码使用
    public setInputValue(value: string) {
        this.inputValue = value;
    }
    public getInputValue(): string {
        return this.inputValue;
    }
    //事件方法留给子类实现
    public abstract getEvent(): Function

    //主界面
    public getMainEl(): HTMLElement {
        return this.mainEl
    }

    //索引
    public getIndex(): number {
        return this.index;
    }

    //公共的错误方法，一般都会用到，不用也没事，一般指app异常
    public parentFailHandel() {
        //删掉token，跳转登陆页
        LocalStorageToken.delToken();
        LocalStorageToken.delRefreshToken();
        Router.go('/login.html');
    }

    //公共的修改表格数据响应成功刷新页面(如果是messagebox，他自己会关闭，如果是自定义组件，需要在这里关闭)
    public responseSuccessRefresh(message: string, isClose: boolean = true) {
        MessageControl.open(message, MessageType.SUCCESS);
        if (isClose) {
            CustomComponentBoxControl.close();
        }
        setTimeout(() => {
            //直接刷新页面，这样对于内存的释放也会更好，资源重复加载也是很快的,否者还要刷新异步请求
            Router.refresh();
        }, 500);
    }
}
export default ParentMain;