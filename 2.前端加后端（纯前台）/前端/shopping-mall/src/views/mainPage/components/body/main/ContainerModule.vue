<template>
  <div>
    <article v-for="(mainData, key) in containerModuleData" :key="key">
      <aside class="aside">{{ mainData["titleName"] }}</aside>
      <el-container>
        <el-aside>
          <div>
            <header @click="togoodsSpecificsPageFun(mainData['specialGoodsInfo']['goodsId'])">
              <main>
                <img :src="mainData['specialGoodsInfo']['imgUrl']" alt="" />
              </main>
              <footer>
                <span class="goods-name">{{
                  mainData["specialGoodsInfo"]["goodsName"]
                }}</span>
                <span class="describe">{{
                  mainData["specialGoodsInfo"]["goodsDescribe"]
                }}</span>
                <span class="price">${{ mainData["specialGoodsInfo"]["goodsPrice"] }}元起</span>
              </footer>
            </header>
          </div>
        </el-aside>
        <el-container>
          <el-header>
            <div>
              <header v-for="(goodsData, key) in mainData['topGoodsInfo']" :key="key"
                @click="togoodsSpecificsPageFun(goodsData['goodsId'])">
                <main>
                  <img :src="goodsData['imgUrl']" alt="" />
                </main>
                <footer>
                  <span class="goods-name">{{ goodsData["goodsName"] }}</span>
                  <span class="describe">{{ goodsData["goodsDescribe"] }}</span>
                  <span class="price">${{ goodsData["goodsPrice"] }}元起</span>
                </footer>
              </header>
            </div>
          </el-header>
          <el-footer>
            <div>
              <header v-for="(goodsData, key) in mainData['bottomGoodsInfo']" :key="key"
                @click="togoodsSpecificsPageFun(goodsData['goodsId'])">
                <main>
                  <img :src="goodsData['imgUrl']" alt="" />
                </main>
                <footer>
                  <span class="goods-name">{{ goodsData["goodsName"] }}</span>
                  <span class="describe">{{ goodsData["goodsDescribe"] }}</span>
                  <span class="price">${{ goodsData["goodsPrice"] }}元起</span>
                </footer>
              </header>
            </div>
          </el-footer>
        </el-container>
      </el-container>
    </article>
  </div>
</template>

<script>
import { mapState, mapActions } from 'pinia';
import { useContainerModule } from '@/stores/mainPageStores/useContainerModuleStore';
export default {
  computed: {
    ...mapState(useContainerModule, ['containerModuleData']),
  },
  mounted() {
    //初始化
    this.initContainerModuleData();
  },
  methods: {
    //初始化
    ...mapActions(useContainerModule, ['initContainerModuleData']),

    //跳转页面
    togoodsSpecificsPageFun(goodsId) {
      this.$router.push({ name: 'goodsSpecifics', params: { goodsId } });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";

article {
  width: 100%;

  .aside {
    font-size: 1.5em;
    padding: 10px;
    border-bottom: 2px $border-color solid;
    margin: 5px;
  }

  .el-container {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .el-header,
  .el-footer {
    height: 50%;
    padding: 10px;
    width: 100%;
    text-align: center;

    div {
      background-color: transparent;
      width: 100%;
      display: flex;
      justify-content: space-around;
      align-items: center;
      padding: 10px;

      header {
        background-color: $title-hover-fgcolor;
        width: 22%;

        main {
          height: 140px;
        }

        footer {
          height: 6em;
        }
      }
    }
  }

  .el-aside {
    padding: 10px;
    width: 20%;
    text-align: center;
  }

  div {
    display: flex;
    justify-content: space-around;
    width: 100%;
    background-color: $title-hover-fgcolor;
    border-radius: 5px;

    header {
      transition: 0.5s;
      width: 100%;
      height: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-around;
      border-radius: 5px;
      cursor: pointer;

      &:hover {
        box-shadow: 2px 2px 10px 2px $shadow-color;
      }

      &:active {
        box-shadow: 2px 2px 10px 2px $shadow-color inset;
      }

      main {
        margin: 10px;
        width: 90%;
        height: 400px;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 10px;

        img {
          width: 100%;
          height: 100%;
        }
      }

      footer {
        height: 8em;
        width: 90%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-around;

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
</style>