package cn.itcast.dto;


import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO extends PageRequestDTO {


    /**
     * 主键id
     */
    private Integer checkPriceId;

    /**
     * 款式类型  0外套/上装  1风衣/大衣  2衬衫  3连衣裙  4裙子  5裤子/打底裤  6羽绒/棉袄  7背心  8西装  9针织衫  10吊带  11其他
     */
    private Short styleType;

    /**
     * 核价状态
     0提交核价  1等待核价  2开始核价  3完成核价
     */
    private Short status;

    /**
     * 送样方式
     * 寄件			拍照			上门取件
     */
    private Short deliveryMethod;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
