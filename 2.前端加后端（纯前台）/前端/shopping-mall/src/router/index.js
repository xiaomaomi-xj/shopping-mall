import {
    createRouter,
    createWebHistory
} from 'vue-router';
import routerConfig from './routerConfig';
import {
    useUserCheck
} from '@/stores/userStores/useUserCheckStore';

const router = createRouter({
    //base url,但是应该注意图片资源的路径
    history: createWebHistory("/shopping-mall/"),
    routes: routerConfig
});

//导航守卫
router.beforeEach((to, from, next) => {
    //验证当前用户是否可以跳转到此页面
    if (to.meta['requiresCheck']) {
        useUserCheck().checkLoginUserCallBack(
            () => {
                //登陆了才可以访问
                next()
            },
            () => {
                //没登陆去登录页
                next({
                    name: 'userLogin'
                });
            }
        );
    } else if (to.meta['loginNoShow']) {
        useUserCheck().checkLoginUserCallBack(
            () => {
                //登陆了回主页
                next({
                    name: 'home'
                });
            },
            () => {
                //没登陆才显示
                next();
            }
        );
    } else {
        //其他的无需关心
        next();
    }
});


export {
    router
};