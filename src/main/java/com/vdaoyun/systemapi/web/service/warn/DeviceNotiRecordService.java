package com.vdaoyun.systemapi.web.service.warn;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.exception.ParamException;
import com.vdaoyun.systemapi.mq.MQConstants;
import com.vdaoyun.systemapi.web.mapper.warn.DeviceNotiRecordMapper;
import com.vdaoyun.systemapi.web.model.device.Device;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;
import com.vdaoyun.systemapi.web.model.sensor.Sensor;
import com.vdaoyun.systemapi.web.model.user.WxUser;
import com.vdaoyun.systemapi.web.model.warn.DeviceNotiRecord;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;
import com.vdaoyun.systemapi.web.service.device.DeviceService;
import com.vdaoyun.systemapi.web.service.ponds.PondsService;
import com.vdaoyun.systemapi.web.service.sensor.SensorService;
import com.vdaoyun.systemapi.web.service.user.WxUserService;
import com.vdaoyun.systemapi.websocket.WsHandler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional
public class DeviceNotiRecordService extends BaseService<DeviceNotiRecord> {
	
//	@Override
//	public int delete(Object key) {
//		DeviceNotiRecord entity = new DeviceNotiRecord();
//		entity.setIsDel(YesOrNo.YES.toString());
//		entity.setId((Integer)key);
//		return super.update(entity);
//	} 
	
