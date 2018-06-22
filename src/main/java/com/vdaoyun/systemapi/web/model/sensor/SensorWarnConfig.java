package com.vdaoyun.systemapi.web.model.sensor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "探测器报警配置表")
@Table(name = "sensor_warn_config")
public class SensorWarnConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(name = "id", value = "", hidden = true )
    private Long id;
	/**
     * 编码
     */
    @NotNull(message = "code is required")
    @Column(name = "code")
    @ApiModelProperty(name = "code", value = "编码" )
    private String code;
	/**
     * 上限值
     */
    @Column(name = "upper")
    @ApiModelProperty(name = "upper", value = "上限值" )
    private String upper;
	/**
     * 下限值
     */
    @Column(name = "lower")
    @ApiModelProperty(name = "lower", value = "下限值" )
    private String lower;
	/**
     * 备注说明
     */
    @Column(name = "remark")
    @ApiModelProperty(name = "remark", value = "备注说明" )
    private String remark;
    
    @Column(name = "unit")
    @ApiModelProperty(name = "unit", value = "单位" )
    private String unit;
    
  	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
     * 获取
     *
     * @return remark - 
     */
    public Long getId() {
        return id;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setId(Long id) {
        this.id = id;
    }
  	/**
     * 获取编码
     *
     * @return remark - 编码
     */
    public String getCode() {
        return code;
    }

	/**
     * 设置编码
     *
     * @param remark 编码
     */
    public void setCode(String code) {
        this.code = code;
    }
  	/**
     * 获取上限值
     *
     * @return remark - 上限值
     */
    public String getUpper() {
        return upper;
    }

	/**
     * 设置上限值
     *
     * @param remark 上限值
     */
    public void setUpper(String upper) {
        this.upper = upper;
    }
  	/**
     * 获取下限值
     *
     * @return remark - 下限值
     */
    public String getLower() {
        return lower;
    }

	/**
     * 设置下限值
     *
     * @param remark 下限值
     */
    public void setLower(String lower) {
        this.lower = lower;
    }
  	/**
     * 获取备注说明
     *
     * @return remark - 备注说明
     */
    public String getRemark() {
        return remark;
    }

	/**
     * 设置备注说明
     *
     * @param remark 备注说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}