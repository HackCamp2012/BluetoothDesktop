import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.obex.SessionNotifier;


public class BTServer {
	private static final String SERVICEUUID = "6ABC1C60693D11E1B86C0800200C9A66";
											   
	public BTServer(){
		
	}
	
	public void init() throws IOException {
		LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
		SessionNotifier serverConnection = (SessionNotifier) Connector.open("btgoep://localhost:" + SERVICEUUID + ";name=RemoteHCIServer");
		int count = 0;
		while (count < 2){
			BTRequestHandler rh = new BTRequestHandler();
			serverConnection.acceptAndOpen(rh);
			System.out.println("Connection Received" + (++count));
		}
	}
	
}
