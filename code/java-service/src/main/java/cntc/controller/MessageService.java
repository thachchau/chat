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

import cntc.bo.MessageBO;
import cntc.dao.JSON_Handle;
import cntc.dao.ReturnCode;
import cntc.dao.ReturnResult;
import cntc.dto.Message;

@Path("/message")
public class MessageService {

	@POST
	@Path("/saveMessage")
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveMessage(String jsonStr) {
		System.out.println(jsonStr);
		String res = null;
		JSON_Handle jsonHandle = new JSON_Handle();
		Message newMessage = new Message();
		newMessage = jsonHandle.JsonToObject(jsonStr, Message.class);
		MessageBO messageBO = new MessageBO();
		newMessage.setIdRoom(messageBO.getIDRoom(newMessage.getRoomName()));
		System.out.println(newMessage.getIdRoom() + " + "
				+ newMessage.getText() + " + " + newMessage.getSender());
		if (messageBO.saveMessage(newMessage.getIdRoom(), newMessage.getText(),
				newMessage.getSender()) == true) {
			res = jsonHandle.ObjectToJson("save message OK");
		} else {
			res = jsonHandle.ObjectToJson("save message NOK");
		}
		return res;
	}

	@GET
	@Path("/getMessages")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessages(@QueryParam("fromUser") String fromUser,
			@QueryParam("toUser") String toUser) {
		ReturnResult returnResult = new ReturnResult(ReturnCode.UNKNOWN_ERROR);
		MessageBO messageBO = new MessageBO();
		List<Message> messages = messageBO.getMessages(fromUser, toUser);
		returnResult.setReturnCode(ReturnCode.SUCCESS);
		returnResult.setData(messages);
		return Response.status(200).entity(returnResult.toJsonString()).build();
	}

	@GET
	@Path("/getOldMessages")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOldMessages(@QueryParam("curUser") String curUser,
			@QueryParam("selUser") String selUser) {
		ReturnResult returnResult = new ReturnResult(ReturnCode.UNKNOWN_ERROR);
		MessageBO messageBO = new MessageBO();
		List<Message> oldMessages = messageBO.getHistoryMessage(curUser, selUser);
		returnResult.setReturnCode(ReturnCode.SUCCESS);
		returnResult.setData(oldMessages);
		return Response.status(200).entity(returnResult.toJsonString()).build();
	}
}
