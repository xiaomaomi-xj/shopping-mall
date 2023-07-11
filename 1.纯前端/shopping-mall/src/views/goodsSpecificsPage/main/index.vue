<template>
    <div class="goods-specifice-main-root">
        <main class="goods-specifice-main-main">
            <div class="left">
                <el-carousel indicator-position="outside" height="450px">
                    <el-carousel-item v-for="(imgUrl, key) in goodsData['rotationGoodsImgs']" :key="key">
                        <main>
                            <img :src="imgUrl" alt="">
                        </main>
                    </el-carousel-item>
                </el-carousel>
            </div>
            <div class="right">
                <div class="goods-name">
                    {{ goodsData['goodsName'] }}
                </div>
                <div class="goods-describ">
                    {{ goodsData['goodsDescribe'] }}
                </div>
                <div class="price">
                    单价:<span>{{ goodsData['goodsPrice'] }}元</span>
                </div>
                <div class="goods-num">
                    <div class="number-input-box">
                        <div class="minus" @click="minusGoodsNumFun">
                            <el-icon :size="14">
                                <MinusIcon />
                            </el-icon>
                        </div>
                        <input type="text" v-model="goodsNum" @input="goodsNumFun">
                        <div class="plus" @click="plusGoodsNumFun">
                            <el-icon :size="14">
                                <PlusIcon />
                            </el-icon>
                        </div>
                    </div>
                </div>
                <div class="total">
                    总计: <span> {{ totalPrice }}元</span>
                </div>
                <div class="settlement">
                    <div class="login-status" v-if="loginStatus && goodsNum !== 0">
                        <aside @click="addShopCartFun">加入购物车</aside>
                        <aside @click="buyGoodsDataFun">立即购买</aside>
                    </div>
                    <div class="no-login-status" v-if="!loginStatus && goodsData !== 0">
                        <span>登录之后才能购买哦!</span>
                        <span @click="toLoginPageFun">立即前往登录>>></span>
                    </div>
                    <div class="no-login-status" v-if="goodsNum === 0">
                        <span></span>
                        <span  @click="toMainPageFun">商品已售罄，请看看其他商品吧!</span>
                    </div>
                </div>
            </div>
        </main>
    </div>
</template>

