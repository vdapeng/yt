package com.vdaoyun.systemapi.web.service.sensor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.vdaoyun.systemapi.web.mapper.sensor.SensorRecordMapper;
import com.vdaoyun.systemapi.web.model.sensor.SensorEchartParams;
import com.vdaoyun.systemapi.web.model.sensor.SensorRecord;

@Service
public class SensorRecordService extends BaseService<SensorRecord> {
	
//	@Override
//	public int delete(Object key) {
//		SensorRecord entity = new SensorRecord();
//		entity.setIsDel(YesOrNo.YES.toString());
//		entity.setId((Integer)key);
//		return super.update(entity);
//	} 
	
	@Override
	public int insert(SensorRecord entity) {
//		entity.setCreateDate(new Date());
		
		return super.insert(entity);
	}
	
	@SuppressWarnings("unchecked")
	public Option selectEchartData(SensorEchartParams params) {
		String formart = "HH:mm";
		Integer exprInt = params.getExpr();
		if (exprInt > 7) {
			params.setExpr(7);
		}
		if (exprInt != 1) {
			formart = "dd日/HH时";
		}
		
		Option option = new Option();
		option.yAxis(new ValueAxis());
		option.title("运行记录");
		option.tooltip(Trigger.axis);
		List<HashMap<String, Object>> result = rootMapper.selectEchartData(params);
		
		if (result == null || result.size() == 0) {
			option.title("暂无数据");
			option.title().left(X.center);
			option.title().top(Y.center);
		} else {
			option.title().left(X.left);
			option.title().top(15);
			HashMap<String, Object> item = null;
			CategoryAxis xAxis = new CategoryAxis();
			xAxis.setBoundaryGap(false);
			for (int i = 0; i < result.size(); i++) {
				item = result.get(i);
				Line line = new Line();
				line.name(item.getOrDefault("name", "").toString());
				line.stack(item.getOrDefault("code", "").toString());
				List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) item.get("datas");
				for (int j = 0; j < datas.size(); j++) {
					line.data().add(datas.get(j).get("value"));
					if (option.getxAxis() == null || option.getxAxis().size() <= 0) {
						xAxis.data().add(DateFormatUtils.format((Date)datas.get(j).get("dataTime"), formart));
					}
				}
				if (option.getxAxis() == null || option.getxAxis().size() <= 0) {
					option.xAxis().add(xAxis);
				}
				option.series().add(line);
				option.legend().data().add(item.getOrDefault("name", "").toString());
				option.legend().top(15);
			}
		}
		
		
		return option;
	}
	
	@Override
	public int batchInsert(List<SensorRecord> entityList) {
		return mapper.insertList(entityList);
	}
	
	@Autowired
	private SensorRecordMapper rootMapper;
	
	public PageInfo<SensorRecord> selectPageInfo(
		SensorRecord entity, 
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
		List<SensorRecord> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	
	@Transactional
	public Integer insertInfo(SensorRecord entity) {
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}

}
