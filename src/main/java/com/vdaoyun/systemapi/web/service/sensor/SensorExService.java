package com.vdaoyun.systemapi.web.service.sensor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.model.sensor.Sensor;

@Service
public class SensorExService extends BaseService<Sensor> {
	
	/**
	 * 
	 * @Title: 清除缓存
	 *  
	 * @Description: 修改传感器相关数据时，刷新缓存
	 * 
	 * 以下情况需要手动刷新缓存:
	 * 		
	 * 		1. 传感器报警
	 * 		2. 添加传感器类型
	 * 		3. 传感器配置
	 * 
	 * @param id 传感器编号，可为空，为空时更新最新一条数据
	 */
	public void version(Long id) {
		Sensor record;
		if (id == null) {
			PageHelper.startPage(1, 1);
			List<Sensor> sensorList = mapper.select(new Sensor());
			if (!sensorList.isEmpty()) {
				record = sensorList.get(0);
			} else {
				return;
			}
		} else {
			record = mapper.selectByPrimaryKey(id);
		}
		if (record != null) {
			Long version = record.getVersion();
			Sensor sensor = new Sensor();
			sensor.setId(record.getId());
			sensor.setVersion(++version);
			mapper.updateByPrimaryKeySelective(sensor);
		}
	}

}
