package com.ri.generalFramework.model;

import java.util.ArrayList;

public class Result<T> implements java.io.Serializable {

	private Exception exception;
	
	private boolean isSuccess;
	
	private T result;
	
	private String stateCode;
	
	private Object userData;
	
	private String detailInfo;

	private String flatId;

	private String corpId;

	private ArrayList operateData;

	public ArrayList getOperateData() {
		return operateData;
	}

	public void setOperateData(ArrayList operateData) {
		this.operateData = operateData;
	}

	public String getFlatId() {
		return flatId;
	}

	public void setFlatId(String flatId) {
		this.flatId = flatId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
		//打印输出在控制台信息，如果是发布程序需要注释掉
		//System.out.println(exception.getMessage());
		//exception.printStackTrace();
	}

	public boolean getSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Object getUserData() {
		return userData;
	}

	public void setUserData(Object userData) {
		this.userData = userData;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
	
	
	
}
