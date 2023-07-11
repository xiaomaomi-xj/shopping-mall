import {
    createRouter,
    createWebHistory
} from 'vue-router';
import routerConfig from './routerConfig';
import {
    useUsers
} from '@/stores/loginAndRegistPageStores/useUserStore';

const router = createRouter({
    //base url,但是应该注意图片资源的路径
    history: createWebHistory("/shopping-mall/"),
    routes: routerConfig
});

router.beforeEach(async (to, from, next) => {
    if (to.meta['requiresCheck']) {
        if (useUsers().getTokenTolocalStorageOnCheck()) {
            next();
        } else {
            next({
                name: 'userLogin'
            });
        }
    } else if (to.meta['loginNoShow']) {
        if (!useUsers().getTokenTolocalStorageOnCheck()) {
            next();
        } else {
            next({
                name: 'home'
            });
        }
    } else {
        next();
    }
});


export {
    router
};