// .eslintrc.js
module.exports = {
    root: true,
    env: {
        browser: true,
        node: true,
        'vue/setup-compiler-macros': true
    },
    extends: [
        "plugin:vue/vue3-essential",
        "eslint:recommended"
    ],
    parserOptions: {
        parser: "@babel/eslint-parser"
    },
    rules: {
        
    }
}