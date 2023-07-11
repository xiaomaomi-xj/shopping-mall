const {
  defineConfig
} = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 80,
  },
  publicPath: process.env.PUBLICK_PATH,
  outputDir: process.env.OUT_PUT_DIR,
  assetsDir: '',
  indexPath: 'index.html',
  productionSourceMap: false,
})