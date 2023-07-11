<template>
    <div class="order-main-root">
        <main v-if="computeOrderData.length == 0">
            <EmptyGoodsPage :tipsTextOnEmpty="tipsTextOnEmpty" />
        </main>
        <main v-if="computeOrderData.length != 0">
            <header>
                <div class="goods-name">
                    商品名称
                </div>
                <div class="unit-price">
                    单价
                </div>
                <div class="goods-num">
                    数量
                </div>
                <div class="post-money">
                    付款金额
                </div>
                <div class="status">
                    状态
                </div>
                <div class="delete">
                    操作
                </div>
            </header>

            <div class="main" v-for="(orderGoodsData, key) in computeOrderData" :key="key"
                v-show="orderGoodsData['isShow']">
                <div class="goods-name">
                    <img :src="orderGoodsData['imgUrl']" @click="togoodsSpecificsPageFun(orderGoodsData['goodsId'])">
                    <span @click="togoodsSpecificsPageFun(orderGoodsData['goodsId'])">{{
                        orderGoodsData['goodsName'] }}</span>
                </div>
                <div class="unit-price">
                    <span>{{ orderGoodsData['goodsPrice'] }}元</span>
                </div>
                <div class="goods-num">
                    <span>{{ orderGoodsData['goodsNum'] }}</span>
                </div>
                <div class="post-money">
                    <span>{{ orderGoodsData['totalPrice'] }}元</span>
                </div>
                <div class="status">
                    <span class="unshipped" v-if="orderGoodsData['shipStatus'] == 1">未发货</span>
                    <span class="freighting" v-if="orderGoodsData['shipStatus'] == 2">运货中</span>
                    <span class="arrived" v-if="orderGoodsData['shipStatus'] == 3">已送达</span>
                </div>
                <div class="delete">
                    <div class="delete-box" @click="deleteSingleGoodsDataFun(orderGoodsData['orderId'])">
                        <el-icon :size="18">
                            <CloseIcon />
                        </el-icon>
                    </div>
                </div>
            </div>

            <footer>
                <aside :class="checkStyle[0]" @click="handleStyle(0)">全部订单</aside>
                <aside :class="checkStyle[1]" @click="handleStyle(1)">未发货</aside>
                <aside :class="checkStyle[2]" @click="handleStyle(2)">运货中</aside>
                <aside :class="checkStyle[3]" @click="handleStyle(3)">已送达</aside>
            </footer>
        </main>
    </div>
</template>

