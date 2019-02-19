package org.jain.sarthak.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jain.sarthak.messenger.model.ErrorMessage;

//This annotation registers our Mapper with Jax-RS
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
//ExceptionMapper<T> is generic so we pass our Exception class to it.
	@Override
	public Response toResponse(DataNotFoundException exception) {
		// TODO Auto-generated method stub
		//But not using .entity() send the correct error code but still some default webpage is sent
		
		ErrorMessage em=new ErrorMessage(exception.getMessage(),404,"https://www.youtube.com/watch?v=9oeJc_VkZxo&t=11s");
		return Response.status(Status.NOT_FOUND).entity(em).build();
	}

}
