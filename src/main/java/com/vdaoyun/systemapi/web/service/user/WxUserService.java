package com.vdaoyun.systemapi.web.service.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.web.model.user.WxUser;

import me.chanjar.weixin.mp.bean.result.WxMpUser;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.service.user
 *  
 * @ClassName: WxUserService
 *  
 * @Description: 公众号用户业务逻辑层
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年8月10日 下午2:47:35
 *
 */
@Service
@Transactional
public class WxUserService extends BaseService<WxUser> {
	
	/**
	 * 
	 * @Title: save
	 *  
	 * @Description: 保存公众号信息，在用户关注公众号时调用
	 *  
	 * @param wxMpUser void
	 */
	public void save(WxMpUser wxMpUser) {
		Example example = new Example(WxUser.class);
		example.createCriteria().andEqualTo("openid", wxMpUser.getOpenId());
		example.or().andEqualTo("unionid", wxMpUser.getUnionId());
		int t = mapper.selectCountByExample(example);
		if (t < 1) {
			mapper.insertSelective(new WxUser(wxMpUser));
		} else {
			WxUser wxUser = new WxUser(wxMpUser);
			wxUser.setIsSubscribe(YesOrNo.YES.toString());
			mapper.updateByExampleSelective(wxUser, example);
		}
	}
	
	/**
	 * 
	 * @Title: unsubscribe
	 *  
	 * @Description: 取消关注，在用户取消关注公众号时调用
	 *  
	 * @param openid void
	 */
	public void unsubscribe(String openid) {
		WxUser user = new WxUser();
		user.setIsSubscribe(YesOrNo.NO.toString());
		Example example = new Example(WxUser.class);
		example.createCriteria().andEqualTo("openid", openid);
		mapper.updateByExampleSelective(user, example);
	}
	
	/**
	 * 
	 * @Title: selectByUserId
	 *  
	 * @Description: 通过小程序用户编号查询公众号用户信息
	 *  
	 * @param userId	小程序用户编号
	 * @return WxUser	公众号用户信息
	 */
	public WxUser selectByUserId(Long userId) {
		Example example = new Example(WxUser.class);
		example.createCriteria().andCondition("unionid = (SELECT unionid FROM user Where id =" + userId + " )");
		List<WxUser> wxUsers = mapper.selectByExample(example);
		return wxUsers.isEmpty() ? null : wxUsers.get(0);
	}

}
