import {
    defineStore
} from 'pinia';
import {
    getContainerModule
} from '@/api/pageConfig';
export const useContainerModule = defineStore("containerModule", {
    state: () => {
        return {
            containerModuleData: []
        };
    },
    actions: {
        initContainerModuleData() {
            //初始化赋值
            getContainerModule().then(res => {
                this.containerModuleData = res.data;
            });
        }
    }
});