package com.shoppingMall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.shoppingMall.constant.GlobalConstant;
import com.shoppingMall.dao.GoodsDao;
import com.shoppingMall.dao.ImgDao;
import com.shoppingMall.dao.OrderDao;
import com.shoppingMall.dao.ShopCartDao;
import com.shoppingMall.entity.bo.GoodsBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.po.Goods;
import com.shoppingMall.entity.po.Img;
import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.GoodsService;
import com.shoppingMall.utils.ConvertUtil;
import com.shoppingMall.utils.UploadFileUtil;
import com.zhuyahui.annotation.ZyhDataSourceRead;
import com.zhuyahui.annotation.ZyhService;
import com.zhuyahui.util.MyAloneHandlerReadWrite;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作商品
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@ZyhService
@ZyhDataSourceRead
public class GoodsServiceImpl implements GoodsService {
    private final ShopCartDao shopCartDao;
    private final OrderDao orderDao;
    private final GoodsDao goodsDao;
    private final ImgDao imgDao;

    public GoodsServiceImpl(ShopCartDao shopCartDao, OrderDao orderDao, GoodsDao goodsDao, ImgDao imgDao) {
        this.shopCartDao = shopCartDao;
        this.orderDao = orderDao;
        this.goodsDao = goodsDao;
        this.imgDao = imgDao;
    }

    /**
     * 根据条件查询商品
     *
     * @return
     */
    @Override
    public List<GoodsBo> getSearchGoods(String goodsName) {
        List<Goods> allByGoodsName = goodsDao.findAllByGoodsNameLike(GlobalConstant.HOLDER + goodsName.trim() + GlobalConstant.HOLDER);
        return ConvertUtil.batchGoodsToGoodsBo(allByGoodsName, imgDao);
    }

    /**
     * 根据id查询商品
     *
     * @param goodsId
     * @return
     */
    @Override
    public GoodsBo getGoodsById(Long goodsId) {
        Goods goods = goodsDao.findById(goodsId).orElse(null);
        if (ObjectUtil.isNull(goods)) {
            return null;
        }
        return ConvertUtil.goodsToGoodsBo(goods, imgDao);
    }

    /**
     * 获取全部商品数据
     *
     * @return
     */
    @Override
    public List<GoodsBo> getAllGoods() {
        List<Goods> all = goodsDao.findAll();
        return ConvertUtil.batchGoodsToGoodsBo(all, imgDao);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public SingleAllBo<Boolean> delGoods(String id) {
        long goodsId = Long.parseLong(id);
        Goods goods = goodsDao.findById(goodsId).orElseThrow(() -> {
            throw new BizException("商品不存在！");
        });
        Img coverImg = imgDao.findByCoverGoods(goodsId).orElseThrow(() -> {
            throw new AppException("商品封面图片不存在");
        });
        List<Img> rotationImageS = imgDao.findAllByRotationGoods(goodsId);
        if (rotationImageS.isEmpty()) {
            throw new AppException("商品详情图片不存在！");
        }
        boolean isExits = false;
        for (Img img : rotationImageS) {
            if (img.getImgId().equals(coverImg.getImgId())) {
                isExits = true;
                break;
            }
        }
        if (!isExits) {
            rotationImageS.add(coverImg);
        }
        MyAloneHandlerReadWrite.write(() -> {
            //删除购物车的商品数据（因为购物车不会存在重复商品，这样删就行）
            shopCartDao.deleteAllByGoodsId(goodsId);
            //删除订单的商品数据
            orderDao.deleteAllByGoodsId(goodsId);
            //删除图片数据
            imgDao.deleteAllInBatch(rotationImageS);
            //删除商品数据
            goodsDao.delete(goods);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 修改
     *
     * @param goodsId
     * @param goodsName
     * @param goodsDescribe
     * @param goodsPrice
     * @param maxBuyNum
     * @param files
     * @return
     */
    @Override
    public SingleAllBo<Boolean> modifyGoods(String goodsId, String goodsName, String goodsDescribe, Float goodsPrice, Integer maxBuyNum, List<MultipartFile> files) {
        long id = Long.parseLong(goodsId);
        Goods goods = goodsDao.findById(id).orElseThrow(() -> {
            throw new BizException("商品不存在！");
        });
        Img coverImg = imgDao.findByCoverGoods(id).orElseThrow(() -> {
            throw new AppException("商品封面图片不存在");
        });
        List<Img> rotationImageS = imgDao.findAllByRotationGoods(id);
        if (rotationImageS.isEmpty()) {
            throw new AppException("商品详情图片不存在！");
        }
        int fileNum = files.size() - 1;
        if (fileNum < rotationImageS.size()) {
            throw new AppException("上传的图片数量不得小于原先数据使用的数量！");
        }
        goods.setGoodsName(goodsName);
        goods.setGoodsDescribe(goodsDescribe);
        goods.setGoodsPrice(goodsPrice);
        goods.setMaxBuyNum(maxBuyNum);
        coverImg.setImgName(files.get(0).getOriginalFilename());
        int index = 1;
        for (Img img : rotationImageS) {
            img.setImgName(files.get(index).getOriginalFilename());
            index++;
        }
        List<Img> imgList = new ArrayList<>();
        for (int i = 0; i < (fileNum - rotationImageS.size()); i++) {
            //多的要新增
            imgList.add(new Img(
                    IdUtil.getSnowflakeNextId(),
                    files.get(index).getOriginalFilename(),
                    null,
                    id
            ));
            index++;
        }
        UploadFileUtil.uploadFile(files);
        MyAloneHandlerReadWrite.write(() -> {
            imgDao.save(coverImg);
            imgDao.saveAll(rotationImageS);
            imgDao.saveAll(imgList);
            goodsDao.save(goods);
            return null;
        });
        return new SingleAllBo<>(true);
    }

    /**
     * 添加
     *
     * @param goodsName
     * @param goodsDescribe
     * @param goodsPrice
     * @param maxBuyNum
     * @param files
     * @return
     */
    @Override
    public SingleAllBo<Boolean> addGoods(String goodsName, String goodsDescribe, Float goodsPrice, Integer maxBuyNum, List<MultipartFile> files) {
        long goodsId = IdUtil.getSnowflakeNextId();
        Goods goods = new Goods(
                goodsId,
                goodsName,
                goodsDescribe,
                goodsPrice,
                maxBuyNum,
                null,
                null,
                null,
                null,
                null,
                null
        );
        List<Img> imgList = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            if (i == 0) {
                imgList.add(new Img(
                        IdUtil.getSnowflakeNextId(),
                        files.get(i).getOriginalFilename(),
                        goodsId,
                        null
                ));
            } else {
                imgList.add(new Img(
                        IdUtil.getSnowflakeNextId(),
                        files.get(i).getOriginalFilename(),
                        null,
                        goodsId
                ));
            }
        }
        UploadFileUtil.uploadFile(files);
        MyAloneHandlerReadWrite.write(() -> {
            imgDao.saveAll(imgList);
            goodsDao.save(goods);
            return null;
        });
        return new SingleAllBo<>(true);
    }
}
