package com.vdaoyun.systemapi.web.model.sensor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.vdaoyun.common.api.enums.IConstant.YesOrNo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "传感器")
@Table(name = "sensor")
public class Sensor implements Serializable {
	
	public Sensor() {
		// TODO Auto-generated constructor stub
	}
	
	public Sensor(String isAlarm) {
		this.isAlarm = StringUtils.trimToEmpty(isAlarm).equalsIgnoreCase(YesOrNo.YES.toString()) ? YesOrNo.YES.toString() : YesOrNo.NO.toString();
	}
	
	public Sensor(String terminalId, Long pondsId, String code, String name) {
		this.terminalId = terminalId;
		this.pondsId = pondsId;
		this.code = code;
		this.name = name;
	}
	
	public Sensor(String terminalId, Long pondsId) {
		this.terminalId = terminalId;
		this.pondsId = pondsId;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	private Long version;

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
    @NotNull(message = "塘口编号不可以为空")
    @Column(name = "ponds_id")
    @ApiModelProperty(name = "pondsId", value = "" )
    private Long pondsId;
	/**
     * 塘口编号
     */
    @NotNull(message = "设备编号不可以为空")
    @Column(name = "terminal_id")
    @ApiModelProperty(name = "terminalId", value = "塘口编号" )
    private String terminalId;
	/**
     * 编码
     */
    @NotNull(message = "探测器编码不可以为空")
    @Column(name = "code")
    @ApiModelProperty(name = "code", value = "编码" )
    private String code;
	/**
     * 单位
     */
    @Column(name = "unit")
    @ApiModelProperty(name = "unit", value = "单位" )
    private String unit;
	/**
     * 名称
     */
    @NotNull(message = "探测器名称不可以为空")
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "名称" )
    private String name;
	/**
     * 创建时间
     */
    @Column(name = "create_date")
    @ApiModelProperty(name = "createDate", value = "创建时间", hidden = true )
    private Date createDate;
	/**
     * 创建人
     */
    @Column(name = "user_id")
    @ApiModelProperty(name = "userId", value = "创建人" )
    private Long userId;
    
    @Column(name = "is_enable")
    @ApiModelProperty(name = "isEnable", value = "是否启用，y：启用 n:未启用")
    private String isEnable;
    
    @Column(name = "is_alarm")
    @ApiModelProperty(name = "isAlarm", value = "是否报警，y：报警 n:未报警")
    private String isAlarm;
    
    @Column(name = "is_noti")
    @ApiModelProperty(name = "isNoti", value = "接收报警时是否通知，y：通知 n:不通知")
    private String isNoti;
    
  	public String getIsNoti() {
		return isNoti;
	}

	public void setIsNoti(String isNoti) {
		this.isNoti = isNoti;
	}

	public String getIsAlarm() {
		return isAlarm;
	}

	public void setIsAlarm(String isAlarm) {
		this.isAlarm = isAlarm;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
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
     * 获取
     *
     * @return remark - 
     */
    public Long getPondsId() {
        return pondsId;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setPondsId(Long pondsId) {
        this.pondsId = pondsId;
    }
  	/**
     * 获取塘口编号
     *
     * @return remark - 塘口编号
     */
    public String getTerminalId() {
        return terminalId;
    }

	/**
     * 设置塘口编号
     *
     * @param remark 塘口编号
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
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
     * 获取单位
     *
     * @return remark - 单位
     */
    public String getUnit() {
        return unit;
    }

	/**
     * 设置单位
     *
     * @param remark 单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
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
    
    
}