package com.vdaoyun.systemapi.web.controller.device;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.common.utils.AjaxJsonUtils;
import com.vdaoyun.systemapi.exception.ParamException;
import com.vdaoyun.systemapi.web.model.device.Device;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;
import com.vdaoyun.systemapi.web.service.device.DeviceService;
import com.vdaoyun.systemapi.web.service.ponds.PondsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "设备信息")
@RestController
@RequestMapping(value = "/device")
public class DeviceController {
	
	@Autowired
	private DeviceService service;
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_终端列表"}, value = "列表查询")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam(value = "页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam(value = "每页条数") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam(value = "排序字段")  String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam(value = "排序方式") String sort,
			@RequestBody Device entity
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectPageInfo(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	
	@ApiOperation(value = "搜索所有设备列表", hidden = true)
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public AjaxJson selectAll() throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectAll());
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_通过用户编号查询设备列表"},value = "")
	@GetMapping("/{userId}/devices")
	public AjaxJson selectListByUserId(@PathVariable("userId") @ApiParam("用户编号") Long userId) throws Exception {
		return AjaxJsonUtils.ajaxJson(service.selectListByUserId(userId));
	}
	
	@ApiOperation(value = "通过主键查询详情", hidden = true)
	@RequestMapping(value = "/{terminalId}", method = RequestMethod.GET)
	public AjaxJson getById(
		@PathVariable(value = "terminalId") @ApiParam(value = "设备编号") String id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectByPrimaryKey(id));
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_新增终端"}, value = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public AjaxJson insert(
		@RequestBody @Valid @ApiParam(value = "Device") Device entity,
		BindingResult bindingResult
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		if (bindingResult.hasErrors()) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
			return ajaxJson;
		}
		Device device = service.selectByPrimaryKey(entity.getTerminalId());
		if (device != null) { // 当设备编号存在时
			if (device.getIsDel().equalsIgnoreCase(YesOrNo.YES.toString())) {
				device.setIsDel(YesOrNo.NO.toString());
				device.setName(entity.getName());
//				device.setUserId(entity.getUserId());
				Boolean result = service.update(device) > 0;
				ajaxJson.setData(entity);
				ajaxJson.setSuccess(result);
				ajaxJson.setMsg(result ? "新增成功" : "新增失败");
				return ajaxJson;
			} else {
				throw new ParamException("该设备已存在");
			}
		}
		Boolean result = service.insertInfo(entity) > 0;
		ajaxJson.setData(entity);
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "新增成功" : "新增失败");
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_终端信息编辑"}, value = "编辑")
	@RequestMapping(value = "/{terminalId}", method = RequestMethod.PUT)
	public AjaxJson update(
		@RequestBody @ApiParam(value = "设备") Device entity, 
		@PathVariable(value = "terminalId") @ApiParam(value = "设备编号") String terminalId
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		Device device = service.selectByPrimaryKey(terminalId);
		if (device == null) {
			throw new ParamException("该终端不存在");
		}
		if (device.getUserId() != null && entity.getUserId() != null && device.getUserId() != entity.getUserId()) {
			throw new ParamException("该先解除该终端绑定关系，再重新绑定新的用户");
		}
		entity.setTerminalId(terminalId);
		Boolean result = service.update(entity) > 0;
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "编辑成功" : "编辑失败");
		ajaxJson.setData(entity);
		return ajaxJson;
	}
	
	@Autowired
	private PondsService pondsService;
//	@Autowired
//	private SensorService sensorService;
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_终端删除"}, value = "")
	@RequestMapping(value = "/{terminalId}", method = RequestMethod.DELETE)
	public AjaxJson delete(
		@PathVariable(value = "terminalId") @ApiParam(value = "设备编号") String terminalId
	) throws Exception {
		
		List<Ponds> pondsList = pondsService.selectListByTerminalId(terminalId);
		if (!pondsList.isEmpty()) {
			String pondsNames = "";
			for (Ponds ponds : pondsList) {
				pondsNames += ponds.getName() + " | ";
			}
			pondsNames = pondsNames.substring(0, pondsNames.length() - 2);
			throw new ParamException("该终端已绑定如下塘口：" + pondsNames + "。请先解除绑定后删除。");
		}
		
//		List<Sensor> sensors = sensorService.selectByPondsIdAndTerminalId(null, terminalId);
//		if (!sensors.isEmpty()) {
//			String sensorNames = "";
//			for (Sensor sensor : sensors) {
//				sensorNames += sensor.getName() + " | ";
//			}
//			sensorNames = sensorNames.substring(0, sensorNames.length() - 1);
//			throw new ParamException("该终端如下探测器：" + sensorNames + "已被绑定。请先解除绑定后删除。");
//		}
		
		AjaxJson ajaxJson = new AjaxJson();
		Boolean result = service.delete(terminalId) > 0;
		ajaxJson.setMsg(result ? "删除成功" : "删除失败");
		ajaxJson.setSuccess(result);
		return ajaxJson;
	}

	@ApiOperation(tags = {"B管理后台_____用户管理_终端"}, value = "", hidden = true)
	@GetMapping("info")
	public AjaxJson selectInfoByPondsId(String terminalId, Long pondsId) throws Exception {
		return AjaxJsonUtils.ajaxJson(service.selectInfoByPondsId(terminalId, pondsId));
	}
	
	@ApiOperation(tags = {"B管理后台_____终端管理_终端列表_终端解绑"}, value = "", hidden = true)
	@GetMapping("{terminalId}/unbundling")
	public AjaxJson unbundling(
			@PathVariable("terminalId") @ApiParam(value = "终端编号") String terminalId
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		Device device = service.selectByPrimaryKey(terminalId);
		if (device == null) {
			throw new ParamException("终端不存在");
		}
		List<Ponds> pondsList = pondsService.selectListByTerminalId(terminalId);
		if (!pondsList.isEmpty()) {
			String pondsNames = "";
			for (Ponds ponds : pondsList) {
				pondsNames += ponds.getName() + " | ";
			}
			pondsNames = pondsNames.substring(0, pondsNames.length() - 2);
			throw new ParamException("该终端已绑定如下塘口：" + pondsNames + "。请先解除绑定后解绑。");
		}
		service.unbundling(device);
		return ajaxJson;
	}
	
	
}
