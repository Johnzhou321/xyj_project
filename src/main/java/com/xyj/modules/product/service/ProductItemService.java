package com.xyj.modules.product.service;


import com.xyj.modules.product.model.ProductItem;
import com.xyj.modules.product.vo.ProductItemVo;

import java.util.List;

public interface ProductItemService {

    List<ProductItemVo> getAll(ProductItem productItem, String keyword);

    void deleteById(Integer id);

    ProductItem getById(Integer id);

    void saveOrUpdate(ProductItemVo productItemVo);

    void batchDelete(Integer[] ids);

    ProductItem getItemById(Integer id);
}