package cntc.dao;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSON_Handle {

	private static final ObjectMapper mapperObj = new ObjectMapper();

	public ObjectMapper getMapperObj() {
		return mapperObj;
	}

	public <T> T JsonToObject(String jsonStr, Class<T> valueType) {
		try {
			return mapperObj.readValue(jsonStr, valueType);
		} catch (IOException e) {
			System.out.println("ERROR: ");
			e.printStackTrace();
		}
		return null;
	}

	public String ObjectToJson(Object obj) {
		try {
			return mapperObj.writeValueAsString(obj);
		} catch (IOException e) {
			System.out.println("ERROR: ");
			e.printStackTrace();
		}
		return null;
	}
}
