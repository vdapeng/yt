package com.vdaoyun.systemapi.web.service.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.web.mapper.user.UserMapper;
import com.vdaoyun.systemapi.web.model.user.User;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class UserService extends BaseService<User> {
	
//	@Override
//	public int delete(Object key) {
//		User entity = new User();
//		entity.setIsDel(YesOrNo.YES.toString());
//		entity.setId((Integer)key);
//		return super.update(entity);
//	} 
	
	// 启用
	public boolean enable(Long id) {
		User entity = new User();
		entity.setId(id);
		entity.setIsEnable(YesOrNo.YES.toString());
		return mapper.updateByPrimaryKeySelective(entity) > 0;
	}

	
	public boolean disEnable(Long id) {
		User entity = new User();
		entity.setId(id);
		entity.setIsEnable(YesOrNo.NO.toString());
		return mapper.updateByPrimaryKeySelective(entity) > 0;
	}
	
	@Override
	public int insert(User entity) {
		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	public User selectInfoByOpenid(String openid) {
		User record = new User();
		record.setOpenid(openid);
		return mapper.selectOne(record);
	}
	
	public User selectInfoByOpenid(String openid, String unionid) {
		User record = new User();
		record.setOpenid(openid);
		record.setUnionid(unionid);
		return mapper.selectOne(record);
	}
	
	public Boolean isExit(String openid, String mobile) {
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("openid", openid);
		example.or().andEqualTo("mobile", mobile);
		return mapper.selectCountByExample(example) > 0;
	}
	
	@Autowired
	private UserMapper rootMapper;
	
	public PageInfo<User> selectPageInfo(
		User entity, 
		Integer wdy_pageNum, 
		Integer wdy_pageSize, 
		String wdy_pageOrder, 
		String wdy_pageSort
	) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("entity", entity);
		if (StringUtils.isNotEmpty(wdy_pageOrder) && StringUtils.isNotEmpty(wdy_pageSort)) {
			param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		} else {
			param.put("orderByClause", "createDate DESC");
		}
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<User> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	@Transactional
	public Integer insertInfo(User entity) {
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: saveMini
	 *  
	 * @Description: 保存小程序相关信息
	 *  
	 * @param openid
	 * @param unionid void
	 */
	public void saveMini(String openid, String unionid) {
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("openid", openid);
		example.or().andEqualTo("unionid", unionid);
		int t = mapper.selectCountByExample(example);
		if (t < 1) {
			mapper.insertSelective(new User(openid, unionid));
		}
	}
	
	public void save(WxMaUserInfo userInfo) {
		saveMini(userInfo.getOpenId(), userInfo.getUnionId());
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("openid", userInfo.getOpenId());
		example.or().andEqualTo("unionid", userInfo.getUnionId());
		mapper.updateByExampleSelective(new User(userInfo), example);
	}
	
	/**
	 * 
	 * @Title: bindMobile
	 *  
	 * @Description: 绑定手机号码
	 *  
	 * @param openid
	 * @param unionid
	 * @param mobile void
	 */
	public void bindMobile(String openid, String unionid, String mobile) {
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("openid", openid);
		example.or().andEqualTo("unionid", unionid);
		if (mapper.selectCountByExample(example) < 1) {
			saveMini(openid, unionid);
		} 
		User record = new User();
		record.setMobile(mobile);
		mapper.updateByExampleSelective(record, example);
	}
	
	public List<User> search(String search) {
		Example example = new Example(User.class);
		example.createCriteria().andLike("nickname", search + "%");
		example.or().andLike("trueName", search + "%");
		example.or().andLike("mobile", search + "%");
		return mapper.selectByExample(example);
				
	}
	
	public int count(String isEnable) {
		User record = new User();
		if (StringUtils.isNotEmpty(isEnable)) {
			record.setIsEnable(isEnable);
		}
		return mapper.selectCount(record);
	}
}