<script>
import { mapState, mapActions } from 'pinia';
import { useGoodsSpecifics } from '@/stores/goodsSpecificePageStores/useGoodsSpecificePageStore';
import { useUsers } from '@/stores/loginAndRegistPageStores/useUserStore';
export default {
    name: 'GoodsSpecificeMain',
    data() {
        return {
            //登录状态
            loginStatus: false,
            //商品名字,商品数量,总计金额
            goodsNum: 1,
            totalPrice: 0,
        }
    },
    computed: {
        //拿取pinia的值
        ...mapState(useGoodsSpecifics, ['goodsData']),
    },
    mounted() {
        //调用pinia的方法
        //因为地址在变，所以这个mounted是会一直执行的
        this.modifyGoodsData(this.$route.params['goodsId']);

        //没有的数据我们直接404
        if (this.goodsData === undefined) {
            //404路由是没有准确的路径的，我们可以随便指定一个
            this.$router.push({ name: 'notFund' });
            //防止报错我们可以给他一个值，因为跳转路由的时候，他还是会往下执行
            this.modifyGoodsData(1);
        }

        if (this.goodsData['maxBuyNum'] <= 0) {
            this.goodsNum = 0;
        }

        this.totalPrice = this.goodsData['goodsPrice'] * this.goodsNum;

        this.loginStatus = this.getTokenTolocalStorageOnCheck();
    },
    methods: {
        //拿取pinia的方法
        ...mapActions(useGoodsSpecifics, ['modifyGoodsData', 'buyGoodsData', 'addGoodsToShopCart']),
        ...mapActions(useUsers, ['getTokenTolocalStorageOnCheck']),
        //进行购买
        buyGoodsDataFun() {
            //模拟购买
            this.$selfMessageBox.openMessageBox({
                type: 'loading'
            });
            setTimeout(() => {
                this.$selfMessage.openMessage({
                    type: 'success',
                    message: '购买成功！'
                });
                //调用pinia方法，给订单增加数据
                this.buyGoodsData({
                    goodsData:this.goodsData,
                    goodsNum: this.goodsNum,
                    totalPrice: this.totalPrice
                });
                //关闭messageBox
                this.$selfMessageBox.closeMesssageBox();

                //路由跳转到订单页
                this.$router.push({ name: 'order' });
            }, 2000);
        },

        //加入购物车
        addShopCartFun() {
            //模拟加入购物车
            this.$selfMessageBox.openMessageBox({
                type: 'loading'
            });
            setTimeout(() => {
                //调用pinia的方法进行添加购物车,返回值就是提示的内容
                this.$selfMessage.openMessage(this.addGoodsToShopCart(this.goodsData));
                //关闭messageBox
                this.$selfMessageBox.closeMesssageBox();
            }, 1000);
        },

        //计算总金额函数
        computeTotalPrice() {
            this.totalPrice = this.goodsData['goodsPrice'] * this.goodsNum;
        },
        //输入框
        goodsNumFun() {
            this.goodsNum = isNaN(parseInt(this.goodsNum)) ? 1 : parseInt(this.goodsNum);
            if (this.goodsNum < 1) {
                this.goodsNum = 1;
            }
            if (this.goodsNum > this.goodsData['maxBuyNum']) {
                this.goodsNum = this.goodsData['maxBuyNum'];
            }
            this.computeTotalPrice();
        },
        //减数量按钮
        minusGoodsNumFun() {
            if (this.goodsNum > 1) {
                this.goodsNum--;
                this.computeTotalPrice();
            }
        },
        //加数量按钮
        plusGoodsNumFun() {
            if (this.goodsNum < this.goodsData['maxBuyNum']) {
                this.goodsNum += 1;
                this.computeTotalPrice();
            }
        },
        //跳转页面
        toLoginPageFun() {
            this.$router.push({ name: 'userLogin' });
        },
        //跳转主页
        toMainPageFun(){
            this.$router.push({ name: 'home' });
        }
    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.goods-specifice-main-root {
    width: 75vw;
    color: $goods-specifics-main-color;

    .goods-specifice-main-main {
        width: 100%;
        display: flex;
        justify-content: space-between;

        .left {
            width: 50%;
            background-color: $goods-specifics-main-goods-bgcolor;
            padding: 3em;

            .el-carousel__item {
                border-radius: 10px;
                main {
                    width: 100%;
                    height: 100%;
                    img {
                        width: 100%;
                        height: 100%;
                    }
                }
            }
        }

        .right {
            width: 50%;
            background-color: $goods-specifics-main-goods-bgcolor;
            height: 100%;

            .goods-name {
                font-size: 1.8em;
                word-break: break-all;
                padding: 1em;
            }

            .goods-describ {
                word-break: break-all;
                color: $goods-specifics-main-goods-describe-color;
                padding: 2em;
            }

            .price {
                font-size: 1.1em;
                color: $special-color;
                padding: 2em;
            }

            .goods-num {
                display: flex;
                align-items: center;
                padding: 2em;

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
                        background-color: $shop-cart-main-goods-bgcolor;
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
                        cursor: pointer;

                        &:hover {
                            background-color: $shop-cart-main-bgcolor;
                        }
                    }

                }
            }

            .total {
                font-size: 1.2em;
                color: $special-color;
                padding: 2em;
            }

            .settlement {
                padding: 2em;
                width: 80%;

                .login-status {
                    display: flex;
                    width: 100%;
                    justify-content: space-between;

                    aside {
                        width: 40%;
                        height: 3em;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        cursor: pointer;
                        background-color: $special-color;
                        color: $goods-specifics-main-button-color;
                        border-radius: 10px;
                        font-weight: bold;

                        &:hover {
                            background-color: $goods-specifics-main-button-hover-bgcolor;
                        }
                    }
                }

                .no-login-status {
                    display: flex;
                    width: 100%;
                    flex-direction: column;
                    align-items: center;
                    height: 4em;
                    justify-content: space-between;

                    span {
                        font-size: 1.1em;

                        &:last-of-type {
                            color: $special-color;
                            cursor: pointer;
                        }
                    }
                }
            }
        }
    }
}
</style>