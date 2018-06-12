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


@ApiModel(value = "异常类型")
@Table(name = "device_warn_type")
public class DeviceWarnType implements Serializable {

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
    @NotNull(message = "编码 is required")
    @Column(name = "code")
    @ApiModelProperty(name = "code", value = "编码" )
    private String code;
	/**
     * 名称
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "名称" )
    private String name;
	/**
     * 排序
     */
    @Column(name = "orderby")
    @ApiModelProperty(name = "orderby", value = "排序" )
    private Integer orderby;
	/**
     * 备注
     */
    @Column(name = "remark")
    @ApiModelProperty(name = "remark", value = "备注" )
    private String remark;
	/**
     * 通知方式 wx:仅微信通知 sms:仅短信通知 all:微信和sms通知
     */
    @Column(name = "noti_type")
    @ApiModelProperty(name = "notiType", value = "通知方式 wx:仅微信通知 sms:仅短信通知 all:微信和sms通知" )
    private String notiType;
	/**
     * 通知间隔 单位:分
     */
    @Column(name = "noti_interval")
    @ApiModelProperty(name = "notiInterval", value = "通知间隔 单位:分" )
    private Integer notiInterval;
    
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
     * 获取排序
     *
     * @return remark - 排序
     */
    public Integer getOrderby() {
        return orderby;
    }

	/**
     * 设置排序
     *
     * @param remark 排序
     */
    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }
  	/**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

	/**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
  	/**
     * 获取通知方式 wx:仅微信通知 sms:仅短信通知 all:微信和sms通知
     *
     * @return remark - 通知方式 wx:仅微信通知 sms:仅短信通知 all:微信和sms通知
     */
    public String getNotiType() {
        return notiType;
    }

	/**
     * 设置通知方式 wx:仅微信通知 sms:仅短信通知 all:微信和sms通知
     *
     * @param remark 通知方式 wx:仅微信通知 sms:仅短信通知 all:微信和sms通知
     */
    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }
  	/**
     * 获取通知间隔 单位:分
     *
     * @return remark - 通知间隔 单位:分
     */
    public Integer getNotiInterval() {
        return notiInterval;
    }

	/**
     * 设置通知间隔 单位:分
     *
     * @param remark 通知间隔 单位:分
     */
    public void setNotiInterval(Integer notiInterval) {
        this.notiInterval = notiInterval;
    }
    
    
}