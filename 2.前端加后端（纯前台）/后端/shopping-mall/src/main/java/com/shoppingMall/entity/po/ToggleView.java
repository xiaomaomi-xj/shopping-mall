package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 标题界面实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@Entity(name = "toggle_view")
public class ToggleView implements Serializable {
    private static final long serialVersionUID = 4710415092861562626L;

    /**
     * 标题界面id
     */
    @Id
    @Column(name = "toggle_view_id")
    private Long toggleViewId;

    /**
     * 标题名字
     */
    @Column(name = "title_name")
    private String titleName;

    public ToggleView() {

    }

    public ToggleView(Long toggleViewId, String titleName) {
        this.toggleViewId = toggleViewId;
        this.titleName = titleName;
    }

    public Long getToggleViewId() {
        return toggleViewId;
    }

    public void setToggleViewId(Long toggleViewId) {
        this.toggleViewId = toggleViewId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToggleView that = (ToggleView) o;
        return Objects.equals(toggleViewId, that.toggleViewId) &&
                Objects.equals(titleName, that.titleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toggleViewId, titleName);
    }

    @Override
    public String toString() {
        return "ToggleView{" +
                "toggleViewId=" + toggleViewId +
                ", titleName='" + titleName + '\'' +
                '}';
    }
}
