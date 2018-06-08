/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.role.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.role.model
 * 
 * @ClassName: SysRoleFunction
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:17:44
 */
@ApiModel(value = "角色功能联系实体")
public class SysRoleFunction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "角色功能联系编号", hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleFunctionId;
	// 功能id,关联t_s_function表id
	@ApiModelProperty(value = "功能id,关联t_s_function表id")
	private Integer functionId;
	// 角色id,关联t_s_role表id
	@ApiModelProperty(value = "角色id,关联t_s_role表id")
	private Integer roleId;

	public Integer getRoleFunctionId() {
		return roleFunctionId;
	}

	public void setRoleFunctionId(Integer roleFunctionId) {
		this.roleFunctionId = roleFunctionId;
	}

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}