package org.jain.sarthak.messenger;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/*
 * Need to tell jersey that is is a MessageBodyWriter Provider of type Date. 
 * We write <Date> because MessageBodyWriter accepts Generic Types.
 * 
 * Also, we write Produces to tell Jersey that this BodyWriter takes in a Date and produces
 * output in Plain Text. We can have multiple MessageBodyWriters depending upon different 
 * Produces
 */

@Provider
/*
 * We can have our own custom Media Types. for each we need to provide MEssageBodyWriter.
 * Here "text/shortDate" is our custom Media Type
 * 
 */
@Produces("text/shortDate")
public class ShortDateMessageBodyWriter implements MessageBodyWriter<Date> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return type.getName().equals(Date.class.getName());
	}

	@Override
	public long getSize(Date t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void writeTo(Date t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		
		String shortDate =t.getDate()+"-"+t.getMonth()+"-"+t.getYear();
		//entityStream is what writes result to in the response. 
		entityStream.write(shortDate.getBytes());
		// TODO Auto-generated method stub
		
	}

}
