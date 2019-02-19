package org.jain.sarthak.messenger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jain.sarthak.messenger.database.DatabaseClass;
import org.jain.sarthak.messenger.model.Comment;
import org.jain.sarthak.messenger.model.ErrorMessage;
import org.jain.sarthak.messenger.model.Message;

public class CommentService {
	
	Map<Long,Message> messages=DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId)
	{
		Map<Long,Comment> comment=messages.get(messageId).getComments();
		return new ArrayList<Comment>(comment.values());
	}
	
	
	/*
	 * Implementing in-built WebApplicationException. This exception is already registered in
	 * Jersey so we dont need a mapper for this. 
	 * 
	 * This WebApplicationException has multiple constructors and one containing Response
	 * so we can build a response and send the response.
	 */
	public Comment getComment(long messageId,long commentId)
	{
		ErrorMessage em=new ErrorMessage("NotFound",404,"https://www.youtube.com/watch?v=9oeJc_VkZxo&t=11s");
		Response response=Response.status(Status.INTERNAL_SERVER_ERROR).entity(em).build();
		
		Message message=messages.get(messageId);
		if(message==null)
			throw new WebApplicationException(response);
		Map<Long,Comment> comments=message.getComments();
		Comment comment=comments.get(commentId);
		if(comment==null)
			throw new NotFoundException(response);
		
		/*
		 * Notfoundexception inherits from parent WebApplicationException.We have multiple such
		 * exception which directly inherit from Webapplicationexception and they automatically
		 * set the error code so we dont need to build a response and set the error code
		 * explicitly.
		 * 
		 */
		
		return comment;
	}
	
	public Comment addComment(long messageId,Comment comment)
	{
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		comment.setId(comments.size()+1);
		comment.setCreated(new Date());
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId,Comment comment)
	{
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		if(comment.getId()<=0)
			return null;
		comments.put(comment.getId(),comment);
		return comment;
	}
	
	public Comment removeComment(long messageId,long commentId)
	{
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
