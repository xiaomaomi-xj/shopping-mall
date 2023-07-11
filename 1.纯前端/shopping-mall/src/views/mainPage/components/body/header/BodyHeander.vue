<template>
  <div style="padding: 20px; width: 100%">
    <el-row :gutter="20">
      <aside>{{ specialText }}</aside>
      <el-col :span="2" v-for="(typeText, key) in toggleViewData" :key="key" :offset="key === 0 ? 2 : 0">
        <div @mouseover="heightValueOnShow(key)" @mouseout="heightValueOnHide(key)" class="grid-content"
          :class="classs[key]">
          {{ typeText['titleName'] }}
        </div>
      </el-col>
      <el-col :span="10" :offset="offset">
        <div class="grid-content">
          <search-box></search-box>
        </div>
      </el-col>
      <toggle-view-ui :height-value="heightValue" :current-menu-id="currentMenuId" :goods-datas="toggleViewData"
        @rendererDom="rendererDom" @cancelRendererDom="cancelRendererDom"></toggle-view-ui>
    </el-row>
  </div>
</template>

<script>
import SearchBox from "./components/SearchBox.vue";
import ToggleViewUi from "./components/ToggleViewUi.vue";
//引入pinia
import { mapState, mapActions } from 'pinia';
import { useToggleView } from '@/stores/mainPageStores/useToggleViewStore';
import { useSpecialConfig } from "@/stores/specialConfigStores/useSpecialConfigStore";
export default {
  data() {
    return {
      heightValue: 0,
      //可以在这里进行传值,可以让每个悬浮显示不同的值
      titleDatas: [],
      currentMenuId: 0,
      //搜索框偏移数量,这个要根据样式列表数量进行修改
      offset: 3,
      //图标字体,只能为一个中文,两个字母
      specialText: process.env.VUE_APP_MAJOR_TEXT,
      classs: []
    };
  },
  computed: {
    //拿到pinia的初始数据,拿一次就行了传给子级
    ...mapState(useToggleView, ['toggleViewData']),
    ...mapState(useSpecialConfig, ['extraConfig']),
  },
  mounted() {

    //关键字
    this.specialText=this.extraConfig['specialText'];

    //调用一下pinia的方法进行初始化
    this.initToggleViewData();

    //初始化样式
    let typeListLen = this.toggleViewData.length;
    this.offset -= (typeListLen - 4) * 2;
    this.classs = new Array(typeListLen);
  },
  methods: {
    //拿到pinia方法
    ...mapActions(useToggleView, ['initToggleViewData']),

    //子组件调用此方法，给dom上色
    rendererDom(id) {
      this.classs[id] = "current";
    },
    cancelRendererDom(id) {
      this.classs[id] = "";
    },
    //组件的显示和隐藏
    heightValueOnShow(id) {
      this.currentMenuId = id;
      this.heightValue = 200;
    },
    heightValueOnHide(id) {
      this.currentMenuId = id;
      this.heightValue = 0;
    },
  },
  components: {
    SearchBox,
    ToggleViewUi,
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";

div {
  position: relative;
}

.el-row {
  &:last-child {
    margin-bottom: 0;
  }

  aside {
    display: flex;
    justify-content: center;
    align-content: center;
    padding: 10px;
    font-size: 1.5em;
    font-weight: bolder;
    background-color: $special-color;
    color: $icon-text-color;
    border-radius: 30%;
    cursor: default;
  }
}

.grid-content {
  height: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  cursor: pointer;

  &:hover {
    color: $special-color;
  }
}

.current {
  color: $special-color;
}
</style>