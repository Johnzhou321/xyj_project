package com.xyj.modules.product.service;

import com.xyj.modules.product.model.ProductImageRelation;

import java.util.List;

public interface ProductImageRelationService {
    List<ProductImageRelation> getRelationsByProductId(Integer productId);
}
