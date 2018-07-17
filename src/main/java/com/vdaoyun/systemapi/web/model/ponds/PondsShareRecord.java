package com.vdaoyun.systemapi.web.model.ponds;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "鱼塘分享记录")
@Table(name = "ponds_share_record")
public class PondsShareRecord implements Serializable {

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
    @ApiModelProperty(name = "id", value = "" )
    private Long id;
	/**
     * 
     */
    @Column(name = "openid")
    @ApiModelProperty(name = "openid", value = "被共享人openid" )
    private String openid;
	/**
     * 
     */
    @Column(name = "share_openid")
    @ApiModelProperty(name = "shareOpenid", value = "共享人openid" )
    private String shareOpenid;
	/**
     * 
     */
    @Column(name = "ponds_id")
    @ApiModelProperty(name = "pondsId", value = "共享塘口id" )
    private Long pondsId;
	/**
     * 
     */
    @Column(name = "create_date")
    @ApiModelProperty(name = "createDate", value = "" )
    private Date createDate;
    
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
    public String getOpenid() {
        return openid;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }
  	/**
     * 获取
     *
     * @return remark - 
     */
    public String getShareOpenid() {
        return shareOpenid;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setShareOpenid(String shareOpenid) {
        this.shareOpenid = shareOpenid;
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
     * 获取
     *
     * @return remark - 
     */
    public Date getCreateDate() {
        return createDate;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    
}