package com.xyj.modules.product.service;


import com.xyj.modules.product.model.ProductType;
import com.xyj.modules.sys.model.SysRole;

import java.util.List;

public interface ProductTypeService {

    List<ProductType> getAll(ProductType productType, String keyword);

    void deleteById(Integer id);

    void saveOrUpdate(ProductType productType);

    ProductType getById(Integer id);

    void batchDelete(Integer[] ids);

    List<ProductType> getAllList();
}