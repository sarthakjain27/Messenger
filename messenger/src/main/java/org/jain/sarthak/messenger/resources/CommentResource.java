package org.jain.sarthak.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jain.sarthak.messenger.model.Comment;
import org.jain.sarthak.messenger.service.CommentService;

/*
 * The following path is preceding the /{messageId}/comments
 * This path is defined in ManageResource. Whenever MangeResource gets above path,
 * Jersey executes the method. In the method we create a new CommentResource instance,
 * the new instance will help Jersey to transfer the control to the instance.
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {
	
	private CommentService cs=new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId)
	{
		return cs.getAllComments(messageId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") long messageId,
							  @PathParam("commentId") long commentId)
	{
		return cs.getComment(messageId, commentId);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId,Comment comment)
	{
		return cs.addComment(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId,
							 	 @PathParam("commentId") long commentId,
							 	 Comment comment)
	{
		comment.setId(commentId);
		return cs.updateComment(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public Comment deleteComment(@PathParam("messageId") long messageId,
							 	 @PathParam("commentId") long commentId)
	{
		return cs.removeComment(messageId, commentId);
	}
}
