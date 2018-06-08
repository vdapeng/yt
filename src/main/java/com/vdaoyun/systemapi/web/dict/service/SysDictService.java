/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.dict.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant;
import com.vdaoyun.systemapi.web.dict.dao.SysDictMapper;
import com.vdaoyun.systemapi.web.dict.model.SysDict;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.dict.service
 * 
 * @ClassName: SysDictService
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-8-23 9:01:04
 */
@Service
public class SysDictService extends BaseService<SysDict> {

	@Autowired
	private SysDictMapper sysDictMapper;

	/**
	 * 
	 * @Title: selectDictListByGroupId
	 * 
	 * @Description: 通过字典组编号获取字典列表
	 * 
	 * @param groupId
	 *            字典组编号
	 * @return List<SysDict>
	 */
	public List<SysDict> selectDictListByGroupId(Integer groupId) {
		Example example = new Example(SysDict.class);
		example.createCriteria().andEqualTo("groupId", groupId);
		List<SysDict> list = sysDictMapper.selectByExample(example);
		return list;
	}

	/**
	 * 
	 * @Title: isHasDictByGroupId
	 * 
	 * @Description: 判断指定的字典组是否存在字典信息
	 * 
	 * @param groupId
	 * @return boolean true,存在，false,不存在
	 */
	public boolean isHasDictByGroupId(Integer groupId) {
		List<SysDict> list = selectDictListByGroupId(groupId);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;

	}

	public List<SysDict> selectDictListByCode(String code, Integer groupId) {
		Example example = new Example(SysDict.class);
		example.createCriteria().andEqualTo("code", code).andEqualTo("groupId", groupId);
		return sysDictMapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: selectSysDictGroupByGroupCode
	 * 
	 * @Description: 通过字典编码查询对应的字典信息，并返回一个字典
	 * 
	 * @param groupCode
	 *            字典编码
	 * @return SysDictGroup 对应的结果,null表示没有找到对应的结果
	 */
	public SysDict selectDictByCode(String code, Integer groupId) {
		List<SysDict> list = selectDictListByCode(code, groupId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * @Title: isExistByGroupCode
	 * 
	 * @Description: 通一个分组字典编码是否存在
	 * 
	 * @param groupCode
	 *            字典编码
	 * @return boolean false:不存在， true:已经存在
	 */
	public boolean isExistByCode(String code, Integer groupId) {
		if (selectDictByCode(code, groupId) != null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: selectDictListByGroupCode
	 * 
	 * @Description: 通过字典组编码获取所有的字典信息
	 * 
	 * @param groupCode
	 * @return List<HashMap<String,Object>>
	 */
	public List<HashMap<String, Object>> selectDictListByGroupCode(String groupCode) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("groupCode", groupCode);
		param.put("states", IConstant.ShowOrHide.SHOW.toString());
		param.put("orderByClause", " a.orderby desc ");
		return super.selectTableList(param);
	}

	public List<HashMap<String, Object>> selectDictListByGroupId(String groupId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("groupId", groupId);
		param.put("orderByClause", " a.orderby desc ");
		return super.selectTableList(param);
	}

	/**
	 * 
	 * @Title: updateDictState
	 * 
	 * @Description: 修改数据字典的状态
	 * 
	 * @param dictId
	 * @param state
	 *            void
	 */
	private void updateDictState(Integer dictId, String state) {
		SysDict sysDict = new SysDict();
		sysDict.setDictId(dictId);
		sysDict.setStates(state);
		super.update(sysDict);
	}

	/**
	 * 
	 * @Title: showDict
	 * 
	 * @Description: 显示数据字典
	 * 
	 * @param dictId
	 *            void
	 */
	public void updateShowDict(Integer dictId) {
		updateDictState(dictId, IConstant.ShowOrHide.SHOW.toString());
	}

	/**
	 * 
	 * @Title: hideDict
	 * 
	 * @Description: 隐藏数据字典
	 * 
	 * @param dictId
	 *            void
	 */
	public void updateHideDict(Integer dictId) {
		updateDictState(dictId, IConstant.ShowOrHide.HIDE.toString());
	}

	public void changeDict(Integer dictId) {
		SysDict sysDict = super.selectByPrimaryKey(dictId);
		String state = sysDict.getStates();
		if (state.equals(IConstant.ShowOrHide.SHOW.toString())) {
			state = IConstant.ShowOrHide.HIDE.toString();
		} else {
			state = IConstant.ShowOrHide.SHOW.toString();
		}
		updateDictState(dictId, state);
	}

}