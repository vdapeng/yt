/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.dict.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.dictgroup.model
 * 
 * @ClassName: SysDictGroup
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-8-23 8:57:36
 */
@ApiModel(value = "字典组实体")
@Table(name = "sys_dict_group")
public class SysDictGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	// 字典组编号
	@Id
	@ApiModelProperty(value = "字典组编号", hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dictGroupId;
	// 字典组编码
	@ApiModelProperty(value = "字典组编码", required = true)
	@NotNull(message = "字典组编码不能为空")
	private String groupCode;
	// 字典组名称
	@ApiModelProperty(value = "字典组名称", required = true)
	@NotNull(message = "字典组名称")
	private String groupName;
	// 字典组描述
	@ApiModelProperty(value = "字典组描述")
	private String groupDesc;
	// 排序
	@ApiModelProperty(value = "排序")
	private Integer orderby;

	public Integer getDictGroupId() {
		return dictGroupId;
	}

	public void setDictGroupId(Integer dictGroupId) {
		this.dictGroupId = dictGroupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

}