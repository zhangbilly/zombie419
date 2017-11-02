package com.zworks.zombie419.web.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <strong>FrontResponse</strong>
 * <p>说明：前台返回通用模型</p>
 * @author zhangqiang
 * @date 2017年11月2日 上午11:39:58
 * @version
 *
 */
public class FrontResponse extends JSONObject{
	
	private static final long serialVersionUID = 1L;
	
	public static final String STATUS = "status";
	public static final String DATA = "data";
	public static final String MESSAGE = "msg";
	
	public static final int SUCCESS = 1;
	public static final int FAILED = 0;
	
	public FrontResponse success() {
		put(STATUS, SUCCESS);
		return this;
	}
	
	public FrontResponse success(Object data) {
		put(STATUS, SUCCESS);
		put(DATA, data);
		return this;
	}
	
	public FrontResponse failed(String msg) {
		put(STATUS, FAILED);
		put(MESSAGE, msg);
		return this;
	}
}
