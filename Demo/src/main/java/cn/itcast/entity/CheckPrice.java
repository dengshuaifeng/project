package cn.itcast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-19 13:23:01
 */
@Data
@TableName("check_price")
public class CheckPrice implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "check_price_id", type = IdType.AUTO)
	private Integer checkPriceId;
	/**
	 * 服装类型
  0女装  1男装  2童装
	 */
	private Short clothingType;
	/**
	 * 面料类型
  		0针织  1梭织  2真丝/雪纺  3牛仔  4皮衣  5双面尼
	 */
	private Short fabricType;
	/**
	 * 款式类型  0外套/上装  1风衣/大衣  2衬衫  3连衣裙  4裙子  5裤子/打底裤  6羽绒/棉袄  7背心  8西装  9针织衫  10吊带  11其他
	 */
	private Short styleType;
	/**
	 * 核价方式
  0核总工时  1核工时明细
	 */
	private Short pricingMethod;
	/**
	 * 款号
	 */
	private String modelNumber;
	/**
	 * 系数
	 */
	private BigDecimal coefficient;
	/**
	 * 需求时间
	 */
	private Date needTime;
	/**
	 * 工价/h
	 */
	private BigDecimal wages;
	/**
	 * 送样方式
	 * 0 寄件			1 拍照			2 上门取件
	 */
	private Short deliveryMethod;
	/**
	 * 物流单号
	 */
	private String logisticsOrderno;
	/**
	 * 上门时间
	 */
	private Date pickUpTime;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 所属公司
	 */
	private String company;
	/**
	 * 联系人电话
	 */
	private String contactNumber;
	/**
	 * 联系人姓名
	 */
	private String contactName;
	/**
	 * 上传图片
	 */
	private String uploadPhotos;
	/**
	 * 上传文件（工艺单）
	 */
	private String uploadFile;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 更新人
	 */
	private String updateUser;
	/**
	 * 核价状态
       0提交核价  1等待核价  2开始核价  3完成核价
	 */
	private Short status;

	/**
	 * 逻辑删除 0未删除  1已删除
	 */
	@TableLogic
	private Short deleted;

}
