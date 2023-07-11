import { defineStore } from 'pinia';
import { extraConfigJson } from '@/handleCustomConfig/extraConfig';
export const useSpecialConfig=defineStore("specialConfig",{
    state:()=>{
        return {
            extraConfig:extraConfigJson
        };
    },
    actions:{
        initExtraConfig(){
            
        }
    }
});