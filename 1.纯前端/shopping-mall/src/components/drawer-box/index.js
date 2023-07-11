import DrawerBox from './DrawerBox.vue';
import { createApp } from 'vue';
import exportElementIconOnTopo from '@/components/module/exportElementIconOnTopo';
import exportElementModuleOnTopo from '../module/exportElementModuleOnTopo';
import message from '../message';
export default{
    install(app){
        const drawerBox=createApp(DrawerBox);
        drawerBox.use(exportElementIconOnTopo);
        drawerBox.use(exportElementModuleOnTopo);
        drawerBox.use(message);
        const drawerBoxVm=drawerBox.mount(document.createElement('div'));
        document.body.appendChild(drawerBoxVm.$el);
        app.config.globalProperties.$selfDrawer = drawerBoxVm;
    }
}