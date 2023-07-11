<template>
  <div style="width: 100%">
    <el-carousel :interval="interval" type="card" height="400px">
      <el-carousel-item v-for="(pageData, key) in goodsDatas" :key="key">
        <main>
          <img :src="pageData['imgUrl']" alt="" />
          <aside>
            <div>
              <span class="goods-name">{{
                pageData["goodsName"]
              }}</span>
              <span class="describe">{{
                pageData["goodsDescribe"]
              }}</span>
              <span class="price">${{ pageData["goodsPrice"] }}元起</span>
            </div>
            <article>
              <span @click="togoodsSpecificsPageFun(pageData['goodsId'])">查看详情</span>
            </article>
          </aside>
        </main>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
import { mapState, mapActions } from 'pinia';
import { useRotationChart } from '@/stores/mainPageStores/useRotationChartStore';
export default {
  data() {
    return {
      //轮播速度，每页的数据,有几页数据就轮播几页
      interval: 3000,
    };
  },
  computed: {
    //拿到pinia的数据
    ...mapState(useRotationChart, ['goodsDatas']),
  },
  mounted() {
    //调用pinia的方法
    this.initGoodsDatas();
  },
  methods: {
    //拿到pinia的方法
    ...mapActions(useRotationChart, ['initGoodsDatas']),

    //跳转页面
    togoodsSpecificsPageFun(goodsId) {
      this.$router.push({ name: 'goodsSpecifics', params: { goodsId } });
    }
  }
};
</script>
  
<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";

.el-carousel__item {
  main {
    width: 100%;
    height: 100%;
    position: relative;
    border-radius: 5px;
    overflow: hidden;

    img {
      width: 100%;
      height: 100%;
    }

    aside {
      padding: 10px;
      width: 45%;
      height: 60%;
      display: flex;
      background-color: $transparent-bgcolor;
      position: absolute;
      border-radius: 10px;
      top: 50%;
      transform: translateY(-50%);
      left: 50%;
      flex-direction: column;
      justify-content: space-between;
      align-items: center;
      box-shadow: 0px 0px 10px 1px $shadow-white-color;

      div {
        width: 100%;
        height: 60%;
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-direction: column;

        .goods-name {
          text-align: center;
          width: 90%;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          font-size: 1.3em;
        }

        .describe {
          text-align: center;
          width: 90%;
          font-size: 1em;
          color: $describe-color;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        .price {
          text-align: center;
          width: 90%;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          font-size: 0.9em;
          color: $special-color;
        }
      }

      article {
        text-align: center;
        height: 40%;
        display: flex;
        justify-content: center;
        align-items: center;

        span {
          color: $button-a-bgcolor;
          background-color: $button-a-fgcolor;
          display: inline;
          border-radius: 3px;
          padding: 10px;
          text-decoration: none;
          box-sizing: content-box;
          transition: 0.5s;
          font-weight: bold;

          &:hover {
            color: $button-a-fgcolor;
            background-color: $button-a-bgcolor;
          }
        }
      }
    }
  }
}
</style>