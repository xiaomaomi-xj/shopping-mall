package com.shoppingMall.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * 主要界面实体类
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/2/28
 */
@Entity(name = "container_module")
public class ContainerModule implements Serializable {
    private static final long serialVersionUID = -3568999446211616038L;

    /**
     * 主要界面id
     */
    @Id
    @Column(name = "container_module_id")
    private Long containerModuleId;

    /**
     * 标题名字
     */
    @Column(name = "title_name")
    private String titleName;

    public ContainerModule(){

    }

    public ContainerModule(Long containerModuleId, String titleName) {
        this.containerModuleId = containerModuleId;
        this.titleName = titleName;
    }

    public Long getContainerModuleId() {
        return containerModuleId;
    }

    public void setContainerModuleId(Long containerModuleId) {
        this.containerModuleId = containerModuleId;
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
        ContainerModule that = (ContainerModule) o;
        return Objects.equals(containerModuleId, that.containerModuleId) &&
                Objects.equals(titleName, that.titleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(containerModuleId, titleName);
    }

    @Override
    public String toString() {
        return "ContainerModule{" +
                "containerModuleId=" + containerModuleId +
                ", titleName='" + titleName + '\'' +
                '}';
    }
}
