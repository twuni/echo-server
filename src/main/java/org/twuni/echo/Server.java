package org.twuni.echo;

import org.apache.http.impl.client.DefaultHttpClient;
import org.twuni.common.config.Configuration;
import org.twuni.common.config.PropertiesConfiguration;
import org.twuni.common.net.http.Method;
import org.twuni.common.net.http.responder.ExceptionHandler;
import org.twuni.common.net.http.responder.RequestMapping;
import org.twuni.echo.responder.EarnResponder;
import org.twuni.echo.responder.FormResponder;
import org.twuni.echo.responder.RedeemResponder;
import org.twuni.money.common.Treasury;
import org.twuni.money.treasury.client.TreasuryClient;

public class Server {

	public static void main( String [] args ) {

		Configuration configuration = PropertiesConfiguration.load( "echo" );

		StringBuilder buffer = new StringBuilder();
		Treasury treasury = new TreasuryClient( new DefaultHttpClient(), configuration.getString( "treasury.url", "http://localhost:8080" ) );

		RequestMapping mapping = new RequestMapping();

		mapping.map( Method.GET, "/", new FormResponder() );
		mapping.map( Method.POST, "/earn", new EarnResponder( treasury, buffer ) );
		mapping.map( Method.POST, "/redeem", new RedeemResponder( treasury, buffer ) );

		new org.twuni.common.net.http.Server( configuration.getInt( "server.port", 80 ), new ExceptionHandler( mapping ) ).start();

	}

}
