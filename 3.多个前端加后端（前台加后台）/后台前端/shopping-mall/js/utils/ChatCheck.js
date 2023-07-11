export var ChatCheck = (function () {
    function ChatCheck() {
    }
    ChatCheck.check = function (str) {
        if (str.charCodeAt(0) >= this.MAX_SIZE) {
            return true;
        }
        return false;
    };
    ChatCheck.isEmpty = function (str) {
        if (str == null || str == undefined) {
            return true;
        }
        if (str.trim().length <= 0) {
            return true;
        }
        return false;
    };
    ChatCheck.MAX_SIZE = 55000;
    return ChatCheck;
}());
