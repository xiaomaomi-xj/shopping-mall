//可接受自定义的浮层
class CustomComponentBoxControl {
    private static customComponentBoxEl;
    private static callbackFun: Function;
    static {
        this.customComponentBoxEl = document.createElement('div');
        this.customComponentBoxEl.className = 'custom-component-box';
    }
    //挂载
    static mount(el: HTMLElement) {
        el.appendChild(this.customComponentBoxEl);
    }
    //卸载
    static unMount(el: HTMLElement) {
        el.removeChild(this.customComponentBoxEl);
    }
    //创建节点
    private static createNode(componentEl: HTMLElement): HTMLElement {
        const mainEl = document.createElement('div');
        mainEl.className = 'custom-component-box-main';
        const headEl = document.createElement('div');
        headEl.className = 'custom-component-box-main-head';
        const closeEl = document.createElement('div');
        closeEl.className = 'custom-component-box-main-head-close';
        closeEl.addEventListener('click', this.close.bind(this));
        const leftEl = document.createElement('div');
        leftEl.className = 'custom-component-box-main-head-close-left';
        const rightEl = document.createElement('div');
        rightEl.className = 'custom-component-box-main-head-close-right';
        const bodyEl = document.createElement('div');
        bodyEl.className = 'custom-component-box-main-body';
        //body里面填传过来的组件
        bodyEl.appendChild(componentEl);
        closeEl.appendChild(leftEl);
        closeEl.appendChild(rightEl);
        headEl.appendChild(closeEl);
        mainEl.appendChild(headEl);
        mainEl.appendChild(bodyEl);
        return mainEl;
    }
    //开启
    static open(componentEl: HTMLElement, callbackFun: Function = () => { }) {
        //可以接受一个关闭回调函数
        this.callbackFun = callbackFun;
        //先清空
        this.customComponentBoxEl.innerHTML = '';
        //先让其显示,去掉类名custom-component
        componentEl.className = componentEl.className.replace('custom-component ', '')
        this.customComponentBoxEl.appendChild(this.createNode(componentEl));
        this.customComponentBoxEl.style.display = 'flex';
        setTimeout(() => {
            this.customComponentBoxEl.style.opacity = '1';
        });
    }
    //关闭
    public static close() {
        //关闭的时候，可以执行一个函数,但是不能影响下面的业务
        setTimeout(() => {
            this.callbackFun();
        });
        this.customComponentBoxEl.style.opacity = '0';
        setTimeout(() => {
            this.customComponentBoxEl.style.display = 'none';
            this.customComponentBoxEl.innerHTML = '';
        }, 600);
    }
}
export default CustomComponentBoxControl;