package com.vdaoyun.systemapi.web.service.ponds;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.web.mapper.ponds.PondsMapper;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;
import com.vdaoyun.systemapi.web.model.ponds.TransferParam;
import com.vdaoyun.systemapi.web.model.sensor.Sensor;
import com.vdaoyun.systemapi.web.service.device.DeviceExService;
import com.vdaoyun.systemapi.web.service.sensor.SensorService;
import com.vdaoyun.systemapi.web.service.warn.DeviceNotiRecordExService;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class PondsService extends BaseService<Ponds> {
	
	@Override
	public int update(Ponds entity) {
		return mapper.updateByPrimaryKey(entity);
	}
	
	/**
	 * 
	 * @Title: setNullByTer
	 *  
	 * @Description: 当设备删除时，将绑定了该设备的塘口，设备编号设置为空
	 *  
	 * @param terminalId void
	 */
	public void setNullByTer(String terminalId) {
		Ponds record = new Ponds();
		record.setTerminalId("000000");
		Example example = new Example(Ponds.class);
		example.createCriteria().andEqualTo("terminalId", terminalId);
		mapper.updateByExampleSelective(record, example);
	}
	
	@Value("${data.ponds.remove}")
	private Boolean ISDELDATA = false;
	
	@Autowired
	private PondsShareRecordService pondsShareRecordService;
	@Autowired
	private DeviceNotiRecordExService deviceNotiRecordService;
	
	@Override
	public int delete(Object key) {
		if (ISDELDATA) {
			sensorService.removeByPondsId((Long)key);
			pondsShareRecordService.removeByPondsId((Long)key);
			deviceNotiRecordService.removeByPondsId((Long)key);
			return mapper.deleteByPrimaryKey(key);
		}
		Ponds entity = new Ponds();
		entity.setIsDel(YesOrNo.YES.toString());
		entity.setId((Long)key);
		return super.update(entity);
	} 
	
	
	@Override
	public int insert(Ponds entity) {
//		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	public List<Ponds> selectAll() {
		Ponds ponds = new Ponds();
		ponds.setIsDel(YesOrNo.NO.toString());
		PageHelper.startPage(1, 10);
		return mapper.select(ponds);
	}
	
	@Autowired
	private PondsMapper rootMapper;
	
	public PageInfo<HashMap<String, Object>> selectPageInfo(
		Ponds entity, 
		Integer wdy_pageNum, 
		Integer wdy_pageSize, 
		String wdy_pageOrder, 
		String wdy_pageSort
	) throws Exception {
		Map<String, Object> param = new HashMap<>();
		entity.setIsDel(YesOrNo.NO.toString());
		param.put("entity", entity);
		if (StringUtils.isNotEmpty(wdy_pageOrder) && StringUtils.isNotEmpty(wdy_pageSort)) {
			param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		} else {
			param.put("orderByClause", "createDate DESC");
		}
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<HashMap<String, Object>> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	public HashMap<String, Object> selectInfoByKey(Long key) {
		return rootMapper.selectInfoByKey(key);
	}
	
	
	@Transactional
	public Integer insertInfo(Ponds entity) {
		entity.setCreateDate(new Date());
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}
	
	public PageInfo<HashMap<String, Object>> selectPageInfoEx(
			Ponds entity, 
			Integer wdy_pageNum, 
			Integer wdy_pageSize, 
			String wdy_pageOrder, 
			String wdy_pageSort) {
		HashMap<String, Object> param = new HashMap<>();
		entity.setIsDel(YesOrNo.NO.toString());
		param.put("entity", entity);
		param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<HashMap<String, Object>> list = rootMapper.selectPageInfoEx(param);
		return new PageInfo<>(list);
	}
	
	public HashMap<String, Object> selectInfoEx(Long id) {
		return rootMapper.selectInfoEx(id);
	}
	
	@SuppressWarnings("unchecked")
	public PageInfo<HashMap<String, Object>> selectListJsonData(Ponds entity, Integer wdy_pageNum, Integer wdy_pageSize,
			String wdy_pageOrder, String wdy_pageSort) {
		HashMap<String, Object> param = new HashMap<>();
		entity.setIsDel(YesOrNo.NO.toString());
		param.put("entity", entity);
		param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<HashMap<String, Object>> list = rootMapper.selectListJsonData(param);
		for (HashMap<String, Object> hashMap : list) {
			Object sensorJsonData = hashMap.get("sensorJsonData");
			if (sensorJsonData != null) {
				((HashMap<String, Object>) sensorJsonData).put("data_json", JSON.parse(((HashMap<String, Object>) sensorJsonData).get("data_json").toString()));
			}
		}
		return new PageInfo<>(list);
	}
	
	/**
	 * 
	 * @Title: 查询塘口信息
	 *  
	 * @Description: 查询塘口信息并查询出塘口下所有传感器最新一条运行数据
	 *  
	 * @param id	塘口编号
	 * @return HashMap<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectInfoJsonData(Long id) {
		HashMap<String, Object> hashMap = rootMapper.selectInfoJsonData(id);
		Object sensorJsonData = hashMap.get("sensorJsonData");
		if (sensorJsonData != null) {
			((HashMap<String, Object>) sensorJsonData).put("data_json", JSON.parse(((HashMap<String, Object>) sensorJsonData).get("data_json").toString()));
		}
		return hashMap;
	}
	
	/**
	 * 
	 * @Title: 搜索塘口
	 *  
	 * @Description: 通过关键字搜索塘口信息
	 *  
	 * @param search		搜索关键字，可匹配名称和地址
	 * @return List<Ponds>	塘口列表信息
	 */
	public List<Ponds> search(String search) {
		if (StringUtils.isEmpty(search)) {
			PageHelper.startPage(1, 30);
		}
		Example example = new Example(Ponds.class);
		example.createCriteria().andLike("name", search + "%").andEqualTo("isDel", "n");
		example.or().andLike("address", "%" + search + "%").andEqualTo("isDel", "n");
		return mapper.selectByExample(example);
	}
	
	/**
	 * 
	 * @Title: 统计未删除的塘口数量
	 *  
	 * @Description: 统计未删除的塘口数量
	 *  
	 * @return 塘口数量
	 */
	public int count() {
		Ponds ponds = new Ponds();
		ponds.setIsDel(YesOrNo.NO.toString());
		return mapper.selectCount(ponds);
	}
	
	/**
	 * 
	 * @Title: 查询指定设备所绑定的塘口列表
	 *  
	 * @Description: 通过设备编号查询塘口列表
	 *  
	 * @param terminalId	设备编号
	 * @return List<Ponds>	塘口列表
	 */
	public List<Ponds> selectListByTerminalId(String terminalId) {
		Ponds ponds = new Ponds();
		ponds.setTerminalId(terminalId);
		return mapper.select(ponds);
	}
	
	
	@Autowired
	private SensorService sensorService;
	
	/**
	 * 
	 * @Title: 查询塘口信息
	 *  
	 * @Description: 通过设备编号和传感器编号查询塘口信息
	 *  
	 * @param terminalId		设备编号
	 * @param code				传感器编号
	 * @return Ponds			塘口信息
	 */
	public Ponds selectInfoByCodeAndTerminalId(String terminalId, String code) {
		Sensor sensor = sensorService.selectInfoByCodeAndTerminalId(terminalId, code);
		if (sensor == null) {
			return null;
		}
		return mapper.selectByPrimaryKey(sensor.getPondsId());
	}
	
	/**
	 * 
	 * @Title: 是否首页显示
	 *  
	 * @Description: 通过塘口编号更新塘口是否首页显示状态
	 *  
	 * @param id			塘口编号
	 * @param isHome 		当前状态；为y时，更改为n，非y时，更改为y
	 */
	public void isHome(Long id, String isHome) {
		Ponds ponds = new Ponds();
		ponds.setId(id);
		ponds.setIsHome(isHome.equalsIgnoreCase(YesOrNo.YES.toString()) ? YesOrNo.NO.toString() : YesOrNo.YES.toString());
		mapper.updateByPrimaryKey(ponds);
	}
	
	
	@Autowired
	private DeviceExService deviceService;
	
	/**
	 * 
	 * @Title: 塘口转让
	 *  
	 * @Description: 塘口转让操作逻辑如下:
	 *  
	 *  1. 更换该塘口终端所有人
	 *  2. 更换该塘口所有报警通知记录接收人
	 *  3. 删除该塘口所有共享信息
	 *  4. 更换塘口所有人
	 *  
	 * @param param
	 * @param terminalId 终端编号
	 */
	public void transfer(TransferParam param, String terminalId) {
		// 1. 更换该塘口终端所有人
		deviceService.transfer(terminalId, param.getToUserId());  
		// 2. 更换该塘口所有报警通知记录接收人
		deviceNotiRecordService.transfer(param);
		// 3. 删除该塘口所有共享信息
		pondsShareRecordService.removeByPondsId(param.getPondsId());
		// 4. 更换塘口所有人
		Ponds record = new Ponds();
		record.setId(param.getPondsId());
		record.setUserId(param.getToUserId());
		mapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 
	 * @Title: 小程序首页接口
	 *  
	 * @Description: 查询指定用户的塘口列表以及分享给我的塘口列表
	 *  	
	 * @param openid		用户openid
	 * @param wdy_pageNum	页码
	 * @param wdy_pageSize	每页条数
	 * @param wdy_pageOrder 排序字段
	 * @param wdy_pageSort	排序方式
	 * @return PageInfo<HashMap<String,Object>>
	 */
	public PageInfo<HashMap<String, Object>> selectMiniList(
			String openid, Integer wdy_pageNum, Integer wdy_pageSize,
			String wdy_pageOrder, String wdy_pageSort) {
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<HashMap<String, Object>> list = rootMapper.selectMiniList(openid, wdy_pageOrder + " " + wdy_pageSort);
		return new PageInfo<>(list);
	}
	
}
