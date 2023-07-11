import {
    defineStore
} from 'pinia';
import {
    getRotationChart
} from '@/api/pageConfig';
export const useRotationChart = defineStore('rotationChart', {
    state: () => {
        return {
            goodsDatas: []
        };
    },
    actions: {
        initGoodsDatas() {
            //初始化
            getRotationChart().then(res => {
                this.goodsDatas = res.data['goodsDatas'];
            });

        }
    }
});