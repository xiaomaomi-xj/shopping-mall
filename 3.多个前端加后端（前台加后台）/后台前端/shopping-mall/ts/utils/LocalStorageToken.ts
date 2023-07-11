//令牌存储
class LocalStorageToken {
    private static token = "shopping_mall_admin_token";
    private static refreshToken = "shopping_mall_admin_refresh_token";
    private static ls = window.localStorage;

    public static getToken() {
        return this.ls.getItem(this.token);
    }

    public static setToken(value: string) {
        this.ls.setItem(this.token, value);
    }

    public static delToken() {
        this.ls.removeItem(this.token);
    }

    public static getRefreshToken() {
        return this.ls.getItem(this.refreshToken);
    }

    public static setRefreshToken(value: string) {
        this.ls.setItem(this.refreshToken, value);
    }

    public static delRefreshToken() {
        this.ls.removeItem(this.refreshToken);
    }
}
export default LocalStorageToken;