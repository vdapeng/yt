/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.param.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.model.param
 * 
 * @ClassName: Param
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-7-17 10:34:23
 */
@ApiModel(value = "参数实体")
@Table(name = "sys_param")
public class Param implements Serializable {

	private static final long serialVersionUID = 1L;

	// 参数编号
	@Id
	@ApiModelProperty(value = "参数编号", hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paramId;
	// 参数名称
	@ApiModelProperty(value = "参数名称")
	private String name;
	// 参数值
	@ApiModelProperty(value = "参数值")
	private String value;
	// 是否生效;y:是;n:否
	@ApiModelProperty(value = "是否生效;y:是;n:否")
	private String isEffect;
	// 备注
	@ApiModelProperty(value = "备注")
	private String remark;
	// 是否删除;y:是;n:否
	@ApiModelProperty(value = "是否删除;y:是;n:否", hidden = true)
	private String isDel;
	// 创建时间
	@ApiModelProperty(value = "创建时间", hidden = true)
	private String createOn;

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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