package com.xyj.modules.product.service.impl;

import com.xyj.modules.product.mapper.ProductImageRelationMapper;
import com.xyj.modules.product.model.ProductImageRelation;
import com.xyj.modules.product.service.ProductImageRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 产品图片文件关联服务类
 */
@Service
public class ProductImageRelationServiceImpl implements ProductImageRelationService {

    @Autowired
    private ProductImageRelationMapper productImageRelationMapper;
    @Override
    public List<ProductImageRelation> getRelationsByProductId(Integer productId) {
        Example example=new Example(ProductImageRelation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        return productImageRelationMapper.selectByExample(example);
    }
}
