import MessageBox from "./MessageBox.vue";
import {
    createApp
} from "vue";
import exportElementIconOnMessageBox from "../module/exportElementIconOnTopo";
import exportElementModuleOnMessageBox from "../module/exportElementModuleOnTopo";
export default {
    install(app) {
        const msgBox = createApp(MessageBox);
        msgBox.use(exportElementIconOnMessageBox);
        msgBox.use(exportElementModuleOnMessageBox);
        const messageBox = msgBox.mount(document.createElement('div'));
        document.body.appendChild(messageBox.$el);
        app.config.globalProperties.$selfMessageBox = messageBox;
    }
}