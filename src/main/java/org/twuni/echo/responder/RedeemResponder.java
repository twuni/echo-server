package org.twuni.echo.responder;

import org.twuni.common.crypto.rsa.PrivateKey;
import org.twuni.common.net.http.UrlEncodedParameters;
import org.twuni.common.net.http.request.Request;
import org.twuni.common.net.http.responder.Responder;
import org.twuni.common.net.http.response.Response;
import org.twuni.common.net.http.response.Status;
import org.twuni.money.common.ShareableToken;
import org.twuni.money.common.SimpleToken;
import org.twuni.money.common.Treasury;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RedeemResponder implements Responder {

	private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

	private final StringBuilder buffer;
	private final Treasury treasury;

	public RedeemResponder( Treasury treasury, StringBuilder buffer ) {
		this.treasury = treasury;
		this.buffer = buffer;
	}

	@Override
	public Response respondTo( Request request ) {
		UrlEncodedParameters parameters = new UrlEncodedParameters( new String( request.getContent() ) );
		String input = parameters.get( "token" );
		ShareableToken token = GSON.fromJson( input, ShareableToken.class );
		treasury.refresh( new SimpleToken( token.getTreasury(), PrivateKey.deserialize( token.getActionKey() ), PrivateKey.deserialize( token.getOwnerKey() ), token.getValue() ) );
		String result = null;
		synchronized( buffer ) {
			result = buffer.substring( 0, token.getValue() );
			buffer.delete( 0, token.getValue() );
		}
		return new Response( Status.OK, "text/plain", result );
	}

}
