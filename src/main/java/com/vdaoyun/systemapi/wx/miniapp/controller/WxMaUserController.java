package com.vdaoyun.systemapi.wx.miniapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.systemapi.web.service.user.UserService;
import com.vdaoyun.systemapi.wx.miniapp.utils.JsonUtils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Api("微信小程序用户接口")
@RestController
@RequestMapping("/miniapp/user")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMaService wxService;
    @Autowired
    private UserService userService;

    /**
     * 登陆接口
     */
    @ApiOperation(tags = {"A小程序_____API_登陆"},value = "登陆")
    @GetMapping("/login")
    public String login(String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }
        try {
            WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            this.logger.info(session.getUnionid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            userService.saveMini(session.getOpenid(), session.getUnionid());
            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @ApiOperation(tags = {"A小程序_____API_获取用户信息接口"},value = "获取用户信息接口")
    @GetMapping("/info")
    public String info(String sessionKey, String signature, String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }
        // 解密用户信息
        WxMaUserInfo userInfo = this.wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        // 保存用户信息
        userService.save(userInfo);
        return JsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @ApiOperation(tags = {"A小程序_____API_获取用户绑定手机号信息"},value = "获取用户绑定手机号信息")
    @GetMapping("/phone")
    public String phone(String sessionKey, String signature, String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = this.wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(phoneNoInfo);
    }

}
