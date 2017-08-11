package cntc.dao;

import java.io.IOException;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

public class ReturnResult {
	private int returnCode;
	private Object data;
	private String errMessage;

	public ReturnResult() {
		super();
		this.returnCode = 0;
	}

	public ReturnResult(int returnCode, Object data) {
		super();
		this.returnCode = returnCode;
		this.data = data;
	}

	public ReturnResult(int returnCode) {
		super();
		this.returnCode = 0;
	}

	@JsonProperty("returnCode")
	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	@JsonProperty("errMessage")
	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	@JsonProperty("data")
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String toJsonString() {
		try {
			JSON_Handle jsonHandle = new JSON_Handle();
			ObjectMapper mapper = jsonHandle.getMapperObj();
			return mapper.writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String toString() {
		return toJsonString();
	}
}
