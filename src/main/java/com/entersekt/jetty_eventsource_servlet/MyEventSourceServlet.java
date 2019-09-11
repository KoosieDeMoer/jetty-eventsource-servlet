package com.entersekt.jetty_eventsource_servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlets.EventSource;
import org.eclipse.jetty.servlets.EventSourceServlet;

public class MyEventSourceServlet extends EventSourceServlet {

	@Override
	protected EventSource newEventSource(HttpServletRequest request) {
		
		return App.eventSource;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		App.eventSource.emitEvent((new Date()).toString() + " " + req.getParameter("test"));
	}

	
	
}
