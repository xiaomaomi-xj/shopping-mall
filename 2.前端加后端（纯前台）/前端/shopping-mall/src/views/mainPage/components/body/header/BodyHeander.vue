<template>
  <div style="padding: 20px; width: 100%">
    <el-row :gutter="20">
      <aside>{{ extraConfig['specialText'] }}</aside>
      <el-col :span="2" v-for="(typeText, key) in toggleViewData" :key="key" :offset="key === 0 ? 2 : 0">
        <div @mouseover="heightValueOnShow(key)" @mouseout="heightValueOnHide(key)" class="grid-content"
          :class="classs[key]">
          {{ typeText['titleName'] }}
        </div>
      </el-col>
      <el-col :span="10" :offset="3-((this.toggleViewData.length - 4) * 2)">
        <div class="grid-content">
          <search-box></search-box>
        </div>
      </el-col>
      <toggle-view-ui :height-value="heightValue" :current-menu-id="currentMenuId" :toggle-view-data="toggleViewData"
        @rendererDom="rendererDom" @cancelRendererDom="cancelRendererDom"></toggle-view-ui>
    </el-row>
  </div>
</template>

<script>
import SearchBox from "./components/SearchBox.vue";
import ToggleViewUi from "./components/ToggleViewUi.vue";
import { mapState, mapActions } from 'pinia';
import { useToggleView } from "@/stores/mainPageStores/useToggleViewStore";
import { useSpecialConfig } from "@/stores/specialConfigStores/useSpecialConfigStore";
export default {
  data() {
    return {
      heightValue: 0,
      currentMenuId: 0,
      //图标字体,只能为一个中文,两个字母
      specialText: process.env.VUE_APP_MAJOR_TEXT,
      classs: [],
    };
  },
  computed: {
    ...mapState(useToggleView, ['toggleViewData']),
    ...mapState(useSpecialConfig, ['extraConfig']),
  },
  mounted() {
    //初始化数据
    this.initToggleViewData();
  },
  methods: {

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