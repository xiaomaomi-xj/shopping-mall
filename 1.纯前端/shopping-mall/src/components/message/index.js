import Message from './MessageInfo.vue';
import {
    createApp
} from 'vue';
import exportElementIconOntopo from '@/components/module/exportElementIconOnTopo';
import exportElementModuleOnTopo from '@/components/module/exportElementModuleOnTopo';
export default {
    install(app) {
        const msg = createApp(Message);
        msg.use(exportElementIconOntopo);
        msg.use(exportElementModuleOnTopo);
        const message = msg.mount(document.createElement("div"));
        document.body.appendChild(message.$el);
        app.config.globalProperties.$selfMessage = message;
    }
}