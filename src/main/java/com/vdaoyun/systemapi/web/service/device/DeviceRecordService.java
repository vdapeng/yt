package com.vdaoyun.systemapi.web.service.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.series.Line;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.mapper.device.DeviceRecordMapper;
import com.vdaoyun.systemapi.web.model.device.DeviceRecord;

@Service
public class DeviceRecordService extends BaseService<DeviceRecord> {
	
//	@Override
//	public int delete(Object key) {
//		DeviceRecord entity = new DeviceRecord();
//		entity.setIsDel(YesOrNo.YES.toString());
//		entity.setId((Integer)key);
//		return super.update(entity);
//	} 
	
	@Override
	@Async
	public int insert(DeviceRecord entity) {
//		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	@Autowired
	private DeviceRecordMapper rootMapper;
	
	public PageInfo<DeviceRecord> selectPageInfo(
		DeviceRecord entity, 
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
		List<DeviceRecord> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	
	@Transactional
	public Integer insertInfo(DeviceRecord entity) {
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Option selectEchartData(HashMap<String, Object> param) {
		String formart = "HH:mm";
		Option option = new Option();
		option.yAxis(new ValueAxis());
		option.title("运行记录");
		option.tooltip(Trigger.axis);
		List<DeviceRecord> list = rootMapper.selectEchartData(param);
		if (list == null || list.size() == 0) {
			option.title("暂无数据");
			option.title().left(X.center);
			option.title().top(Y.center);
		} else {
			option.title().left(X.left);
			option.title().top(15);
			Line solarVoltageLine = new Line();
			solarVoltageLine.name("太阳能电压").stack("太阳能电压");
			Line batteryVoltageLine = new Line();
			batteryVoltageLine.name("电池电压").stack("电池电压");
			Line batteryTemperatureLine = new Line();
			batteryTemperatureLine.name("电池温度").stack("电池温度");
			Line systemTemperatureLine = new Line();
			systemTemperatureLine.name("系统温度").stack("系统温度");
			Line batteryLevelLine = new Line();
			batteryLevelLine.name("电池电量").stack("电池电量");
			Line shellTemperature = new Line();
			shellTemperature.name("表面温度").stack("表面温度");
			CategoryAxis xAxis = new CategoryAxis();
			xAxis.setBoundaryGap(false);
			DeviceRecord item;
			for (int i = 0; i < list.size(); i++) {
				item = list.get(i);
				solarVoltageLine.data().add(item.getSolarVoltage());
				batteryVoltageLine.data().add(item.getBatteryVoltage());
				batteryTemperatureLine.data().add(item.getBatteryTemperature());
				systemTemperatureLine.data().add(item.getSystemTemperature());
				batteryLevelLine.data().add(item.getBatteryLevel());
				shellTemperature.data().add(item.getShellTemperature());
				xAxis.data().add(DateFormatUtils.format(item.getPostTime(), formart));
			}
			option.xAxis().add(xAxis);
			option.series(solarVoltageLine, batteryVoltageLine, batteryTemperatureLine, systemTemperatureLine, batteryLevelLine, shellTemperature);
			option.legend().data("太阳能电压", "电池电压", "电池温度", "系统温度", "电池电量", "表面温度");
			option.legend().top(15);
		}
		return option;
	}
	
}
