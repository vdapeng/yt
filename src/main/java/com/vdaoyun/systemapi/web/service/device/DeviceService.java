package com.vdaoyun.systemapi.web.service.device;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.mapper.device.DeviceMapper;
import com.vdaoyun.systemapi.web.model.device.Device;

@Transactional
@Service
@SuppressWarnings("unchecked")
public class DeviceService extends BaseService<Device> {
	
	private static final Logger log = LoggerFactory.getLogger(DeviceService.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public int delete(Object key) {
		Device entity = new Device();
		entity.setTerminalId((String) key);
		int result = mapper.delete(entity);
		redisTemplate.delete("DEVICE_LIST");
		return result;
	} 
	
	@Override
	public int insert(Device entity) {
		entity.setCreateDate(new Date());
		int result = super.insert(entity);
		return result;
	}
	
	@Autowired
	private DeviceMapper rootMapper;
	
	public PageInfo<Device> selectPageInfo(
		Device entity, 
		Integer wdy_pageNum, 
		Integer wdy_pageSize, 
		String wdy_pageOrder, 
		String wdy_pageSort
	) throws Exception {
		
		if (redisTemplate.hasKey("DEVICE_LIST")) {
			Long start = (long) ((wdy_pageNum - 1) * wdy_pageSize);
			List<Device> list = redisTemplate.opsForList().range("DEVICE_LIST", start, start + (wdy_pageSize - 1));
			return new PageInfo<>(list);
		}
		
		Map<String, Object> param = new HashMap<>();
		param.put("entity", entity);
		if (StringUtils.isNotEmpty(wdy_pageOrder) && StringUtils.isNotEmpty(wdy_pageSort)) {
			param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		} else {
			param.put("orderByClause", "createDate DESC");
		}
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<Device> list = rootMapper.selectPageInfo(param);
		
		Long result = redisTemplate.opsForList().leftPushAll("DEVICE_LIST", list);
		log.info(result + "===================================");
		
		return new PageInfo<>(list);
	}
	
	@Transactional
	public Integer insertInfo(Device entity) {
		entity.setCreateDate(new Date());
		Integer result = super.insert(entity);
		entity.setSensorCount(0);
		Long resultEx = redisTemplate.opsForList().leftPush("DEVICE_LIST", entity);
		log.info(resultEx + "===================================");
		return result;
	}

}
