package com.envision.samples;

import java.io.IOException;

import javax.servlet.ServletException;
import org.eclipse.jetty.server.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.orvibo.APIMain;
import org.orvibo.Status;

public class HttpServerSample extends AbstractHandler
{
    @Override
       public void handle( String target,
                        Request baseRequest,
                        HttpServletRequest request,
                        HttpServletResponse response ) throws IOException,
                                                      ServletException
    {
    	String result = "";
    	try
    	
    	{
    		String status = baseRequest.getParameter("status");
    		String deviceId = baseRequest.getParameter("deviceId");
    		String uid = baseRequest.getParameter("uid");
    		String uri = request.getRequestURI();
    		System.out.println(uri);
    		
    		if (uri.equals("/control"))
    		{
    		if (status.equals("on"))
    				result=APIMain.control(uid,deviceId, Status.on);
    		else
    			result=APIMain.control(uid, deviceId, Status.off);
    		}
    		else if (uri.equals("/info"))
    		{
    			result=APIMain.getStatus(uid,deviceId);
    		}
    		else if (uri.equals("/list"))
    		{
    			result=APIMain.getDeviceList();
    		}
    	}
        
    	catch (Exception e)
    	{
    		  response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    	        // Write back response
    	       response.getWriter().println(e.getMessage());
    	       return;
    	}
    	// Declare response encoding and types
        response.setContentType("application/json; charset=UTF-8");

        // Declare response status code
        response.setStatus(HttpServletResponse.SC_OK);

        // Write back response
        response.getWriter().println(result);

        // Inform jetty that this request has now been handled
        baseRequest.setHandled(true);
    }

    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(8050);
        server.setHandler(new HttpServerSample());

        server.start();
        server.join();
    }

	
}