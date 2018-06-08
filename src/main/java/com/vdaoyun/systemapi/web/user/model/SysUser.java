/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.user.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.user.model
 * 
 * @ClassName: SysUser
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:16:51
 */
@ApiModel(value = "用户实体")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	// 用户编号
	@Id
	@ApiModelProperty(value = "用户编号", hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	// 用户名
	@ApiModelProperty(value = "用户名", required = true)
	@NotNull(message = "用户名为空")
	private String username;
	// 密码
	@ApiModelProperty(value = "密码")
	private String password;
	// 真实姓名
	@ApiModelProperty(value = "真实姓名", required = true)
	@NotNull(message = "真实姓名为空")
	private String realname;
	// 手机号
	@ApiModelProperty(value = "手机号")
	private String mobile;
	// 状态;0:正常;1:禁用
	@ApiModelProperty(value = "状态;0:正常;1:禁用")
	private String status;
	// 是否删除;y:是;n:否
	@ApiModelProperty(value = "是否删除;y:是;n:否", hidden = true)
	private String isDel;
	// 排序
	@ApiModelProperty(value = "排序")
	private Integer orderno;
	// 创建时间
	@ApiModelProperty(value = "创建时间", hidden = true)
	private String createOn;
	// 角色编号
	@Transient
	@ApiModelProperty(value = "角色编号", required = true)
	@NotNull(message = "角色为空")
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}