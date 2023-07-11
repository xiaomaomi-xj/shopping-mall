<template>
    <div class="chat-boos-main-root">
        <main class="chat-boos-main-control-scroll">
            <article v-for="(messageData, key) in messageDatas" :key="key">
                <div class="server" v-if="messageData['fromUser'] == 0">
                    <img :src="serverHeadPortrait" alt="">
                    <div class="chat-message">
                        {{ messageData['message'] }}
                    </div>
                </div>
                <div class="self" v-if="messageData['fromUser'] == 1">
                    <div class="chat-message">
                        {{ messageData['message'] }}
                    </div>
                    <img :src="selfHeadPortrait" alt="">
                </div>
            </article>
        </main>
        <footer>
            <div class="input-box" :class="inputStatus">
                <input type="text" @input="messageInputFun" v-model="userMessage" @focusout="messageInputFocusoutFun"
                    @focus="messageInputFocusFun" @keyup.enter="enterPostMessageFun">
            </div>
            <div class="button-box">
                <aside class="no-accept" v-if="userMessage.length <= 0">发送</aside>
                <aside class="accept" v-if="userMessage.length > 0" @click="postMessageFun">发送</aside>
            </div>
        </footer>
    </div>
</template>

<script>
import { mapActions } from 'pinia';
import { useChatBoos } from '@/stores/chatBoosPageStores/useChatBoosStore';
import { useUsers } from '@/stores/loginAndRegistPageStores/useUserStore';
export default {
    name: 'ChatBoosMain',
    data() {
        return {
            serverHeadPortrait: window.customerService,
            //系统头像
            manOnSexImgUrl: window.manOnSexImgUrl,
            girlOnSexImgUrl: window.girlOnSexImgUrl,
            selfHeadPortrait: '',
            //输入框的内容
            userMessage: '',
            //输入框状态样式
            inputStatus: '',
            messageDatas: [],

            //界面销毁事件，清除任务
            messageTaskId: -1
        }
    },
    watch: {
        messageDatas: {
            handler(oldV, newV) {
                if (oldV.length != newV.length) {
                    //监听数据，一旦数据发生变化，就要修改滚动条.并且把消息改为已读
                    if (this.$route.name === 'chatBoos') {
                        this.readMessageDatas();
                        this.setMainScrollHeight();
                    }
                }
            },
            deep: true
        }
    },
    mounted() {

        //初始化
        this.initMessageDatas();

        //读掉消息
        this.readMessageDatas();
        //获取当前用户头像
        this.selfHeadPortrait = this.getUserDataOnToken()['userSex'] === '1' ? this.manOnSexImgUrl : this.girlOnSexImgUrl;

        //获取信息
        this.messageDatas = this.getcurrentUserMessage();

        //开启消息接受监听
        this.listenReceiveMessageEvent();
    },

    unmounted(){
        //销毁界面事件
        clearInterval(this.messageTaskId);
    },

    methods: {

        //pinia拿方法
        ...mapActions(useChatBoos, ['initMessageDatas', 'getcurrentUserMessage', 'readMessageDatas', 'postMessageData']),
        ...mapActions(useUsers, ['getUserDataOnToken']),
        //聊天框输入事件
        messageInputFun() {
            this.userMessage = this.userMessage.trim();
        },
        //获取焦点事件
        messageInputFocusFun() {
            this.inputStatus = 'input-box-active';
        },
        //失去焦点事件
        messageInputFocusoutFun() {
            this.inputStatus = '';
        },
        //输入框回车事件
        enterPostMessageFun() {
            if (this.userMessage.length > 0) {
                this.postMessageFun();
            }
        },
        //点击用户发送事件
        postMessageFun() {
            //增加一个消息
            this.postMessageData({
                fromUser: 1,
                message: this.userMessage,
                //自己的消息不用未读
                isUnRead: false
            });
            //发送完，获取信息
            this.messageDatas = this.getcurrentUserMessage();
            //发送完就清空
            this.userMessage = '';
        },
        //必须要保证滚动条在最下面
        setMainScrollHeight() {
            this.$nextTick(() => {
                let mainEl = document.querySelector('.chat-boos-main-control-scroll');
                mainEl.scrollTop = mainEl.scrollHeight;
            });
        },

        //监听事件，监听是否收到信息
        listenReceiveMessageEvent() {
            //3s执行一次
            this.messageTaskId = setInterval(() => {
                this.messageDatas = this.getcurrentUserMessage();
            }, 3000);
        }

    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.chat-boos-main-root {
    width: 75vw;
    color: $chat-boos-main-color;

    main {
        width: 100%;
        display: flex;
        flex-direction: column;
        background-color: $chat-boos-main-box-bgcolor;
        height: 80vh;
        overflow-y: scroll;

        div {
            padding: 1em;
            width: 100%;
            box-sizing: border-box;
            display: flex;
            justify-content: flex-start;

            img {
                width: 3em;
                height: 3em;
                border-radius: 1.5em;
            }

            .chat-message {
                padding: .6em 1.2em;
                box-sizing: border-box;
                height: auto;
                width: auto;
                max-width: 70%;
                margin-left: 1.4em;
                border-radius: .5em;
                word-break: break-all;
                font-size: 1.1em;
                background-color: $chat-boos-main-news-box-bgcolor;
                position: relative;
            }
        }

        .server {
            .chat-message {
                &::after {
                    content: '';
                    position: absolute;
                    width: 1.5em;
                    height: 1.5em;
                    background-color: $chat-boos-main-news-box-bgcolor;
                    top: 0.6em;
                    left: -0.6em;
                    transform: rotateZ(45deg);
                }
            }
        }

        .self {
            justify-content: flex-end;

            .chat-message {
                margin-left: 0;
                margin-right: 1.4em;

                &::after {
                    content: '';
                    position: absolute;
                    width: 1.5em;
                    height: 1.5em;
                    background-color: $chat-boos-main-news-box-bgcolor;
                    top: 0.6em;
                    right: -0.6em;
                    transform: rotateZ(45deg);
                }
            }
        }
    }

    footer {
        background-color: $chat-boos-main-news-box-bgcolor;
        height: 4em;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: .5em;
        box-sizing: border-box;

        .input-box {
            width: 83%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            border: 1px solid $chat-boos-main-input-border-color;
            border-radius: 10px;
            overflow: hidden;
            padding: 1em .2em;
            box-sizing: border-box;

            input {
                height: 100%;
                width: 100%;
                border: none;
                outline: none;
                padding: 1em .2em;
                font-size: 1.1em;
            }
        }

        .input-box-active {
            border: 1px solid $special-color;
        }

        .button-box {
            height: 100%;
            width: 15%;
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
            border-radius: 10px;
            overflow: hidden;

            .accept {
                height: 100%;
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: $special-color;
                font-size: 1.1em;
                color: $chat-boos-main-bgcolor;
                font-weight: bold;

                &:hover {
                    background-color: $chat-boos-main-aside-hover-bgcolor;
                }
            }

            .no-accept {
                cursor: default;
                height: 100%;
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: $chat-boos-main-aside-no-accept-bgcolor;
                font-size: 1.1em;
                color: $chat-boos-main-aside-no-accept-color;
                font-weight: bold;
            }
        }
    }
}
</style>