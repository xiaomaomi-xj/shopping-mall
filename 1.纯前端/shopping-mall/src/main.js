import {
    createApp
} from 'vue'
import App from './App.vue'
import 'element-plus/theme-chalk/index.css'
import module from './globalModule'
import {router} from './router';
import { pinia } from './stores';
let app = createApp(App)
app.use(module);
app.use(router);
app.use(pinia);
app.mount('#app');
