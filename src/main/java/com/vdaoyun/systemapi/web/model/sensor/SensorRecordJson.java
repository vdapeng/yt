package com.vdaoyun.systemapi.web.model.sensor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "传感器运行记录")
@Table(name = "sensor_record_json")
public class SensorRecordJson implements Serializable {

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
    @ApiModelProperty(name = "terminal_id", value = "设备编号" )
    private String terminalId;
	/**
     * 推送时间
     */
    @NotNull(message = "推送时间 is required")
    @Column(name = "post_time")
    @ApiModelProperty(name = "postTime", value = "推送时间" )
    private Date postTime;
    
	/**
     * 监测时间
     */
    @NotNull(message = "监测时间 is required")
    @Column(name = "data_time")
    @ApiModelProperty(name = "dataTime", value = "监测时间" )
    private Date dataTime;
	/**
     * json数据
     */
    @Column(name = "data_json")
    @ApiModelProperty(name = "dataJson", value = "json数据" )
    private String dataJson;
    

	public String getDataJson() {
		return dataJson;
	}

	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
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
  	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
     * 获取推送时间
     *
     * @return remark - 推送时间
     */
    public Date getPostTime() {
        return postTime;
    }

	/**
     * 设置推送时间
     *
     * @param remark 推送时间
     */
    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
  	/**
     * 获取监测时间
     *
     * @return remark - 监测时间
     */
    public Date getDataTime() {
        return dataTime;
    }

	/**
     * 设置监测时间
     *
     * @param remark 监测时间
     */
    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }
    
    @Transient
    @ApiModelProperty(value = "开始时间")
    private Date beginDate;
    
    @Transient
    @ApiModelProperty(value = "结束时间")
    private Date finishDate;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
    
    
}