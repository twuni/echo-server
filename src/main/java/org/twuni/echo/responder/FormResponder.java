package org.twuni.echo.responder;

import org.twuni.common.net.http.request.Request;
import org.twuni.common.net.http.responder.Responder;
import org.twuni.common.net.http.response.Response;
import org.twuni.common.net.http.response.Status;


public class FormResponder implements Responder {

	@Override
	public Response respondTo( Request request ) {
		return new Response( Status.OK, "<html><body><form method=\"post\" action=\"/earn\"><textarea name=\"input\"></textarea><button type=\"submit\">Save</button></form></body></html>" );
	}

}
