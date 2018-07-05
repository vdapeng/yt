package com.vdaoyun.systemapi.common.utils;

import java.util.HashMap;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.collections.MapUtils;

/**
 * 
 * @Package com.vdaoyun.systemapi.common.utils
 *  
 * @ClassName: ScriptEngineUtil
 *  
 * @Description: 字符串运算公式工具类
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年7月4日 下午2:26:57
 *
 */
public class ScriptEngineUtil {
	
	/**
	 * 
	 * @Title: 根据字符串运算公式进行计算
	 *  
	 * @Description: 根据字符串运算公式进行计算
	 *  
	 * @param script	字符串运算公式。例如：(1+0.1 * (F/100) * T)*P0
	 * @param params	运算公式中需要替换的值。
	 * @return Object	运算结果
	 * 
	 * ================== 调用示例 ==================
	 * String script = "(1+0.1 * (F/100) * T)*P0"; 
	 * 	HashMap<String, Object> params = new HashMap<>();
	 * 	params.put("F", 2.5);
	 * 	params.put("T", 30);
	 * 	params.put("A", 100);
	 *  params.put("P0", 100);
	 * 	Object result = ScriptEngineUtil.eval(script, params);
	 * 	System.out.println(result);
	 * =============================================
	 * 
	 */
	public static Object eval(String script, HashMap<String, Object> params) {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		Compilable compilable = (Compilable) engine;
		Bindings bindings = engine.createBindings();
		CompiledScript JSFunction = null;
		Object result = null;
		try {
			JSFunction = compilable.compile(script);
			if (MapUtils.isNotEmpty(params)) {
				params.forEach((key, val) -> {
					bindings.put(key, val);
				});
			}
			result = JSFunction.eval(bindings);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
