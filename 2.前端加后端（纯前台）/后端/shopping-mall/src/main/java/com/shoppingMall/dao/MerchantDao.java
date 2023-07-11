package com.shoppingMall.dao;

import com.shoppingMall.entity.po.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/23
 */
public interface MerchantDao extends JpaRepository<Merchant,Long>, JpaSpecificationExecutor<Merchant> {
}
