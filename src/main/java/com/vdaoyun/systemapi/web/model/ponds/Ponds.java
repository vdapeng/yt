package com.vdaoyun.systemapi.web.model.ponds;

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


@ApiModel(value = "塘口信息表")
@Table(name = "ponds")
public class Ponds implements Serializable {

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
     * 名称
     */
    @NotNull(message = "name is required")
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "名称" )
    private String name;
	/**
     * 地址
     */
    @Column(name = "address")
    @ApiModelProperty(name = "address", value = "地址" )
    private String address;
	/**
     * 纬度，浮点数，范围为-90~90，负数表示南纬
     */
    @Column(name = "latitude")
    @ApiModelProperty(name = "latitude", value = "纬度，浮点数，范围为-90~90，负数表示南纬" )
    private String latitude;
	/**
     * 经度，浮点数，范围为-180~180，负数表示西经
     */
    @Column(name = "longitude")
    @ApiModelProperty(name = "longitude", value = "经度，浮点数，范围为-180~180，负数表示西经" )
    private String longitude;
	/**
     * 创建人
     */
    @NotNull(message = "userId is required")
    @Column(name = "user_id")
    @ApiModelProperty(name = "userId", value = "用户编号" )
    private Long userId;
    
    @Transient
    @ApiModelProperty(value = "小程序openid")
    private String openid;
    
    @Column(name = "is_del")
    @ApiModelProperty(name = "is_del", value = "是否删除" )
    private String isDel;
    
    public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	private Long version;
    
    public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	/**
     * 设备编号
     */
    @Column(name = "terminal_id")
    @ApiModelProperty(name = "terminal_id", value = "设备编号" )
    private String terminalId;
    
	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
     * 创建时间
     */
    @Column(name = "create_date")
    @ApiModelProperty(name = "createDate", value = "创建时间", hidden = true )
    private Date createDate;
	/**
     * 
     */
    @Column(name = "activated")
    @ApiModelProperty(name = "activated", value = "", hidden = true )
    private String activated;
	/**
     * 
     */
    @Column(name = "enabled")
    @ApiModelProperty(name = "enabled", value = "", hidden = true )
    private String enabled;
    
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
     * 获取地址
     *
     * @return remark - 地址
     */
    public String getAddress() {
        return address;
    }

	/**
     * 设置地址
     *
     * @param remark 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
  	/**
     * 获取纬度，浮点数，范围为-90~90，负数表示南纬
     *
     * @return remark - 纬度，浮点数，范围为-90~90，负数表示南纬
     */
    public String getLatitude() {
        return latitude;
    }

	/**
     * 设置纬度，浮点数，范围为-90~90，负数表示南纬
     *
     * @param remark 纬度，浮点数，范围为-90~90，负数表示南纬
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
  	/**
     * 获取经度，浮点数，范围为-180~180，负数表示西经
     *
     * @return remark - 经度，浮点数，范围为-180~180，负数表示西经
     */
    public String getLongitude() {
        return longitude;
    }

	/**
     * 设置经度，浮点数，范围为-180~180，负数表示西经
     *
     * @param remark 经度，浮点数，范围为-180~180，负数表示西经
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
     * 获取
     *
     * @return remark - 
     */
    public String getActivated() {
        return activated;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setActivated(String activated) {
        this.activated = activated;
    }
  	/**
     * 获取
     *
     * @return remark - 
     */
    public String getEnabled() {
        return enabled;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
    
    
    // 2018-08-10 新增字段
    @Column(name = "is_home")
    @ApiModelProperty(value = "是否首页显示")
    private String isHome;

	public String getIsHome() {
		return isHome;
	}

	public void setIsHome(String isHome) {
		this.isHome = isHome;
	}
	
	@Column(name = "is_alarm")
    @ApiModelProperty(name = "isAlarm", value = "是否报警，y：报警 n:未报警")
    private String isAlarm;

	public String getIsAlarm() {
		return isAlarm;
	}

	public void setIsAlarm(String isAlarm) {
		this.isAlarm = isAlarm;
	}
    
	
}