package com.vdaoyun.systemapi.web.controller.ponds;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;
import com.vdaoyun.systemapi.web.service.ponds.PondsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "塘口信息表")
@RestController
@RequestMapping(value = "/ponds")
public class PondsController {
	
	@Autowired
	private PondsService service;
	
	@ApiOperation(value = "列表查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", paramType = "query"),
		@ApiImplicitParam(name = "order", value = "排序字段", defaultValue= "createDate", paramType = "query"),
		@ApiImplicitParam(name = "sort", value = "排序方式", defaultValue = "DESC", paramType = "query")
	})
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) String sort,
			@RequestBody Ponds entity
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectPageInfo(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	
	@ApiOperation("搜索所有鱼塘列表")
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public AjaxJson selectAll() throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectAll());
		return ajaxJson;
	}
	
	@ApiOperation(value = "通过主键查询详情")
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer", paramType = "path")	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public AjaxJson getById(
		@PathVariable(value = "id") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectByPrimaryKey(id));
		return ajaxJson;
	}
	
	@ApiOperation(value = "新增")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public AjaxJson insert(
		@RequestBody @Valid @ApiParam(value = "Ponds") Ponds entity,
		BindingResult bindingResult
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		if (bindingResult.hasErrors()) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		Boolean result = service.insertInfo(entity) > 0;
		ajaxJson.setData(entity);
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "新增成功" : "新增失败");
		return ajaxJson;

	}
	
	@ApiOperation(value = "编辑")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", paramType = "path", value = "主键", dataType = "Integer"),
			@ApiImplicitParam(name = "entity", paramType = "body", value = "实体", dataType = "Ponds")
	})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(
		@RequestBody Ponds entity, 
		@PathVariable(value = "id") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		entity.setId(id);
		Boolean result = service.update(entity) > 0;
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "编辑成功" : "编辑失败");
		ajaxJson.setData(entity);
		return ajaxJson;
	}
	
	@ApiOperation(value = "通过主键删除")
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer", paramType = "path")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(
		@PathVariable(value = "id") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		Boolean result = service.delete(id) > 0;
		ajaxJson.setMsg(result ? "删除成功" : "删除失败");
		ajaxJson.setSuccess(result);
		return ajaxJson;
	}

}
