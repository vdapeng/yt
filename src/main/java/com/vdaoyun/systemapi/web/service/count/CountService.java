package com.vdaoyun.systemapi.web.service.count;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.web.service.ponds.PondsService;
import com.vdaoyun.systemapi.web.service.user.UserService;
import com.vdaoyun.systemapi.web.service.warn.DeviceWarnRecordService;

@Service
public class CountService {
	
	@Autowired
	private PondsService pondsService;
	@Autowired
	private DeviceWarnRecordService deviceWarnRecordService;
	@Autowired
	private UserService userService;

	public HashMap<String, Object> count() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("userNum", userService.count(null));
		result.put("pondsNum", pondsService.count());
		result.put("warnNum", deviceWarnRecordService.count());
		result.put("unAuditUserNum", userService.count(YesOrNo.NO.toString()));
		return result;
	}
	
}
