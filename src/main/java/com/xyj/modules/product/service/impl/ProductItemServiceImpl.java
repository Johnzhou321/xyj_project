package com.xyj.modules.product.service.impl;

import com.mongodb.gridfs.GridFSInputFile;
import com.xyj.base.GridfsService;
import com.xyj.modules.product.mapper.ProductImageRelationMapper;
import com.xyj.modules.product.mapper.ProductItemMapper;
import com.xyj.modules.product.model.ProductImageRelation;
import com.xyj.modules.product.model.ProductItem;
import com.xyj.modules.product.service.ProductImageRelationService;
import com.xyj.modules.product.service.ProductItemService;
import com.xyj.modules.product.vo.ProductItemVo;
import com.xyj.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("productItemService")
public class ProductItemServiceImpl implements ProductItemService {

    @Autowired
    private ProductItemMapper productItemMapper;
    @Autowired
    private GridfsService gridfsService;
    @Autowired
    private ProductImageRelationMapper productImageRelationMapper;
    @Autowired
    private ProductImageRelationService productImageRelationService;

    @Override
    public List<ProductItemVo> getAll(ProductItem productItem, String keyword) {
//        PageHelper.startPage(productItem.getPage(),productItem.getRows());
//        Example example=new Example(ProductItem.class);
//        Example.Criteria criteria = example.createCriteria();
//        if(StringUtils.isNotBlank(keyword)){
//            keyword="%"+keyword+"%";
//            criteria.andLike("name",keyword);
//        }
//        example.orderBy("createTime");
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("page", (productItem.getPage()-1) * productItem.getRows());
        params.put("rows", productItem.getRows());
        return productItemMapper.getAllByPage(params);
    }

    @Override
    public void deleteById(Integer id) {
        productItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProductItem getById(Integer id) {
        return productItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(ProductItemVo productItemVo){
        ProductItem productItem = new ProductItem();
        BeanUtils.copyFields(productItemVo, productItem, null);
        if(productItem.getId()!=null){//update
            productItem.setEditTime(new Date());
            productItemMapper.updateByPrimaryKeySelective(productItem);
        }else{//insert
            productItem.setCreateTime(new Date());
            productItemMapper.insert(productItem);
            System.out.println("id = " + productItem.getId());
            MultipartFile[] files = productItemVo.getFiles();
            if (files != null && files.length > 0){
                GridFSInputFile gridFile = null;
                ProductImageRelation relation = null;
                for (MultipartFile file : files){
                    if (file.getSize() <= 0){
                        continue;
                    }
                    gridFile = gridfsService.save(file);
                    relation = new ProductImageRelation();
                    relation.setCreateTime(new Date());
                    relation.setFileId(String.valueOf(gridFile.getId()));
                    relation.setFileName(gridFile.getFilename());
                    relation.setProductId(productItem.getId());
                    //relation.setStatus((byte)0);
                    productImageRelationMapper.insert(relation);
                }
            }

        }
    }

    @Override
    public void batchDelete(Integer[] ids) {
        if(ids.length>0){
            for (Integer id:ids){
                ProductItem productItem=new ProductItem();
                productItem.setId(id);
                productItemMapper.delete(productItem);
            }
        }
    }

    @Override
    public ProductItem getItemById(Integer id) {
        ProductItem item = productItemMapper.selectByPrimaryKey(id);
        if (item == null){
            return null;
        }
        List<ProductImageRelation> relations = productImageRelationService.getRelationsByProductId(id);
        List<String> fileIds = relations.stream().map(re -> re.getFileId()).collect(Collectors.toList());
        item.setFileIds(fileIds);
        return item;
    }
}
