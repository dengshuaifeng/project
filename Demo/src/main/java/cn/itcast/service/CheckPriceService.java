package cn.itcast.service;


import cn.itcast.config.CommonResult;
import cn.itcast.dto.OrderDTO;
import cn.itcast.entity.CheckPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface CheckPriceService extends IService<CheckPrice> {
    CommonResult listByDto(OrderDTO orderDto);

    /**
     * 上传核价订单,包含图片和工艺单
     * @param request 请求体包含核价订单信息
     * @param uploadPhotos 图片
     * @param uploadFile 工艺单
     * @return
     */
    CommonResult uploadCheckPrice(HttpServletRequest request, MultipartFile[] uploadPhotos, MultipartFile uploadFile);

    /**
     * 修改核价单
     * @param request 请求体包含核价订单信息
     * @param uploadPhotos 图片
     * @param uploadFile 工艺单
     * @return
     */
    CommonResult editCheckPrice(HttpServletRequest request, MultipartFile[] uploadPhotos, MultipartFile uploadFile);
}
