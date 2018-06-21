package com.vdaoyun.systemapi.web.model.user;

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


@ApiModel(value = "用户")
@Table(name = "user")
public class User implements Serializable {

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
     * 昵称
     */
    @Column(name = "nickname")
    @ApiModelProperty(name = "nickname", value = "昵称" )
    private String nickname;
	/**
     * 真实姓名
     */
    @Column(name = "true_name")
    @ApiModelProperty(name = "trueName", value = "真实姓名" )
    private String trueName;
	/**
     * 手机号码
     */
    @NotNull(message = "手机号码 is required")
    @Column(name = "mobile")
    @ApiModelProperty(name = "mobile", value = "手机号码" )
    private String mobile;
	/**
     * 
     */
    @Column(name = "password")
    @ApiModelProperty(name = "password", value = "" )
    private String password;
	/**
     * 微信openid
     */
    @NotNull(message = "微信openid is required")
    @Column(name = "openid")
    @ApiModelProperty(name = "openid", value = "微信openid" )
    private String openid;
	/**
     * 头像
     */
    @Column(name = "avatar_url")
    @ApiModelProperty(name = "avatarUrl", value = "头像" )
    private String avatarUrl;
	/**
     * 1: 男 2：女 0：未知
     */
    @Column(name = "gender")
    @ApiModelProperty(name = "gender", value = "1: 男 2：女 0：未知" )
    private String gender;
	/**
     * 
     */
    @Column(name = "create_date")
    @ApiModelProperty(name = "createDate", value = "" )
    private Date createDate;
	/**
     * 
     */
    @Column(name = "last_update_date")
    @ApiModelProperty(name = "lastUpdateDate", value = "" )
    private Date lastUpdateDate;
	/**
     * 
     */
    @Column(name = "country")
    @ApiModelProperty(name = "country", value = "" )
    private String country;
	/**
     * 
     */
    @Column(name = "province")
    @ApiModelProperty(name = "province", value = "" )
    private String province;
	/**
     * 
     */
    @Column(name = "city")
    @ApiModelProperty(name = "city", value = "" )
    private String city;
    
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
     * 获取昵称
     *
     * @return remark - 昵称
     */
    public String getNickname() {
        return nickname;
    }

	/**
     * 设置昵称
     *
     * @param remark 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
  	/**
     * 获取真实姓名
     *
     * @return remark - 真实姓名
     */
    public String getTrueName() {
        return trueName;
    }

	/**
     * 设置真实姓名
     *
     * @param remark 真实姓名
     */
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
  	/**
     * 获取手机号码
     *
     * @return remark - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

	/**
     * 设置手机号码
     *
     * @param remark 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
  	/**
     * 获取
     *
     * @return remark - 
     */
    public String getPassword() {
        return password;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setPassword(String password) {
        this.password = password;
    }
  	/**
     * 获取微信openid
     *
     * @return remark - 微信openid
     */
    public String getOpenid() {
        return openid;
    }

	/**
     * 设置微信openid
     *
     * @param remark 微信openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }
  	/**
     * 获取头像
     *
     * @return remark - 头像
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

	/**
     * 设置头像
     *
     * @param remark 头像
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
  	/**
     * 获取1: 男 2：女 0：未知
     *
     * @return remark - 1: 男 2：女 0：未知
     */
    public String getGender() {
        return gender;
    }

	/**
     * 设置1: 男 2：女 0：未知
     *
     * @param remark 1: 男 2：女 0：未知
     */
    public void setGender(String gender) {
        this.gender = gender;
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
  	/**
     * 获取
     *
     * @return remark - 
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
  	/**
     * 获取
     *
     * @return remark - 
     */
    public String getCountry() {
        return country;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setCountry(String country) {
        this.country = country;
    }
  	/**
     * 获取
     *
     * @return remark - 
     */
    public String getProvince() {
        return province;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setProvince(String province) {
        this.province = province;
    }
  	/**
     * 获取
     *
     * @return remark - 
     */
    public String getCity() {
        return city;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setCity(String city) {
        this.city = city;
    }
    
    
}