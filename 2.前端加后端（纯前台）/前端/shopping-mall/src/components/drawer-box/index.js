import DrawerBox from './DrawerBox.vue';
import { createApp } from 'vue';
import exportElementIconOnTopo from '@/components/module/exportElementIconOnTopo';
import exportElementModuleOnTopo from '../module/exportElementModuleOnTopo';
//这地方用到了message，需要use，
import message from '../message';
//使用pinia
import { pinia } from '@/stores/index';
export default{
    install(app){
        const drawerBox=createApp(DrawerBox);
        drawerBox.use(exportElementIconOnTopo);
        drawerBox.use(exportElementModuleOnTopo);
        drawerBox.use(message);
        drawerBox.use(pinia);
        const drawerBoxVm=drawerBox.mount(document.createElement('div'));
        document.body.appendChild(drawerBoxVm.$el);
        app.config.globalProperties.$selfDrawer = drawerBoxVm;
    }
}