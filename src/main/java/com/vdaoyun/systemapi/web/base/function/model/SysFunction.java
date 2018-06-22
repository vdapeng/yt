/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.function.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.function.model
 * 
 * @ClassName: SysFunction
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 14:49:58
 */
@ApiModel(value = "菜单实体")
public class SysFunction implements Serializable {

	private static final long serialVersionUID = 1L;

	// 菜单编号
	@Id
	@ApiModelProperty(value = "菜单编号", hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer functionId;
	// 菜单类型编码
	@ApiModelProperty(value = "菜单类型编码")
	private String typeCode;
	// 父菜单编号
	@ApiModelProperty(value = "父菜单编号")
	private Integer parentFunctionId;
	// 菜单等级
	@ApiModelProperty(value = "菜单等级")
	private Integer functionLevel;
	// 菜单名称
	@ApiModelProperty(value = "菜单名称", required = true)
	@NotNull(message = "菜单名称为空")
	private String functionName;
	// 菜单编码
	@ApiModelProperty(value = "菜单编码")
	private String functionCode;
	// 菜单排序
	@ApiModelProperty(value = "菜单排序")
	private Integer orderby;
	// 菜单地址
	@ApiModelProperty(value = "菜单地址")
	private String functionUrl;
	// 字体图标
	@ApiModelProperty(value = "字体图标")
	private String fontIcon;
	// 是否显示;y:是;n:否
	@ApiModelProperty(value = "是否显示;y:是;n:否", hidden = true)
	private String isShow;
	// 是否删除;y:是;n:否
	@ApiModelProperty(value = "是否删除;y:是;n:否", hidden = true)
	private String isDel;
	// 创建时间
	@ApiModelProperty(value = "创建时间", hidden = true)
	private String createOn;
	// 菜单子列表
	@Transient
	@ApiModelProperty(value = "菜单子列表")
	private List<SysFunction> subList;

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getParentFunctionId() {
		return parentFunctionId;
	}

	public void setParentFunctionId(Integer parentFunctionId) {
		this.parentFunctionId = parentFunctionId;
	}

	public Integer getFunctionLevel() {
		return functionLevel;
	}

	public void setFunctionLevel(Integer functionLevel) {
		this.functionLevel = functionLevel;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public String getFontIcon() {
		return fontIcon;
	}

	public void setFontIcon(String fontIcon) {
		this.fontIcon = fontIcon;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
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

	public List<SysFunction> getSubList() {
		return subList;
	}

	public void setSubList(List<SysFunction> subList) {
		this.subList = subList;
	}

}