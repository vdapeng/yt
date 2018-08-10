package com.vdaoyun.systemapi.web.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.user.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

	public List<User> selectPageInfo(Map<String, Object> param);
	
	public User selectInfoByKey(Integer id);
	
	@Select("select * from user where openid = #{openid}")
	User selectInfoByOpenid(String openid);
}