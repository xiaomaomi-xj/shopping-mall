import {
    defineStore
} from 'pinia';
import {
    getSearchGoods
} from '@/api/goods';
export const useSearchOnResult = defineStore('searchOnResult', {
    state: () => {
        return {
            searchResultDatas: []
        };
    },
    actions: {
        //接收参数，返回结果
        modifySearchResultDatas(content) {
            getSearchGoods({
                goodsName: content
            }).then(res => {
                this.searchResultDatas = res.data;
            });
        }
    }
});