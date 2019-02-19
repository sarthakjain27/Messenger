package org.jain.sarthak.messenger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("date/{dateString}")
public class DateResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	
	/*
	 * PathParam can easily convert string to other Primitive types. But if we have our own custom
	 * type to which we want to convert, then we need to provide a converter. 
	 * 
	 * We need to implement ParamConverter and ParamConverterProvider
	 * 
	 */
	
	/*
	 * This conversion of String to my custom data type is beind handled by Jersey
	 * We have implemented ParamConverterProvider and ParamConverter in 
	 * MyDateConverterProvider file and we have registered it as a converter provider
	 * with annotation @Provider
	 * 
	 */
	public String getRequestedDate(@PathParam("dateString") MyDate myDate)
	{
		return myDate.toString();
	}
}
