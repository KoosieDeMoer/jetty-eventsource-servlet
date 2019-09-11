package com.entersekt.jetty_eventsource_servlet;

import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Date;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Hello world!
 *
 */
public class App 
{
	// http://localhost:8080/angularjs-ui/sse_test.html
	private static final Logger log = LoggerFactory.getLogger(App.class);
	static int portNo;
	static MyEventSource eventSource = new MyEventSource();

	public static void main( String[] args ) throws Exception
    {
    	usage(args);
		new App().start();
    }

    
    public void start() throws Exception {
		final HandlerList handlers = new HandlerList();

		// URL has form: http://<host>:<port>/angularjs-ui/
		handlers.addHandler(buildWebUI("angularjs-ui", "AngularJS based Web UI"));		

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
 
        context.addServlet(new ServletHolder(new MyEventSourceServlet()),"/event-source/*");
        handlers.addHandler(context);
		Server jettyServer = new Server(portNo);
		
		jettyServer.setHandler(handlers);

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}

    }
    
	private static void usage(String[] args) {

		if (args.length < 1) {
			log.error("Usage requires command line parameter MY_PORT, eg 8080");
			System.exit(0);
		} else {
			portNo = Integer.parseInt(args[0]);
		}
	}

	public static ContextHandler buildWebUI(String resourcePath, String webUiTitle) throws Exception
	{
 	   // this puts a <>-ui at: http://host:port/<>-ui
	    final ResourceHandler angularJsUIResourceHandler = new ResourceHandler();
		URL angularJsUIResourcePath = App.class.getClassLoader().getResource(resourcePath);
	    log.info("Embedded " + resourcePath + " resource path is '" + angularJsUIResourcePath + "'");
	    final ContextHandler angularJsUIContext = new ContextHandler();
	    if(angularJsUIResourcePath != null) {
	    	angularJsUIResourceHandler.setResourceBase( angularJsUIResourcePath.toURI().toString() );
	    	angularJsUIContext.setContextPath( "/" + resourcePath + "/");
	    	angularJsUIContext.setHandler( angularJsUIResourceHandler );
	    } else {
			log.error("No resource named " + resourcePath + " means no " + webUiTitle + " capability");
	    }
	    return angularJsUIContext;    	
	
	}

}
