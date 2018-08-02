package com.vdaoyun.systemapi.web.service.ponds;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;


@Service
public class PondsExService extends BaseService<Ponds> {
	
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
			ponds.setId(id);
			ponds.setVersion(++version);
			mapper.updateByPrimaryKeySelective(ponds);
		}
		
	}

}
