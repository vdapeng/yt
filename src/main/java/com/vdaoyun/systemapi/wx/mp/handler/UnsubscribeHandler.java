package com.vdaoyun.systemapi.wx.mp.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vdaoyun.systemapi.web.service.user.WxUserService;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

	@Autowired
	private WxUserService userService;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String openId = wxMessage.getFromUser();
		this.logger.info("取消关注用户 OPENID: " + openId);
		// TODO 可以更新本地数据库为取消关注状态
		userService.unsubscribe(openId);
		return null;
	}

}
