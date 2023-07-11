import {
    defineStore
} from 'pinia';
import {
    containerModuleData
} from '@/handleCustomConfig/pageConfig';
export const useContainerModule = defineStore("containerModule", {
    state: () => {
        return {
            containerModuleData
        };
    },
    actions: {
        intContainerModuleData() {
            
        }
    }
});