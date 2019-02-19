package org.jain.sarthak.messenger;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */



//@Singleton //Singleton commented if we want to inject @PathParam and @QueryParam value
/*
 * Making singleton creates only single instance of the below class. So that all requests
 * access the same instance. Thus count variable can increment the number of times get happens.
 * Otherwise each get will create new instance and value will always be 1
 */
@Path("{pathParam}/myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	
	/*
	 * Singleton Resource gets created before the request. So using @pathParam @queryParam
	 * wont inject the desired values in the variables. 
	 * Otherwise resource in instantiated after the request and thus can easily inject the
	 * parameter values
	 * 
	 */
	
	@PathParam("pathParam") private String pathParamExample;
	@QueryParam("query") private String queryParamExample;
	
	/*
	 * We can write @PathParam and @QueryParam in function signature as well as for member variables;
	 * Member variable gives us accessibility if multiple fucntions are using those same paramters.
	 * So we dont need to write @QueryParam and @PathParam again and again in each fucntion signature
	 * 
	 */
	private int count=0;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    
    /*
     * Here we are returing a string and then in Jersey is taking this string and easily
     * writing it to Plain Text. But what if I want to return a custom object or Date object;
     * Then we need to implement MessageBodyWriter.
     * 
     */
    
    public String getIt() {
    	count=count+1;
        return "Handled "+count+" times" + "pathParam is: "+pathParamExample+" QueryParam is: "+queryParamExample;
    }
    
    @GET
    @Produces(value= {MediaType.TEXT_PLAIN,"text/shortDate"})
    
    /*
     * This gives us the flexibility if the client is requesting for TEXT_Plain the Body writer
     * concerning that will execute and if text/shortDate is requested the BodyWriter
     * ShortDateMessageBodyWriter will execute
     * 
     */
    @Path("Date")
    public Date getDate() {
		return Calendar.getInstance().getTime();
    	
    }
    
}
