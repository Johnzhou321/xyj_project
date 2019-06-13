
package com.xyj.modules.product.controller;

import com.github.pagehelper.PageInfo;
import com.mongodb.gridfs.GridFSDBFile;
import com.xyj.base.GridfsService;
import com.xyj.core.annotation.SysLog;
import com.xyj.core.entity.ProcessResult;
import com.xyj.core.result.PageResult;
import com.xyj.modules.product.model.ProductItem;
import com.xyj.modules.product.service.ProductItemService;
import com.xyj.modules.product.vo.ProductItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @author qjp
 * @since 2015-12-19 11:10
 */
@Api(value ="产品管理模块", description = "产品管理Api",tags = {"产品管理操作接口"})
@RestController
@RequestMapping("/productItem")
public class ProductItemController {

    @Autowired
    private ProductItemService productItemService;
    @Autowired
    private GridfsService gridfsService;

    @ApiOperation(value = "产品列表视图",notes = "产品列表视图")
    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("/modules/product/list");
    }

    @ApiOperation(value = "获取产品列表",notes = "获取产品列表")
    @PostMapping
    public PageResult<ProductItemVo> getAll(ProductItem productItem, String keyword, HttpServletRequest request) {
        List<ProductItemVo> typeList = productItemService.getAll(productItem, keyword);
        return new PageResult(new PageInfo<ProductItemVo>(typeList));
    }

    @ApiOperation(value = "批量删除产品",notes = "批量删除产品")
    @SysLog("批量删除产品")
    @PostMapping(value = "/batchDelete")
    public ProcessResult batchDelete(@ApiParam(name = "ids",value="产品ID数组",required = true) @RequestParam("ids[]") Integer[] ids) {
        try {
            productItemService.batchDelete(ids);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ProcessResult.ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "保存产品",notes = "保存产品")
    @SysLog("保存产品")
    @PostMapping(value = "/saveOrUpdate")
    public ProcessResult saveOrUpdate(@ApiParam(name = "productItemVo",value="产品实体",required = true) ProductItemVo productItemVo) {
        try {
            productItemService.saveOrUpdate(productItemVo);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ProcessResult.ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "获取产品",notes = "获取产品")
    @PostMapping(value = "/view/{id}")
    public ProductItem view(@ApiParam(name = "id",value="产品ID",required = true) @PathVariable Integer id) {
        ProductItem productItem = productItemService.getItemById(id);
        return productItem;
    }

    @ApiOperation(value = "删除产品",notes = "删除产品")
    @SysLog("删除产品")
    @PostMapping(value = "/delete/{id}")
    public ProcessResult delete(@ApiParam(name = "id",value="ID",required = true) @PathVariable Integer id) {
        try {
            productItemService.deleteById(id);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ProcessResult.ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "新增产品视图",notes = "新增产品视图")
    @GetMapping("/form")
    public ModelAndView form() {
        return new ModelAndView("/modules/product/form");
    }

    @ApiOperation(value = "产品明细视图",notes = "产品明细视图")
    @GetMapping("/detailDialog")
    public ModelAndView detailDialog() {
        return new ModelAndView("/modules/product/detailDialog");
    }

    @ApiOperation(value = "查询产品图片",notes = "查询产品图片")
    @GetMapping(value = "/getFileById/{id}")
    public void getFileById(@ApiParam(name = "id",value="ID",required = true) @PathVariable String id, HttpServletResponse resp){
        try {
            OutputStream out = resp.getOutputStream();
            resp.setContentType("image/png");
            GridFSDBFile file = gridfsService.getById(new ObjectId(id));
            file.writeTo(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
