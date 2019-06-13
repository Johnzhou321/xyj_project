package com.xyj.modules.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.xyj.modules.product.mapper.ProductTypeMapper;
import com.xyj.modules.product.model.ProductType;
import com.xyj.modules.product.service.ProductTypeService;
import com.xyj.modules.sys.model.SysRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service("productTypeService")
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll(ProductType productType, String keyword) {
        PageHelper.startPage(productType.getPage(),productType.getRows());
        Example example=new Example(ProductType.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(keyword)){
            keyword="%"+keyword+"%";
            criteria.andLike("name",keyword);
        }
        example.orderBy("createTime");
        return productTypeMapper.selectByExample(example);
    }

    @Override
    public void deleteById(Integer id) {
        productTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(ProductType productType) {
        if(productType.getId()!=null){//update
            productType.setEditTime(new Date());
            productTypeMapper.updateByPrimaryKeySelective(productType);
        }else{//insert
            productType.setCreateTime(new Date());
            productTypeMapper.insert(productType);
        }
    }

    @Override
    public ProductType getById(Integer id) {
        return productTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void batchDelete(Integer[] ids) {
        if(ids.length>0){
            for (Integer id:ids){
                ProductType productType=new ProductType();
                productType.setId(id);
                productTypeMapper.delete(productType);
            }
        }
    }

    @Override
    public List<ProductType> getAllList() {
        return productTypeMapper.selectAll();
    }
}
