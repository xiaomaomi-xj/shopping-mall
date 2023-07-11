package com.shoppingMall.entity.po.self;

/**
 * 头像文件
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
public class HeadImgFile {
    /**
     * 允许的头像图片大小
     */
    private Long imgSize;

    public HeadImgFile() {
    }

    public HeadImgFile(Long imgSize) {
        this.imgSize = imgSize;
    }

    public Long getImgSize() {
        return imgSize;
    }

    public void setImgSize(Long imgSize) {
        this.imgSize = imgSize;
    }

    @Override
    public String toString() {
        return "HeadImgFile{" +
                "imgSize=" + imgSize +
                '}';
    }
}
