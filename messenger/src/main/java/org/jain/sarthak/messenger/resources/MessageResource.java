package org.jain.sarthak.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jain.sarthak.messenger.model.Message;
import org.jain.sarthak.messenger.resources.beans.MessageFilterBean;
import org.jain.sarthak.messenger.service.MessageService;

//Need to tell servlet that all requests for /messages uri need to be handled
//by below class. For this we annotate the class with @


//mapped an url to a class
@Path("messages")

/*
 * We can annotate different functions with different produces and consumes and depending
 * on what client has requested by providing Accept key and corresponding value,
 * our function annotated with the matching value will be called.
 * 
 */

@Consumes(value= {MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
@Produces(value= {MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
//all the functions in this class are mapped to above address. If you want to map a method
//to an address after messages/... then give a @Path above that function as done in getMessage
public class MessageResource {
	
	MessageService ms=new MessageService();
	
	//annotating the http methods to the url with specific class methods
	@GET
	
	//usage of produces to send response back. In what way the response would be sent
	//defined by passed parameters
	
	/*
	public List<Message> getMessages(@QueryParam("year") int year,
									@QueryParam("start") int start,
									@QueryParam("size") int size)
	*/
	
	//Below Function signature is using BeanParam. It accepts instance of MessageFilterBean
	//We have defined all the three queryparam in that class. And we will use getter and setters
	//to access their param values
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean)
	{
		if(filterBean.getYear()>0)
			return ms.getAllMessagesForYear(filterBean.getYear());
		if(filterBean.getStart()>=0 && filterBean.getSize()>0)
			return ms.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		return ms.getAllMessages();
	}
	
	
	/*
	 * Below function is to show implementation of different parameters.
	 * 
	 * 1) @MatrixParam -> It works similar to @QueryParam. The difference being
	 * 					  @QueryParam uses "?" and @MatrixParam uses ":" to denote the parameters
	 * 
	 * 2) @HeaderParam -> This is to get the header values which are being sent along with the
	 * 					  http request. So you can use @HeaderParam to get the value for each parameter
	 * 
	 * 3) @CookieParam -> This is to get hold of the cookie key values.
	 * 
	 * 4) @FormParam -> When you submit an html form and want to get hold of field names and the values
	 * 
	 */
	
	
	//Write the Param name inside the ("..") to catch which param you want
	@GET
	@Path("/annotations")
	
	/*
	 * Produces and Consumes above function signature overwrites the produces and consumes
	 * above the class
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String getParamUsingAnnotations(@MatrixParam("param") String matrixParam,
										   @HeaderParam("authSessionId") String header,
										   @CookieParam("JSESSIONID") String value)
	{
		return "Matrix Param: " + matrixParam + " Header Param: " + header + " CookieParam: "+value;
	}
	
	
	/*
	 * The below function makes use of @Context which accepts only some particular type of 
	 * parameter types, UriInfo, HttpHeaders
	 */
	@GET
	@Path("/context")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String getParamUsingContext(@Context UriInfo uriInfo,
									   @Context HttpHeaders httpHeaders)
	{
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = httpHeaders.getCookies().toString();
		return "Path: "+path+" Cookies: "+cookies;
	}
	
	/*
	 * Example of getting param values passed in URL
	 * /something/{id1}/name/{id2}
	 * 
	 * @PathParam("id1") int id1, @PathParam("id2") int id2
	 * 
	 */
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)	//@Produces because we are converting String to JSON and returing that JSON
	//can give regular expression in @PathParam to catch a range or url names
	public Message getMessage(@PathParam("messageId") long id,@Context UriInfo uriInfo)
	{
		Message message=ms.getMessage(id);
		message.addLink(getUriForSelf(id, uriInfo), "self");
		message.addLink(getUriForProfile(message,uriInfo), "profile");
		message.addLink(getUriForComments(message,uriInfo), "comments");
		return message;
	}


	private String getUriForProfile(Message message, UriInfo uriInfo) {
		// TODO Auto-generated method stub
		return uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build().toString();
	}


	private String getUriForSelf(long id, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(id))
				.build().toString();
	}
	
	private String getUriForComments(Message message,UriInfo uriInfo)
	{
		URI uri=uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class,"getCommentResource")
				//.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build();
		return uri.toString();
	}
	
	
	//old addMessage
	/*
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//Consumes because POST accepts some data. So we are saying in our POST we would be accepting
	//JSON format data to be added in our hashmap
	@Produces(MediaType.APPLICATION_JSON)
	public Message addMessage(Message message)
	{
		return ms.addMessage(message);
	}
	*/
	
	
	/*
	 * Suppose we want to modify sent status code, along with send something in header response.
	 * Use Response class. So below we will send the uri for newly created message.
	 * 
	 * Using UriInfo from context. it has getabsolutepath and getabsolutepathbuilder
	 * using builder because former return type is uri. we would need to convert it to string
	 * and then strign back to uri.
	 * builder helps to form the string easily
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//Consumes because POST accepts some data. So we are saying in our POST we would be accepting
	//JSON format data to be added in our hashmap
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessage(Message message,@Context UriInfo uriInfo)
	{
		Message newMessage=ms.addMessage(message);
		String newId=String.valueOf(message.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();
		
		//created directly sent 201 successfully created status code
		//entity to send back the newMessage in the body
		
	}
	
	@PUT
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	//Consumes because POST accepts some data. So we are saying in our POST we would be accepting
	//JSON format data to be added in our hashmap
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long id, Message message)
	{
		message.setId(id);
		return ms.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	//Consumes because POST accepts some data. So we are saying in our POST we would be accepting
	//JSON format data to be added in our hashmap
	@Produces(MediaType.APPLICATION_JSON)
	public Message deleteMessage(@PathParam("messageId") long id)
	{
		return ms.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
