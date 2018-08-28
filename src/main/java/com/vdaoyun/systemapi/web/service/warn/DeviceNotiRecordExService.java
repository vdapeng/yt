package com.vdaoyun.systemapi.web.service.warn;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.model.ponds.TransferParam;
import com.vdaoyun.systemapi.web.model.warn.DeviceNotiRecord;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.service.warn
 *  
 * @ClassName: DeviceNotiRecordExService
 *  
 * @Description: 为避免循环注入 将部分业务在此类进行处理
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年8月2日 上午10:22:44
 *
 */
@Service
@Transactional
public class DeviceNotiRecordExService extends BaseService<DeviceNotiRecord> {

	/**
	 * 
	 * @Title: removeByTer
	 *  
	 * @Description: 通过设备编号删除相关通知记录
	 *  
	 * @param terminalId 设备编号
	 */
	public void removeByTer(String terminalId) {
		DeviceNotiRecord record = new DeviceNotiRecord();
		record.setTerminalId(terminalId);
		mapper.delete(record);
	}
	
	/**
	 * 
	 * @Title: removeByPondsId
	 *  
	 * @Description: 通过塘口编号删除相关通知记录
	 *  
	 * @param ponsId 塘口编号
	 */
	public void removeByPondsId(Long ponsId) {
		DeviceNotiRecord record = new DeviceNotiRecord();
		record.setPondsId(ponsId);
		mapper.delete(record);
	}
	
	public void transfer(TransferParam param) {
		DeviceNotiRecord record = new DeviceNotiRecord();
		record.setUserId(param.getToUserId());
		Example example = new Example(DeviceNotiRecord.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", param.getFromUserId());
		criteria.andEqualTo("pondsId", param.getPondsId());
		mapper.updateByExampleSelective(record, example);
	}
	
}
