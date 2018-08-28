package com.vdaoyun.systemapi.web.service.sensor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
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
import com.vdaoyun.systemapi.mq.model.MQSensorRecordModel;
import com.vdaoyun.systemapi.web.model.echart.WLegend;
import com.vdaoyun.systemapi.web.model.echart.WLine;
import com.vdaoyun.systemapi.web.model.sensor.Sensor;
import com.vdaoyun.systemapi.web.model.sensor.SensorEchartParams;
import com.vdaoyun.systemapi.web.model.sensor.SensorRecordJson;
import com.vdaoyun.systemapi.web.service.ponds.PondsExService;
import com.vdaoyun.util.DateUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional
public class SensorRecordJsonService extends BaseService<SensorRecordJson> {
	
	public void removeByTer(String terminalId) {
		SensorRecordJson record = new SensorRecordJson();
		record.setTerminalId(terminalId);
		mapper.delete(record);
	}
	
	private static final DecimalFormat df = new DecimalFormat("#.00");
	
	@Autowired
	private SensorService sensorService;
	
	@SuppressWarnings("unchecked")
	public Option selectEchartData(SensorEchartParams params) {
		
		String formart = "HH:mm";
		if (params.getExpr() != 1) {
			formart = "MM月dd日/HH:mm";
		}
		
		Option option = new Option();
		option.yAxis(new ValueAxis());
//		option.title("运行记录");
		option.tooltip(Trigger.axis);
		// 取出该塘口所有探测器
		List<Sensor> sensors = sensorService.selectSensorsByPondsId(params.getPondsId());
		if (sensors == null || sensors.size() == 0) {
			option.title("暂无数据");
			option.title().left(X.center);
			option.title().top(Y.center);
			return option;
		}
		
		// 取出该塘口时间段内运行数据，默认取出24小时内的数据
		Example example = new Example(SensorRecordJson.class);
		
		if (params.getExpr() > 7) {
			params.setExpr(7);
		}
		
		example.createCriteria().andCondition("data_time >= NOW() - interval " + params.getExpr() * 24 + " hour").andEqualTo("terminalId", params.getTerminalId());
		List<SensorRecordJson> list = mapper.selectByExample(example);
		
		if (list == null || list.size() == 0) {
			option.title("暂无数据");
			option.title().left(X.center);
			option.title().top(Y.center);
			return option;
		}
		
		WLegend wLegend = new WLegend();
		
		
		HashMap<String, Line> aHashMap = new HashMap<>();
		Boolean isFirst = true;
		for (Sensor sensor : sensors) {
			WLine line = new WLine();
			line.name(sensor.getName());
			line.stack(sensor.getCode());
			line.smooth(true);
			line.setConnectNulls(true);
			aHashMap.put(sensor.getCode(), line);
			
			wLegend.data().add(sensor.getName());
			wLegend.selected(sensor.getName(), isFirst);
			
//			option.legend().data().add(sensor.getName());
//			option.legend().selected(sensor.getName(), isFirst);
			isFirst = false;
			
			WLine tLine = new WLine();
			tLine.name(sensor.getName() + "温度");
			tLine.stack(sensor.getCode() + "_T");
			tLine.smooth(true);
			tLine.setConnectNulls(true);
			aHashMap.put(sensor.getCode() + "_T", tLine);
//			option.legend().data().add(sensor.getName() + "温度");
//			option.legend().selected(sensor.getName() + "温度", isFirst);
			
			wLegend.data().add(sensor.getName() + "温度");
			wLegend.selected(sensor.getName() + "温度", isFirst);
		}
		
		wLegend.padding(5, 15, 10, 15);
		wLegend.x(X.left);
		wLegend.y(Y.bottom);
		wLegend.setType("scroll");
		wLegend.setPageIconSize(20);
		option.setLegend(wLegend);
//		option.legend().padding(5, 15, 10, 15);
//		option.legend().x(X.left);
//		option.legend().y(Y.bottom);
		
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setBoundaryGap(false);
		for (SensorRecordJson json : list) {
			JSONObject data = JSONObject.parseObject(json.getDataJson(), JSONObject.class, Feature.AllowArbitraryCommas);
			for (Sensor sensor : sensors) {
				if (data.containsKey(sensor.getCode())) {
					aHashMap.get(sensor.getCode()).data().add(df.format(data.getDoubleValue(sensor.getCode())));
				} else {
					aHashMap.get(sensor.getCode()).data().add("");
				}
				if (data.containsKey(sensor.getCode() + "_Temperature")) {
					aHashMap.get(sensor.getCode() + "_T").data().add(df.format( data.getDoubleValue(sensor.getCode() + "_Temperature")));
				} else if (data.containsKey(sensor.getCode() + "_T")) {
					aHashMap.get(sensor.getCode() + "_T").data().add(df.format( data.getDoubleValue(sensor.getCode() + "_T")));
				} else {
					aHashMap.get(sensor.getCode() + "_T").data().add("");
				}
			}
			xAxis.data().add(DateFormatUtils.format(json.getDataTime(), formart));
		}
		for (Sensor sensor : sensors) {
			option.series().add(aHashMap.get(sensor.getCode()));
			option.series().add(aHashMap.get(sensor.getCode() + "_T"));
		}
		option.xAxis().add(xAxis);
		option.grid().bottom(70);
		option.grid().top(30);
		
//		WBackgroundColor backgroundColor = new WBackgroundColor();
//		
//		backgroundColor.getColorStops().add(new ColorStop(0.0, "red"));
//		backgroundColor.getColorStops().add(new ColorStop(1.0, "blud"));
//		HashMap<String, Object> cHashMap = new HashMap<>();
//		backgroundColor.setGlobalCoord(true);
//		cHashMap.put("color", backgroundColor);
//		option.backgroundColor(cHashMap);
		return option;
	}
	
