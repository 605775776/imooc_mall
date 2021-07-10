package com.imooc.mall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.UpdateProductReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

/**
 * @author dsw
 * @Description  后台商品管理Controller
 * @create 2021-07-07 10:47
 */
@RestController
public class ProductAdminController {


    @Autowired
    ProductService productService;


    @ApiOperation("后台添加商品")
    @PostMapping("admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq){
        productService.addProduct(addProductReq);
        return ApiRestResponse.success();
    }


    @ApiOperation("上传图片接口")
    @PostMapping("admin/upload/file")
    public ApiRestResponse uploadFile(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf('.'));
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffixName;
        //创建文件夹
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        // 目标文件
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }
        // 把文件从请求中放到文件目录下地址中的操作
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //string转stringBuffer 返回地址
        try {
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/images/" + newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }
    }
    // 获取当前的ip和端口号
    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                    null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }
    @ApiOperation("后台更新商品接口")
    @PostMapping("admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq, product);
        productService.updateProduct(product);
        return ApiRestResponse.success();
    }
//
//
    @ApiOperation("后台删除商品")
    @PostMapping("admin/product/delete")
    public ApiRestResponse deleteProduct(@RequestParam Integer id){
        productService.deleteProduct(id);
        return ApiRestResponse.success();
    }
//
//
//    @ApiOperation("批量上架/下架商品")
//    @PostMapping("admin/product/batchUpdateSellingStatus")
//    public ApiRestResponse batchUpdateSellingStatus(List list){
//        return ApiRestResponse.success();
//    }
//
//    @ApiOperation("后台商品列表")
//    @PostMapping("admin/product/list")
//    public ApiRestResponse productListForAdmin(Integer PageNum, Integer PageSize){
//        Page<Object> objects = PageHelper.startPage(PageNum, PageSize);
//        return ApiRestResponse.success();
//    }
//
//    @ApiOperation("前台商品列表")
//    @PostMapping("product/list")
//    public ApiRestResponse productListForCustomer(){
//        return ApiRestResponse.success();
//    }
//
//    @ApiOperation("商品详情")
//    @PostMapping("product/detail")
//    public ApiRestResponse productDetail(Integer id){
//
//        return ApiRestResponse.success();
//    }
}
