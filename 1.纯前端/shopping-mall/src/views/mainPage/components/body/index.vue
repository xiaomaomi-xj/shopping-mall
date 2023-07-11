<template>
  <div class="root">
    <BodyHeander />
    <RotationChart />
    <SpecialGoodsAd :goodsAdData="goodsDatas[0]" />
    <ContainerModule />
    <SpecialGoodsAd :goodsAdData="goodsDatas[1]" />
  </div>
</template>

<script>
import BodyHeander from "./header/BodyHeander.vue";
import RotationChart from "./header/RotationChart.vue";
import ContainerModule from "./main/ContainerModule.vue";
import SpecialGoodsAd from "./main/SpecialGoodsAd.vue";
import { mapState, mapActions } from 'pinia';
import { useGoodsAd } from "@/stores/mainPageStores/useGoodsAdStore";
export default {
  //index文件要写这个名字
  name: "mainPageBody",
  components: {
    BodyHeander,
    RotationChart,
    ContainerModule,
    SpecialGoodsAd
  },
  computed: {
    ...mapState(useGoodsAd,['goodsDatas']),
  },
  mounted(){
    this.initGoodsDatas();
  },
  methods: {
    ...mapActions(useGoodsAd,['initGoodsDatas']),
  },
};
</script>

<style lang="scss" scoped>
//这里的div样式竟然影响到了子级,只能加上root来控制
.root {
  width: 80%;
}
</style>