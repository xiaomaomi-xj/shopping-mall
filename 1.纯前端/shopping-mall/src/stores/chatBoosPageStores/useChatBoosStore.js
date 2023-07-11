import {
    defineStore
} from 'pinia';
import axios from 'axios';
import {
    useUsers
} from '../loginAndRegistPageStores/useUserStore';
export const useChatBoos = defineStore("chatBoos", {
    state: () => {
        return {
            messageDatas: [{
                // 0表示老板，1表示用户
                fromUser: 0,
                message: '你好，欢迎使用！',
                isUnRead: false,
                userEmail: 'all'
            }]
        };
    },
    actions: {
        initMessageDatas() {
            //处理所有用户
        },
        getcurrentUserMessage() {
            const userInfo = useUsers().getUserDataOnToken();
            const userEmail = userInfo['userEmail'];
            return this.messageDatas.filter(v =>
                (v['fromUser'] === 0 && v['userEmail'] === 'all') ||
                (v['fromUser'] === 0 && v['userEmail'] === userEmail) ||
                (v['fromUser'] === 1 && v['userEmail'] === userEmail)
            );
        },
        //对于读了的消息
        readMessageDatas() {
            const userEmail = useUsers().getUserDataOnToken()['userEmail'];
            this.messageDatas.map(v => {
                if (v['userEmail'] === userEmail) {
                    v['isUnRead'] = false;
                }
            });
        },
        //消息数据增加
        addMessageData(messageData) {
            const userEmail = useUsers().getUserDataOnToken()['userEmail'];
            messageData['userEmail'] = userEmail;
            this.messageDatas.push(messageData);
        },
        //发送信息
        postMessageData(messageData) {
            //发送消息
            this.addMessageData(messageData);
            //触发回复消息
            this.replyMessageData();
        },
        //回复消息
        replyMessageData() {
            setTimeout(() => {
                axios.get(window.chatMessageRobot).then(response => {
                    const data = response.data;
                    let message = data['hitokoto'] + "-----" + data['from'];
                    this.addMessageData({
                        message,
                        fromUser: 0,
                        isUnRead: true
                    });
                }).catch(e => {
                    alert('网络错误:' + e.message + ",链接可能失效，或者你的网络不好！");
                });
            }, 1500);
        }
    }
});