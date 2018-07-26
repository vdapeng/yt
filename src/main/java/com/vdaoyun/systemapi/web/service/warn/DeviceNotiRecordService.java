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
import com.vdaoyun.systemapi.web.mapper.warn.DeviceNotiRecordMapper;
import com.vdaoyun.systemapi.web.model.device.Device;
import com.vdaoyun.systemapi.web.model.user.WxUser;
import com.vdaoyun.systemapi.web.model.warn.DeviceNotiRecord;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;
import com.vdaoyun.systemapi.web.service.device.DeviceService;
import com.vdaoyun.systemapi.web.service.user.WxUserService;
import com.vdaoyun.systemapi.websocket.WsHandler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram;
import tk.mybatis.mapper.entity.Example;

@Service
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
	private WxMpService wxMpService;
	@Autowired
    private WxMaService wxMaService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private WsHandler wsHandler;
	
	private static final Logger log = LoggerFactory.getLogger(DeviceNotiRecordService.class);
	
	private static final String ALARM_TEMPLATEID = "Lhj-nGLR9H0tSlYHgKA01fAfbhNlQ2flbSpNPrOSK2g";
	
	@Async
	public void sendWxMpTemplateMessage(DeviceWarnRecord entity) {
		
		Device device = deviceService.selectByPrimaryKey(entity.getTerminalId());
		if (device == null || device.getUserId() == null) {
			return;
		}
		Date now = new Date();
		DeviceNotiRecord record = new DeviceNotiRecord();
		record.setDeviceWarnRecordId(entity.getId());
		record.setCreateDate(now);
		record.setWxNotiDate(now);
		record.setUserId(device.getUserId());
		mapper.insertSelective(record);
		
		WxUser wxUser = wxUserService.selectByUserId(device.getUserId());
		if (wxUser == null || StringUtils.isEmpty(wxUser.getOpenid())) {
			return;
		}
		
		MiniProgram miniProgram = new MiniProgram();
		miniProgram.setPagePath("/");
		miniProgram.setAppid(wxMaService.getWxMaConfig().getAppid());
		WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
				.toUser(wxUser.getOpenid())
				.templateId(ALARM_TEMPLATEID)
				.miniProgram(miniProgram)
				.build();
		
		templateMessage.addData(new WxMpTemplateData("first", "尊敬的用户，您的设备发生如下报警", ""));
		templateMessage.addData(new WxMpTemplateData("keyword1", device.getName(), ""));
		templateMessage.addData(new WxMpTemplateData("keyword2", DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss"), ""));
		templateMessage.addData(new WxMpTemplateData("keyword3", entity.getAlaramBusiness(), ""));
		templateMessage.addData(new WxMpTemplateData("remark", "请及时处理！", ""));
		try {
			String msgid = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
			log.info( "\n=============SUCCESS===============\n\t"
					+ "msgId: {}\n"
					+ "===============END=================", msgid);
		} catch (WxErrorException e) {
			log.error( "\n=============ERROR===============\n\t"
					+ "msg: {}\n"
					+ "==============END================", e.getMessage());
		}
		
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

	public int count(Long deviceWarnRecordId) {
		DeviceNotiRecord record = new DeviceNotiRecord();
		record.setDeviceWarnRecordId(deviceWarnRecordId);
		return mapper.selectCount(record);
	}
}
