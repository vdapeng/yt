/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.dict.controller;

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
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.base.dict.model.SysDictGroup;
import com.vdaoyun.systemapi.web.base.dict.service.SysDictGroupService;
import com.vdaoyun.systemapi.web.base.dict.service.SysDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.dictgroup.controller
 * 
 * @ClassName: SysDictGroupController
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-8-23 8:57:36
 */
@ApiIgnore
@Api(tags = { "数据字典分组相关接口" }, hidden = true)
@RestController
@RequestMapping(value = "/sys/dictgroup")
public class SysDictGroupController {

	private static final Logger log = LoggerFactory.getLogger(SysDictGroupController.class);

	@Autowired
	protected SysDictGroupService sysDictGroupService;
	@Autowired
	protected SysDictService sysDictService;

	@ApiOperation("查询列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(defaultValue = "1") @ApiParam(value = "页码", required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") @ApiParam(value = "每页条数", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "orderby") @ApiParam(value = "排序字段", required = true, defaultValue = "orderby") String sortby,
			@RequestParam(defaultValue = "desc") @ApiParam(value = "排序方式", required = true, defaultValue = "desc") String order,
			@RequestBody @ApiParam(value = "搜索条件", required = false) HashMap<String, Object> param) throws Exception {
		AjaxJson j = new AjaxJson();
		PageHelper.startPage(pageNum, pageSize);
		param.put("orderByClause", sortby + " " + order);
		// 列表返回数据
		j.setData(new PageInfo<HashMap<String, Object>>(sysDictGroupService.selectTableList(param)));
		return j;
	}

	@ApiOperation("查询指定数据字典分组信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public AjaxJson select(@PathVariable @ApiParam(value = "字典分组编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setData(sysDictGroupService.selectByPrimaryKey(id));
		return j;
	}

	@ApiOperation("新增")
	@RequestMapping(method = RequestMethod.POST)
	public AjaxJson insert(@RequestBody @ApiParam(value = "字典分组信息", required = true) @Valid SysDictGroup sysDictGroup,
			Errors errors) throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		if (sysDictGroupService.isExistByGroupCode(sysDictGroup.getGroupCode())) {
			j.setSuccess(false);
			j.setMsg("字典组编码[" + sysDictGroup.getGroupCode() + "]已经存在");
			return j;
		}
		if (sysDictGroupService.insert(sysDictGroup) > 0) {
			j.setMsg("新增成功");
			log.info("sys_dict_group表新增数据");
		} else {
			j.setMsg("新增失败");
		}
		return j;
	}

	@ApiOperation("更新")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(@PathVariable @ApiParam(value = "字典分组编号", required = true) Integer id,
			@RequestBody @ApiParam(value = "字典分组信息", required = true) @Valid SysDictGroup sysDictGroup, Errors errors)
			throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		sysDictGroup.setGroupCode(null);// 字典组编码不可以修改
		sysDictGroupService.update(sysDictGroup);
		j.setMsg("更新成功");
		log.info("sys_dict_group表更新数据，dictGroupId：" + id);
		return j;
	}

	@ApiOperation("删除")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(@PathVariable @ApiParam(value = "字典分组编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		if (id == null) {
			j.setSuccess(false);
			j.setMsg("编号不可以为空");
			return j;
		}
		if (sysDictService.isHasDictByGroupId(id)) {
			j.setSuccess(false);
			j.setMsg("字典组[" + id + "]下有字典信息，无法删除");
			return j;
		}
		sysDictGroupService.delete(id);
		j.setMsg("删除成功");
		log.info("sys_dict_group表删除数据，dictGroupId：" + id);

		return j;
	}
}