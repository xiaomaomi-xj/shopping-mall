package com.shoppingMall.controller;

import com.shoppingMall.admin.annotation.CheckAdmin;
import com.shoppingMall.entity.bo.GoodsBo;
import com.shoppingMall.entity.bo.SingleAllBo;
import com.shoppingMall.entity.vo.GoodsVo;
import com.shoppingMall.entity.vo.IdVo;
import com.shoppingMall.exception.BizException;
import com.shoppingMall.service.GoodsService;
import com.shoppingMall.utils.Assert;
import com.shoppingMall.utils.UploadFileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * 商品操作接口
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    /**
     * 最少要两个文件（一个封面一个详情）
     */
    private static final int MIN_FILE_NUM = 2;
    /**
     * 最多要5个文件(一个封面，4个详情)
     */
    private static final int MAX_FILE_NUM = 5;
    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * 获取所有商品，需要管理员权限
     *
     * @return
     */
    @PostMapping("/get_all_goods")
    @CheckAdmin
    public List<GoodsBo> getAllGoods() {
        return goodsService.getAllGoods();
    }

    /**
     * 添加商品
     *
     * @param request
     * @param goodsBo
     * @return
     */
    @PostMapping("/add_goods")
    @CheckAdmin
    public SingleAllBo<Boolean> addGoods(MultipartHttpServletRequest request, GoodsBo goodsBo) {
        goodsBo.check();
        String goodsName = goodsBo.getGoodsName();
        String goodsDescribe = goodsBo.getGoodsDescribe();
        Float goodsPrice = goodsBo.getGoodsPrice();
        Integer maxBuyNum = goodsBo.getMaxBuyNum();
        List<MultipartFile> files = request.getFiles(UploadFileUtil.FILE_KEY);
        check(files);
        UploadFileUtil.check(files);
        return goodsService.addGoods(goodsName, goodsDescribe, goodsPrice, maxBuyNum, files);
    }

    /**
     * 修改商品
     *
     * @param request
     * @param goodsBo
     * @return
     */
    @PostMapping("/modify_goods")
    @CheckAdmin
    public SingleAllBo<Boolean> modifyGoods(MultipartHttpServletRequest request, GoodsBo goodsBo) {
        String goodsId = goodsBo.getGoodsId();
        Assert.isNotBlank(goodsId, "商品id");
        goodsBo.check();
        String goodsName = goodsBo.getGoodsName();
        String goodsDescribe = goodsBo.getGoodsDescribe();
        Float goodsPrice = goodsBo.getGoodsPrice();
        Integer maxBuyNum = goodsBo.getMaxBuyNum();
        List<MultipartFile> files = request.getFiles(UploadFileUtil.FILE_KEY);
        check(files);
        UploadFileUtil.check(files);
        return goodsService.modifyGoods(goodsId, goodsName, goodsDescribe, goodsPrice, maxBuyNum, files);
    }

    /**
     * 检验图片数量
     *
     * @param files
     */
    private void check(List<MultipartFile> files) {
        if (files.size() < MIN_FILE_NUM) {
            throw new BizException("文件最少要有2个！");
        }
        if (files.size() > MAX_FILE_NUM) {
            throw new BizException("文件最多为5个！");
        }
    }

    /**
     * 删除商品
     *
     * @param idVo
     * @return
     */
    @PostMapping("/del_goods")
    @CheckAdmin
    public SingleAllBo<Boolean> delGoods(@RequestBody IdVo idVo) {
        String id = idVo.getId();
        Assert.isNotBlank(id, "商品id");
        return goodsService.delGoods(id);
    }

    /**
     * 根据搜索条件获取商品
     *
     * @param goodsVo
     * @return
     */
    @PostMapping("/get_search_goods")
    public List<GoodsBo> getSearchGoods(@RequestBody GoodsVo goodsVo) {
        String goodsName = goodsVo.getGoodsName();
        Assert.isNotBlank(goodsName, "搜索内容");
        return goodsService.getSearchGoods(goodsName);
    }

    /**
     * 根据id查询商品
     *
     * @param goodsVo
     * @return
     */
    @PostMapping("/get_goods_by_id")
    public GoodsBo getGoodsById(@RequestBody GoodsVo goodsVo) {
        String goodsIdStr = goodsVo.getGoodsId();
        Assert.isNotNull(goodsIdStr, "商品id");
        long goodsId;
        try {
            goodsId = Long.parseLong(goodsIdStr);
        } catch (NumberFormatException e) {
            throw new BizException("未找到此商品！");
        }
        return goodsService.getGoodsById(goodsId);
    }
}
