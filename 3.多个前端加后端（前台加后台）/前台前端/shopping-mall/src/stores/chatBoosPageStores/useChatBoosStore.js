import {
    defineStore
} from 'pinia';
import {
    ls
} from '@/utils/ls';
import {
    globals
} from '@/main';
import {
    getChatBoosData,
    sendChatBoosData,
    readChatBoosData
} from '@/api/chatBoos';
export const useChatBoos = defineStore("chatBoos", {
    state: () => {
        return {
            chatBoosDatas: [{}],
            //任务id
            messageTaskId:-1
        };
    },
    actions: {
        //获取聊天数据
        getMessageFun() {
            let token = ls.get('token');
            getChatBoosData({
                token
            }).then(res => {
                this.chatBoosDatas = res.data;
            });
        },
        //发送聊天信息
        sendMessageFun(message) {
            let token = ls.get('token');
            sendChatBoosData({
                token,
                message
            }).then(res => {
                if (res.data['value']) {
                    globals.$selfMessage.openMessage({
                        type: 'success',
                        message: '消息发送成功！'
                    });
                } else {
                    globals.$selfMessage.openMessage({
                        type: 'error',
                        message: '消息发送失败！'
                    });
                }
                //发送完之后要刷新聊天数据
                this.getMessageFun();
            });
        },
        //把消息变为已读
        readMessageFun() {
            let token = ls.get('token');
            readChatBoosData({token}).then(()=>{
                //已读不需要刷新聊天数据，因为有轮询
            });
        },
        //设置任务id
        setTaskId(taskId){
            this.clearTaskId();
            this.messageTaskId=taskId;
        },
        //清除任务id
        clearTaskId(){
            clearInterval(this.messageTaskId);
        }
    }
});