package com.vdaoyun.systemapi.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vdaoyun.common.bean.AjaxJson;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
    public ResponseEntity<AjaxJson> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    	log.error("\n==========================\n"
    			+ "EXCEPTION:\t{}\n"
    			+ "MESSAGE:\t{} \n"
    			+ "==========================", 
    			e.getClass().toString(), e.getMessage());
    	AjaxJson ajaxJson = new AjaxJson(false, "请求错误，请检查网络连接是否正常", 1001);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ajaxJson);
    }
	
	@ExceptionHandler(value = ParamException.class)
    public ResponseEntity<AjaxJson> paramErrorHandler(HttpServletRequest req, ParamException e) throws Exception {
    	log.error("\n==========================\n"
    			+ "EXCEPTION:\t{}\n"
    			+ "MESSAGE:\t{} \n"
    			+ "PARAMS:\t\t[ {} ]\n"
    			+ "DATETIME:\t{}\n"
    			+ "==========================", 
    			e.getClass().toString(), e.getMessage(), StringUtils.join(e.getParams(), ","), LocalDateTime.now());
    	return ResponseEntity.status(HttpStatus.OK).body(new AjaxJson(false, e.getMessage(), 1002));
	}



}
