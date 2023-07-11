import { UserUrls } from "../../api/ApiConstant.js";
import { PublicMainView } from "../../components/PublicMainView.js";
import { UserType } from "../../interface/Types.js";
import Ajax from "../../utils/Ajax.js";
import { CustomTableControl } from "../../components/CustomTableControl.js";
import { DelTipsBoxControl } from "../../components/DelTipsBoxControl.js";
import ParentMain from "../ParentMain.js";
import { MessageControl, MessageType } from "../../components/MessageControl.js";
//用户管理
class UserManage extends ParentMain {
    //每个管理类，只需要处理自己的事件即可
    getEvent(): Function {
        return () => {
            const mainHtml = document.querySelector('main') as HTMLElement;
            this.getUserData([mainHtml]);
        };
    }
    //获取用户表数据
    getUserData(els: Array<HTMLElement>) {
        new Ajax(UserUrls.allUserDataUrl).config().sendEmpty().result(
            (data: Array<UserType>) => {
                new Ajax(UserUrls.allWechatDataUrl).config().sendEmpty().result(
                    (da: Array<UserType>) => {
                        this.createTable(els, data.concat(da));
                    },
                    () => { },
                    this.parentFailHandel
                );
            },
            () => { },
            this.parentFailHandel
        );
    }
    //创建表格
    createTable(els: HTMLElement[], data: UserType[], index: number = -1, value: string = '', currentPageNum: number = 1) {
        const tableTitleData = ['用户id', '头像', '昵称', '账号', '性别', '绑定id', '用户类型'];
        const tableContentData = [] as Array<Array<string>>;
        data.forEach(userData => {
            tableContentData.push([userData.userId, userData.userHeadUrl, userData.userName, userData.userAccount, userData.userSex == 1 ? '男' : '女', userData.bindId, userData.userType == 'email-user' ? '邮箱用户' : '微信用户']);
        });
        //过滤重组一个新的表
        const tableContentDataBak = [] as Array<Array<string>>;
        if (index != -1 && value != '') {
            tableContentData.forEach(user => {
                if (user[index].indexOf(value) != -1) {
                    tableContentDataBak.push(user);
                }
            });
        }
        const tableData = {
            title: tableTitleData,
            content: (index != -1 && value != '') ? tableContentDataBak : tableContentData
        };
        //生成表格
        CustomTableControl
            .configValue(index, value)
            .configHaveImgOrHavepageNum(true, true, (currentPageNum: number) => {
                //页码事件
                this.createTable(els, data, this.getInputIndex(), this.getInputValue(), currentPageNum);
            })
            .configQueryAndAdd(false, () => { }, true, (index: number, value: string) => {
                //查询事件，得到下标和文本框的值,然后重新生成表格
                this.setInputIndex(index);
                this.setInputValue(value);
                this.createTable(els, data, index, value);
            }, 1, 4, 7)
            .configUpdateAndDelete(false, () => { }, true, (data: Array<string>) => {
                DelTipsBoxControl.open('确定要删除此用户吗？', () => {
                    let type = '';
                    if ('邮箱用户' == data[6]) {
                        type = 'email-user';
                        this.delUser(data[0], type);
                    } else if ('微信用户' == data[6]) {
                        type = 'wechat-user';
                        this.delUser(data[0], type);
                    } else {
                        MessageControl.open('用户类型错误！', MessageType.ERROR);
                    }

                });
            }).create(els[0], null, tableData, [1], currentPageNum);
    }
    delUser(id: string, type: string) {
        if (type == 'email-user') {
            new Ajax(UserUrls.delUserUrl).config().sendJson({ id }).result(
                () => {
                    this.responseSuccessRefresh('删除用户成功！', false);
                },
                () => { },
                this.parentFailHandel
            );
        } else if (type == 'wechat-user') {
            new Ajax(UserUrls.delWechatUrl).config().sendJson({ id }).result(
                () => {
                    this.responseSuccessRefresh('删除用户成功！', false);
                },
                () => { },
                this.parentFailHandel
            );
        } else {
            MessageControl.open('用户类型错误！', MessageType.ERROR);
        }
    }
}
PublicMainView.run(new UserManage(4));