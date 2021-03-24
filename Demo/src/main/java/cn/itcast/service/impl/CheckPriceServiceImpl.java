package cn.itcast.service.impl;

import cn.itcast.config.CommonResult;
import cn.itcast.config.PageResponseResult;
import cn.itcast.controller.BaseController;
import cn.itcast.dao.CheckPriceDao;
import cn.itcast.dto.OrderDTO;
import cn.itcast.entity.CheckPrice;
import cn.itcast.service.CheckPriceService;
import cn.itcast.util.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class CheckPriceServiceImpl extends ServiceImpl<CheckPriceDao, CheckPrice> implements CheckPriceService {


    @Override
    public CommonResult listByDto(OrderDTO dto) {
        if (dto == null) {
            return BaseController.Result.error("参数传递有误");
        }

        dto.checkParam();
        //设置分页
        IPage pageParam = new Page(dto.getPage(), dto.getSize());
        //设置查询条件
        LambdaQueryWrapper<CheckPrice> query = new LambdaQueryWrapper<>();
        //id查询
        if (null != dto.getCheckPriceId()) {
            query.eq(CheckPrice::getCheckPriceId, dto.getCheckPriceId());
        }
        //款式类型查询
        if (null != dto.getStyleType()) {
            query.eq(CheckPrice::getStyleType, dto.getStyleType());
        }
        //状态查询
        if (null != dto.getStatus()) {
            query.eq(CheckPrice::getStatus, dto.getStatus());
        }
        //寄送方式查询
        if (null != dto.getDeliveryMethod()) {
            query.eq(CheckPrice::getDeliveryMethod, dto.getDeliveryMethod());
        }
        //时间查询
        if (null != dto.getStartTime() && null != dto.getEndTime()) {
            query.ge(CheckPrice::getCreateTime, dto.getStartTime()).le(CheckPrice::getCreateTime, dto.getEndTime());
        }
        //时间倒叙
        query.orderByDesc(CheckPrice::getCreateTime);

        //根据查询结果设置封装分页返回类
        IPage page = this.page(pageParam, query);
        if (null == page) {
            return BaseController.Result.error("数据库连接查询失败");
        }
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());

        return result;
    }

    /**
     * 上传核价订单,包含图片和工艺单
     *
     * @param request      请求体包含核价订单信息
     * @param uploadPhotos 图片
     * @param uploadFile   工艺单
     * @return
     */
    @Override
    public CommonResult uploadCheckPrice(HttpServletRequest request, MultipartFile[] uploadPhotos, MultipartFile uploadFile) {
        //批量将请求的值复制给实体类
        CheckPrice checkPrice = BeanUtil.fillBean(request, CheckPrice.class);

        //判断必填项属性
        if (null==checkPrice.getClothingType()){
            return BaseController.Result.error("服装类型为必填项");
        }
        if (null==checkPrice.getFabricType()){
            return BaseController.Result.error("面料类型为必填项");
        }
        if (null==checkPrice.getStyleType()){
            return BaseController.Result.error("款式类型为必填项");
        }
        if (null==checkPrice.getPricingMethod()){
            return BaseController.Result.error("核价方式为必填项");
        }
        if (null==checkPrice.getModelNumber()){
            return BaseController.Result.error("款号为必填项");
        }

        if (null==checkPrice.getCoefficient()){
            return BaseController.Result.error("系数为必填项");
        }
        if (null==checkPrice.getWages()){
            return BaseController.Result.error("工价为必填项");
        }
        if (null==checkPrice.getDeliveryMethod()){
            return BaseController.Result.error("送样方式为必填项");
        }
        if (null==checkPrice.getCompany()){
            return BaseController.Result.error("所属公司为必填项");
        }
        if (null==checkPrice.getContactName()){
            return BaseController.Result.error("联系人姓名为必填项");
        }
        if (null==checkPrice.getContactNumber()){
            return BaseController.Result.error("联系人电话为必填项");
        }
        //判断运送方式的必填项
        if (checkPrice.getDeliveryMethod()==0){
            if (null==checkPrice.getLogisticsOrderno()){
                return BaseController.Result.error("送样为寄送时快递单号为必填项");
            }
            checkPrice.setPickUpTime(null);
        }else if (checkPrice.getDeliveryMethod()==1){
            if (null==uploadPhotos||uploadPhotos.length==0){
                return BaseController.Result.error("送样为拍照时必须上传照片");
            }
            checkPrice.setLogisticsOrderno(null);
            checkPrice.setPickUpTime(null);
        }else if (checkPrice.getDeliveryMethod()==2){
            if (null==checkPrice.getPickUpTime()){
                return BaseController.Result.error("送样为上门取件时上门时间为必填项");
            }
            checkPrice.setLogisticsOrderno(null);
        }else{
            return BaseController.Result.error("送样方式:"+checkPrice.getDeliveryMethod()+"错误");
        }




        //验证文件格式
        boolean b = checkFileType(uploadPhotos, uploadFile);
        if (!b) {
            return BaseController.Result.error("文件格式验证失败,请重新选择文件");
        }
        //获取项目路径下/static/uploads的完整路径
        String parentPath = request.getSession().getServletContext().getRealPath("./static/uploads/");
        if (null != uploadPhotos && uploadPhotos.length >= 1) {
            //循环保存照片
            String uploadPhotosString = "";
            for (int i = 0; i < uploadPhotos.length; i++) {
                MultipartFile uploadPhoto = uploadPhotos[i];
                if (i != 0) {
                    uploadPhotosString += ",";
                }
                String path = null;
                try {
                    path = uploadAndGetPath(parentPath + "pictures", uploadPhoto);
                } catch (IOException e) {
                    BaseController.Result.error("文件存储失败");
                }
                uploadPhotosString += path;
            }
            checkPrice.setUploadPhotos(uploadPhotosString);
        }

        if (null != uploadFile) {
            //保存工艺单
            String excelPath = "";
            try {
                excelPath = uploadAndGetPath(parentPath + "excels", uploadFile);
            } catch (IOException e) {
                BaseController.Result.error("文件存储失败");
            }
            checkPrice.setUploadFile(excelPath);
        }

        //获取提交时间
        checkPrice.setCreateTime(new Date());
        checkPrice.setDeleted(Short.valueOf("0"));
        boolean flag = save(checkPrice);
        if (flag) {
            return BaseController.Result.success();
        } else {
            return BaseController.Result.error("数据库操作失败");
        }
    }

    /**
     * 校验文件格式是否正确
     *
     * @param uploadPhotos
     * @param uploadFile
     */
    private boolean checkFileType(MultipartFile[] uploadPhotos, MultipartFile uploadFile) {
        //判断文件是否为图片
        if (null != uploadPhotos && uploadPhotos.length >= 1) {
            for (MultipartFile uploadPhoto : uploadPhotos) {
                if (uploadPhoto.getOriginalFilename().endsWith(".bmp")) {
                    continue;
                }
                if (uploadPhoto.getOriginalFilename().endsWith(".gif")) {
                    continue;
                }
                if (uploadPhoto.getOriginalFilename().endsWith(".jpg")) {
                    continue;
                }
                if (uploadPhoto.getOriginalFilename().endsWith(".png")) {
                    continue;
                }
                if (uploadPhoto.getOriginalFilename().endsWith(".jpeg")) {
                    continue;
                }
                log.error(uploadPhoto.getOriginalFilename() + "不是图片");
                //文件不是上诉几种格式,说明不是图片
                return false;
            }
        }
        if (null != uploadFile) {
            //验证工艺单的格式,为excel才行
            if (uploadFile.getOriginalFilename().endsWith(".xlsx")) {
                return true;
            }
            if (uploadFile.getOriginalFilename().endsWith(".xls")) {
                return true;
            }
            if (uploadFile.getOriginalFilename().endsWith(".csv")) {
                return true;
            }
            if (uploadFile.getOriginalFilename().endsWith(".pdf")) {
                return true;
            }
            //全都验证不过则说明不是表格文件
            log.error(uploadFile.getOriginalFilename() + "不是表格文件");
            return false;
        }
        return  true;
    }

    /**
     * 修改核价单
     *
     * @param request      请求体包含核价订单信息
     * @param uploadPhotos 图片
     * @param uploadFile   工艺单
     * @return
     */
    @Override
    public CommonResult editCheckPrice(HttpServletRequest request, MultipartFile[] uploadPhotos, MultipartFile uploadFile) {
        //批量将请求的值复制给实体类
        CheckPrice checkPrice = BeanUtil.fillBean(request, CheckPrice.class);
        //验证文件格式
        boolean b = checkFileType(uploadPhotos, uploadFile);
        if (!b) {
            return BaseController.Result.error("文件格式验证失败,请重新选择文件");
        }

        //获取项目路径下/static/uploads的完整路径
        String parentPath = request.getSession().getServletContext().getRealPath("./static/uploads/");
        if (null != uploadPhotos && uploadPhotos.length >= 1) {
            //循环保存照片
            String uploadPhotosString = "";
            for (int i = 0; i < uploadPhotos.length; i++) {
                MultipartFile uploadPhoto = uploadPhotos[i];
                if (i != 0) {
                    uploadPhotosString += ",";
                }
                String path = null;
                try {
                    path = uploadAndGetPath(parentPath + "pictures", uploadPhoto);
                } catch (IOException e) {
                    BaseController.Result.error("文件存储失败");
                }
                uploadPhotosString += path;
            }
            checkPrice.setUploadPhotos(uploadPhotosString);
        }

        if (null != uploadFile) {
            //保存工艺单
            String excelPath = "";
            try {
                excelPath = uploadAndGetPath(parentPath + "excels", uploadFile);
            } catch (IOException e) {
                BaseController.Result.error("文件存储失败");
            }
            checkPrice.setUploadFile(excelPath);
        }
        checkPrice.setUpdateTime(new Date());
        boolean flag = updateById(checkPrice);
        if (flag) {
            return BaseController.Result.success();
        } else {
            return BaseController.Result.error("数据库操作失败");
        }
    }

    /**
     * 将文件上传到本地服务器
     * @param parentPath 文件要上传的文件夹路径
     * @param uploadPhoto 文件
     * @return 文件的全路径
     * @throws IOException
     */
    private String uploadAndGetPath(String parentPath, MultipartFile uploadPhoto) throws IOException {
        String type = uploadPhoto.getOriginalFilename().substring(uploadPhoto.getOriginalFilename().lastIndexOf("."));
        String fileName = System.currentTimeMillis() + UUID.randomUUID().toString().replace("-", "").substring(0, 4) + type;
        //判断该路径是否存在
        File file = new File(parentPath);
        // 若不存在则创建该文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
        uploadPhoto.transferTo(new File(parentPath, fileName));
        return parentPath + fileName;
    }
}
