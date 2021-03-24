package cn.itcast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-19 13:23:01
 */
@Data
@TableName("company_info")
public class CompanyInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 公司id
	 */
	@TableId(value = "company_id", type = IdType.AUTO)
	private Integer companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司地址
	 */
	private String companyAddress;
	/**
	 * 是否有效
	 */
	private Integer status;
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

}
