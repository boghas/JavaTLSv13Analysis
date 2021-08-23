--------------------------------------------------------------------------------------------------------------------------------------------

This an analysis of TLS 1.3 version while also comparing with TLS 1.2 version.

	* Project is starting first with the implementation of a simple Echo server and Echo client to demonstrate the following:
		** Why we need the TLS protocol
		** How to add the TLS protocol over an existing HTTP server 
		
			*** To test the Echo Server run the following class:
				**** src/main/java/com.tls.analysis.echo.server.EchoServer
				
			*** To test the Echo client run the following class:
				**** src/main/java/com.tls.analysis.echo.client.EchoClient
				
		** Created the secure communication between the client and the server trough SSL:
			*** To test the secure communication you need to run the following classes:
				**** src/main/java/com.tls.analysis.echoTLS.EchoSSLServer.Server
				**** src/main/java/com.tls.analysis.echoTLS.EchoSSLClient.EchoSSLCient

		** Added the packet tracing for the plain text communication between client and server
			*** The wireshark capture was recorded on a kali linux virtual machine

		** Added the TLS version negocition and cipcher suites negocition
			*** To negocitiate the TLS version set the wanted version in client.setEnabledProtocols for example:
				**** client.setEnabledProtocols(new String[] {"TLSv1.3"});
				
			*** To negocitiate the cipcher suites used set the wanted cipher suite in client.setEnabledCipherSuites for example:
				**** client.setEnabledCipherSuites(new String[] {"TLS_AES_128_GCM_SHA256"});
		
		** To enable/disable logs commentate/uncommentate one of the log system properties for example:
			*** System.setProperty("javax.net.debug", "ssl:handshake:data");
	