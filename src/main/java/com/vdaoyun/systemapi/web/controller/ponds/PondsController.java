package com.vdaoyun.systemapi.web.controller.ponds;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.common.utils.AjaxJsonUtils;
import com.vdaoyun.systemapi.exception.ParamException;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;
import com.vdaoyun.systemapi.web.model.ponds.TransferParam;
import com.vdaoyun.systemapi.web.model.sensor.Sensor;
import com.vdaoyun.systemapi.web.service.ponds.PondsService;
import com.vdaoyun.systemapi.web.service.ponds.PondsShareRecordService;
import com.vdaoyun.systemapi.web.service.sensor.SensorService;

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
	@Autowired
	private SensorService sensorService;
	@Autowired
	private PondsShareRecordService pondsShareRecordService;
//	@Autowired
//	private DeviceService deviceService;
	
	@ApiOperation(tags = {"A小程序_____我的_塘口管理_列表"}, value = "列表查询")
	@GetMapping("list")
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam("页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam("每页条数") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam("排序字段") String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam("排序方式") String sort,
			@RequestParam(value = "openid") @ApiParam("微信openid") String openid
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectMiniList(openid, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_塘口管理_列表"}, value = "列表查询")
	@PostMapping("list")
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam("页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam("每页条数") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam("排序字段") String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam("排序方式") String sort,
			@RequestBody @ApiParam("查询条件") Ponds entity
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectPageInfo(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	
	@GetMapping("search")
	@ApiOperation(value = "", hidden = true, tags = {"B管理后台____首页_搜索"})
	public AjaxJson search(@RequestParam(value = "search", required = false) @ApiParam("搜索") String search) throws Exception {
		return AjaxJsonUtils.ajaxJson(service.search(search));
	}
	
	@ApiOperation(tags = {"A小程序_____首页_列表查询"}, value = "")
	@RequestMapping(value = "mini", method = RequestMethod.POST)
	public AjaxJson selectEx(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) String sort,
			@RequestBody Ponds entity
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
//		ajaxJson.setData(service.selectPageInfoEx(entity, pageNum, pageSize, order, sort));
		ajaxJson.setData(service.selectListJsonData(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____首页_塘口详情_基本信息和探测器信息"}, value = "")
	@RequestMapping(value = "mini/{id}", method = RequestMethod.GET)
	public AjaxJson selectExDetail(
			@PathVariable(value = "id") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
//		ajaxJson.setData(service.selectInfoEx(id));
		ajaxJson.setData(service.selectInfoJsonData(id));
		return ajaxJson;
	}
	
	@ApiOperation(value = "搜索所有鱼塘列表", tags = {"B_管理后台_____塘口列表默认查询"}, hidden = true)
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public AjaxJson selectAll() throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectAll());
		return ajaxJson;
	}
	
	@ApiOperation(value = "通过主键查询详情", hidden = true)
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer", paramType = "path")	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public AjaxJson getById(
		@PathVariable(value = "id") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectByPrimaryKey(id));
		return ajaxJson;
	}
	
	@GetMapping("info")
	@ApiOperation(value = "通过主键查询详情", hidden = true)
	public AjaxJson info(@RequestParam(value = "id") Long id) throws Exception {
		return AjaxJsonUtils.ajaxJson(service.selectInfoByKey(id));
	}
	
	@ApiOperation(tags = {"A小程序_____我的_塘口管理_新增"}, value = "新增")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public AjaxJson insert(
		@RequestBody @Valid @ApiParam(value = "Ponds") Ponds entity,
		BindingResult bindingResult
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		if (bindingResult.hasErrors()) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
			return ajaxJson;
		}
		Boolean result = service.insertInfo(entity) > 0;
		ajaxJson.setData(entity);
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "新增成功" : "新增失败");
		return ajaxJson;

	}
	
	@ApiOperation(tags = {"A小程序_____我的_塘口管理_编辑"}, value = "编辑")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", paramType = "path", value = "主键", dataType = "Integer"),
			@ApiImplicitParam(name = "entity", paramType = "body", value = "实体", dataType = "Ponds")
	})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(
		@RequestBody Ponds entity, 
		@PathVariable(value = "id") Long id
	) throws Exception {
		
		Ponds ponds = service.selectByPrimaryKey(id);
		if (ponds == null) {
			throw new ParamException("塘口不存在");
		}
		String terminalId = ponds.getTerminalId();
		Boolean isUpdateWarn = false;
		// 限制，只有将探测器解除绑定之后才能进行终端更改
		if (StringUtils.isNotEmpty(terminalId) && !terminalId.equalsIgnoreCase(entity.getTerminalId())) {
			isUpdateWarn = true;
			List<Sensor> sensors = sensorService.selectByPondsIdAndTerminalId(ponds.getId(), terminalId);
			if (!sensors.isEmpty()) {
				String sensorNames = "";
				for (Sensor sensor : sensors) {
					sensorNames += sensor.getName() + " | ";
				}
				sensorNames = sensorNames.substring(0, sensorNames.length() - 2);
				throw new ParamException("该终端如下探测器：" + sensorNames + "已绑定在该塘口。请先解除绑定后修改。");
			}
		}
		AjaxJson ajaxJson = new AjaxJson();
		entity.setId(id);
		Boolean result = service.update(entity) > 0;
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "编辑成功" : "编辑失败");
		ajaxJson.setData(entity);
		if (result && isUpdateWarn) {
			//TODO:待清空报警缓存
		}
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_塘口管理_删除"}, value = "通过主键删除")
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer", paramType = "path")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(
		@PathVariable(value = "id") Long id
	) throws Exception {
		Ponds ponds = service.selectByPrimaryKey(id);
		if (ponds == null) {
			throw new ParamException("未找到相关塘口");
		}
		if (sensorService.isBindSensor(id)) {
			throw new ParamException("该塘口已绑定探测器，请先解绑该塘口所有探测器");
		}
		if (pondsShareRecordService.isShare(id)) {
			throw new ParamException("该塘口已被分享，请先取消该塘口所有分享");
		}
		AjaxJson ajaxJson = new AjaxJson();
		Boolean result = service.delete(id) > 0;
		ajaxJson.setMsg(result ? "删除成功" : "删除失败");
		ajaxJson.setSuccess(result);
		return ajaxJson;
	}
	
	@ApiOperation(value = "", hidden = true, tags = {"B_后台管理_____首页_塘口是否首页显示"})
	@PutMapping("/{id}/home")
	public AjaxJson isHome(@PathVariable(value = "id") Long id) throws Exception {
		Ponds ponds = service.selectByPrimaryKey(id);
		if (ponds == null) {
			throw new ParamException("未找到相关塘口");
		}
		return AjaxJsonUtils.ajaxJson(null);
	}

//	public static void main(String[] args) {
//		System.out.println(Math.max(5, 5));
//		System.out.println(Math.min(0, 0));
//		System.out.println(Math.max(3, 5));
//		System.out.println(Math.min(-19.2, 3.21));
//	}
	
	@PostMapping("transfer")
	public AjaxJson transfer(@RequestBody @Valid TransferParam param, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			String errorMessage = "";
			for (ObjectError error : result.getAllErrors()) {
				errorMessage += error.getDefaultMessage() + ",";
			}
			errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
			throw new ParamException(errorMessage);
		}
		Ponds ponds = service.selectByPrimaryKey(param.getPondsId());
		if (ponds.getUserId() != param.getFromUserId()) {
			throw new ParamException("该塘口非你所有，无法转移");
		}
		
		
		AjaxJson ajaxJson = new AjaxJson();
		return ajaxJson;
	}
	
}
