var Router = (function () {
    function Router() {
    }
    Router.go = function (url) {
        window.location.href = url;
    };
    Router.refresh = function () {
        window.location.reload();
    };
    return Router;
}());
export default Router;
