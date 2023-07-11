<template>
  <div>
    <article :style="borderHoverColor">
      <aside :style="borderHoverColor">
        <input
          type="search"
          @focusout="changeBorderColorOnDefault"
          @focus="changeBorderColorOnSelect"
          v-model="searchMessage"
        />
      </aside>
      <aside :style="borderHoverColor" @click="search">
        <button :style="searchIconColor"><SearchIcon style="cursor: pointer;"/></button>
      </aside>
    </article>
  </div>
</template>
  <script>
export default {
  data() {
    return {
      borderHoverColor: "",
      searchIconColor: "",
      //要搜索的内容
      searchMessage:''
    };
  },
  methods: {
    //时间样式
    changeBorderColorOnSelect() {
      this.borderHoverColor = "border-color: rgb(247, 82, 27)";
      this.searchIconColor = "color: rgb(247, 82, 27)";
    },
    changeBorderColorOnDefault() {
      this.borderHoverColor = "";
      this.searchIconColor = "";
      this.searchMessage=this.searchMessage.trim();
    },
    //搜索跳转到搜索页面
    search() {
      if(this.searchMessage.length<=0){
        this.$selfMessage.openMessage({
          type:'warning',
          message:'搜索内容不能为空！'
        });
      }else{
        this.$router.push({name:'search',query: { content: this.searchMessage }});
        this.searchMessage='';
      }
    },
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";
article {
  aside {
    input {
      padding: 0.3em;
      outline: none;
      width: 20em;
      border: none;
      background-color: transparent;
      font-size: 1.2em;
    }
    &:last-of-type {
      padding: 10px;
      border-right: none;
      width: 3em;
      text-align: center;
      border-left: 1px $border-color solid;
      cursor: pointer;
      button {
        width: 20px;
        outline: none;
        border: none;
        background-color: transparent;
        font-weight: bolder;
        cursor: pointer;
      }
      &:hover {
        border-color: $special-color;
        background-color: $special-color;
        button {
          color: $icon-text-color !important;
        }
      }
    }
  }
  border-radius: 5px;
  padding-left: 0.3em;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px $border-color solid;
}
</style>