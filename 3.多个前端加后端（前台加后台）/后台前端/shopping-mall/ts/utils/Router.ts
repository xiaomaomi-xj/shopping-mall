class Router{
    static go(url:string){
        window.location.href= url;
    }
    //刷新页面
    static refresh(){
        window.location.reload();
    }
}

export default Router;