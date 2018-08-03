package com.vdaoyun.systemapi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.vdaoyun.systemapi.configuration.SpringContextHolder;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;

/**
 * 
 * @Package com.vdaoyun.systemapi.common.utils
 *  
 * @ClassName: SmsUtils
 *  
 * @Description: 阿里云短信通知工具类
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年8月3日 下午2:37:03
 *
 */
public class SmsUtils {
	
	private static final String defaultConnectTimeout = "10000";
	private static final String defaultReadTimeout = "10000";
	
	private static final String accessKeyId = "LTAID7tAbKcq6f2t";
	private static final String accessKeySecret = "l2WYEcxdr7owSAdA4JeMGbVBddzrky";
	
	private static final String product = "Dysmsapi";
	private static final String domain = "dysmsapi.aliyuncs.com";
	
	private static final String signName = "";
	private static final String templateCode = "";
	
	// 短信发送成功CODE编码
	private static final String send_success_code = "OK";
	// 短信发送异常 微信推送给管理员
	private static final String[] toUsers = new String[] {"oDJHb0Ws7H4hN7NRhIz13DQCg3IM"};
	
	private static final Logger log = LoggerFactory.getLogger(SmsUtils.class);
	
	public static SendSmsResponse sendAlarmNoti(String phoneNumber, String templateParam) {
		if (StringUtils.isEmpty(phoneNumber) || !ValidationUtils.isMobile(phoneNumber)) {
			log.debug("\n=============================\n\t"
					+ "TITLE: \t{}\n\t"
					+ "MESSAGE: \t{}\n\t"
					+ "PHONE: \t{}\n"
					+ "=============================", "短信发送失败", "手机号码无效", phoneNumber);
			
			return null;
		}
		return SmsUtils.sendSms(phoneNumber, templateParam, signName, templateCode);
	}
	
	/**
	* @Title: sensSms 
	* @Description: 发送短信
	* @param @param phoneNumber			接收人手机号码
	* @param @param templateParam		短信模板json数据
	* @param @param signName			签名
	* @param @param templateCode		短信模板
	* @param @return
	* @param @throws ClientException    设定文件 
	* @return SendSmsResponse    返回类型 
	* @throws
	 */
	private static SendSmsResponse sendSms(String phoneNumber, String templateParam, String signName, String templateCode) {
		if (StringUtils.isEmpty(signName)) {
			signName = SmsUtils.signName;
		}
		if (StringUtils.isEmpty(templateCode)) {
			templateCode = SmsUtils.templateCode;
		}
		//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", defaultConnectTimeout);
        System.setProperty("sun.net.client.defaultReadTimeout", defaultReadTimeout);

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			e1.printStackTrace();
		}
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ServerException e1) {
			e1.printStackTrace();
		} catch (ClientException e1) {
			e1.printStackTrace();
		}
		
		// 短信发送失败
        if (sendSmsResponse == null || sendSmsResponse.getCode() == null || !sendSmsResponse.getCode().equalsIgnoreCase(send_success_code)) {
        	
        	WxMpService wxMpService = SpringContextHolder.getBean("wxMpService");
        	WxMpMassOpenIdsMessage message = new WxMpMassOpenIdsMessage();
        	message.setToUsers(Arrays.asList(toUsers));
        	message.setMsgType(WxConsts.MassMsgType.TEXT);
        	message.setContent("阿里云短信服务异常：" + sendSmsResponse.getMessage());
        	try {
				wxMpService.getMassMessageService().massOpenIdsMessageSend(message);
			} catch (WxErrorException e) {
				log.error("\n================================\n\t"
						+ "TITLE: \t{}\n\t"
						+ "TOUSER: \t{}\n\t"
						+ "MESSAGE: \t{}\n\t"
						+ "================================", "短信发送失败", phoneNumber , e.getMessage());
			}
		}
        return sendSmsResponse;
	}
	
	/**
	* @Title: querySendDetails 
	* @Description: 查询
	* @param @param bizId				发送回执ID
	* @param @param phoneNumber			手机号码
	* @param @return
	* @param @throws ClientException    设定文件 
	* @return QuerySendDetailsResponse  返回类型 
	* @throws
	 */
	public static QuerySendDetailsResponse querySendDetails(String bizId, String phoneNumber) throws ClientException {
		 //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", defaultConnectTimeout);
        System.setProperty("sun.net.client.defaultReadTimeout", defaultReadTimeout);

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phoneNumber);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
        return querySendDetailsResponse;
	}

}
