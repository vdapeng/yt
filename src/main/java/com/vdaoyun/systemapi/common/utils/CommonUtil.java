/**     
 * 版权所有：2017 vdaoyun.com 武汉微道云信息科技有限公司
 * 项目名称：hsSystemApi     
 *  
 * 类描述：  
 * 类名称：com.vdaoyun.systemweb.common.utils.CommonUtil       
 * 创建人：Chuny
 * 创建时间：2017年4月6日 下午6:15:58     
 * 修改人：  
 * 修改时间：2017年4月6日 下午6:15:58     
 * 修改备注：     
 * @version   V1.0      
 */  
package com.vdaoyun.systemapi.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.vdaoyun.systemapi.common.bean.TreeBean;

/** 
 * @Package com.vdaoyun.systemweb.common.utils
 *  
 * @ClassName: CommonUtil
 *  
 * @Description: 公用工具类
 *  
 * @author MingJie (limingjie@vdaoyun.com)
 *  
 * @date 2017年4月6日 下午6:15:58
 *  
 */
public class CommonUtil {

	/**
	 * 
	 * @Title: getTreeList
	 *  
	 * @Description: 获取树形列表
	 *  
	 * @param list
	 * @return List<TreeBean>
	 */
	public static List<TreeBean> getTreeList(List<TreeBean> list){
		List<TreeBean> treeList = new ArrayList<TreeBean>();
		for (TreeBean treeBean : list) {
			int level = treeBean.getLevel();
			if(level == 0){
				treeList.add(treeBean);
			} else if(level == 1){
				for (TreeBean treeBean2 : treeList) {
					if(treeBean.getPid() == treeBean2.getId()){
						if(treeBean2.getSubList() == null){
							treeBean2.setSubList(new ArrayList<TreeBean>());
						}
						treeBean2.getSubList().add(treeBean);
						break;
					}
				}
			} else if(level == 2){
				for (TreeBean treeBean2 : treeList) {
					if(treeBean2.getSubList() != null){
						for (TreeBean treeBean3 : treeBean2.getSubList()) {
							if(treeBean.getPid() == treeBean3.getId()){
								if(treeBean3.getSubList() == null){
									treeBean3.setSubList(new ArrayList<TreeBean>());
								}
								treeBean3.getSubList().add(treeBean);
								break;
							}
						}
					}
				}
			}
		}
		return treeList;
	}
}
