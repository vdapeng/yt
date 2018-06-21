package com.vdaoyun.systemapi.web.model.warn;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "异常配置")
@Table(name = "device_warn_config")
public class DeviceWarnConfig implements Serializable {

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
     * 
     */
    @NotNull(message = " is required")
    @Column(name = "terminal_id")
    @ApiModelProperty(name = "terminalId", value = "" )
    private String terminalId;
	/**
     * 报警类型编号
     */
    @Column(name = "device_warn_type_id")
    @ApiModelProperty(name = "deviceWarnTypeId", value = "报警类型编号" )
    private Long deviceWarnTypeId;
	/**
     * 是否启用
     */
    @Column(name = "is_enabled")
    @ApiModelProperty(name = "isEnabled", value = "是否启用" )
    private String isEnabled;
    
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
     * 获取
     *
     * @return remark - 
     */
    public String getTerminalId() {
        return terminalId;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
  	/**
     * 获取报警类型编号
     *
     * @return remark - 报警类型编号
     */
    public Long getDeviceWarnTypeId() {
        return deviceWarnTypeId;
    }

	/**
     * 设置报警类型编号
     *
     * @param remark 报警类型编号
     */
    public void setDeviceWarnTypeId(Long deviceWarnTypeId) {
        this.deviceWarnTypeId = deviceWarnTypeId;
    }
  	/**
     * 获取是否启用
     *
     * @return remark - 是否启用
     */
    public String getIsEnabled() {
        return isEnabled;
    }

	/**
     * 设置是否启用
     *
     * @param remark 是否启用
     */
    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }
    
    
}