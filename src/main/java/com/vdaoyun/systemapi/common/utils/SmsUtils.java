package com.vdaoyun.systemapi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsUtils {
	
	private static final String defaultConnectTimeout = "10000";
	private static final String defaultReadTimeout = "10000";
	
	private static final String accessKeyId = "";
	private static final String accessKeySecret = "";
	
	private static final String product = "Dysmsapi";
	private static final String domain = "dysmsapi.aliyuncs.com";
	
	private static final String signName = "";
	private static final String templateCode = "";
	
	public static SendSmsResponse sendAlarmNoti(String phoneNumber, String templateParam) throws ClientException {
		if (StringUtils.isEmpty(phoneNumber)) {
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
	private static SendSmsResponse sendSms(String phoneNumber, String templateParam, String signName, String templateCode) throws ClientException {
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
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
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
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
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
