package com.vdaoyun.systemapi.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.vdaoyun.systemapi.web.model.device.DeviceRecord;
import com.vdaoyun.systemapi.web.service.device.DeviceRecordService;

/**
 * 
 * @Package com.vdaoyun.systemapi.listener
 *  
 * @ClassName: MQMessageListener
 *  
 * @Description: 监听mq消息
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年6月12日 下午8:00:11
 *
 */
public class MQMessageListener implements MessageListener {
	
	@Autowired
	private DeviceRecordService deviceRecordService;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		System.out.println("=====================================");
		System.out.println("recv msg:"+message.getBody());
		System.out.println("=====================================");
		DeviceRecord entity = new DeviceRecord();
		entity.setTerminalId("TK232");
		deviceRecordService.insert(entity);
        return Action.CommitMessage;
	}

}
