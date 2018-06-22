/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.dict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.base.dict.dao.SysDictGroupMapper;
import com.vdaoyun.systemapi.web.base.dict.model.SysDictGroup;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.dictgroup.service
 * 
 * @ClassName: SysDictGroupService
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-8-23 8:57:36
 */
@Service
public class SysDictGroupService extends BaseService<SysDictGroup> {
	@Autowired
	private SysDictGroupMapper sysDictGroupMapper;

	public List<SysDictGroup> selectDictGroupListByGroupCode(String groupCode) {
		Example example = new Example(SysDictGroup.class);
		example.createCriteria().andEqualTo("groupCode", groupCode);
		return sysDictGroupMapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: selectSysDictGroupByGroupCode
	 * 
	 * @Description: 通过字典组编码查询对应的字典组信息，并返回一个字典组
	 * 
	 * @param groupCode
	 *            字典组编码
	 * @return SysDictGroup 对应的结果,null表示没有找到对应的结果
	 */
	public SysDictGroup selectDictGroupByGroupCode(String groupCode) {
		List<SysDictGroup> list = selectDictGroupListByGroupCode(groupCode);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * @Title: isExistByGroupCode
	 * 
	 * @Description: 字典组编码是否存在
	 * 
	 * @param groupCode
	 *            字典组编码
	 * @return boolean false:不存在， true:已经存在
	 */
	public boolean isExistByGroupCode(String groupCode) {
		if (selectDictGroupByGroupCode(groupCode) != null) {
			return true;
		}
		return false;
	}

}