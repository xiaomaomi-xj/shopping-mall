import {
    defineStore
} from 'pinia';
import { useAllGoods } from '../UtilsStores/useAllGoodsStore';
export const useSearchOnResult = defineStore('searchOnResult', {
    state: () => {
        return {
            searchResultDatas:[]
        };
    },
    actions: {
        //接收参数，返回结果
        modifySearchResultDatas(content){
            this.searchResultDatas=useAllGoods().getGoodsDatasOnSearch(content);
        }
    }
});