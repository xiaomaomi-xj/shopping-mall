//删除按钮的弹窗,为了解决请求和删除按钮引起的弹窗冲突
export class DelTipsBoxControl {
    private static delTipsBoxEl;
    static {
        this.delTipsBoxEl = document.createElement('div');
        this.delTipsBoxEl.className = 'del-tips-box';
    }
    //挂载
    static mount(el: HTMLElement) {
        el.appendChild(this.delTipsBoxEl);
    }
    //卸载
    static unMount(el: HTMLElement) {
        el.removeChild(this.delTipsBoxEl);
    }
    //创建节点
    private static createNode(message: string, callbackFun: Function): HTMLElement {
        const mainEl = document.createElement('div');
        mainEl.className = 'del-tips-box-main';
        const headerEl = document.createElement('div');
        headerEl.className = 'del-tips-box-header';
        const tipsEl = document.createTextNode("特别提示");
        headerEl.appendChild(tipsEl);
        mainEl.appendChild(headerEl);
        const bodyEl = document.createElement('div');
        bodyEl.className = 'del-tips-box-body';
        bodyEl.innerHTML = message;
        mainEl.appendChild(bodyEl);
        const footerEl = document.createElement('div');
        footerEl.className = 'del-tips-box-footer';
        const cancellBthEl = document.createElement('div');
        cancellBthEl.className = 'del-tips-box-calcel';
        cancellBthEl.innerText = '取消';
        cancellBthEl.addEventListener('click', this.close.bind(this));
        const determineBthEl = document.createElement('div');
        determineBthEl.className = 'del-tips-box-determine';
        determineBthEl.innerText = '确定';
        determineBthEl.addEventListener('click', () => {
            try {
                callbackFun();
            } finally {
                //无论怎样要关闭
                this.close();
            }
        });
        footerEl.appendChild(cancellBthEl);
        footerEl.appendChild(determineBthEl);
        mainEl.appendChild(footerEl);
        return mainEl;
    }
    //开启
    static open(message: string, callbackFun: Function) {
        //先清空
        this.delTipsBoxEl.innerHTML = '';
        this.delTipsBoxEl.appendChild(this.createNode(message, callbackFun));
        this.delTipsBoxEl.style.display = 'flex';
        setTimeout(() => {
            this.delTipsBoxEl.style.opacity = '1';
        });
    }
    //关闭
    private static close() {
        this.delTipsBoxEl.style.opacity = '0';
        setTimeout(() => {
            this.delTipsBoxEl.style.display = 'none';
            this.delTipsBoxEl.innerHTML = '';
        }, 600);
    }
}