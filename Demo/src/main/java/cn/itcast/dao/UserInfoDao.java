package cn.itcast.dao;


import cn.itcast.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-19 10:22:52
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo> {

}
