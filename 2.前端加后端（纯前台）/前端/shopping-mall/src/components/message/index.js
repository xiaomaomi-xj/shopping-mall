import Message from './MessageInfo.vue';
import {
    createApp
} from 'vue';
//message用到的element的图标
import exportElementIconOntopo from '@/components/module/exportElementIconOnTopo';
import exportElementModuleOnTopo from '@/components/module/exportElementModuleOnTopo';
export default {
    install(app) {
        const msg = createApp(Message);
        msg.use(exportElementIconOntopo);
        msg.use(exportElementModuleOnTopo);
        const message = msg.mount(document.createElement("div"));
        //appendChild不会累加是因为要加入的dom对象是一个，导致他就不会累加,可以通过cloneNode（复制一个副本）解决
        document.body.appendChild(message.$el);
        app.config.globalProperties.$selfMessage = message;
    }
}