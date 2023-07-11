<template>
    <div class="shop-cart-main-root">
        <main v-if="computedShopCartGoodsData.length <= 0">
            <EmptyGoodsPage :tipsTextOnEmpty="tipsTextOnEmpty" />
        </main>
        <main v-if="computedShopCartGoodsData.length > 0">
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

            <div class="main" v-for="(goodsData, key) in computedShopCartGoodsData" :key="key">
                <div class="select">
                    <div class="check-box" v-if="!goodsData['selectSwitch'] && goodsData['goodsNum'] > 0"
                        @click="singleSelectFun(key)">
                        <el-icon :size="18">
                            <SelectIcon />
                        </el-icon>
                    </div>
                    <div class="checked-box" v-if="goodsData['selectSwitch'] && goodsData['goodsNum'] > 0"
                        @click="singleSelectFun(key)">
                        <el-icon :size="18">
                            <SelectIcon />
                        </el-icon>
                    </div>
                    <div class="checked-box" v-if="goodsData['goodsNum'] <= 0">
                        <span>已售罄</span>
                    </div>
                </div>
                <div class="goods-name">
                    <img :src="goodsData['imgUrl']" @click="togoodsSpecificsPageFun(goodsData['goodsId'])">
                    <span @click="togoodsSpecificsPageFun(goodsData['goodsId'])">{{
                        goodsData['goodsName'] }}</span>
                </div>
                <div class="unit-price">
                    <span>{{ goodsData['goodsPrice'] }}元</span>
                </div>
                <div class="goods-num">
                    <div class="number-input-box-over" v-if="goodsData['goodsNum'] <= 0">
                        <span>已售罄</span>
                    </div>
                    <div class="number-input-box" v-if="goodsData['goodsNum'] > 0">
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
                    <span v-if="goodsData['goodsNum'] > 0">{{ goodsData['totalPrice'] }}元</span>
                    <span v-if="goodsData['goodsNum'] <= 0">已售罄</span>
                </div>
                <div class="delete">
                    <div class="delete-box" @click="deleteSingleGoodsDataFun(goodsData['goodsId'])">
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
                <div class="right" @click="buyGoodsDatas" v-if="selectGoodsNum > 0">
                    合计：<span>{{ selectGoodsPrice }}</span>元
                    <aside class="aside-style">
                        去结算
                    </aside>
                </div>
            </footer>
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
import EmptyGoodsPage from '@/components/emptyGoodsPage';
import { mapActions, mapState } from 'pinia';
import { useShopCart } from '@/stores/shopCartPageStores/useShopCartStore';
import { useMerchant } from '@/stores/merchantStores/useMerchantStore';
import { useOrderGoods } from '@/stores/orderPageStores/useOrderGoodsStore';
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
            //弹窗控制
            opacity: 0,
            merchantShow: false
        }
    },
    computed: {
        ...mapState(useShopCart, ['shopCartGoodsData']),
        ...mapState(useMerchant, ['merchantInfos']),
        //处理一些后台不存在的数据，例如数量，总价，等等
        computedShopCartGoodsData() {
            const resultShopCartGoodsDatas = [
                ...this.shopCartGoodsData,
            ];
            resultShopCartGoodsDatas.map(resultShopCartGoodsData => {
                resultShopCartGoodsData['goodsNum'] = 1;
                resultShopCartGoodsData['totalPrice'] = resultShopCartGoodsData['goodsPrice'];
                resultShopCartGoodsData['selectSwitch'] = false;
                //如果没有库存了
                if (resultShopCartGoodsData['maxBuyNum'] <= 0) {
                    resultShopCartGoodsData['goodsNum'] = 0;
                    resultShopCartGoodsData['totalPrice'] = 0
                }
            });
            return resultShopCartGoodsDatas;
        }
    },
    mounted() {
        //获取数据
        this.getShopCartDataFun();
        //获取商户
        this.getAllMerchantFun();
    },
    methods: {
        //拿到pinia的方法
        ...mapActions(useShopCart, ['getShopCartDataFun', 'delShopCartDataFun']),
        ...mapActions(useMerchant, ['getAllMerchantFun']),
        ...mapActions(useOrderGoods, ['createMultipleOrderDataFun']),
        //数据改变，计算结算数据
        computeSettlementData() {
            //先清空再累加
            this.selectGoodsNum = 0;
            this.selectGoodsPrice = 0;
            this.computedShopCartGoodsData.map(v => {
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
            this.computedShopCartGoodsData.map(v => {
                //全部为选择
                if (v['goodsNum'] > 0) {
                    v['selectSwitch'] = this.selectAllSwitch;
                }
            });
            //更新结算数据
            this.computeSettlementData();
        },
        //单个商品的选择
        singleSelectFun(index) {
            this.computedShopCartGoodsData[index]['selectSwitch'] = !this.computedShopCartGoodsData[index]['selectSwitch'];
            //更新结算数据
            this.computeSettlementData();
        },
        //数量改变，小计改变
        computeSubtotalFun(index) {
            this.computedShopCartGoodsData[index]['totalPrice'] = this.computedShopCartGoodsData[index]['goodsNum'] * this.computedShopCartGoodsData[index]['goodsPrice'];
            //数据改变的同时，更新结算数据
            this.computeSettlementData();
        },
        //单个商品数量输入框
        singleGoodsNumFun(index) {
            this.computedShopCartGoodsData[index]['goodsNum'] = isNaN(parseInt(this.computedShopCartGoodsData[index]['goodsNum'])) ? 1 : parseInt(this.computedShopCartGoodsData[index]['goodsNum']);
            if (this.computedShopCartGoodsData[index]['goodsNum'] < 1) {
                this.computedShopCartGoodsData[index]['goodsNum'] = 1;
            }
            if (this.computedShopCartGoodsData[index]['goodsNum'] > this.computedShopCartGoodsData[index]['maxBuyNum']) {
                this.computedShopCartGoodsData[index]['goodsNum'] = this.computedShopCartGoodsData[index]['maxBuyNum'];
            }
            this.computeSubtotalFun(index);
        },
        //减少数量
        minusGoodsNumFun(index) {
            if (this.computedShopCartGoodsData[index]['goodsNum'] > 1) {
                this.computedShopCartGoodsData[index]['goodsNum']--;
                this.computeSubtotalFun(index);
            }
        },
        //增加数量
        plusGoodsNumFun(index) {
            if (this.computedShopCartGoodsData[index]['goodsNum'] < this.computedShopCartGoodsData[index]['maxBuyNum']) {
                this.computedShopCartGoodsData[index]['goodsNum']++;
                this.computeSubtotalFun(index);
            }
        },
        //删除某一个购物车的商品
        deleteSingleGoodsDataFun(goodsId) {
            this.$selfMessageBox.openMessageBox({
                type: 'tips',
                tipsMessage: '你确定要删除购物车里面的这个商品吗？',
                callBackFun: () => {
                    //调用pinia的删除方法
                    this.delShopCartDataFun(goodsId);
                    //删除后，数据初始化
                    this.selectAllSwitch = false;
                    this.selectGoodsNum = 0;
                    this.selectGoodsPrice = 0;
                }
            });
        },
        //购买商品
        buyGoodsDatas() {
            this.getAllMerchantFun();
            this.merchantShow = true;
            setTimeout(() => {
                this.opacity = 1;
            });
        },
        //关闭弹窗
        closeUtw() {
            this.opacity = 0;
            setTimeout(() => {
                this.merchantShow = false;
            }, 500);
        },
        //跳转支付页面
        toPayUrl(mercahntId) {
            const goodsInfo = {};
            goodsInfo['goodsNum'] = this.selectGoodsNum;
            goodsInfo['goodsPrice'] = this.selectGoodsPrice;
            const goodsInfos = [];
            this.computedShopCartGoodsData.map(v => {
                if (v['selectSwitch']) {
                    goodsInfos.push({
                        goodsId: v['goodsId'],
                        goodsNum: v['goodsNum'],
                        totalPrice: v['totalPrice']
                    });
                }
            });
            this.createMultipleOrderDataFun(mercahntId, goodsInfo, goodsInfos);
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
@import '@/assets/sass/zIndexValue.scss';
@import '@/assets/sass/colors.scss';

.shop-cart-main-root {
    position: relative;
    width: 75vw;
    color: $shop-cart-main-color;
    min-height: 500px;

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
            //粘性定位（没滚到自己的位置就相当于fixed,一旦滚到自己的位置，就相当于relative，回到自己的位置）
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