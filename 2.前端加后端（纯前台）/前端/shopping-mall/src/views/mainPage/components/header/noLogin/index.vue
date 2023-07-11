<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4" :offset="2">
        <div class="grid-content">
          <span class="title" v-html="computeStoreName"></span>
        </div>
      </el-col>
      <el-col :span="2" :offset="12">
        <div class="grid-content">
          <span class="link" @click="toLoginPageFun"><el-icon :size="16">
              <UserFilledIcon />
            </el-icon>登录</span>
        </div>
      </el-col>
      <el-col :span="2">
        <div class="grid-content">
          <span class="link" @click="toRegisterPageFun"><el-icon :size="16">
              <UserIcon />
            </el-icon>注册</span>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "NoLoginUi",
  props: {
    storeInfo: {
      type: Object,
      required: true
    }
  },
  computed: {
    //计算商标的值,如果这样写函数，必须有返回值
    computeStoreName() {
      //因为这里面含有标签，需要使用v-html
      return this.storeInfo['storeNameHaveSpecial'] == "1" ? this.storeInfo['storeName'].replaceAll('^', `<span>"${this.storeInfo['specialText']}"</span>`) : this.storeInfo['storeName'];
    },
  },
  methods: {
    //跳转页面
    toLoginPageFun() {
      this.$router.push({ name: 'userLogin' });
    },
    toRegisterPageFun() {
      this.$router.push({ name: 'userRegister' });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";

.el-row {
  margin-bottom: 20px;
  padding-top: 10px;
  padding-bottom: 10px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  line-height: 100%;
  border-radius: 4px;
  text-align: center;
  color: $title-fgcolor;
}

.title {
  font-size: 1.6em;
  font-weight: bold;
  color: $title-hover-fgcolor;
  cursor: default;

  //这里是解决v-html,没有样式的问题
  ::v-deep(span) {
    color: $special-color;
  }
}

.link {
  width: 100%;
  height: 100%;

  i {
    //调整图标和字体不整齐的问题
    vertical-align: top;
  }

  &:hover {
    cursor: pointer;
    color: $title-hover-fgcolor;
  }
}
</style>