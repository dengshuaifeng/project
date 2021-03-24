package cn.itcast.config;

import cn.itcast.constant.ResultEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageResponseResult extends CommonResult implements Serializable {
    private Integer currentPage;
    private Integer size;
    private Integer total;

    //返回分页结果并返回成功状态码
    public PageResponseResult(Integer currentPage, Integer size, Integer total) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
        this.setCode(ResultEnum.SUCCESS.getCode());
        this.setMessage(ResultEnum.SUCCESS.getMessage());
    }


}
