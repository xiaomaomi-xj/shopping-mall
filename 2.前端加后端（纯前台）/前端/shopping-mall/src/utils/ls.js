//使用本地存储token
//来个前缀名
const prefix='shopping_mall_';
const ls={
    get(key){
        return localStorage.getItem(prefix + key);
    },
    set(key,value){
        localStorage.setItem(prefix + key,value);
    },
    del(key){
        localStorage.removeItem(prefix + key);
    }
}
export { ls }; 