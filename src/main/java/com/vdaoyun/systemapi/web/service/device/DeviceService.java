package com.vdaoyun.systemapi.web.service.device;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.web.mapper.device.DeviceMapper;
import com.vdaoyun.systemapi.web.model.device.Device;
import com.vdaoyun.systemapi.web.service.ponds.PondsService;
import com.vdaoyun.systemapi.web.service.sensor.SensorRecordJsonService;
import com.vdaoyun.systemapi.web.service.sensor.SensorRecordService;
import com.vdaoyun.systemapi.web.service.sensor.SensorService;
import com.vdaoyun.systemapi.web.service.warn.DeviceNotiRecordExService;
import com.vdaoyun.systemapi.web.service.warn.DeviceWarnRecordService;

@Transactional
@Service
public class DeviceService extends BaseService<Device> {
	
	@Autowired
	private SensorRecordService sensorRecordService;
	@Autowired
	private SensorRecordJsonService sensorRecordJsonService;
	@Autowired
	private DeviceRecordService deviceRecordService;
	@Autowired
	private SensorService sensorService;
	@Autowired
	private PondsService pondsService;
	@Autowired
	private DeviceNotiRecordExService deviceNotiRecordService;
	@Autowired
	private DeviceWarnRecordService deviceWarnRecordService;

	// 是否物理删除设备数据，默认false
	@Value("${data.device.remove}")
	private Boolean ISDELDATA = false;
	
	@Override
	public int delete(Object key) {
		if (ISDELDATA) {// 物理删除
			Device device = mapper.selectByPrimaryKey(key);
			if (device == null) {
				return 0;
			}
			String terminalId = device.getTerminalId();
			sensorRecordService.removeByTer(terminalId);			// 删除设备传感器运行数据
			sensorRecordJsonService.removeByTer(terminalId);		// 删除设备运行数据
			deviceRecordService.removeByTer(terminalId);			// 删除设备运行数据
			sensorService.removeByTer(terminalId);					// 删除设备所有传感器
			deviceNotiRecordService.removeByTer(terminalId);		// 删除设备所有报警通知记录
			deviceWarnRecordService.removeByTer(terminalId);		// 删除设备所有报警记录
			pondsService.setNullByTer(terminalId);					// 将绑定该设备的所有塘口设备编号置000000
			return mapper.deleteByPrimaryKey(key);					// 删除设备
		}
		Device entity = new Device();
		entity.setTerminalId((String) key);
		entity.setIsDel(YesOrNo.YES.toString());
		return mapper.updateByPrimaryKeySelective(entity);
	}

	/**
	 * 
	 * @Title: selectAll
	 * 
	 * @Description: 搜索所有设备列表
	 * 
	 * @return List<Device>
	 */
	public List<Device> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public int insert(Device entity) {
		entity.setCreateDate(new Date());
		int result = super.insert(entity);
		return result;
	}

	@Autowired
	private DeviceMapper rootMapper;

	public PageInfo<Device> selectPageInfo(Device entity, Integer wdy_pageNum, Integer wdy_pageSize,
			String wdy_pageOrder, String wdy_pageSort) throws Exception {

		Map<String, Object> param = new HashMap<>();
		entity.setIsDel(YesOrNo.NO.toString());
		param.put("entity", entity);
		if (StringUtils.isNotEmpty(wdy_pageOrder) && StringUtils.isNotEmpty(wdy_pageSort)) {
			param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		} else {
			param.put("orderByClause", "createDate DESC");
		}
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<Device> list = rootMapper.selectPageInfo(param);

		return new PageInfo<>(list);
	}

	@Transactional
	public Integer insertInfo(Device entity) {
		entity.setCreateDate(new Date());
		Integer result = super.insert(entity);
		entity.setSensorCount(0);
		return result;
	}

	public List<Device> selectListByUserId(Long userId) {
		Device record = new Device();
		record.setIsDel(YesOrNo.NO.toString());
		record.setUserId(userId);
		return mapper.select(record);
	}
	
	public HashMap<String, Object> selectInfoByPondsId(String terminalId, Long pondsId) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("terminalId", terminalId);
		param.put("pondsId", pondsId);
		return rootMapper.selectInfoByPondsId(param);
	}
	
}
