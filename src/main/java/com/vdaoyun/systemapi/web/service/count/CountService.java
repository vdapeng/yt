package com.vdaoyun.systemapi.web.service.count;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.web.service.ponds.PondsService;
import com.vdaoyun.systemapi.web.service.user.UserService;
import com.vdaoyun.systemapi.web.service.warn.DeviceWarnRecordService;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.service.count
 *  
 * @ClassName: CountService
 *  
 * @Description: 数据相关统计
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年8月10日 下午2:42:20
 *
 */
@Service
public class CountService {
	
	@Autowired
	private PondsService pondsService;
	@Autowired
	private DeviceWarnRecordService deviceWarnRecordService;
	@Autowired
	private UserService userService;

	/**
	 * 
	 * @Title: 数量统计
	 *  
	 * @Description: 统计平台下，用户数量，塘口数量，报警条数和未审核用户数量
	 *  
	 * @return HashMap<String,Object>	统计集合
	 */
	public HashMap<String, Object> count() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("userNum", userService.count(null));
		result.put("pondsNum", pondsService.count());
		result.put("warnNum", deviceWarnRecordService.count());
		result.put("unAuditUserNum", userService.count(YesOrNo.NO.toString()));
		return result;
	}
	
}
