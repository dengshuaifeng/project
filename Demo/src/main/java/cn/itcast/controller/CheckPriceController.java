package cn.itcast.controller;

import cn.itcast.config.CommonResult;
import cn.itcast.dto.OrderDTO;
import cn.itcast.service.CheckPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("price")
public class CheckPriceController {

    @Autowired
    private CheckPriceService checkPriceService;

    @PostMapping("list")
    public CommonResult list(@RequestBody OrderDTO orderDto) {
        try {
            return checkPriceService.listByDto(orderDto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseController.Result.error();
        }
    }

    /**
     * 提交订单
     * @param request 请求体参数
     * @param uploadPhotos 照片
     * @param uploadFile  工艺单
     * @return
     */
    @PostMapping("add")
    public CommonResult addCheckPrice(HttpServletRequest request,
                                      @RequestParam(value = "uploadPhotos", required = false) MultipartFile[] uploadPhotos,
                                      @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) {
        try {
            return checkPriceService.uploadCheckPrice(request, uploadPhotos, uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseController.Result.error();
        }
    }

    /**
     * 删除核价单
     * @param id 核价单编号
     * @return
     */
    @DeleteMapping("delete/{id}")
    public CommonResult deleteCheckPric(@PathVariable("id") Long id) {
        try {
            boolean flag = checkPriceService.removeById(id);
            if (flag) return BaseController.Result.success();
            else return BaseController.Result.error("数据库删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseController.Result.error();
        }
    }

    /**
     * 修改核价订单
     * @param request 请求体参数
     * @param uploadPhotos 照片
     * @param uploadFile  工艺单
     * @return
     */
    @PutMapping("edit")
    public CommonResult editCheckPrice(HttpServletRequest request,
                                      @RequestParam(value = "uploadPhotos", required = false) MultipartFile[] uploadPhotos,
                                      @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) {
        try {
            return checkPriceService.editCheckPrice(request, uploadPhotos, uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseController.Result.error();
        }
    }



}
