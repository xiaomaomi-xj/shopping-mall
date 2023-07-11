import { UserUrls } from "../../api/ApiConstant.js";
import { ChatBoosUrls } from "../../api/ApiConstant.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { PublicMainView } from "../../components/PublicMainView.js";
import { ChatBoosType, UserType } from "../../interface/Types.js";
import Ajax from "../../utils/Ajax.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
import ParentMain from "../ParentMain.js";
//用户列表数据类型
interface UserListDataType {
    userId: string;
    headImg: string;
    userName: string;
    messageNum: number;
}
//消息列表数据类型
interface ChatListDataType {
    userId: string;
    userName: string;
    message: string;
}
//聊天管理
class ChatManage extends ParentMain {
    //当前的聊天用户
    private currentUserId: string = '';
    //当前消息数，是否需要跑到最底部
    private currentMessageNum: number = -1;
    private isScrollGoBottom: boolean = true;

    //每个管理类，只需要处理自己的事件即可
    getEvent(): Function {
        return () => {
            const userListBoxEl = document.querySelector('.chat-manage-box-left-body') as HTMLElement;
            const messageListBoxEl = document.querySelector('.chat-manage-box-right-body') as HTMLElement;
            const userNameTitleEl = document.querySelector('.chat-manage-box-right-top') as HTMLElement;
            const sendMessageInputEl = document.querySelector('.chat-manage-box-right-bottom-input-box > input') as HTMLInputElement;
            const sendMessageBtnEl = document.querySelector('.chat-manage-box-right-bottom-button') as HTMLElement;
            //没选择用户之前，不可编辑
            sendMessageInputEl.style.display = 'none';
            //监听事件，输入框有值，按钮变
            this.listenInputEvent(sendMessageInputEl, sendMessageBtnEl);
            //发送消息事件
            this.sendMessageEvent(sendMessageInputEl, sendMessageBtnEl, messageListBoxEl);
            //获取数据并渲染(开始先运行一次)
            this.getMessageData(userListBoxEl, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl);
            //轮询监听(5s刷一次)
            setInterval(() => {
                this.getMessageData(userListBoxEl, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl);
            }, 5000);
        };
    }
    //按钮变化事件
    sendBtnStyleChange(message: string, sendMessageBtnEl: HTMLElement) {
        if (ChatCheck.isEmpty(message)) {
            sendMessageBtnEl.className = 'chat-manage-box-right-bottom-button';
        } else {
            sendMessageBtnEl.className = 'chat-manage-box-right-bottom-button chat-manage-box-right-bottom-button-accept';
        }
    }
    //监听事件，输入框有值，按钮变
    listenInputEvent(sendMessageInputEl: HTMLInputElement, sendMessageBtnEl: HTMLElement) {
        sendMessageInputEl.addEventListener('input', () => {
            this.sendBtnStyleChange(sendMessageInputEl.value, sendMessageBtnEl);
        });
    }
    //发送聊天数据
    sendMessageEvent(sendMessageInputEl: HTMLInputElement, sendMessageBtnEl: HTMLElement, messageListBoxEl: HTMLElement) {
        sendMessageBtnEl.addEventListener('click', () => {
            if (sendMessageBtnEl.className.indexOf('chat-manage-box-right-bottom-button-accept') != -1) {
                //发送事件
                if (ChatCheck.isEmpty(this.currentUserId)) {
                    MessageControl.open('用户id不得为空！', MessageType.ERROR);
                    return;
                }
                if (ChatCheck.isEmpty(sendMessageInputEl.value)) {
                    MessageControl.open('不能发送空消息！', MessageType.ERROR);
                    return;
                }
                new Ajax(ChatBoosUrls.sendChatBoosUrl).config().sendJson({
                    userId: this.currentUserId,
                    message: sendMessageInputEl.value
                }).result(() => {
                    MessageControl.open('发送成功！', MessageType.SUCCESS);
                    messageListBoxEl.innerHTML += this.getBoosMessageTemplate(sendMessageInputEl.value);
                    messageListBoxEl.scrollTop = messageListBoxEl.scrollHeight;
                    this.sendBtnStyleChange('', sendMessageBtnEl);
                    sendMessageInputEl.value = '';
                },
                    () => { },
                    this.parentFailHandel
                );
            }
        });
    }
    //消息变为已读
    readChatBoos(id: string) {
        new Ajax(ChatBoosUrls.readChatBoosUrl).config().sendJson({
            id
        }).result(() => { },
            () => { },
            this.parentFailHandel
        );
    }
    //远程获取聊天数据
    getMessageData(userListBoxEl: HTMLElement, messageListBoxEl: HTMLElement, userNameTitleEl: HTMLElement, sendMessageInputEl: HTMLInputElement, sendMessageBtnEl: HTMLElement) {
        new Ajax(ChatBoosUrls.allDataUrl).config().sendEmpty().result(
            (data: Array<ChatBoosType>) => {
                if (this.currentUserId != '') {
                    //判断是否有了新数据
                    if (this.currentMessageNum != -1 && this.currentUserId != '' && this.currentMessageNum != data.filter(chat => chat.userId == this.currentUserId).length) {
                        this.isScrollGoBottom = true;
                    } else {
                        this.isScrollGoBottom = false;
                    }
                    //当前用户消息数
                    this.currentMessageNum = data.filter(chat => chat.userId == this.currentUserId).length;
                }
                this.renderEvent(data, userListBoxEl, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl);
            },
            () => { },
            this.parentFailHandel
        );
    }
    //渲染事件
    renderEvent(chatBoosData: Array<ChatBoosType>, userListBoxEl: HTMLElement, messageListBoxEl: HTMLElement, userNameTitleEl: HTMLElement, sendMessageInputEl: HTMLInputElement, sendMessageBtnEl: HTMLElement) {
        //获取用户数据
        new Ajax(UserUrls.allUserDataUrl).config().sendEmpty().result(
            (userData: Array<UserType>) => {
                //获取微信数据
                new Ajax(UserUrls.allWechatDataUrl).config().sendEmpty().result(
                    (wechatData: Array<UserType>) => {
                        const userListDatas = [] as Array<UserListDataType>;
                        const chatListDatas = [] as Array<ChatListDataType>;
                        //正常用户
                        userData.forEach(user => {
                            let headImg = '';
                            if (ChatCheck.isEmpty(user.userHeadUrl)) {
                                headImg = user.userSex == 1 ? '../src/manOnSex.jpg' : '../src/girlOnSex.jpg';
                            } else {
                                headImg = user.userHeadUrl;
                            }
                            userListDatas.push({
                                userId: user.userId,
                                headImg,
                                userName: user.userName,
                                messageNum: chatBoosData.filter(chat => chat.isUnRead == 1 && chat.fromUser == 1 && chat.userId == user.userId).length
                            });
                        });
                        //微信用户(如果绑定了就不再显示)
                        wechatData.forEach(wechat => {
                            if (ChatCheck.isEmpty(wechat.bindId)) {
                                let headImg = '';
                                if (ChatCheck.isEmpty(wechat.userHeadUrl)) {
                                    headImg = wechat.userSex == 1 ? '../src/manOnSex.jpg' : '../src/girlOnSex.jpg';
                                } else {
                                    headImg = wechat.userHeadUrl;
                                }
                                userListDatas.push({
                                    userId: wechat.userId,
                                    headImg,
                                    userName: wechat.userName,
                                    messageNum: chatBoosData.filter(chat => chat.isUnRead == 1 && chat.fromUser == 1 && chat.userId == wechat.userId).length
                                });
                            }
                        });
                        //降序
                        userListDatas.sort((a, b) => b.messageNum - a.messageNum);
                        let userListTemplate = '';
                        userListDatas.forEach(userData => {
                            userListTemplate += this.getUserTemplate(userData);
                        });
                        userListBoxEl.innerHTML = userListTemplate;
                        //保存聊天数据
                        userListDatas.forEach(userListData => {
                            const currentChatListDatas = chatBoosData.filter(chat => chat.userId == userListData.userId);
                            let chatTemplate = '';
                            if (currentChatListDatas.length <= 0) {
                                //聊天数据没有也要显示
                                chatListDatas.push({
                                    userId: userListData.userId,
                                    userName: userListData.userName,
                                    message: ''
                                });
                            } else {
                                currentChatListDatas.forEach(currentChat => {
                                    if (currentChat.fromUser == 1) {
                                        chatTemplate += this.getUserMessageTemplate(userListData.headImg, currentChat.message);
                                    } else {
                                        chatTemplate += this.getBoosMessageTemplate(currentChat.message);
                                    }
                                    chatListDatas.push({
                                        userId: userListData.userId,
                                        userName: userListData.userName,
                                        message: chatTemplate
                                    });
                                });
                            }
                        });
                        //赋予用户列表点击事件
                        this.userListClickEvent(chatListDatas, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl);
                    },
                    () => { },
                    this.parentFailHandel
                );
            },
            () => { },
            this.parentFailHandel
        );
    }
    //用户点击事件
    userListClickEvent(chatListDatas: Array<ChatListDataType>, messageListBoxEl: HTMLElement, userNameTitleEl: HTMLElement, sendMessageInputEl: HTMLInputElement, sendMessageBtnEl: HTMLElement) {
        const userListEls = document.querySelectorAll('.chat-manage-box-left-box') as unknown as Array<HTMLElement>;
        userListEls.forEach(userListEl => {
            if ((userListEl.firstElementChild as HTMLElement).innerText == this.currentUserId) {
                //刷新的时候，点击不能取消
                userListEl.className = 'chat-manage-box-left-box chat-manage-box-left-box-check';
                this.showMessageData(userListEl, chatListDatas, messageListBoxEl, userNameTitleEl)
            }
            userListEl.addEventListener('click', () => {
                if (userListEl.className.indexOf('chat-manage-box-left-box-check') == -1) {
                    sendMessageInputEl.style.display = 'block';
                    sendMessageInputEl.value = '';
                    this.sendBtnStyleChange('', sendMessageBtnEl);
                    //把消息红点去掉
                    const messageTipsEl = userListEl.lastElementChild as HTMLElement;
                    if (messageTipsEl.className.indexOf('chat-manage-box-left-box-tips-none') == -1) {
                        messageTipsEl.className = 'chat-manage-box-left-box-tips chat-manage-box-left-box-tips-none';
                    }
                    //更换的时候就把属性回复初始值
                    this.currentMessageNum = -1;
                    this.isScrollGoBottom = true;
                    userListEls.forEach(el => {
                        el.className = 'chat-manage-box-left-box';
                    });
                    userListEl.className = 'chat-manage-box-left-box chat-manage-box-left-box-check';
                    this.showMessageData(userListEl, chatListDatas, messageListBoxEl, userNameTitleEl);
                }
            });
        });
    }
    //显示消息数据
    showMessageData(userListEl: HTMLElement, chatListDatas: Array<ChatListDataType>, messageListBoxEl: HTMLElement, userNameTitleEl: HTMLElement) {
        const userId = (userListEl.firstElementChild as HTMLElement).innerText;
        this.currentUserId = userId;
        this.readChatBoos(userId);
        chatListDatas.forEach(chatListData => {
            if (chatListData.userId == userId) {
                userNameTitleEl.innerText = chatListData.userName;
                messageListBoxEl.innerHTML = chatListData.message;
                //消息数据直接到最低头
                if (this.isScrollGoBottom) {
                    messageListBoxEl.scrollTop = messageListBoxEl.scrollHeight;
                }
            }
        });
    }
    //用户模板
    getUserTemplate(userListData: UserListDataType): string {
        let temp = '';
        if (this.currentUserId != '' && this.currentUserId == userListData.userId) {
            temp = '<div class="chat-manage-box-left-box-tips chat-manage-box-left-box-tips-none"></div>';
        } else if (userListData.messageNum <= 0) {
            temp = '<div class="chat-manage-box-left-box-tips chat-manage-box-left-box-tips-none"></div>';
        } else {
            temp = '<div class="chat-manage-box-left-box-tips">' + userListData.messageNum + '</div>';
        }
        return `
        <div class="chat-manage-box-left-box">
            <div class="chat-manage-box-left-box-hidden-id">${userListData.userId}</div>
            <div class="chat-manage-box-left-box-img"><img src="${userListData.headImg}"></div>
            <div class="chat-manage-box-left-box-name">${userListData.userName}</div>
            `+ temp + `
        </div>
        `;
    }
    //他人消息模板
    getUserMessageTemplate(userImg: string, message: string): string {
        return `
        <div class="chat-manage-box-right-body-left">
            <div class="chat-manage-box-right-body-img"><img src="${userImg}"></div>
            <div class="chat-manage-box-right-body-left-content">${message}</div>
        </div>
        `;
    }
    //自己消息模板
    getBoosMessageTemplate(message: string): string {
        return `
        <div class="chat-manage-box-right-body-right">
            <div class="chat-manage-box-right-body-right-content">${message}</div>
            <div class="chat-manage-box-right-body-img"><img src="../src/customerService.jpg"></div>
        </div>
        `;
    }
}
PublicMainView.run(new ChatManage(2));