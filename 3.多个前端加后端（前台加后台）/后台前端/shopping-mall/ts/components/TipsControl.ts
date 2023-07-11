//文字提示组件
class TipsControl {
    private static taskId = -1;
    private static tipssEl
    static {
        //构造提示信息组件
        this.tipssEl = document.createElement('span');
        this.tipssEl.className = 'tips-message';
    }
    //挂载(接受在谁之前插入)
    static mount(el: HTMLElement, beforeEl: HTMLElement|null) {
        el.insertBefore(this.tipssEl, beforeEl);
    }
    //显示
    static show(message: string = '账号或密码不得为空！') {
        clearTimeout(this.taskId);
        this.tipssEl.innerText = message;
        this.tipssEl.style.opacity = '1';
        this.hide();
    }
    //隐藏
    private static hide() {
        this.taskId = setTimeout(() => {
            //3s自动隐藏
            this.tipssEl.style.opacity = '0';
        }, 3000);
    }
}
export default TipsControl;