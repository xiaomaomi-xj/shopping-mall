<template>
    <div class="shop-cart-main-root">
        <main v-if="goodsDatasOnShopingCart.length == 0">
            <EmptyGoodsPage :tipsTextOnEmpty="tipsTextOnEmpty" />
        </main>
        <main v-if="goodsDatasOnShopingCart.length != 0">
            <header>
                <div class="select">
                    <div class="check-box" @click="selectAllFun" v-if="!selectAllSwitch">
                        <el-icon :size="18">
                            <SelectIcon />
                        </el-icon>
                    </div>
                    <div class="checked-box" @click="selectAllFun" v-if="selectAllSwitch">
                        <el-icon :size="18">
                            <SelectIcon />
                        </el-icon>
                    </div>
                    全选
                </div>
                <div class="goods-name">
                    商品名称
                </div>
                <div class="unit-price">
                    单价
                </div>
                <div class="goods-num">
                    数量
                </div>
                <div class="subtotal">
                    小计
                </div>
                <div class="delete">
                    操作
                </div>
            </header>

            <div class="main" v-for="(goodsData, key) in goodsDatasOnShopingCart" :key="key">
                <div class="select">
                    <div class="check-box" v-if="!goodsData['selectSwitch'] && goodsData['goodsNum'] !== 0"
                        @click="singleSelectFun(key)">
                        <el-icon :size="18">
                            <SelectIcon />
                        </el-icon>
                    </div>
                    <div class="checked-box" v-if="goodsData['selectSwitch'] && goodsData['goodsNum'] !== 0"
                        @click="singleSelectFun(key)">
                        <el-icon :size="18">
                            <SelectIcon />
                        </el-icon>
                    </div>
                    <div class="checked-box" v-if="goodsData['goodsNum'] === 0">
                        <span>已售罄</span>
                    </div>
                </div>
                <div class="goods-name">
                    <img :src="goodsData['goodsData']['imgUrl']"
                        @click="togoodsSpecificsPageFun(goodsData['goodsData']['goodsId'])">
                    <span @click="togoodsSpecificsPageFun(goodsData['goodsData']['goodsId'])">{{
                        goodsData['goodsData']['goodsName'] }}</span>
                </div>
                <div class="unit-price">
                    <span>{{ goodsData['goodsData']['goodsPrice'] }}元</span>
                </div>
                <div class="goods-num">
                    <div class="number-input-box-over" v-if="goodsData['goodsNum'] === 0">
                        <span>已售罄</span>
                    </div>
                    <div class="number-input-box" v-if="goodsData['goodsNum'] !== 0">
                        <div class="minus" @click="minusGoodsNumFun(key)">
                            <el-icon :size="14">
                                <MinusIcon />
                            </el-icon>

                        </div>
                        <input type="text" v-model="goodsData['goodsNum']" @input="singleGoodsNumFun(key)">
                        <div class="plus" @click="plusGoodsNumFun(key)">
                            <el-icon :size="14">
                                <PlusIcon />
                            </el-icon>

                        </div>
                    </div>
                </div>
                <div class="subtotal">
                    <span v-if="goodsData['goodsNum'] !== 0">{{ goodsData['totalPrice'] }}元</span>
                    <span v-if="goodsData['goodsNum'] === 0">已售罄</span>
                </div>
                <div class="delete">
                    <div class="delete-box" @click="deleteSingleGoodsDataFun(goodsData['goodsData']['goodsId'])">
                        <el-icon :size="18">
                            <CloseIcon />
                        </el-icon>
                    </div>
                </div>
            </div>

            <footer>
                <div class="left">
                    已选择<span>{{ selectGoodsNum }}</span>件
                </div>
                <div class="right" v-if="selectGoodsNum === 0">
                    合计：<span>{{ selectGoodsPrice }}</span>元
                    <aside>
                        去结算
                    </aside>
                </div>
                <div class="right" @click="buyGoodsDatas" v-if="selectGoodsNum != 0">
                    合计：<span>{{ selectGoodsPrice }}</span>元
                    <aside class="aside-style">
                        去结算
                    </aside>
                </div>
            </footer>
        </main>
    </div>
</template>

