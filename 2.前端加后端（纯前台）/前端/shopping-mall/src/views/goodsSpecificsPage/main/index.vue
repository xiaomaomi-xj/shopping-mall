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
                    <div class="number-input-box" v-if="goodsData['maxBuyNum'] > 0">
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
                    <div class="number-input-box" v-if="goodsData['maxBuyNum'] <= 0">
                        <div class="close">已售罄</div>
                    </div>
                </div>
                <div class="total">
                    <span v-if="goodsData['maxBuyNum'] > 0">总计: {{ computeTotalPrice }}元</span>
                    <span v-if="goodsData['maxBuyNum'] <= 0" class="close">已售罄</span>
                </div>
                <div class="settlement">
                    <div class="login-status" v-if="loginStatus && goodsData['maxBuyNum'] > 0">
                        <aside @click="addShopCartFun">加入购物车</aside>
                        <aside @click="buyGoodsDataFun">立即购买</aside>
                    </div>
                    <div class="no-login-status" v-if="!loginStatus">
                        <span>登录之后才能购买哦!</span>
                        <span @click="toLoginPageFun">立即前往登录>>></span>
                    </div>
                    <div class="no-login-status" v-if="goodsData['maxBuyNum'] <= 0">
                        <span></span>
                        <span @click="toMainPageFun">商品已售罄，请看看其他商品吧!</span>
                    </div>
                </div>
            </div>
        </main>
        <article class="merchant-utw" :style="`opacity: ${opacity};`" v-if="merchantShow">
            <div class="mark">
                <div class="title">
                    <span>请选择你要使用的支付方式</span>
                </div>
                <div class="content">
                    <div class="item" v-for="(merchant, key) in merchantInfos" :key="key"
                        @click="toPayUrl(merchant['merchantId'])">
                        <span>{{ merchant['merchantName'] }}</span>
                    </div>
                </div>
                <div class="close-utw">
                    <div @click="closeUtw">关闭</div>
                </div>
            </div>
        </article>
    </div>
</template>

<script>
import { mapState, mapActions } from 'pinia';
import { useGoodsSpecifics } from '@/stores/goodsSpecificePageStores/useGoodsSpecificePageStore';
import { useUserCheck } from '@/stores/userStores/useUserCheckStore';
import { useShopCart } from '@/stores/shopCartPageStores/useShopCartStore';
import { useMerchant } from '@/stores/merchantStores/useMerchantStore';
import { useOrderGoods } from '@/stores/orderPageStores/useOrderGoodsStore';
export default {
    name: 'GoodsSpecificeMain',
    data() {
        return {
            //商品数量,总计金额
            goodsNum: 1,
            //弹窗控制
            opacity: 0,
            merchantShow: false
        }
    },
    computed: {
        ...mapState(useMerchant, ['merchantInfos']),
        ...mapState(useGoodsSpecifics, ['goodsData']),
        ...mapState(useUserCheck, ['loginStatus']),
        computeTotalPrice(){
            return this.goodsData['goodsPrice'] * this.goodsNum;
        }
    },
    mounted() {
        //根据路径获取商品,如果路径id太长会错误，所以要检查
        let goodsId = this.$route.params['goodsId'];
        goodsId.length > 19 ? this.$router.push({ name: 'notFund' }) : this.getGoodsDataById(goodsId);
        //验证登录
        this.checkLoginUser();
        //获取所有商户
        this.getAllMerchantFun();
    },
    methods: {
        ...mapActions(useMerchant, ['getAllMerchantFun']),
        ...mapActions(useGoodsSpecifics, ['getGoodsDataById']),
        ...mapActions(useUserCheck, ['checkLoginUser']),
        ...mapActions(useShopCart, ['addShopCartDataFun']),
        ...mapActions(useOrderGoods, ['createSingleOrderDataFun']),
        //进行购买
        buyGoodsDataFun() {
            this.getAllMerchantFun();
            this.merchantShow = true;
            setTimeout(() => {
                this.opacity = 1;
            });
        },
        //关闭商品信息弹窗
        closeUtw() {
            this.opacity = 0;
            setTimeout(() => {
                this.merchantShow = false;
            }, 500);
        },
        //加入购物车
        addShopCartFun() {
            this.addShopCartDataFun(this.goodsData['goodsId']);
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
        },
        //减数量按钮
        minusGoodsNumFun() {
            if (this.goodsNum > 1) {
                this.goodsNum--;
            }
        },
        //加数量按钮
        plusGoodsNumFun() {
            if (this.goodsNum < this.goodsData['maxBuyNum']) {
                this.goodsNum += 1;
            }
        },
        //跳转支付地址
        toPayUrl(merchantId) {
            const goodsInfo = {};
            goodsInfo['totalPrice'] = this.computeTotalPrice;
            goodsInfo['goodsId'] = this.goodsData['goodsId'];
            goodsInfo['goodsNum'] = this.goodsNum;
            this.createSingleOrderDataFun(merchantId, goodsInfo);
        },
        //跳转页面
        toLoginPageFun() {
            this.$router.push({ name: 'userLogin' });
        },
        //跳转主页
        toMainPageFun() {
            this.$router.push({ name: 'home' });
        }
    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/zIndexValue.scss';
@import '@/assets/sass/colors.scss';

.goods-specifice-main-root {
    position: relative;
    width: 75vw;
    color: $goods-specifics-main-color;

    .merchant-utw {
        width: 100%;
        height: 100%;
        position: absolute;
        background-color: $goods-specifics-utw-mark-bgcolor;
        z-index: $selfUtwZIndex;
        left: 0;
        top: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        opacity: 0;
        transition: .5s;

        .mark {
            width: 40%;
            background-color: $goods-specifics-main-bgcolor;
            padding: 1em;
            border-radius: 8px;
            box-shadow: -2px -2px 10px 10px $shadow-color;
            box-sizing: border-box;

            .title {
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: .8em;
                box-sizing: border-box;

                span {
                    font-size: 1.4em;
                    font-weight: bolder;
                    color: $goods-specifics-main-color;
                }

            }

            .content {
                width: 100%;
                max-height: 230px;
                padding: 1em;
                display: flex;
                //主轴居中会影响滚动条
                // justify-content: center;
                align-items: center;
                box-sizing: border-box;
                flex-direction: column;
                overflow-y: scroll;

                .item {
                    height: 40px;
                    transition: .5s;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    cursor: pointer;
                    font-size: 1.1em;
                    font-weight: bolder;
                    padding: 1em;
                    margin: 10px;
                    border-radius: 5px;
                    width: 80%;
                    border: 2px solid $goods-specifics-main-color;

                    span {
                        width: 80%;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        white-space: nowrap;
                    }

                    &:hover {
                        border-color: $goods-specifics-main-success-color;
                        background-color: $goods-specifics-main-success-color;
                        color: $goods-specifics-main-success-bgcolor;
                    }
                }
            }

            .close-utw {
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: .8em;
                box-sizing: border-box;

                div {
                    cursor: pointer;
                    transition: .3s;
                    font-size: 1em;
                    font-weight: bolder;
                    width: 5em;
                    height: 2.5em;
                    border: 2px solid $goods-specifics-main-color;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    border-radius: 5px;

                    &:hover {
                        border-color: $goods-specifics-main-error-color;
                        background-color: $goods-specifics-main-error-color;
                        color: $goods-specifics-main-error-bgcolor;
                    }
                }
            }
        }
    }

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

                    .close {
                        cursor: default;
                        background-color: $special-color;

                        &:hover {
                            background-color: $special-color;
                        }

                        color: $shop-cart-main-goods-bgcolor;
                    }

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