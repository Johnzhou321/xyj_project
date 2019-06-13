package com.xyj.modules.product.vo;

import com.xyj.modules.product.model.ProductItem;
import org.springframework.web.multipart.MultipartFile;

public class ProductItemVo extends ProductItem{
    /**
     * 分类名称
     */
    private String typeName;

    //上传图片文件
    private MultipartFile[] files;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
