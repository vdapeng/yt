/**     
 * 版权所有：2017 vdaoyun.com 武汉微道云信息科技有限公司
 * 项目名称：hsSystemApi     
 *  
 * 类描述：  
 * 类名称：com.vdaoyun.systemweb.common.bean.TreeBean       
 * 创建人：Chuny
 * 创建时间：2017年4月6日 下午4:08:22     
 * 修改人：  
 * 修改时间：2017年4月6日 下午4:08:22     
 * 修改备注：     
 * @version   V1.0      
 */  
package com.vdaoyun.systemapi.common.bean;

import java.util.List;

import javax.persistence.Transient;

/** 
 * @Package com.vdaoyun.systemweb.common.bean
 *  
 * @ClassName: TreeBean
 *  
 * @Description: 树形实体
 *  
 * @author MingJie (limingjie@vdaoyun.com)
 *  
 * @date 2017年4月6日 下午4:08:22
 *  
 */
public class TreeBean {

	// 主键编号
	private int id;
	// 父编号
	private int pid;
	// 名称
	private String name;
	// 字体图标
	private String fontIcon;
	// 是否选中
	private String isCheck;
	// 类型 （根据业务自行赋值）
	private String type;
	// 等级
	private int level;
	// 子列表
	@Transient
	private List<TreeBean> subList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFontIcon() {
		return fontIcon;
	}

	public void setFontIcon(String fontIcon) {
		this.fontIcon = fontIcon;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<TreeBean> getSubList() {
		return subList;
	}

	public void setSubList(List<TreeBean> subList) {
		this.subList = subList;
	}
	
}
