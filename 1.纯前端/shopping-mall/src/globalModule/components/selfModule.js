import MessageBox from '@/components/message-box';
import Message from '@/components/message';
import DrawerBox from '@/components/drawer-box';
export default {
    install(Vue) {
        Vue.use(MessageBox);
        Vue.use(Message);
        Vue.use(DrawerBox);
    }
}