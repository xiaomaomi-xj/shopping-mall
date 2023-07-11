
import VueWechatTitle from 'vue-wechat-title';
export default{
    install(app){
        //根据每个页面路由的meta的title显示每个页面的title
        app.use(VueWechatTitle);
    }
}