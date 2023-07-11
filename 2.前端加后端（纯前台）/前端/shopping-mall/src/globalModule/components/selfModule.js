import MessageBox from '@/components/message-box';
import Message from '@/components/message';
import DrawerBox from '@/components/drawer-box';
export default {
    install(Vue) {
        //自己写的组件就放在这里
        Vue.use(MessageBox);
        Vue.use(Message);
        Vue.use(DrawerBox);
    }
}