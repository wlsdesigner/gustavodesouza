package br.com.relato.restlet;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.ConnectorService;

public class RestletRelatoApplication extends Application {

	public RestletRelatoApplication(){
		
	}
	
	
	@Override
	public synchronized Restlet createInboundRoot() {
		Router router = new Router(getContext());
		ConnectorService s = this.getConnectorService();
		s.getClientProtocols().add(Protocol.HTTP);
		router.attachDefault(ContentResource.class);
		return router;
	}
}