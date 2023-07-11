var LocalStorageToken = (function () {
    function LocalStorageToken() {
    }
    LocalStorageToken.getToken = function () {
        return this.ls.getItem(this.token);
    };
    LocalStorageToken.setToken = function (value) {
        this.ls.setItem(this.token, value);
    };
    LocalStorageToken.delToken = function () {
        this.ls.removeItem(this.token);
    };
    LocalStorageToken.getRefreshToken = function () {
        return this.ls.getItem(this.refreshToken);
    };
    LocalStorageToken.setRefreshToken = function (value) {
        this.ls.setItem(this.refreshToken, value);
    };
    LocalStorageToken.delRefreshToken = function () {
        this.ls.removeItem(this.refreshToken);
    };
    LocalStorageToken.token = "shopping_mall_admin_token";
    LocalStorageToken.refreshToken = "shopping_mall_admin_refresh_token";
    LocalStorageToken.ls = window.localStorage;
    return LocalStorageToken;
}());
export default LocalStorageToken;
