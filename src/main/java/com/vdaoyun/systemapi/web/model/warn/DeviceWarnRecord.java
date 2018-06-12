package com.vdaoyun.systemapi.web.model.warn;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "异常记录")
@Table(name = "device_warn_record")
public class DeviceWarnRecord implements Serializable {

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
     * 设备编号
     */
    @NotNull(message = "设备编号 is required")
    @Column(name = "terminal_id")
    @ApiModelProperty(name = "terminalId", value = "设备编号" )
    private String terminalId;
	/**
     * 报警时间
     */
    @Column(name = "post_time")
    @ApiModelProperty(name = "postTime", value = "报警时间" )
    private Date postTime;
	/**
     * 传感器报警
     */
    @Column(name = "alaram_business")
    @ApiModelProperty(name = "alaramBusiness", value = "传感器报警" )
    private String alaramBusiness;
	/**
     * 设备报警
     */
    @Column(name = "alaram_equipment")
    @ApiModelProperty(name = "alaramEquipment", value = "设备报警" )
    private String alaramEquipment;
    
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
     * 获取设备编号
     *
     * @return remark - 设备编号
     */
    public String getTerminalId() {
        return terminalId;
    }

	/**
     * 设置设备编号
     *
     * @param remark 设备编号
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
  	/**
     * 获取报警时间
     *
     * @return remark - 报警时间
     */
    public Date getPostTime() {
        return postTime;
    }

	/**
     * 设置报警时间
     *
     * @param remark 报警时间
     */
    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
  	/**
     * 获取传感器报警
     *
     * @return remark - 传感器报警
     */
    public String getAlaramBusiness() {
        return alaramBusiness;
    }

	/**
     * 设置传感器报警
     *
     * @param remark 传感器报警
     */
    public void setAlaramBusiness(String alaramBusiness) {
        this.alaramBusiness = alaramBusiness;
    }
  	/**
     * 获取设备报警
     *
     * @return remark - 设备报警
     */
    public String getAlaramEquipment() {
        return alaramEquipment;
    }

	/**
     * 设置设备报警
     *
     * @param remark 设备报警
     */
    public void setAlaramEquipment(String alaramEquipment) {
        this.alaramEquipment = alaramEquipment;
    }
    
    
}