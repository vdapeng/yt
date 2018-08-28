package com.vdaoyun.systemapi.web.service.ponds;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Service
public class PondsExService extends BaseService<Ponds> {
	
	/**
	 * 
	 * @Title: version
	 *  
	 * @Description: 更新version字段，更新二级缓存用
	 *  
	 * @param id void
	 */
	public void version(Long id) {
		Ponds record;
		if (id == null) {
			PageHelper.startPage(1, 1);
			List<Ponds> pondsList = mapper.select(new Ponds());
			if (!pondsList.isEmpty()) {
				record = pondsList.get(0);
			} else {
				return;
			}
		} else {
			record = mapper.selectByPrimaryKey(id);
		}
		if (record != null) {
			Long version = record.getVersion();
			Ponds ponds = new Ponds();
			ponds.setId(record.getId());
			ponds.setVersion(++version);
			mapper.updateByPrimaryKeySelective(ponds);
		}
	}

	public void alarm(String[] codes, String terminalId) {
		Example example = new Example(Ponds.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("terminalId", terminalId);
		Ponds record = new Ponds();
		record.setIsAlarm(YesOrNo.NO.toString());
		mapper.updateByExampleSelective(record, example);
	}
	
}
