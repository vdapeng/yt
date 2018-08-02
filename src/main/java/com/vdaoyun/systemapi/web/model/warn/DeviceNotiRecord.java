package com.vdaoyun.systemapi.web.model.warn;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "异常通知记录")
@Table(name = "device_noti_record")
public class DeviceNotiRecord implements Serializable {

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
     * 报警记录表
     */
    @NotNull(message = "报警记录表 is required")
    @Column(name = "device_warn_record_id")
    @ApiModelProperty(name = "deviceWarnRecordId", value = "报警记录表" )
    private Long deviceWarnRecordId;
	/**
     * 通知内容
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "通知内容" )
    private String content;
	/**
     * 接收人user_id
     */
    @NotNull(message = "接收人user_id is required")
    @Column(name = "user_id")
    @ApiModelProperty(name = "userId", value = "接收人user_id" )
    private Long userId;
	/**
     * 是否已读
     */
    @Column(name = "is_read")
    @ApiModelProperty(name = "isRead", value = "是否已读" )
    private String isRead;
	/**
     * 创建时间
     */
    @Column(name = "create_date")
    @ApiModelProperty(name = "createDate", value = "创建时间" )
    private Date createDate;
	/**
     * 是否微信通知
     */
    @Column(name = "is_wx_noti")
    @ApiModelProperty(name = "isWxNoti", value = "是否微信通知" )
    private String isWxNoti;
	/**
     * 微信通知时间
     */
    @Column(name = "wx_noti_date")
    @ApiModelProperty(name = "wxNotiDate", value = "微信通知时间" )
    private Date wxNotiDate;
	/**
     * 是否短信通知
     */
    @Column(name = "is_sms_noti")
    @ApiModelProperty(name = "isSmsNoti", value = "是否短信通知" )
    private String isSmsNoti;
	/**
     * 短信通知时间
     */
    @Column(name = "sms_noti_date")
    @ApiModelProperty(name = "smsNotiDate", value = "短信通知时间" )
    private Date smsNotiDate;
	/**
     * 查看时间
     */
    @Column(name = "read_date")
    @ApiModelProperty(name = "readDate", value = "查看时间" )
    private Date readDate;
    
    @Column(name = "biz_id")
    @ApiModelProperty(name = "bizId", value = "发送回执ID,可根据该ID查询具体的发送状态" )
    private String bizId;
    
    @Column(name = "ponds_id")
    @ApiModelProperty(name = "pondsId", value = "塘口编号" )
    private Long pondsId;
    
    @Column(name = "code")
    @ApiModelProperty(name = "code", value = "探测器编码" )
    private String code;
    
    @Column(name = "terminal_id")
    @ApiModelProperty(name = "terminalId", value = "终端编号" )
    private String terminalId;
    
    @Column(name = "msg_Id")
    @ApiModelProperty(name = "msgId", value = "微信消息编号" )
    private String msgId;
    
  	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Long getPondsId() {
		return pondsId;
	}

	public void setPondsId(Long pondsId) {
		this.pondsId = pondsId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
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
     * 获取报警记录表
     *
     * @return remark - 报警记录表
     */
    public Long getDeviceWarnRecordId() {
        return deviceWarnRecordId;
    }

	/**
     * 设置报警记录表
     *
     * @param remark 报警记录表
     */
    public void setDeviceWarnRecordId(Long deviceWarnRecordId) {
        this.deviceWarnRecordId = deviceWarnRecordId;
    }
  	/**
     * 获取通知内容
     *
     * @return remark - 通知内容
     */
    public String getContent() {
        return content;
    }

	/**
     * 设置通知内容
     *
     * @param remark 通知内容
     */
    public void setContent(String content) {
        this.content = content;
    }
  	/**
     * 获取接收人user_id
     *
     * @return remark - 接收人user_id
     */
    public Long getUserId() {
        return userId;
    }

	/**
     * 设置接收人user_id
     *
     * @param remark 接收人user_id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
  	/**
     * 获取是否已读
     *
     * @return remark - 是否已读
     */
    public String getIsRead() {
        return isRead;
    }

	/**
     * 设置是否已读
     *
     * @param remark 是否已读
     */
    public void setIsRead(String isRead) {
        this.isRead = isRead;
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
     * 获取是否微信通知
     *
     * @return remark - 是否微信通知
     */
    public String getIsWxNoti() {
        return isWxNoti;
    }

	/**
     * 设置是否微信通知
     *
     * @param remark 是否微信通知
     */
    public void setIsWxNoti(String isWxNoti) {
        this.isWxNoti = isWxNoti;
    }
  	/**
     * 获取微信通知时间
     *
     * @return remark - 微信通知时间
     */
    public Date getWxNotiDate() {
        return wxNotiDate;
    }

	/**
     * 设置微信通知时间
     *
     * @param remark 微信通知时间
     */
    public void setWxNotiDate(Date wxNotiDate) {
        this.wxNotiDate = wxNotiDate;
    }
  	/**
     * 获取是否短信通知
     *
     * @return remark - 是否短信通知
     */
    public String getIsSmsNoti() {
        return isSmsNoti;
    }

	/**
     * 设置是否短信通知
     *
     * @param remark 是否短信通知
     */
    public void setIsSmsNoti(String isSmsNoti) {
        this.isSmsNoti = isSmsNoti;
    }
  	/**
     * 获取短信通知时间
     *
     * @return remark - 短信通知时间
     */
    public Date getSmsNotiDate() {
        return smsNotiDate;
    }

	/**
     * 设置短信通知时间
     *
     * @param remark 短信通知时间
     */
    public void setSmsNotiDate(Date smsNotiDate) {
        this.smsNotiDate = smsNotiDate;
    }
  	/**
     * 获取查看时间
     *
     * @return remark - 查看时间
     */
    public Date getReadDate() {
        return readDate;
    }

	/**
     * 设置查看时间
     *
     * @param remark 查看时间
     */
    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }
    
    
}