	@Override
	public int insert(DeviceNotiRecord entity) {
//		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	@Autowired
	private DeviceNotiRecordMapper rootMapper;
	
	public PageInfo<DeviceNotiRecord> selectPageInfo(
		DeviceNotiRecord entity, 
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
		List<DeviceNotiRecord> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	
	@Transactional
	public Integer insertInfo(DeviceNotiRecord entity) {
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}
	
	
	@Autowired
	private DeviceWarnRecordService deviceWarnRecordService;
	
	public void wxNoti(Long id) throws ParamException {
		DeviceNotiRecord record = mapper.selectByPrimaryKey(id);
		if (record == null || record.getPondsId() == null) {
			throw new ParamException("无效通知");
		}
		Ponds ponds = pondsService.selectByPrimaryKey(record.getPondsId());
		if (ponds == null) {
			throw new ParamException("无效通知");
		}
		Device device = deviceService.selectByPrimaryKey(ponds.getTerminalId());
		if (device == null) {
			throw new ParamException("无效通知");
		}
		Sensor sensor = sensorService.selectInfoByCodeAndTerminalId(device.getTerminalId(), record.getCode());
		if (sensor == null) {
			throw new ParamException("无效通知");
		}
		WxUser wxUser = wxUserService.selectByUserId(ponds.getUserId());
		if (wxUser == null || wxUser.getIsSubscribe().equalsIgnoreCase(YesOrNo.NO.toString())) {
			// TODO 改用短信通知
			throw new ParamException("未关注公众号，无法通知");
		}
		DeviceWarnRecord deviceWarnRecord = deviceWarnRecordService.selectByPrimaryKey(record.getDeviceWarnRecordId());
		if (deviceWarnRecord == null) {
			throw new ParamException("无效通知");
		}
		MiniProgram miniProgram = new MiniProgram();
		miniProgram.setPagePath("pages/monitor/templateMsgDetail/templateMsgDetail?pondsId=" + ponds.getId() + "&pondsName=" + ponds.getName() + "&terminalId=" + device.getTerminalId());
//		miniProgram.setPagePath("/");
		miniProgram.setAppid(wxMaService.getWxMaConfig().getAppid());
		WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
				.toUser(wxUser.getOpenid())
				.templateId(ALARM_TEMPLATEID)
				.miniProgram(miniProgram)
				.build();
		
		templateMessage.addData(new WxMpTemplateData("first", "尊敬的用户，您的设备发生如下报警", ""));
		templateMessage.addData(new WxMpTemplateData("keyword1", device.getName(), ""));
		templateMessage.addData(new WxMpTemplateData("keyword2", DateFormatUtils.format(deviceWarnRecord.getPostTime(), "yyyy-MM-dd HH:mm:ss"), ""));
		templateMessage.addData(new WxMpTemplateData("keyword3", sensor.getName(), ""));
		templateMessage.addData(new WxMpTemplateData("remark", "请及时处理！", ""));
		String msgid = "";
		try {
			msgid = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
			
			log.info( "\n=============SUCCESS===============\n\t"
					+ "msgId: {}\n"
					+ "===============END=================", msgid);
			
		} catch (WxErrorException e) {
			log.error( "\n=============ERROR===============\n\t"
					+ "msg: {}\n"
					+ "==============END================", e.getMessage());
//			throw new ParamException("微信消息发送失败，错误消息：" + e.getMessage());
		}
		if (StringUtils.isNotEmpty(msgid)) {
			DeviceNotiRecord record2 = new DeviceNotiRecord();
			record2.setId(id);
			record2.setIsWxNoti(YesOrNo.YES.toString());
			record2.setMsgId(msgid);
			mapper.updateByPrimaryKeySelective(record2);
			deviceWarnRecordService.version(record.getDeviceWarnRecordId(), deviceWarnRecord.getVersion());
		}
		
	}

	@Autowired
	private WxMpService wxMpService;
	@Autowired
    private WxMaService wxMaService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private WsHandler wsHandler;
	@Autowired
	private PondsService pondsService;
	@Autowired
	private SensorService sensorService;
	
	private static final Logger log = LoggerFactory.getLogger(DeviceNotiRecordService.class);
	
	private static final String ALARM_TEMPLATEID = "Lhj-nGLR9H0tSlYHgKA01fAfbhNlQ2flbSpNPrOSK2g";
	
	/**
	 * 
	 * @Title: sendWxMpTemplateMessage
	 *  
	 * @Description: 发送微信模板消息
	 *  
	 * @param entity 报警信息
	 */
	@Async
	public void sendWxMpTemplateMessage(DeviceWarnRecord entity) {
		
		String alaramBusiness = entity.getAlaramBusiness();
		if (StringUtils.isEmpty(alaramBusiness)) { // 非业务报警不发送通知
			return;
		}
		String terminalId = entity.getTerminalId();
		Device device = deviceService.selectByPrimaryKey(terminalId);
		if (device == null || device.getUserId() == null) {	// 未找到相关设备不发送通知
			return;
		}
		WxUser wxUser = wxUserService.selectByUserId(device.getUserId()); 
		if (wxUser == null || StringUtils.isEmpty(wxUser.getOpenid()) || wxUser.getIsEnable().equalsIgnoreCase(YesOrNo.NO.toString()) || wxUser.getIsSubscribe().equalsIgnoreCase(YesOrNo.NO.toString())) {
			// 用户信息不存在，或者在禁用状态，或者未关注 不发送 TODO: 改用短信通知
			return;
		}
		
		String[] alarams = alaramBusiness.trim().split(MQConstants.WARN_SEPARATOR);
		for (String alarm : alarams) { // 拆分探测器，分别发送
			
			Sensor sensor = sensorService.selectInfoByCodeAndTerminalId(terminalId, alarm);
			if (sensor == null || sensor.getIsAlarm().equalsIgnoreCase(YesOrNo.NO.toString()) || sensor.getPondsId() == null) {
				log.info( "\n=============FAILED===============\n\t"
						+ "MESSAGE: {}\n"
						+ "===============END=================", sensor);
				return;
			}
			
			Ponds ponds = pondsService.selectByPrimaryKey(sensor.getPondsId());
			if (ponds == null) {
				log.info( "\n=============FAILED===============\n\t"
						+ "MESSAGE: {}\n"
						+ "===============END=================", ponds);
				return;
			}
			
			Date now = new Date();
			DeviceNotiRecord record = new DeviceNotiRecord();
			record.setDeviceWarnRecordId(entity.getId());
			record.setCreateDate(now);
			record.setWxNotiDate(now);
			record.setUserId(device.getUserId());
			record.setPondsId(ponds.getId());
			record.setTerminalId(terminalId);
			record.setCode(alarm);
			mapper.insertSelective(record); // 新增报警通知记录
			
			MiniProgram miniProgram = new MiniProgram();
			miniProgram.setPagePath("pages/monitor/templateMsgDetail/templateMsgDetail?pondsId=" + ponds.getId() + "&pondsName=" + ponds.getName() + "&terminalId=" + terminalId);
			miniProgram.setAppid(wxMaService.getWxMaConfig().getAppid());
			WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
					.toUser(wxUser.getOpenid())
					.templateId(ALARM_TEMPLATEID)
					.miniProgram(miniProgram)
					.build();
			
			templateMessage.addData(new WxMpTemplateData("first", "尊敬的用户，您的设备发生如下报警", ""));
			templateMessage.addData(new WxMpTemplateData("keyword1", device.getName(), ""));
			templateMessage.addData(new WxMpTemplateData("keyword2", DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss"), ""));
			templateMessage.addData(new WxMpTemplateData("keyword3", sensor.getName(), ""));
			templateMessage.addData(new WxMpTemplateData("remark", "请及时处理！", ""));
			String msgid = "";
			try {
				msgid = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
				log.info( "\n=============SUCCESS===============\n\t"
						+ "msgId: {}\n"
						+ "===============END=================", msgid);
			} catch (WxErrorException e) {
				log.error( "\n=============ERROR===============\n\t"
						+ "msg: {}\n"
						+ "==============END================", e.getMessage());
			}
			if (StringUtils.isNotEmpty(msgid)) {
				DeviceNotiRecord record2 = new DeviceNotiRecord();
				record2.setId(record.getId());
				record2.setIsWxNoti(YesOrNo.YES.toString());
				record2.setMsgId(msgid);
				mapper.updateByPrimaryKeySelective(record2);
			}
		}
		// 通过websocket给管理后台发送消息
		wsHandler.sendAll("有新报警:" + device.getName() + "__" + entity.getAlaramBusiness());
		
	}

	public void read(Long deviceWarnRecordId) {
		DeviceNotiRecord record = new DeviceNotiRecord();
		record.setIsRead(YesOrNo.YES.toString());
		record.setReadDate(new Date());
		Example example = new Example(DeviceNotiRecord.class);
		example.createCriteria().andEqualTo("deviceWarnRecordId", deviceWarnRecordId);
		mapper.updateByExampleSelective(record, example);
	}
	
	/**
	 * 
	 * @Title: readByPondsId
	 *  
	 * @Description: 通过塘口编号设置报警通知是否已读
	 *  
	 * @param pondsId void
	 */
	public void readByPondsId(Long pondsId) {
		DeviceNotiRecord record = new DeviceNotiRecord();
		record.setIsRead(YesOrNo.YES.toString());
		record.setReadDate(new Date());
		Example example = new Example(DeviceNotiRecord.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("pondsId", pondsId);
		criteria.andEqualTo("isRead", YesOrNo.NO.toString());
		mapper.updateByExampleSelective(record, example);
	}

	public int count(Long deviceWarnRecordId) {
		DeviceNotiRecord record = new DeviceNotiRecord();
		record.setDeviceWarnRecordId(deviceWarnRecordId);
		return mapper.selectCount(record);
	}
	
	/**
	 * 
	 * @Title: selectUnRead
	 *  
	 * @Description: 查询微信通知三十分钟后未查看的报警通知记录
	 *  
	 * @return List<DeviceNotiRecord>
	 */
	public List<DeviceNotiRecord> selectUnRead() {
		Example example = new Example(DeviceNotiRecord.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("isRead", YesOrNo.NO.toString());
		criteria.andEqualTo("isSmsNoti", YesOrNo.NO.toString());
		criteria.andEqualTo("isWxNoti", YesOrNo.YES.toString());
		criteria.andCondition("wx_noti_date < date_sub(NOW(), interval 30 MINUTE)");
		return mapper.selectByExample(example);
	}
	
	public void sendSms(Long id, String bizId) {
		DeviceNotiRecord record = new DeviceNotiRecord();
		record.setId(id);
		record.setIsSmsNoti(YesOrNo.YES.toString());
		record.setSmsNotiDate(new Date());
		record.setBizId(bizId);
		mapper.updateByPrimaryKeySelective(record);
	}
	
}
