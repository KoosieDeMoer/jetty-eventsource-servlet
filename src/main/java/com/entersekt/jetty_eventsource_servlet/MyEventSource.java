package com.entersekt.jetty_eventsource_servlet;

import java.io.IOException;

public class MyEventSource implements org.eclipse.jetty.servlets.EventSource
{
    private Emitter emitter;

    public void onOpen(Emitter emitter) throws IOException
    {
        this.emitter = emitter;
    }

    public void emitEvent(String dataToSend)
    {
        try {
			this.emitter.data(dataToSend);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void onClose()
    {
    }
}