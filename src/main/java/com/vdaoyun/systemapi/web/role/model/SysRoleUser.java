/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.role.model;

import java.io.Serializable;

import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.role.model
 * 
 * @ClassName: SysRoleUser
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:17:44
 */
@ApiModel(value = "角色用户实体")
public class SysRoleUser implements Serializable {

	private static final long serialVersionUID = 1L;

	// 角色用户编号
	@Id
	@ApiModelProperty(value = "角色用户编号", hidden = true)
	private Integer roleUserId;
	// 主键id
	@ApiModelProperty(value = "主键id")
	private Integer roleId;
	// 用户编号
	@ApiModelProperty(value = "用户编号")
	private Integer userId;

	public Integer getRoleUserId() {
		return roleUserId;
	}

	public void setRoleUserId(Integer roleUserId) {
		this.roleUserId = roleUserId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}