package com.shoppingMall.entity.po;

import com.shoppingMall.constant.GlobalConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 图片实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@Entity(name = "img")
public class Img implements Serializable {
    private static final long serialVersionUID = -7300662025859823283L;

    /**
     * 图片id
     */
    @Id
    @Column(name = "img_id")
    private Long imgId;

    /**
     * 图片名称
     */
    @Column(name = "img_name")
    private String imgName;

    /**
     * 商品用于封面的商品id
     */
    @Column(name = "cover_goods")
    private Long coverGoods;

    /**
     * 商品详情用到的图片，商品id
     */
    @Column(name = "rotation_goods")
    private Long rotationGoods;

    public Img() {

    }

    public Img(Long imgId, String imgName, Long coverGoods, Long rotationGoods) {
        this.imgId = imgId;
        this.imgName = imgName;
        this.coverGoods = coverGoods;
        this.rotationGoods = rotationGoods;
    }

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    /**
     * 获取的时候加上前缀
     *
     * @return
     */
    public String getImgName() {
        return GlobalConstant.IMG_BASE_URL + imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Long getCoverGoods() {
        return coverGoods;
    }

    public void setCoverGoods(Long coverGoods) {
        this.coverGoods = coverGoods;
    }

    public Long getRotationGoods() {
        return rotationGoods;
    }

    public void setRotationGoods(Long rotationGoods) {
        this.rotationGoods = rotationGoods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Img img = (Img) o;
        return Objects.equals(imgId, img.imgId) &&
                Objects.equals(imgName, img.imgName) &&
                Objects.equals(coverGoods, img.coverGoods) &&
                Objects.equals(rotationGoods, img.rotationGoods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgId, imgName, coverGoods, rotationGoods);
    }

    @Override
    public String toString() {
        return "Img{" +
                "imgId=" + imgId +
                ", imgName='" + imgName + '\'' +
                ", coverGoods=" + coverGoods +
                ", rotationGoods=" + rotationGoods +
                '}';
    }
}
