import {
    defineStore
} from 'pinia';
import {
    getToggleView
} from '@/api/pageConfig';
export const useToggleView = defineStore("toggleView", {
    state: () => ({
        toggleViewData: [{}]
    }),
    actions: {
        initToggleViewData() {
            //初始化
            getToggleView().then(res => {
                this.toggleViewData = res.data;
            });
        },
    }
});