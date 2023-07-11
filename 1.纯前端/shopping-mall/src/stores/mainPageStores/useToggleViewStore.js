import {
    defineStore
} from 'pinia';
import {
    toggleViewData
} from '@/handleCustomConfig/pageConfig';
export const useToggleView = defineStore("toggleView", {
    state: () => ({
        toggleViewData
    }),
    actions: {
        initToggleViewData() {
            
        },
    }
});