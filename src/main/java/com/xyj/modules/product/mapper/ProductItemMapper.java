package com.xyj.modules.product.mapper;

import com.xyj.core.mapper.MyMapper;
import com.xyj.modules.product.model.ProductItem;
import com.xyj.modules.product.vo.ProductItemVo;

import java.util.List;
import java.util.Map;

public interface ProductItemMapper extends MyMapper<ProductItem> {
    public List<ProductItemVo> getAllByPage(Map<String, Object> params);
}