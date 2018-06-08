/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.function.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.function.model
 * 
 * @ClassName: SysFunctionOperation
 * 
 * @Description: 
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:16:24
 */
public class SysFunctionOperation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer operationId;
	// 功能id,关联t_s_function表id
	@NotNull(message = "菜单编号为空")
    private Integer functionId;
	// 编码
	@NotNull(message = "操作编码为空")
    private String operationCode;
	// 图标
	@NotNull(message = "操作图标为空")
    private String operationIcon;
	// 名称
	@NotNull(message = "操作名称为空")
    private String operationName;
 
    public Integer getOperationId() {
        return operationId;
    }
 
    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getFunctionId() {
        return functionId;
    }
 
    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getOperationCode() {
        return operationCode;
    }
 
    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getOperationIcon() {
        return operationIcon;
    }
 
    public void setOperationIcon(String operationIcon) {
        this.operationIcon = operationIcon;
    }

    public String getOperationName() {
        return operationName;
    }
 
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

}