/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.dict.controller;

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

import com.vdaoyun.common.api.enums.IConstant;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.dict.model.SysDict;
import com.vdaoyun.systemapi.web.dict.service.SysDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.dict.controller
 * 
 * @ClassName: SysDictController
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-8-23 9:01:04
 */
@Api(tags = { "数据字典相关接口" })
@RestController
@RequestMapping(value = "/sys/dict")
public class SysDictController {

	private static final Logger log = LoggerFactory.getLogger(SysDictController.class);

	@Autowired
	protected SysDictService sysDictService;

	@ApiOperation("查询列表")
	@RequestMapping(method = RequestMethod.GET)
	public AjaxJson select(@RequestParam @ApiParam(value = "所属分组编号", required = true) Integer groupId)
			throws Exception {
		AjaxJson j = new AjaxJson();
		j.setData(sysDictService.selectDictListByGroupId(groupId));
		return j;
	}

	@ApiOperation("查询指定数据字典信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public AjaxJson selectById(@PathVariable @ApiParam(value = "数据字典编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setData(sysDictService.selectByPrimaryKey(id));
		return j;
	}

	@ApiOperation("查询指定数据字典信息")
	@RequestMapping(value = "/queryGroupCode/{groupCode}", method = RequestMethod.GET)
	public AjaxJson select(@PathVariable @ApiParam(value = "分组编码", required = true) String groupCode) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setData(sysDictService.selectDictListByGroupCode(groupCode));
		return j;
	}

	@ApiOperation("新增")
	@RequestMapping(method = RequestMethod.POST)
	public AjaxJson insert(@RequestBody @ApiParam(value = "数据字典信息", required = true) @Valid SysDict sysDict,
			Errors errors) throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		if (sysDictService.isExistByCode(sysDict.getCode(), sysDict.getGroupId())) {
			j.setSuccess(false);
			j.setMsg("字典编码[" + sysDict.getCode() + "]已经存在");
			return j;
		}
		sysDict.setStates(IConstant.ShowOrHide.SHOW.toString());// 默认状态
		if (sysDictService.insert(sysDict) > 0) {
			j.setMsg("新增成功");
			j.setData(sysDict.getDictId());
			log.info("sys_dict表新增数据");
		} else {
			j.setMsg("新增失败");
		}
		return j;
	}

	@ApiOperation("更新")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(@PathVariable @ApiParam(value = "数据字典编号", required = true) Integer id,
			@RequestBody @ApiParam(value = "数据字典信息", required = true) @Valid SysDict sysDict, Errors errors)
			throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		sysDict.setCode(null);// 字典编码不可以修改
		sysDictService.update(sysDict);
		j.setMsg("更新成功");
		log.info("sys_dict表更新数据，dictId：" + id);
		return j;
	}

	@ApiOperation("删除")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(@PathVariable @ApiParam(value = "数据字典编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		if (id == null) {
			j.setSuccess(false);
			j.setMsg("编号不可以为空");
		} else {
			sysDictService.delete(id);
			j.setMsg("删除成功");
			log.info("sys_dict表删除数据，dictId：" + id);
		}
		return j;
	}

	@ApiOperation("切换")
	@RequestMapping(value = "/{id}/change", method = RequestMethod.GET)
	public AjaxJson change(@PathVariable @ApiParam(value = "数据字典编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		if (id == null) {
			j.setSuccess(false);
			j.setMsg("编号不可以为空");
			return j;
		}
		sysDictService.changeDict(id);
		j.setMsg("切换成功");
		log.info("sys_dict表更新数据，dictId：" + id);
		return j;
	}

}