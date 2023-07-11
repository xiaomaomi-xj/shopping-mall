//对下拉框组件进行控制
class SelectComponentControl {
    public static run() {
        const selectCheckEls = document.querySelectorAll('.select-box > .select-check-value') as unknown as Array<HTMLElement>;
        const selectItemBoxEls = document.querySelectorAll('.select-box > .select-item-box') as unknown as Array<HTMLElement>;
        selectCheckEls.forEach((selectCheckEl, index) => {
            for (let i = 0; i < selectItemBoxEls[index].childElementCount; i++) {
                selectItemBoxEls[index].children.item(i)?.addEventListener('click', e => {
                    this.hide(selectItemBoxEls[index]);
                    selectCheckEl.innerText = (e.target as HTMLElement).innerText;
                });
            }
            selectCheckEl.addEventListener('click', () => {
                if (selectItemBoxEls[index].style.opacity == '1') {
                    this.hide(selectItemBoxEls[index]);
                } else {
                    this.show(selectItemBoxEls[index]);
                }

            });
        });
    }
    //隐藏动画
    private static hide(selectItemBoxEl: HTMLElement) {
        selectItemBoxEl.style.opacity = '0';
        setTimeout(() => {
            selectItemBoxEl.style.display = 'none';
        }, 500);
    }
    //显示动画
    private static show(selectItemBoxEl: HTMLElement) {
        selectItemBoxEl.style.display = 'flex';
        setTimeout(() => {
            selectItemBoxEl.style.opacity = '1';
        });
    }
}

export default SelectComponentControl;