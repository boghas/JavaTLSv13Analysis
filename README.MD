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

		** Added the TLS version negocition and cipher suites negocition
			*** To negocitiate the TLS version set the wanted version in client.setEnabledProtocols for example:
				**** client.setEnabledProtocols(new String[] {"TLSv1.3"});
				
			*** To negocitiate the cipher suites used set the wanted cipher suite in client.setEnabledCipherSuites for example:
				**** client.setEnabledCipherSuites(new String[] {"TLS_AES_128_GCM_SHA256"});
		
		** To enable/disable logs commentate/uncommentate one of the log system properties for example:
			*** System.setProperty("javax.net.debug", "ssl:handshake:data");

		** Console logs for various attributes for javax.net.debug can be found in console_logs folder

		** Traffic tracing for TLSv1.2 and TLSv1.3 can be found in traffic_tracing folder

		** Examples of getting server certificate chain with openssl in the openssl folder
			*** To get the server certificate with openssl run the following command in cmd openssl s_client -showcerts -connect localhost:9000 

		** Added ClassFileServer which is a server that accepts both HTTP and HTTPS connections.
			
		** Added the SSLSocketClientAuth class to retrieve the bytes of the file we want to acces from the ClassFileServer

		** Added the option to chose wether to log or not from the command line and to choose the log options

		** For echoServerSSL added the option to choose which version of the protocol to use

			*** NOTE: To run the ClassFileServer or the SSLSocketClientAuth you must use valid signed certificates, otherwise Java will reject them.
				**** For testing purposes non-valid signed certifacets or self signed certificates can be used if we add them to the Java truststore.
				**** First get the certificate from the keystore.
					***** Run the following command: keytool -export -alias some_alias -keystore path_to_my_keystore -rfc -file name_of_the_cert_file.cert

				**** Add the certificate to the truststore:
					***** Run the following command with Administrator rights:  keytool -import -alias some_alias -cacerts -file path_to_my_cert.cer
			


--------------------------------------------------------------------------------------------------------------------------------------------
	