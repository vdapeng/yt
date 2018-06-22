/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.dict.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.dict.model
 * 
 * @ClassName: SysDict
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-8-23 9:01:04
 */
@ApiModel(value = "数据字典实体")
@Table(name = "sys_dict")
public class SysDict implements Serializable {

	private static final long serialVersionUID = 1L;

	// 字典编号
	@Id
	@ApiModelProperty(value = "详情编号", hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dictId;
	// 所属分组
	@ApiModelProperty(value = "所属分组")
	private Integer groupId;
	// 字典编码
	@ApiModelProperty(value = "字典编码")
	private String code;
	// 字典名称
	@ApiModelProperty(value = "字典名称")
	private String name;
	// 状态;0:显示;1:隐藏
	@ApiModelProperty(value = "状态;0:显示;1:隐藏")
	private String states;
	// 字典排序
	@ApiModelProperty(value = "字典排序")
	private Integer orderby;

	public Integer getDictId() {
		return dictId;
	}

	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

}