//对密码的显示和隐藏控制
class PasswordKeyControl {
    //运行
    public static run() {
        //拿取所有，因为是固定的，所以可以直接拿取
        const passwordIconEls = document.querySelectorAll('.input-box .password') as unknown as Array<HTMLElement>;
        passwordIconEls.forEach(passwordIconEl => {
            const headerEl = passwordIconEl.firstElementChild as HTMLElement;
            const inputEl = passwordIconEl.nextElementSibling as HTMLInputElement;
            passwordIconEl.addEventListener('click', () => {
                headerEl.className.indexOf('open') == -1 ? this.show(headerEl, inputEl) : this.hide(headerEl, inputEl);
            });
        });
    }

    //显示密码
    private static show(el: HTMLElement, inputEl: HTMLInputElement) {
        el.className += ' open';
        inputEl.type = 'text';
    }
    //隐藏密码
    private static hide(el: HTMLElement, inputEl: HTMLInputElement) {
        el.className = el.className.replace(' open', '');
        inputEl.type = 'password';
    }
}
export default PasswordKeyControl;