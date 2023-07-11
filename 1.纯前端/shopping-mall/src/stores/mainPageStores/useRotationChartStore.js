import {
    defineStore
} from 'pinia';
import {
    rotationChartData
} from '@/handleCustomConfig/pageConfig';
export const useRotationChart = defineStore('rotationChart', {
    state: () => {
        return {
            goodsDatas:rotationChartData['goodsDatas']
        };
    },
    actions: {
        initGoodsDatas() {
            
        }
    }
});