<script>
import EmptyGoodsPage from '@/components/emptyGoodsPage';
import { mapActions } from 'pinia';
import { useShopCart } from '@/stores/shopCartPageStores/useShopCartStore';
export default {
    name: 'ShopCartMain',
    components: {
        //当没有商品的时候显示
        EmptyGoodsPage
    },
    data() {
        return {
            //当没有商品的时候提示信息
            tipsTextOnEmpty: window.emptyShopCartTips,
            //全部选择
            selectAllSwitch: false,
            //选择商品的数量
            selectGoodsNum: 0,
            //选择的商品的总价格
            selectGoodsPrice: 0,
            goodsDatasOnShopingCart: []
        }
    },
    mounted() {
        this.initGoodsDatas();

        //获取当前用户的购物车数据
        this.goodsDatasOnShopingCart=this.getCurrentUserShopCart();

        //检查
        this.checkGoodsMaxBuyNum();

    },
    methods: {

        //拿到pinia的方法
        ...mapActions(useShopCart, ['deleteGoodsData', 'getCurrentUserShopCart', 'initGoodsDatas', 'buyGoodsDatasOnShopCart']),

        //检查当前的所有商品是否卖完
        checkGoodsMaxBuyNum() {
            this.goodsDatasOnShopingCart.map(v => {
                if (v['goodsData']['maxBuyNum'] <= 0) {
                    v['goodsNum'] = 0;
                    v['totalPrice'] = 0;
                }
            });
        },

        //数据改变，计算结算数据
        computeSettlementData() {
            //先清空再累加
            this.selectGoodsNum = 0;
            this.selectGoodsPrice = 0;
            this.goodsDatasOnShopingCart.map(v => {
                if (v['selectSwitch']) {
                    this.selectGoodsNum += v['goodsNum'];
                    this.selectGoodsPrice += v['totalPrice'];
                }
            });
        },
        //全选
        selectAllFun() {
            this.selectAllSwitch = !this.selectAllSwitch;
            //全选就把所有单个的选了,不管用户选了哪一个
            this.goodsDatasOnShopingCart.map(v => {
                //全部为选择
                v['selectSwitch'] = this.selectAllSwitch;
            });
            //更新结算数据
            this.computeSettlementData();
        },
        //单个商品的选择
        singleSelectFun(index) {
            this.goodsDatasOnShopingCart[index]['selectSwitch'] = !this.goodsDatasOnShopingCart[index]['selectSwitch'];
            //更新结算数据
            this.computeSettlementData();
        },
        //数量改变，小计改变
        computeSubtotalFun(index) {
            this.goodsDatasOnShopingCart[index]['totalPrice'] = this.goodsDatasOnShopingCart[index]['goodsNum'] * this.goodsDatasOnShopingCart[index]['goodsData']['goodsPrice'];
            //数据改变的同时，更新结算数据
            this.computeSettlementData();
        },
        //单个商品数量输入框
        singleGoodsNumFun(index) {
            this.goodsDatasOnShopingCart[index]['goodsNum'] = isNaN(parseInt(this.goodsDatasOnShopingCart[index]['goodsNum'])) ? 1 : parseInt(this.goodsDatasOnShopingCart[index]['goodsNum']);
            if (this.goodsDatasOnShopingCart[index]['goodsNum'] < 1) {
                this.goodsDatasOnShopingCart[index]['goodsNum'] = 1;
            }
            if (this.goodsDatasOnShopingCart[index]['goodsNum'] > this.goodsDatasOnShopingCart[index]['goodsData']['maxBuyNum']) {
                this.goodsDatasOnShopingCart[index]['goodsNum'] = this.goodsDatasOnShopingCart[index]['goodsData']['maxBuyNum'];
            }
            this.computeSubtotalFun(index);
        },
        //减少数量
        minusGoodsNumFun(index) {
            if (this.goodsDatasOnShopingCart[index]['goodsNum'] > 1) {
                this.goodsDatasOnShopingCart[index]['goodsNum']--;
                this.computeSubtotalFun(index);
            }
        },
        //增加数量
        plusGoodsNumFun(index) {
            if (this.goodsDatasOnShopingCart[index]['goodsNum'] < this.goodsDatasOnShopingCart[index]['goodsData']['maxBuyNum']) {
                this.goodsDatasOnShopingCart[index]['goodsNum']++;
                this.computeSubtotalFun(index);
            }
        },
        //删除某一个购物车的商品
        deleteSingleGoodsDataFun(goodsId) {

            //删除前弹出提示
            this.$selfMessageBox.openMessageBox({
                type: 'tips',
                tipsMessage: '你确定要删除购物车里面的这个商品吗？',
                //写一个回调方法，让弹窗那边调用
                callBackFun: () => {
                    //调用pinia的删除方法
                    this.deleteGoodsData(goodsId);
                    //然后刷新一下当前用户数据
                    this.goodsDatasOnShopingCart=this.getCurrentUserShopCart();
                    //删除后，还要更新结算数据
                    this.computeSettlementData();
                }
            });
        },
        //购买商品
        buyGoodsDatas() {
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
                this.buyGoodsDatasOnShopCart(
                    //传的是选中的数据
                    this.goodsDatasOnShopingCart.filter(v => v['selectSwitch'] && v['goodsNum'] > 0)
                );
                //关闭messageBox
                this.$selfMessageBox.closeMesssageBox();
                //路由跳转到订单页
                this.$router.push({ name: 'order' });
            }, 2000);
        },
        //跳转页面
        togoodsSpecificsPageFun(goodsId) {
            //这里需要接受商品id，先拿key来用
            this.$router.push({ name: 'goodsSpecifics', params: { goodsId } });
        }
    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.shop-cart-main-root {
    width: 75vw;
    color: $shop-cart-main-color;

    main {
        width: 100%;
        display: flex;
        flex-direction: column;
        position: relative;

        .select {
            flex: 1;

            .check-box {
                border: 1px solid $shop-cart-main-color;
                padding: 1px;
                border-radius: 1px;
                margin-right: 5px;
                cursor: pointer;

                svg {
                    color: $shop-cart-main-goods-bgcolor !important;

                    &:hover {
                        color: $shop-cart-main-select-icon-hover-color !important;
                    }
                }
            }

            .checked-box {
                padding: 2px;
                border-radius: 1px;
                margin-right: 5px;
                background-color: $special-color;
                cursor: pointer;

                span {
                    color: $shop-cart-main-bgcolor;
                }

                svg {
                    color: $shop-cart-main-goods-bgcolor !important;
                }
            }
        }

        .goods-name {
            flex: 5;
        }

        .unit-price {
            flex: 1;
        }

        .goods-num {
            flex: 1;
        }

        .subtotal {
            flex: 1;
        }

        .delete {
            flex: 1;
        }

        header {
            width: 100%;
            background-color: $shop-cart-main-goods-bgcolor;
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
            background-color: $shop-cart-main-goods-bgcolor;
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

                .number-input-box-over {
                    width: 10em;
                    height: 2.5em;
                    border-radius: 4px;
                    background-color: $special-color;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    color: $shop-cart-main-bgcolor;
                }

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

                        &:hover {
                            background-color: $shop-cart-main-bgcolor;
                        }
                    }

                }
            }

            .subtotal {
                color: $special-color;
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
                            color: $shop-cart-main-goods-bgcolor !important;
                        }
                    }
                }
            }
        }

        footer {
            margin-top: 1em;
            background-color: $shop-cart-main-goods-bgcolor;
            display: flex;
            align-items: center;
            justify-content: space-between;
            height: 4em;
            position: sticky;
            bottom: 0;
            left: 0;
            width: 100%;

            .left {
                margin-left: 3em;
                height: 100%;
                display: flex;
                align-items: center;

                span {
                    color: $special-color;
                }
            }

            .right {
                display: flex;
                height: 100%;
                display: flex;
                align-items: center;

                span {
                    color: $special-color;
                    font-size: 1.5em;
                }

                aside {
                    cursor: default;
                    margin-left: 2em;
                    width: 10em;
                    height: 100%;
                    font-size: 1.3em;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    color: $shop-cart-main-settlement-aside-color;
                    background-color: $shop-cart-main-settlement-aside-bgcolor;
                }

                .aside-style {
                    color: $shop-cart-main-goods-bgcolor;
                    background-color: $special-color;
                    font-weight: bold;
                    cursor: pointer;
                }
            }
        }
    }
}
</style>