<script>
import EmptyGoodsPage from '@/components/emptyGoodsPage';
import { mapActions, mapState } from 'pinia';
import { useOrderGoods } from '@/stores/orderPageStores/useOrderGoodsStore';
export default {
    name: 'OrderMain',
    components: {
        EmptyGoodsPage
    },
    data() {
        return {
            //当没有商品的时候提示信息
            tipsTextOnEmpty: window.emptyOrderTips,
            checkStyle: ['check-style', '', '', ''],
            checkIndex: 0
        }
    },
    computed: {
        ...mapState(useOrderGoods, ['orderGoodsData']),
        computeOrderData() {
            const resultOrderData = [
                ...this.orderGoodsData
            ];
            //因为删除的时候会重新赋值，所以要做处理
            if (this.checkIndex != 0) {
                resultOrderData.map(v => v['shipStatus'] == this.checkIndex ? v['isShow'] = true : v['isShow'] = false);
            } else {
                resultOrderData.map(v => v['isShow'] = true);
            }
            return resultOrderData;
        }
    },
    mounted() {
        //获取订单数据
        this.getOrderDataFun();
    },
    methods: {
        //解构pinia方法
        ...mapActions(useOrderGoods, ['getOrderDataFun', 'delOrderDataFun']),
        //删除某个商品
        deleteSingleGoodsDataFun(orderId) {
            this.$selfMessageBox.openMessageBox({
                type: 'tips',
                tipsMessage: '你确定要删除此订单吗？',
                callBackFun: () => {
                    //删除
                    this.delOrderDataFun(orderId);
                }
            });
        },
        //处理不同类型订单样式
        handleStyle(index) {
            this.checkIndex = index;
            this.switchButtonStyle(index);
            if (index == 0) {
                this.computeOrderData.map(v => v['isShow'] = true);
            } else {
                this.computeOrderData.map(v => v['shipStatus'] === index ? v['isShow'] = true : v['isShow'] = false);
            }
        },
        //按钮切换样式
        switchButtonStyle(index) {
            this.checkStyle = this.checkStyle.map(() => '');
            this.checkStyle[index] = 'check-style';

        },
        //跳转页面
        togoodsSpecificsPageFun(goodsId) {
            this.$router.push({ name: 'goodsSpecifics', params: { goodsId } });
        }
    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.order-main-root {
    width: 75vw;
    color: $order-main-color;

    main {
        width: 100%;
        display: flex;
        flex-direction: column;
        position: relative;

        .goods-name {
            flex: 3;
        }

        .unit-price {
            flex: 1;
        }

        .goods-num {
            flex: 1;
        }

        .post-money {
            flex: 1;
        }

        .status {
            flex: 1;
        }

        .delete {
            flex: 1;
        }

        header {
            width: 100%;
            background-color: $order-main-goods-bgcolor;
            display: flex;
            height: 5em;
            align-items: center;

            div {
                display: flex;
                justify-content: center;
            }
        }

        .main {
            height: 8em;
            margin-top: .5em;
            width: 100%;
            background-color: $order-main-goods-bgcolor;
            display: flex;
            align-items: center;

            div {
                display: flex;
                justify-content: center;
            }

            .goods-name {
                display: flex;
                align-items: center;

                img {
                    border-radius: 10px;
                    height: 6em;
                    width: 8em;
                    cursor: pointer;
                }

                span {
                    margin-left: 1em;
                    font-size: 1.2em;
                    cursor: pointer;
                }
            }

            .goods-num {
                display: flex;
                align-items: center;

                .number-input-box {
                    width: 10em;
                    height: 2.5em;
                    border-radius: 4px;
                    border: 1px solid $border-color;
                    display: flex;

                    input {
                        width: 5em;
                        outline: none;
                        border: none;
                        height: 100%;
                        background-color: $order-main-goods-bgcolor;
                        text-align: center;
                        font-weight: bold;
                        font-size: 1em;
                    }

                    div {
                        flex: 1;
                        height: 100%;
                        display: flex;
                        justify-content: center;
                        align-items: center;

                        &:hover {
                            background-color: $order-main-bgcolor;
                        }
                    }

                }
            }

            .post-money {
                color: $special-color;
            }

            .status {
                span {
                    font-weight: bold;
                }

                .unshipped {
                    color: $order-unshipped-color;
                }

                .freighting {
                    color: $order-freighting-color;
                }

                .arrived {
                    color: $order-arrived-color;
                }
            }

            .delete {
                .delete-box {
                    cursor: pointer;
                    transition: .3s;
                    padding: 5px;
                    border-radius: 50%;

                    &:hover {
                        background-color: $special-color;

                        svg {
                            color: $order-main-goods-bgcolor !important;
                        }
                    }
                }
            }
        }

        footer {
            display: flex;
            justify-content: space-around;
            align-items: center;
            height: 4em;
            position: sticky;
            bottom: 0;
            left: 0;
            margin-top: 1em;
            background-color: $order-main-goods-bgcolor;

            aside {
                width: 8em;
                height: 90%;
                border-radius: 10px;
                background-color: $order-aside-button-bgcolor;
                display: flex;
                justify-content: center;
                align-items: center;
                cursor: pointer;

                &:hover {
                    background-color: $order-aside-button-hover-bgcolor;
                }
            }

            .check-style {
                background-color: $order-aside-button-hover-bgcolor;
            }
        }
    }
}
</style>