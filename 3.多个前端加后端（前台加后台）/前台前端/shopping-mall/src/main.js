import {
    createApp
} from 'vue'
import App from './App.vue'
//用到的element样式
import 'element-plus/theme-chalk/index.css'
//全局用到的element的图标和自定义组件
import module from './globalModule'
//路由
import {router} from './router';
//pinia
import { pinia } from './stores';
let app = createApp(App)
//use要写在mount前面
app.use(module);
app.use(router);
app.use(pinia);
app.mount('#app');
//导出全局变量
export const globals=app.config.globalProperties;
