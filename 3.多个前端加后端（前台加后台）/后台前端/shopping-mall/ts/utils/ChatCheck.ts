/* 表情字符检测(初略判断) */
export class ChatCheck {
    //表情字符
    private static MAX_SIZE = 55000;

    //检验是不是表情字符
    public static check(str: string): boolean {
        if (str.charCodeAt(0) >= this.MAX_SIZE) {
            return true;
        }
        return false;
    }

    //判断字符串不为空
    public static isEmpty(str: string): boolean {
        if (str == null || str == undefined) {
            return true;
        }
        if (str.trim().length <= 0) {
            return true;
        }
        return false;
    }
}