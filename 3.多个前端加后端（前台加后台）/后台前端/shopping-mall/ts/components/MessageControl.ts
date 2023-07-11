//message组件，用于提示消息
//组件类型，决定背景颜色
export enum MessageType {
    INFO = '#2f2f30',
    ERROR = '#432d2d',
    SUCCESS = '#18351c',
    WARNING = '#4e3c26'
}
export class MessageControl {
    private static messageEl;
    static {
        this.messageEl = document.createElement('div');
        this.messageEl.className = 'message';
    }
    //挂载
    static mount(el: HTMLElement) {
        el.appendChild(this.messageEl);
    }
    //卸载
    static unMount(el: HTMLElement) {
        el.removeChild(this.messageEl);
    }
    //创建子组件
    private static createChild(message: string, type: MessageType): HTMLElement {
        const resultEl = document.createElement('div');
        resultEl.className = 'message-item';
        resultEl.style.backgroundColor = type;
        resultEl.innerText = message;
        const closeEl = document.createElement('div');
        closeEl.className = 'message-close';
        const closeLeftChild = document.createElement('div');
        closeLeftChild.className = 'message-left';
        const closeRightChild = document.createElement('div');
        closeRightChild.className = 'message-right';
        closeEl.appendChild(closeLeftChild)
        closeEl.appendChild(closeRightChild);
        resultEl.appendChild(closeEl);
        this.bindEvent(closeEl, resultEl);
        return resultEl;
    }
    //绑定事件
    private static bindEvent(closeEl: HTMLElement, childEl: HTMLElement) {
        const handelFun = () => {
            this.close(childEl);
        }
        closeEl.addEventListener('click', handelFun);
        //清除掉监听
        setTimeout(() => {
            closeEl.removeEventListener('click', handelFun);
        }, 2400);
    }
    //自动关闭
    private static autoClose(childEl: HTMLElement) {
        setTimeout(() => {
            childEl.style.opacity = '0';
        }, 2200);
        setTimeout(() => {
            try {
                this.messageEl.removeChild(childEl);
            } catch (error) {
                //手动删除的话，会影响自动删除，会抛异常，所以要捕捉
            }
        }, 2500);
    }
    //开启
    static open(message: string, type: MessageType) {
        const childEl = this.createChild(message, type);
        this.messageEl.appendChild(childEl);
        this.autoClose(childEl);
    }
    //关闭
    static close(el: HTMLElement) {
        this.messageEl.removeChild(el);
    }
}