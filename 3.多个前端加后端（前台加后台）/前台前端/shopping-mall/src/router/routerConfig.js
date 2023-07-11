import {
    mainPageRouter,
    userPageRouter,
    searchPageRouter,
    goodsSpecificsPageRouter,
    confirmNotFundPageRouter,
    vagueNotFundPageRouter
} from './publicRouter';
import {
    shopCartPageRouter,
    orderPageRouter,
    chatBoosPageRouter
} from './privateRouter';
export default [
    mainPageRouter,
    ...userPageRouter,
    searchPageRouter,
    goodsSpecificsPageRouter,
    confirmNotFundPageRouter,
    vagueNotFundPageRouter,
    shopCartPageRouter,
    orderPageRouter,
    chatBoosPageRouter
]