const {
  defineConfig
} = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    //修改端口号
    port: 80,
  },
  publicPath: process.env.PUBLICK_PATH,
  outputDir: process.env.OUT_PUT_DIR,
  //下面这两个都是给予outputDir路径
  //资源存放的位置（比如那些scss文件）
  assetsDir: '',
  //生成的根文件名
  indexPath: 'index.html',
  //默认是生成map文件的，是为了更好的定位错误，我们并不需要，就算出错了大部分也可以通过源码进行调节
  productionSourceMap: false,

})