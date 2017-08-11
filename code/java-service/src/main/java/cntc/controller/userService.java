package cntc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import cntc.bo.MessageBO;
import cntc.bo.UserBO;
import cntc.dao.JSON_Handle;
import cntc.dao.ReturnCode;
import cntc.dao.ReturnResult;
import cntc.dto.Avatar;
import cntc.dto.Message;
import cntc.dto.User;

@Path("/user")
public class userService {

	@POST
	@Path("/authLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateLogin(String jsonStr) {
		JSON_Handle jsonHandle = new JSON_Handle();
		User userAuth = jsonHandle.JsonToObject(jsonStr, User.class);
		ReturnResult returnResult = new ReturnResult(ReturnCode.UNKNOWN_ERROR);
		UserBO userBO = new UserBO();
		User userFinded = null;
		userFinded = userBO.findUser(userAuth.getUsername(),
				userAuth.getPassword());
			userBO.changeStatusUser(userFinded.getUsername(), true);
			returnResult.setReturnCode(ReturnCode.SUCCESS);
			returnResult.setData(userFinded);
		return Response.status(200).entity(returnResult.toJsonString()).build();
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public String registerUser(String jsonStr) {
		String res = null;
		JSON_Handle jsonHandle = new JSON_Handle();
		User userSignup = jsonHandle.JsonToObject(jsonStr, User.class);
		UserBO userBO = new UserBO();
		if (userBO.isInsertUser(userSignup.getEmail(),
				userSignup.getUsername(), userSignup.getPassword()) == true)
			res = jsonHandle.ObjectToJson("register OK");
		else
			res = jsonHandle.ObjectToJson("register NOK");
		return res;
	}
	
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authLogout(String jsonStr){
		JSON_Handle jsonHandle = new JSON_Handle();
		User userLogout = jsonHandle.JsonToObject(jsonStr, User.class);
		ReturnResult returnResult = new ReturnResult(ReturnCode.UNKNOWN_ERROR);
		UserBO userBo = new UserBO();
		if(userBo.changeStatusUser(userLogout.getUsername(), false)){
			returnResult.setReturnCode(ReturnCode.SUCCESS);
		}
		else{
			returnResult.setReturnCode(ReturnCode.LOGOUT_FAIL);
		}
		return Response.status(200).entity(returnResult.toJsonString()).build();
	}

	@GET
	@Path("/user-online")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListUerOnline(){
		ReturnResult returnResult = new ReturnResult(ReturnCode.UNKNOWN_ERROR);
		UserBO userBO = new UserBO();
		ArrayList<User> userOnlineList = userBO.getAllUserOnline();
		returnResult.setReturnCode(ReturnCode.SUCCESS);
		returnResult.setData(userOnlineList);
		return Response.status(200).entity(returnResult.toJsonString()).build();
	}
	
	public String Location = null;
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
		String type = null;
		if (fileDetail.getFileName().endsWith("jpg") || fileDetail.getFileName().endsWith("png"))
			type = "Avatar";
		if (fileDetail.getFileName().endsWith("mp4"))
			type = "Video";
		String uploadedFileLocation = "E://uploaded/" + type + "/" + fileDetail.getFileName();

		// save it  
		writeToFile(uploadedInputStream, uploadedFileLocation);
		
		String output = "File uploaded successfully. " ;
		// uploadedInputStream.close();
		return Response.status(200).entity(output).build();

	}

	/*
	 * public static byte[] toByte(InputStream input) throws IOException { byte[]
	 * buffer = null; int bytesRead; ByteArrayOutputStream output = new
	 * ByteArrayOutputStream(); while ((bytesRead = input.read(buffer)) != -1) {
	 * output.write(buffer, 0, bytesRead); } return output.toByteArray(); }
	 */
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			// System.out.println("Closed");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	@POST
	@Path("/update-avatar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAvatar(String jsonStr){
		System.out.println(jsonStr);
		JSON_Handle jsonHandle = new JSON_Handle();
		Avatar avatar = jsonHandle.JsonToObject(jsonStr, Avatar.class);
		ReturnResult returnResult = new ReturnResult(ReturnCode.UNKNOWN_ERROR);
		UserBO userBo = new UserBO();
		userBo.updateAvatar(avatar.getUsername(), avatar.getImage());
		returnResult.setReturnCode(ReturnCode.SUCCESS);
		returnResult.setData(avatar);
		return Response.status(200).entity(returnResult.toJsonString()).build();
	}
	
	@GET
	@Path("/get-avatar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessages(@QueryParam("username") String username) {
		ReturnResult returnResult = new ReturnResult(ReturnCode.UNKNOWN_ERROR);
		UserBO userBO = new UserBO();
		Avatar avatar = userBO.getAvatar(username);
		returnResult.setReturnCode(ReturnCode.SUCCESS);
		returnResult.setData(avatar);
		return Response.status(200).entity(returnResult.toJsonString()).build();
	}
}
