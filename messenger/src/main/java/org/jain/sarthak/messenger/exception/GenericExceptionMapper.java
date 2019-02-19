package org.jain.sarthak.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/*
 * ExceptionMapper lets us map our customException or existing exception to a custom JSON response.
 * We needed this since Jersey has no idea of what kind of exceptions can be thrown
 * and thus by default they cannot map those to some response.
 */


import org.jain.sarthak.messenger.model.ErrorMessage;

//Because every exception implements throwable so, we have now created a GenericExceptionMapper
// to handle all cases where we dont want tomcat server default page.

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
	//ExceptionMapper<T> is generic so we pass our Exception class to it.
		@Override
		public Response toResponse(Throwable exception) {
			// TODO Auto-generated method stub
			//But not using .entity() send the correct error code but still some default webpage is sent
			
			ErrorMessage em=new ErrorMessage(exception.getMessage(),404,"https://www.youtube.com/watch?v=9oeJc_VkZxo&t=11s");
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(em).build();
		}

	}
