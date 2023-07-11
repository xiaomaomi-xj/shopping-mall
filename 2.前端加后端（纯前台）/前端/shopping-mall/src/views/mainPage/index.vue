<template>
  <div class="main-root">
    <el-container>
      <el-header>
        <mainPageHeader />
      </el-header>
      <el-main>
        <mainPageBody />
      </el-main>
      <el-footer>
        <mainPageFooter />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import mainPageHeader from "./components/header";
import mainPageBody from "./components/body";
import mainPageFooter from './components/footer';
export default {
  name: "mainPage",
  components: {
    mainPageHeader,
    mainPageBody,
    mainPageFooter
  },
  mounted(){

  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";
.el-header,
.el-footer {
  background-color: $title-bgcolor;
  height: auto;
  padding: 10px;
}
.el-main {
  background-color: $main-bgcolor;
  display: flex;
  justify-content: center;
  align-content: center;
}

body > .el-container {
  margin-bottom: 40px;
}
</style>