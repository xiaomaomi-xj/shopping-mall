/* 用于显示图片，为了避免和imgComponentBoxControl冲突 */
export class ImgComponentBoxControl {
    private static imgComponentBoxEl;
    static {
        this.imgComponentBoxEl = document.createElement('div');
        this.imgComponentBoxEl.className = 'img-component-box';
    }
    //挂载
    static mount(el: HTMLElement) {
        el.appendChild(this.imgComponentBoxEl);
    }
    //卸载
    static unMount(el: HTMLElement) {
        el.removeChild(this.imgComponentBoxEl);
    }
    //创建节点
    private static createNode(componentEl: HTMLElement): HTMLElement {
        const mainEl = document.createElement('div');
        mainEl.className = 'img-component-box-main';
        const headEl = document.createElement('div');
        headEl.className = 'img-component-box-main-head';
        const closeEl = document.createElement('div');
        closeEl.className = 'img-component-box-main-head-close';
        closeEl.addEventListener('click', this.close.bind(this));
        const leftEl = document.createElement('div');
        leftEl.className = 'img-component-box-main-head-close-left';
        const rightEl = document.createElement('div');
        rightEl.className = 'img-component-box-main-head-close-right';
        const bodyEl = document.createElement('div');
        bodyEl.className = 'img-component-box-main-body';
        //body里面填传过来的组件
        bodyEl.appendChild(componentEl);
        closeEl.appendChild(leftEl);
        closeEl.appendChild(rightEl);
        headEl.appendChild(closeEl);
        mainEl.appendChild(headEl);
        mainEl.appendChild(bodyEl);
        return mainEl;
    }
    //创建图片节点
    private static createImgNode(imgSrc: string): HTMLElement {
        const imgBoxEl = document.createElement('div');
        imgBoxEl.style.width = '80vh';
        imgBoxEl.style.height = '50vh';
        imgBoxEl.style.display = 'flex';
        imgBoxEl.style.justifyContent = 'center';
        imgBoxEl.style.alignItems = 'center';
        const imgEl = document.createElement('img');
        imgEl.alt = '图片资源丢失...';
        imgEl.style.maxWidth = '100%';
        imgEl.style.height = '100%';
        imgEl.style.borderRadius = '5px';
        imgEl.src = imgSrc;
        imgBoxEl.appendChild(imgEl);
        return imgBoxEl;
    }
    //开启
    static open(src: string) {
        //先清空
        this.imgComponentBoxEl.innerHTML = '';
        this.imgComponentBoxEl.appendChild(this.createNode(this.createImgNode(src)));
        this.imgComponentBoxEl.style.display = 'flex';
        setTimeout(() => {
            this.imgComponentBoxEl.style.opacity = '1';
        });
    }
    //关闭
    public static close() {
        this.imgComponentBoxEl.style.opacity = '0';
        setTimeout(() => {
            this.imgComponentBoxEl.style.display = 'none';
            this.imgComponentBoxEl.innerHTML = '';
        }, 600);
    }
}