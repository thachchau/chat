package cntc.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cntc.bo.RoomBO;
import cntc.dao.JSON_Handle;
import cntc.dao.ReturnCode;
import cntc.dao.ReturnResult;
import cntc.dto.Room;

@Path("/room")
public class RoomService {

	@POST
	@Path("/saveRoom")
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveRoom(String jsonStr) {
		String res = null;
		JSON_Handle jsonHandle = new JSON_Handle();
		Room newRoom = jsonHandle.JsonToObject(jsonStr, Room.class);
		RoomBO roomBO = new RoomBO();
		if (roomBO.isSaveRoom(newRoom.getRoomName(), newRoom.getFromUser(),
				newRoom.getToUser()) == true){
			res = jsonHandle.ObjectToJson("OK");
		}else{
			res = jsonHandle.ObjectToJson("NOK");
		}	
		return res;
	}
	
	@GET
	@Path("/getContactUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContactUsers(@QueryParam("username") String username){
		ReturnResult returnResult = new ReturnResult(ReturnCode.UNKNOWN_ERROR);
		RoomBO roomBO = new RoomBO();
		List<String> contactedUsers = roomBO.getContactUsername(username);
		returnResult.setReturnCode(ReturnCode.SUCCESS);
		returnResult.setData(contactedUsers);
		return Response.status(200).entity(returnResult.toJsonString()).build();
	}
}
