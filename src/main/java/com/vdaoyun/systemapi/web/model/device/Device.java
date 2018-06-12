package com.vdaoyun.systemapi.web.model.device;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "设备信息")
@Table(name = "device")
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 设备编号
     */
	@Id
    @NotNull(message = "设备编号 is required")
    @Column(name = "terminal_id")
    @ApiModelProperty(name = "terminalId", value = "设备编号" )
    private String terminalId;
	/**
     * 名称
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "名称" )
    private String name;
	/**
     * 精度
     */
    @Column(name = "longitude")
    @ApiModelProperty(name = "longitude", value = "精度" )
    private String longitude;
	/**
     * 纬度
     */
    @Column(name = "latitude")
    @ApiModelProperty(name = "latitude", value = "纬度" )
    private String latitude;
	/**
     * 创建时间
     */
    @Column(name = "create_date")
    @ApiModelProperty(name = "createDate", value = "创建时间", hidden = true )
    private Date createDate;
	/**
     * 创建人
     */
    @NotNull(message = "创建人 is required")
    @Column(name = "user_id")
    @ApiModelProperty(name = "userId", value = "创建人" )
    private Long userId;
    
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
     * 获取名称
     *
     * @return remark - 名称
     */
    public String getName() {
        return name;
    }

	/**
     * 设置名称
     *
     * @param remark 名称
     */
    public void setName(String name) {
        this.name = name;
    }
  	/**
     * 获取精度
     *
     * @return remark - 精度
     */
    public String getLongitude() {
        return longitude;
    }

	/**
     * 设置精度
     *
     * @param remark 精度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
  	/**
     * 获取纬度
     *
     * @return remark - 纬度
     */
    public String getLatitude() {
        return latitude;
    }

	/**
     * 设置纬度
     *
     * @param remark 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
  	/**
     * 获取创建时间
     *
     * @return remark - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

	/**
     * 设置创建时间
     *
     * @param remark 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
  	/**
     * 获取创建人
     *
     * @return remark - 创建人
     */
    public Long getUserId() {
        return userId;
    }

	/**
     * 设置创建人
     *
     * @param remark 创建人
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Transient
    @ApiModelProperty(hidden = true)
    private Integer sensorCount;

	public Integer getSensorCount() {
		return sensorCount;
	}

	public void setSensorCount(Integer sensorCount) {
		this.sensorCount = sensorCount;
	}
    
    
    
}