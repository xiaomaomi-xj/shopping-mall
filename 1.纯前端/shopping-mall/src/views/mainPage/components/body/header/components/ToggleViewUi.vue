<template>
  <div :style="`height: ${heightValue}px;`" @mouseover="callParentOnRenderer" @mouseout="cancelCallParentOnRenderer">
    <article>
      <aside v-for="(titleData, k) in goodsDatas[currentMenuId]['goodsDatas']" :key="k"
        @click="togoodsSpecificsPageFun(titleData['goodsId'])">
        <header>
          <main>
            <img :src="titleData['imgUrl']" alt="" />
          </main>
          <footer>
            <span class="intro">{{ titleData["goodsName"] }}</span>
            <span class="price">${{ titleData["goodsPrice"] }}元起</span>
          </footer>
        </header>
      </aside>
    </article>
  </div>
</template>

<script>
export default {
  props: {
    heightValue: {
      type: Number,
      required: true,
      default: 0,
    },
    currentMenuId: {
      type: Number,
      required: true,
      default: 0,
    },
    goodsDatas: {
      type: Array,
      required: true,
      default: null
    }
  },
  methods: {
    //调用父级渲染方法
    callParentOnRenderer() {
      this.$emit("rendererDom", this.currentMenuId);
    },
    cancelCallParentOnRenderer() {
      this.$emit("cancelRendererDom", this.currentMenuId);
    },
    //跳转页面
    togoodsSpecificsPageFun(goodsId) {
      //这里需要接受商品id，先拿key来用
      this.$router.push({ name: 'goodsSpecifics', params: { goodsId } });
    }
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";

div {
  transition: 0.8s;
  overflow: hidden;
  position: absolute;
  width: 100%;
  height: 200px;
  top: 10px;
  left: -1%;
  border-radius: 8px;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;
  //inset:阴影反转，外边的阴影转到里面
  box-shadow: 0px 0px 10px 1px $shadow-color inset;

  &:hover {
    height: 200px !important;
  }

  article {
    width: 80%;
    height: 160px;
    display: flex;
    justify-content: space-around;
    align-items: center;

    aside {
      width: 170px;
      height: 150px;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      padding: 10px 20px;
      border-right: 2px $border-color solid;

      &:last-of-type {
        border-right: 0;
      }

      header {
        border-radius: 3px;
        cursor: pointer;
        padding: 10px;
        width: 100%;
        transition: 0.5s;

        &:hover {
          box-shadow: 2px 2px 10px 1px $shadow-color;
        }

        &:active {
          //鼠标按下让阴影去里面
          box-shadow: 2px 2px 10px 1px $shadow-color inset;
        }

        main {
          box-sizing: border-box;
          padding: 5px;
          width: 100%;
          height: 100px;

          img {
            width: 100%;
            height: 100%;
          }
        }

        footer {
          display: flex;
          justify-content: center;
          align-items: center;
          flex-direction: column;

          span {
            margin: 3px;
          }

          .price {
            text-align: center;
            width: 90%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            font-size: 0.8em;
            color: $special-color;
          }

          .intro {
            text-align: center;
            width: 90%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            font-size: 1em;
          }
        }
      }
    }
  }
}
</style>