	@SuppressWarnings("unchecked")
	public Option selectDeviceEchartData(SensorEchartParams params) {
		
		String formart = "HH:mm";
		if (params.getExpr() != 1) {
			formart = "MM月dd日/HH:mm";
		}
		
		Option option = new Option();
		option.yAxis(new ValueAxis());
//		option.title("运行记录");
		option.tooltip(Trigger.axis);
		// 取出该塘口所有探测器
		List<Sensor> sensors = sensorService.selectSensorsByTerminalId(params.getTerminalId(), params.getSensorCode());
		if (sensors == null || sensors.size() == 0) {
			option.title("暂无数据");
			option.title().left(X.center);
			option.title().top(Y.center);
			return option;
		}
		
		// 取出该塘口时间段内运行数据
		Example example = new Example(SensorRecordJson.class);
		example.createCriteria().andCondition("data_time >= NOW() - interval " + params.getExpr() * 24 + " hour").andEqualTo("terminalId", params.getTerminalId());
		List<SensorRecordJson> list = mapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			option.title("暂无数据");
			option.title().left(X.center);
			option.title().top(Y.center);
			return option;
		}
		
		WLegend wLegend = new WLegend();
		HashMap<String, Line> aHashMap = new HashMap<>();
		Boolean isFirst = true;
		for (Sensor sensor : sensors) {
			WLine line = new WLine();
			line.name(sensor.getName());
			line.smooth(true);
			line.setConnectNulls(true);
			aHashMap.put(sensor.getCode(), line);
//			option.legend().data().add(sensor.getName());
//			option.legend().selected(sensor.getName(), isFirst);
			
			wLegend.data().add(sensor.getName());
			wLegend.selected(sensor.getName(), isFirst);
			
			isFirst = false;
			
			WLine tLine = new WLine();
			tLine.name(sensor.getName() + "温度");
			tLine.stack(sensor.getCode() + "_T");
			tLine.smooth(true);
			tLine.setConnectNulls(true);
			aHashMap.put(sensor.getCode() + "_T", tLine);
//			option.legend().data().add(sensor.getName() + "温度");
//			option.legend().selected(sensor.getName() + "温度", isFirst);
			
			wLegend.data().add(sensor.getName() + "温度");
			wLegend.selected(sensor.getName() + "温度", isFirst);
			
		}
//		option.legend().padding(5, 15, 0, 15);
//		option.legend().x(X.left);
//		option.legend().y(Y.bottom);
		wLegend.padding(5, 15, 10, 15);
		wLegend.x(X.left);
		wLegend.y(Y.bottom);
		wLegend.setType("scroll");
		wLegend.setPageIconSize(20);
		option.setLegend(wLegend);
		
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setBoundaryGap(false);
		for (SensorRecordJson json : list) {
			JSONObject data = JSONObject.parseObject(json.getDataJson(), JSONObject.class, Feature.AllowArbitraryCommas);
			for (Sensor sensor : sensors) {
				if (data.containsKey(sensor.getCode())) {
					aHashMap.get(sensor.getCode()).data().add(df.format(data.getDoubleValue(sensor.getCode())));
				} else {
					aHashMap.get(sensor.getCode()).data().add("");
				}
				if (data.containsKey(sensor.getCode() + "_Temperature")) {
					aHashMap.get(sensor.getCode() + "_T").data().add(df.format( data.getDoubleValue(sensor.getCode() + "_Temperature")));
				} else if (data.containsKey(sensor.getCode() + "_T")) {
					aHashMap.get(sensor.getCode() + "_T").data().add(df.format( data.getDoubleValue(sensor.getCode() + "_T")));
				} else {
					aHashMap.get(sensor.getCode() + "_T").data().add("");
				}
			}
			xAxis.data().add(DateFormatUtils.format(json.getDataTime(), formart));
		}
		for (Sensor sensor : sensors) {
			option.series().add(aHashMap.get(sensor.getCode()));
			option.series().add(aHashMap.get(sensor.getCode() + "_T"));
		}
		option.xAxis().add(xAxis);
		option.grid().bottom(80);
//		option.backgroundColor("rgb(128, 128, 128)");
		return option;
	}
	
	
	@Autowired
	private PondsExService pondsExService;
	
	@Async
	public void insertRecord(MQSensorRecordModel data) {
		String terminalId = data.getTerminalID();				// 设备编号
		Date postTime = new Date();		 						// 上传时间
		Integer SampeFrequency = data.getSampeFrequency();		// 数据采集频率
		List<HashMap<String, Object>> list = data.getData();	// 采集到的数据列表
		HashMap<String, Object> item = null;					// 临时变量，用于存储data
		List<SensorRecordJson> sensorRecordJsons = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			item = list.get(i);
			SensorRecordJson sensorRecordJson = new SensorRecordJson();
			sensorRecordJson.setDataTime(DateUtil.subtractDate(postTime, SampeFrequency / 60 * (list.size() - 1 - i)));	// 计算数据生产时间
			sensorRecordJson.setPostTime(postTime);
			sensorRecordJson.setTerminalId(terminalId);
			sensorRecordJson.setDataJson(JSON.toJSONString(item));
			sensorRecordJsons.add(sensorRecordJson);
		}
		batchInsert(sensorRecordJsons);
		pondsExService.version(null);
	}
	
	public PageInfo<SensorRecordJson> selectPageInfo(
			SensorRecordJson entity, 
			Integer wdy_pageNum, 
			Integer wdy_pageSize, 
			String wdy_pageOrder, 
			String wdy_pageSort
		) throws Exception {
			Example example = new Example(SensorRecordJson.class);
		 	Criteria criteria = example.createCriteria();
		 	criteria.andEqualTo(entity);
		 	if (entity.getBeginDate() != null) {
				criteria.andGreaterThanOrEqualTo("postTime", entity.getBeginDate());
			}
		 	if (entity.getFinishDate() != null) {
		 		criteria.andLessThanOrEqualTo("postTime", entity.getFinishDate());
			}
		 	example.setOrderByClause(wdy_pageOrder + " " + wdy_pageSort);
		 	PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		 	List<SensorRecordJson> list = mapper.selectByExample(example);
			return new PageInfo<>(list);
		}

}
