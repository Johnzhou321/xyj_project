
package com.xyj.modules.product.controller;

import com.xyj.core.annotation.SysLog;
import com.xyj.core.entity.ProcessResult;
import com.xyj.core.result.PageResult;
import com.xyj.modules.product.model.ProductType;
import com.xyj.modules.product.service.ProductTypeService;
import com.xyj.modules.sys.model.SysRole;
import com.xyj.modules.sys.service.RoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qjp
 * @since 2015-12-19 11:10
 */
@Api(value ="产品管理模块", description = "产品管理Api",tags = {"产品管理操作接口"})
@RestController
@RequestMapping("/productType")
public class ProductTypeController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProductTypeService productTypeService;

    @ApiOperation(value = "产品类型列表视图",notes = "产品类型列表视图")
    @GetMapping("/typeList")
    public ModelAndView typeList() {
        return new ModelAndView("/modules/product/typeList");
    }

    @ApiOperation(value = "获取产品类型列表",notes = "获取产品类型列表")
    @PostMapping
    public PageResult<ProductType> getAll(ProductType productType, String keyword, HttpServletRequest request) {
        List<ProductType> typeList = productTypeService.getAll(productType, keyword);
        return new PageResult(new PageInfo<ProductType>(typeList));
    }

    @ApiOperation(value = "获取所有产品类型列表",notes = "获取所有产品类型列表")
    @PostMapping("/typeAllList")
    public List<ProductType> getAllList(HttpServletRequest request) {
        List<ProductType> typeList = productTypeService.getAllList();
        return typeList;
    }

    @ApiOperation(value = "批量删除分类",notes = "批量删除分类")
    @SysLog("批量删除分类")
    @PostMapping(value = "/batchDelete")
    public ProcessResult batchDelete(@ApiParam(name = "ids",value="分类ID数组",required = true) @RequestParam("ids[]") Integer[] ids) {
        try {
            productTypeService.batchDelete(ids);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ProcessResult.ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "保存分类",notes = "保存分类")
    @SysLog("保存分类")
    @PostMapping(value = "/saveOrUpdate")
    public ProcessResult saveOrUpdate(@ApiParam(name = "productType",value="分类实体",required = true) ProductType productType) {
        try {
            productTypeService.saveOrUpdate(productType);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ProcessResult.ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "获取分类信息",notes = "获取分类信息")
    @PostMapping(value = "/view/{id}")
    public ProductType view(@ApiParam(name = "id",value="分类ID",required = true) @PathVariable Integer id) {
        ProductType productType = productTypeService.getById(id);
        return productType;
    }

    @ApiOperation(value = "删除分类",notes = "删除分类")
    @SysLog("删除分类")
    @PostMapping(value = "/delete/{id}")
    public ProcessResult delete(@ApiParam(name = "id",value="分类ID",required = true) @PathVariable Integer id) {
        try {
            productTypeService.deleteById(id);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ProcessResult.ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "新增类型视图",notes = "新增类型视图")
    @GetMapping("/typeForm")
    public ModelAndView form() {
        return new ModelAndView("/modules/product/typeForm");
    }

}
