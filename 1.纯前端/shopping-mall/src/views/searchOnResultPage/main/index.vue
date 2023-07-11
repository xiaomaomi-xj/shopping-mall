<template>
  <div class="search-result-main-root">
    <aside v-if="searchResultDatas.length == 0">
      <EmptyGoodsPage :tipsTextOnEmpty="tipsTextOnEmpty" />
    </aside>
    <main v-if="searchResultDatas.length != 0">
      <div v-for="(searchResultData, key) in searchResultDatas" :key="key" @click="togoodsSpecificsPageFun(searchResultData['goodsId'])">
        <header>
          <main>
            <img :src="searchResultData['imgUrl']" alt="" />
          </main>
          <footer>
            <span class="goods-name">{{
              searchResultData["goodsName"]
            }}</span>
            <span class="describe">{{
              searchResultData["goodsDescribe"]
            }}</span>
            <span class="price">${{ searchResultData["goodsPrice"] }}元起</span>
          </footer>
        </header>
      </div>
    </main>
  </div>
</template>

<script>
import EmptyGoodsPage from '@/components/emptyGoodsPage';
import { mapState, mapActions } from 'pinia';
import { useSearchOnResult } from '@/stores/searchOnResultPageStores/useSearchOnResultStore';
export default {
  name: 'SearchResultMain',
  components: {
    EmptyGoodsPage
  },
  data() {
    return {
      //当没有商品的时候提示信息
      tipsTextOnEmpty: window.emptySearchTips,
    }
  },
  computed: {
    ...mapState(useSearchOnResult,['searchResultDatas']),
  },
  mounted() {
    //根据参数搜索
    this.modifySearchResultDatas(this.$route.query['content']);
  },
  methods: {

    ...mapActions(useSearchOnResult,['modifySearchResultDatas']),

    togoodsSpecificsPageFun(goodsId) {
      this.$router.push({ name: 'goodsSpecifics', params: { goodsId } });
    }
  },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.search-result-main-root {
  width: 75vw;
  color: $order-main-color;

  main {
    width: 100%;
    background-color: $search-result-goods-main-bgcolor;
    display: flex;
    flex-flow: wrap;
    justify-content: space-around;
    padding: 1em 2em 2em 2em;

    div {
      margin-top: 1em;
      display: flex;
      justify-content: space-around;
      width: 19%;
      background-color: $title-hover-fgcolor;
      border-radius: 5px;

      header {
        transition: 0.5s;
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-around;
        border-radius: 5px;
        padding: 1em;
        box-sizing: border-box;
        cursor: pointer;

        &:hover {
          box-shadow: 2px 2px 10px 2px $shadow-color;
        }

        &:active {
          box-shadow: 2px 2px 10px 2px $shadow-color inset;
        }

        main {
          width: 90%;
          height: 160px;
          display: flex;
          justify-content: center;
          align-items: center;
          padding: 10px;
          background-color: $title-hover-fgcolor;
          border-radius: 10px;

          img {
            width: 100%;
            height: 100%;
          }
        }

        footer {
          height: 5em;
          width: 90%;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: space-around;

          span {
            text-align: center;
          }

          .goods-name {
            width: 90%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            font-size: 1.2em;
          }

          .describe {
            width: 90%;
            font-size: 0.9em;
            color: $describe-color;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }

          .price {
            width: 90%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            font-size: 0.8em;
            color: $special-color;
          }
        }
      }
    }
  }
}
</style>