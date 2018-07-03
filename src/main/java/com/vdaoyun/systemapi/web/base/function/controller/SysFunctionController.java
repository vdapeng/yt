/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.function.controller;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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

import com.vdaoyun.common.api.enums.IConstant;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.base.function.model.SysFunction;
import com.vdaoyun.systemapi.web.base.function.service.SysFunctionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

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
@ApiIgnore
@Api(tags = { "菜单相关接口" }, hidden = true)
@RestController
@RequestMapping("/sys/function")
public class SysFunctionController {

	private static final Logger log = LoggerFactory.getLogger(SysFunctionController.class);

	@Autowired
	private SysFunctionService sysFunctionService;

	@ApiOperation("查询列表")
	@RequestMapping(method = RequestMethod.GET)
	public AjaxJson select(@RequestParam("typeCode") @ApiParam(value = "菜单类型编码", required = true) String typeCode)
			throws Exception {
		AjaxJson j = new AjaxJson();
		if (StringUtils.isEmpty(typeCode)) {
			j.setSuccess(false);
			j.setMsg("分类编码不可以为空");
		} else {
			j.setData(sysFunctionService.selectList(typeCode));
		}
		return j;
	}

	@ApiOperation("新增")
	@RequestMapping(method = RequestMethod.POST)
	public AjaxJson insert(@RequestBody @ApiParam(value = "菜单信息", required = true) @Valid SysFunction sysFunction,
			Errors errors) throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		if (sysFunctionService.insert(sysFunction) > 0) {
			j.setData(sysFunction.getFunctionId());
			j.setMsg("新增成功");
			log.info("sys_function表新增数据");
		} else {

			j.setMsg("新增失败");
		}

		return j;
	}

	@ApiOperation("更新")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(@PathVariable @ApiParam(value = "菜单编号", required = true) Integer id,
			@RequestBody @ApiParam(value = "菜单信息", required = true) @Valid SysFunction sysFunction, Errors errors)
			throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		sysFunctionService.update(sysFunction);
		j.setMsg("更新成功");
		log.info("sys_function表更新数据，functionId：" + id);
		return j;
	}

	@ApiOperation("删除")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(@PathVariable @ApiParam(value = "菜单编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		SysFunction sysFunction = new SysFunction();
		sysFunction.setFunctionId(id);
		sysFunction.setIsDel(IConstant.YesOrNo.YES.toString());
		sysFunctionService.update(sysFunction);
		j.setMsg("删除成功");
		log.info("sys_function表删除数据，functionId：" + id);
		return j;
	}
}