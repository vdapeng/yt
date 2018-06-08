/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.role.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.role.model
 * 
 * @ClassName: SysRole
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:17:44
 */
@ApiModel(value = "角色实体")
@Table(name = "sys_role")
public class SysRole implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键id
	@Id
	@ApiModelProperty(value = "字典组编号", hidden = true)
	private Integer roleId;
	// 类型编码
	@ApiModelProperty(value = "类型编码", required = true)
	@NotNull(message = "类型编码为空")
	private String typeCode;
	// 编码
	@ApiModelProperty(value = "编码", required = true)
	@NotNull(message = "角色编码为空")
	private String roleCode;
	// 名称
	@ApiModelProperty(value = "角色名称")
	@NotNull(message = "角色名称为空")
	private String roleName;
	// 操作
	@ApiModelProperty(value = "操作")
	private String operation;
	// 排序
	@ApiModelProperty(value = "排序")
	private Integer orderby;
	// 是否删除;y:是;n:否
	@ApiModelProperty(value = "是否删除;y:是;n:否", hidden = true)
	private String isDel;
	// 创建时间
	@ApiModelProperty(value = "创建时间", hidden = true)
	private String createOn;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

}