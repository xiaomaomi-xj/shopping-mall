//颜色控制，登录页太单一了，只能加点颜色变化了
//自己使用，不导出
enum ColorType {
    ONE_LEVEL = '#555',
    TWO_LEVEL = '#888',
    THREE_LEVEL = '#bbb'
}
class LoginColor {
    private userInputEl;
    private passwordInputEl;
    private colorEls;
    private bgColorEls;
    private borderColorsEls
    constructor(userInputEl: HTMLInputElement, passwordInputEl: HTMLInputElement, colorEls: Array<HTMLElement>, bgColorEls: Array<HTMLElement>, borderColorsEls: Array<HTMLElement>) {
        this.userInputEl = userInputEl;
        this.passwordInputEl = passwordInputEl;
        this.colorEls = colorEls;
        this.bgColorEls = bgColorEls;
        this.borderColorsEls = borderColorsEls;
    }
    //运行
    run() {
        this.handelEvent();
    }
    //处理事件
    private handelEvent() {
        this.userInputEl.addEventListener('input', () => {
            this.colorLevel();
        });
        this.passwordInputEl.addEventListener('input', () => {
            this.colorLevel();
        });
    }
    colorLevel() {
        if (this.userInputEl.value.trim().length != 0) {
            if (this.passwordInputEl.value.trim().length != 0) {
                //三级亮度
                this.colorEls.map(v => v.style.color = ColorType.THREE_LEVEL);
                this.bgColorEls.map(v => v.style.backgroundColor = ColorType.THREE_LEVEL);
                this.borderColorsEls.map(v => v.style.borderColor = ColorType.THREE_LEVEL);
            } else {
                //二级亮度
                this.colorEls.map(v => v.style.color = ColorType.TWO_LEVEL);
                this.bgColorEls.map(v => v.style.backgroundColor = ColorType.TWO_LEVEL);
                this.borderColorsEls.map(v => v.style.borderColor = ColorType.TWO_LEVEL);
            }
        } else {
            if (this.passwordInputEl.value.trim().length != 0) {
                //二级亮度
                this.colorEls.map(v => v.style.color = ColorType.TWO_LEVEL);
                this.bgColorEls.map(v => v.style.backgroundColor = ColorType.TWO_LEVEL);
                this.borderColorsEls.map(v => v.style.borderColor = ColorType.TWO_LEVEL);
            } else {
                //一级亮度
                this.colorEls.map(v => v.style.color = ColorType.ONE_LEVEL);
                this.bgColorEls.map(v => v.style.backgroundColor = ColorType.ONE_LEVEL);
                this.borderColorsEls.map(v => v.style.borderColor = ColorType.ONE_LEVEL);
            }
        }
    }
}
export default LoginColor;