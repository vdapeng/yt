package com.vdaoyun.systemapi.web.model.ponds;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class TransferParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "塘口编号")
	@NotNull(message = "塘口编号不可以为空")
	private Long pondsId;
	
	@ApiModelProperty(value = "接收人编号")
	@NotNull(message = "接收人编号不可以为空")
	private Long toUserId;
	
	@ApiModelProperty(value = "转让人编号/塘口所有人")
	@NotNull(message = "转让人编号不可以为空")
	private Long fromUserId;

	public Long getPondsId() {
		return pondsId;
	}

	public void setPondsId(Long pondsId) {
		this.pondsId = pondsId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	

}
