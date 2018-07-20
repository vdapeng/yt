package com.vdaoyun.systemapi.web.service.warn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.mapper.warn.DeviceNotiRecordMapper;
import com.vdaoyun.systemapi.web.model.warn.DeviceNotiRecord;

import cn.binarywang.wx.miniapp.api.WxMaService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram;

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
	
	private static final Logger log = LoggerFactory.getLogger(DeviceNotiRecordService.class);
	
	@Async
	public void sendWxMpTemplateMessage() {
		MiniProgram miniProgram = new MiniProgram();
		miniProgram.setPagePath("/");
		miniProgram.setAppid(wxMaService.getWxMaConfig().getAppid());
		WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
				.toUser("oDJHb0Ws7H4hN7NRhIz13DQCg3IM")
				.templateId("Lhj-nGLR9H0tSlYHgKA01fAfbhNlQ2flbSpNPrOSK2g")
				.miniProgram(miniProgram)
				.build();
		
		templateMessage.addData(new WxMpTemplateData("first", "尊敬的用户，您的设备发生如下报警", ""));
		templateMessage.addData(new WxMpTemplateData("keyword1", "超低温冷柜", ""));
		templateMessage.addData(new WxMpTemplateData("keyword2", "2016-12-01 10:36:10", ""));
		templateMessage.addData(new WxMpTemplateData("keyword3", "温度报警", ""));
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
	}
}
