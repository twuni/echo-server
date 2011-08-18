package org.twuni.echo.responder;

import org.twuni.common.net.http.UrlEncodedParameters;
import org.twuni.common.net.http.request.Request;
import org.twuni.common.net.http.responder.Responder;
import org.twuni.common.net.http.response.Response;
import org.twuni.common.net.http.response.Status;
import org.twuni.money.common.ShareableToken;
import org.twuni.money.common.Token;
import org.twuni.money.common.Treasury;

public class EarnResponder implements Responder {

	private final StringBuilder buffer;
	private final Treasury treasury;

	public EarnResponder( Treasury treasury, StringBuilder buffer ) {
		this.treasury = treasury;
		this.buffer = buffer;
	}

	@Override
	public Response respondTo( Request request ) {
		UrlEncodedParameters parameters = new UrlEncodedParameters( new String( request.getContent() ) );
		String input = parameters.get( "input" );
		Token token = treasury.create( input.length() );
		buffer.append( input );
		return new Response( Status.OK, "text/plain", new ShareableToken( token ).toString() );
	}

}
