/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.param.controller;

import java.util.HashMap;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.enums.IConstant;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.base.param.model.Param;
import com.vdaoyun.systemapi.web.base.param.service.ParamService;
import com.vdaoyun.util.StringTool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.controller.common.param
 * 
 * @ClassName: ParamController
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-7-17 10:34:23
 */
@Api(tags = { "参数信息相关接口" })
@RestController
@RequestMapping(value = "/sys/param")
public class ParamController {

	private static final Logger log = LoggerFactory.getLogger(ParamController.class);

	@Autowired
	protected ParamService paramService;

	@ApiOperation(value = "查询列表", hidden = true)
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(defaultValue = "1") @ApiParam(value = "页码", required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") @ApiParam(value = "每页条数", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "createOn") @ApiParam(value = "排序字段", required = true, defaultValue = "createOn") String sortby,
			@RequestParam(defaultValue = "desc") @ApiParam(value = "排序方式", required = true, defaultValue = "desc") String order,
			@RequestBody @ApiParam(value = "搜索条件", required = false) HashMap<String, Object> param) throws Exception {
		AjaxJson j = new AjaxJson();
		PageHelper.startPage(pageNum, pageSize);
		param.put("isDel", IConstant.YesOrNo.NO.toString());
		param.put("orderByClause", sortby + " " + order);
		// 列表返回数据
		j.setData(new PageInfo<HashMap<String, Object>>(paramService.selectTableList(param)));
		return j;
	}

	@ApiOperation(value = "新增", hidden = true)
	@RequestMapping(method = RequestMethod.POST)
	public AjaxJson insert(@RequestBody @ApiParam(value = "参数信息", required = true) @Valid Param param, Errors errors)
			throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		param.setCreateOn(StringTool.newDate());
		param.setIsDel(IConstant.YesOrNo.NO.toString());
		if (paramService.insert(param) > 0) {
			j.setMsg("新增成功");
			log.info("sys_param表新增数据");
		} else {
			j.setMsg("新增失败");
		}
		return j;
	}

	@ApiOperation(value = "更新", hidden = true)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(@PathVariable @ApiParam(value = "参数编号", required = true) Integer id,
			@RequestBody @ApiParam(value = "参数信息", required = true) @Valid Param param, Errors errors)
			throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		paramService.update(param);
		j.setMsg("更新成功");
		log.info("sys_param表更新数据，paramId：" + id);
		return j;
	}

	@ApiOperation(value = "删除", hidden = true)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(@PathVariable @ApiParam(value = "参数编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		if (id == null) {
			j.setSuccess(false);
			j.setMsg("编号不可以为空");
		} else {
			Param param = new Param();
			param.setParamId(id);
			param.setIsDel(IConstant.YesOrNo.YES.toString());
			paramService.update(param);
			j.setMsg("删除成功");
			log.info("sys_param表删除数据，paramId：" + id);
		}
		return j;
	}

	@ApiOperation(value = "验证唯一性", hidden = true)
	@RequestMapping(value = "/verifyOnly", method = RequestMethod.GET)
	public boolean verifyOnly(@RequestParam @ApiParam(value = "参数值", required = true) String value,
			@RequestParam(required = false) @ApiParam(value = "参数编号", required = false) Integer id) throws Exception {
		return paramService.selectByName(value, id) != null;
	}

	@ApiOperation(value = "通过参数名称，获取参数值", tags = {"A小程序_____全局_系统参数_通过参数名称，获取参数值"})
	@RequestMapping(value = "/name", method = RequestMethod.GET)
	public AjaxJson selectByName(
			@RequestParam(defaultValue = "小程序首页数据间隔", value = "name", required = false) @ApiParam(value = "参数名称", required = false, defaultValue = "小程序首页数据间隔") String name
	) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setData(paramService.selectByName(name, null));
		return j;
	}
}