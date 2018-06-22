/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.function.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.base.function.model.SysFunctionOperation;
import com.vdaoyun.systemapi.web.base.function.service.SysFunctionOperationService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.function.controller
 * 
 * @ClassName: SysFunctionController
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 20:58:54
 */
@RestController
@RequestMapping("/sys/function/operation")
public class SysFunctionOperationController {

	private static final Logger log = LoggerFactory.getLogger(SysFunctionOperationController.class);

	@Autowired
	private SysFunctionOperationService sysFunctionOperationService;

	@ApiOperation("查询列表")
	@RequestMapping(method = RequestMethod.GET)
	public AjaxJson select(HttpServletRequest request, @RequestParam Integer functionId) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setData(sysFunctionOperationService.selectList(functionId));
		return j;
	}

	@ApiOperation("新增")
	@RequestMapping(method = RequestMethod.POST)
	public AjaxJson insert(@RequestBody @Valid SysFunctionOperation sysFunctionOperation, Errors errors)
			throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		if (sysFunctionOperationService.insert(sysFunctionOperation) > 0) {
			j.setData(sysFunctionOperation.getOperationId());
			j.setMsg("新增成功");
			log.info("sys_function_operation表新增数据");
		} else {
			j.setMsg("新增失败");
		}
		return j;
	}

	@ApiOperation("更新")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(@PathVariable Integer id, @RequestBody @Valid SysFunctionOperation sysFunctionOperation,
			Errors errors) throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		sysFunctionOperationService.update(sysFunctionOperation);
		j.setMsg("更新成功");
		log.info("sys_function_operation表更新数据，operationId：" + id);
		return j;
	}

	@ApiOperation("删除")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(HttpServletRequest request, @PathVariable Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		if (sysFunctionOperationService.delete(id) > 0) {
			j.setMsg("删除成功");
			log.info("sys_function表删除数据，functionId：" + id);
		} else {
			j.setMsg("删除失败");
			j.setSuccess(false);
		}
		return j;
	}
}