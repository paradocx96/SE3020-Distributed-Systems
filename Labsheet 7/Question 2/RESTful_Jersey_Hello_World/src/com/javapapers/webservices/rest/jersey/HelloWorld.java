package com.javapapers.webservices.rest.jersey;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
//import sun.misc.BASE64Decoder;
import Decoder.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;

@Path("/helloworld")
public class HelloWorld {
 

	 @Context
	 private HttpServletRequest request;
	 
	 private static final String template = "Hello, %s!";
	    private static Integer counter = 0;


	  @GET 
	 @Produces(MediaType.APPLICATION_JSON)
		public Greeting sayXMLHello() {
		String decoded = null;
		Greeting greeting = null;
		  try {
		   // Get the Authorisation Header from Request
		   String header = request.getHeader("authorization");
		    
		   // Header is in the format "Basic 3nc0dedDat4"
		   // We need to extract data before decoding it back to original string
		   String data = header.substring(header.indexOf(" ") +1 );
		    
		   // Decode the data back to original string
		   byte[] bytes = new BASE64Decoder().decodeBuffer(data);
		   decoded = new String(bytes);
		  }catch(Exception ex) {
			  greeting = new Greeting(-1, "Auth error"); 
			  
			  return greeting;
		  }
		   System.out.println(decoded);
		    
		   if(!decoded.equals("admin:password123")) {
			   greeting = new Greeting(-1, "Invalid User");
		   }else {			
			 greeting = new Greeting(counter++, String.format(template, "world"));
		   }
		   
		   return greeting;
		}
	    

	@GET @Path("{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Greeting sayXMLHello(@PathParam("name") String name) {
		
		
		return new Greeting(counter++, String.format(template, name));
	}
}