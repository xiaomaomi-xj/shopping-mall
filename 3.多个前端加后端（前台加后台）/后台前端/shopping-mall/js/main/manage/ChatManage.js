var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        if (typeof b !== "function" && b !== null)
            throw new TypeError("Class extends value " + String(b) + " is not a constructor or null");
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
import { UserUrls } from "../../api/ApiConstant.js";
import { ChatBoosUrls } from "../../api/ApiConstant.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
import { PublicMainView } from "../../components/PublicMainView.js";
import Ajax from "../../utils/Ajax.js";
import { ChatCheck } from "../../utils/ChatCheck.js";
import ParentMain from "../ParentMain.js";
var ChatManage = (function (_super) {
    __extends(ChatManage, _super);
    function ChatManage() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.currentUserId = '';
        _this.currentMessageNum = -1;
        _this.isScrollGoBottom = true;
        return _this;
    }
    ChatManage.prototype.getEvent = function () {
        var _this = this;
        return function () {
            var userListBoxEl = document.querySelector('.chat-manage-box-left-body');
            var messageListBoxEl = document.querySelector('.chat-manage-box-right-body');
            var userNameTitleEl = document.querySelector('.chat-manage-box-right-top');
            var sendMessageInputEl = document.querySelector('.chat-manage-box-right-bottom-input-box > input');
            var sendMessageBtnEl = document.querySelector('.chat-manage-box-right-bottom-button');
            sendMessageInputEl.style.display = 'none';
            _this.listenInputEvent(sendMessageInputEl, sendMessageBtnEl);
            _this.sendMessageEvent(sendMessageInputEl, sendMessageBtnEl, messageListBoxEl);
            _this.getMessageData(userListBoxEl, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl);
            setInterval(function () {
                _this.getMessageData(userListBoxEl, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl);
            }, 5000);
        };
    };
    ChatManage.prototype.sendBtnStyleChange = function (message, sendMessageBtnEl) {
        if (ChatCheck.isEmpty(message)) {
            sendMessageBtnEl.className = 'chat-manage-box-right-bottom-button';
        }
        else {
            sendMessageBtnEl.className = 'chat-manage-box-right-bottom-button chat-manage-box-right-bottom-button-accept';
        }
    };
    ChatManage.prototype.listenInputEvent = function (sendMessageInputEl, sendMessageBtnEl) {
        var _this = this;
        sendMessageInputEl.addEventListener('input', function () {
            _this.sendBtnStyleChange(sendMessageInputEl.value, sendMessageBtnEl);
        });
    };
    ChatManage.prototype.sendMessageEvent = function (sendMessageInputEl, sendMessageBtnEl, messageListBoxEl) {
        var _this = this;
        sendMessageBtnEl.addEventListener('click', function () {
            if (sendMessageBtnEl.className.indexOf('chat-manage-box-right-bottom-button-accept') != -1) {
                if (ChatCheck.isEmpty(_this.currentUserId)) {
                    MessageControl.open('用户id不得为空！', MessageType.ERROR);
                    return;
                }
                if (ChatCheck.isEmpty(sendMessageInputEl.value)) {
                    MessageControl.open('不能发送空消息！', MessageType.ERROR);
                    return;
                }
                new Ajax(ChatBoosUrls.sendChatBoosUrl).config().sendJson({
                    userId: _this.currentUserId,
                    message: sendMessageInputEl.value
                }).result(function () {
                    MessageControl.open('发送成功！', MessageType.SUCCESS);
                    messageListBoxEl.innerHTML += _this.getBoosMessageTemplate(sendMessageInputEl.value);
                    messageListBoxEl.scrollTop = messageListBoxEl.scrollHeight;
                    _this.sendBtnStyleChange('', sendMessageBtnEl);
                    sendMessageInputEl.value = '';
                }, function () { }, _this.parentFailHandel);
            }
        });
    };
    ChatManage.prototype.readChatBoos = function (id) {
        new Ajax(ChatBoosUrls.readChatBoosUrl).config().sendJson({
            id: id
        }).result(function () { }, function () { }, this.parentFailHandel);
    };
    ChatManage.prototype.getMessageData = function (userListBoxEl, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl) {
        var _this = this;
        new Ajax(ChatBoosUrls.allDataUrl).config().sendEmpty().result(function (data) {
            if (_this.currentUserId != '') {
                if (_this.currentMessageNum != -1 && _this.currentUserId != '' && _this.currentMessageNum != data.filter(function (chat) { return chat.userId == _this.currentUserId; }).length) {
                    _this.isScrollGoBottom = true;
                }
                else {
                    _this.isScrollGoBottom = false;
                }
                _this.currentMessageNum = data.filter(function (chat) { return chat.userId == _this.currentUserId; }).length;
            }
            _this.renderEvent(data, userListBoxEl, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl);
        }, function () { }, this.parentFailHandel);
    };
    ChatManage.prototype.renderEvent = function (chatBoosData, userListBoxEl, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl) {
        var _this = this;
        new Ajax(UserUrls.allUserDataUrl).config().sendEmpty().result(function (userData) {
            new Ajax(UserUrls.allWechatDataUrl).config().sendEmpty().result(function (wechatData) {
                var userListDatas = [];
                var chatListDatas = [];
                userData.forEach(function (user) {
                    var headImg = '';
                    if (ChatCheck.isEmpty(user.userHeadUrl)) {
                        headImg = user.userSex == 1 ? '../src/manOnSex.jpg' : '../src/girlOnSex.jpg';
                    }
                    else {
                        headImg = user.userHeadUrl;
                    }
                    userListDatas.push({
                        userId: user.userId,
                        headImg: headImg,
                        userName: user.userName,
                        messageNum: chatBoosData.filter(function (chat) { return chat.isUnRead == 1 && chat.fromUser == 1 && chat.userId == user.userId; }).length
                    });
                });
                wechatData.forEach(function (wechat) {
                    if (ChatCheck.isEmpty(wechat.bindId)) {
                        var headImg = '';
                        if (ChatCheck.isEmpty(wechat.userHeadUrl)) {
                            headImg = wechat.userSex == 1 ? '../src/manOnSex.jpg' : '../src/girlOnSex.jpg';
                        }
                        else {
                            headImg = wechat.userHeadUrl;
                        }
                        userListDatas.push({
                            userId: wechat.userId,
                            headImg: headImg,
                            userName: wechat.userName,
                            messageNum: chatBoosData.filter(function (chat) { return chat.isUnRead == 1 && chat.fromUser == 1 && chat.userId == wechat.userId; }).length
                        });
                    }
                });
                userListDatas.sort(function (a, b) { return b.messageNum - a.messageNum; });
                var userListTemplate = '';
                userListDatas.forEach(function (userData) {
                    userListTemplate += _this.getUserTemplate(userData);
                });
                userListBoxEl.innerHTML = userListTemplate;
                userListDatas.forEach(function (userListData) {
                    var currentChatListDatas = chatBoosData.filter(function (chat) { return chat.userId == userListData.userId; });
                    var chatTemplate = '';
                    if (currentChatListDatas.length <= 0) {
                        chatListDatas.push({
                            userId: userListData.userId,
                            userName: userListData.userName,
                            message: ''
                        });
                    }
                    else {
                        currentChatListDatas.forEach(function (currentChat) {
                            if (currentChat.fromUser == 1) {
                                chatTemplate += _this.getUserMessageTemplate(userListData.headImg, currentChat.message);
                            }
                            else {
                                chatTemplate += _this.getBoosMessageTemplate(currentChat.message);
                            }
                            chatListDatas.push({
                                userId: userListData.userId,
                                userName: userListData.userName,
                                message: chatTemplate
                            });
                        });
                    }
                });
                _this.userListClickEvent(chatListDatas, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl);
            }, function () { }, _this.parentFailHandel);
        }, function () { }, this.parentFailHandel);
    };
    ChatManage.prototype.userListClickEvent = function (chatListDatas, messageListBoxEl, userNameTitleEl, sendMessageInputEl, sendMessageBtnEl) {
        var _this = this;
        var userListEls = document.querySelectorAll('.chat-manage-box-left-box');
        userListEls.forEach(function (userListEl) {
            if (userListEl.firstElementChild.innerText == _this.currentUserId) {
                userListEl.className = 'chat-manage-box-left-box chat-manage-box-left-box-check';
                _this.showMessageData(userListEl, chatListDatas, messageListBoxEl, userNameTitleEl);
            }
            userListEl.addEventListener('click', function () {
                if (userListEl.className.indexOf('chat-manage-box-left-box-check') == -1) {
                    sendMessageInputEl.style.display = 'block';
                    sendMessageInputEl.value = '';
                    _this.sendBtnStyleChange('', sendMessageBtnEl);
                    var messageTipsEl = userListEl.lastElementChild;
                    if (messageTipsEl.className.indexOf('chat-manage-box-left-box-tips-none') == -1) {
                        messageTipsEl.className = 'chat-manage-box-left-box-tips chat-manage-box-left-box-tips-none';
                    }
                    _this.currentMessageNum = -1;
                    _this.isScrollGoBottom = true;
                    userListEls.forEach(function (el) {
                        el.className = 'chat-manage-box-left-box';
                    });
                    userListEl.className = 'chat-manage-box-left-box chat-manage-box-left-box-check';
                    _this.showMessageData(userListEl, chatListDatas, messageListBoxEl, userNameTitleEl);
                }
            });
        });
    };
    ChatManage.prototype.showMessageData = function (userListEl, chatListDatas, messageListBoxEl, userNameTitleEl) {
        var _this = this;
        var userId = userListEl.firstElementChild.innerText;
        this.currentUserId = userId;
        this.readChatBoos(userId);
        chatListDatas.forEach(function (chatListData) {
            if (chatListData.userId == userId) {
                userNameTitleEl.innerText = chatListData.userName;
                messageListBoxEl.innerHTML = chatListData.message;
                if (_this.isScrollGoBottom) {
                    messageListBoxEl.scrollTop = messageListBoxEl.scrollHeight;
                }
            }
        });
    };
    ChatManage.prototype.getUserTemplate = function (userListData) {
        var temp = '';
        if (this.currentUserId != '' && this.currentUserId == userListData.userId) {
            temp = '<div class="chat-manage-box-left-box-tips chat-manage-box-left-box-tips-none"></div>';
        }
        else if (userListData.messageNum <= 0) {
            temp = '<div class="chat-manage-box-left-box-tips chat-manage-box-left-box-tips-none"></div>';
        }
        else {
            temp = '<div class="chat-manage-box-left-box-tips">' + userListData.messageNum + '</div>';
        }
        return "\n        <div class=\"chat-manage-box-left-box\">\n            <div class=\"chat-manage-box-left-box-hidden-id\">".concat(userListData.userId, "</div>\n            <div class=\"chat-manage-box-left-box-img\"><img src=\"").concat(userListData.headImg, "\"></div>\n            <div class=\"chat-manage-box-left-box-name\">").concat(userListData.userName, "</div>\n            ") + temp + "\n        </div>\n        ";
    };
    ChatManage.prototype.getUserMessageTemplate = function (userImg, message) {
        return "\n        <div class=\"chat-manage-box-right-body-left\">\n            <div class=\"chat-manage-box-right-body-img\"><img src=\"".concat(userImg, "\"></div>\n            <div class=\"chat-manage-box-right-body-left-content\">").concat(message, "</div>\n        </div>\n        ");
    };
    ChatManage.prototype.getBoosMessageTemplate = function (message) {
        return "\n        <div class=\"chat-manage-box-right-body-right\">\n            <div class=\"chat-manage-box-right-body-right-content\">".concat(message, "</div>\n            <div class=\"chat-manage-box-right-body-img\"><img src=\"../src/customerService.jpg\"></div>\n        </div>\n        ");
    };
    return ChatManage;
}(ParentMain));
PublicMainView.run(new ChatManage(2));
