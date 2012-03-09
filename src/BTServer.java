import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.L2CAPConnection;
import javax.bluetooth.L2CAPConnectionNotifier;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.obex.SessionNotifier;


public class BTServer {
	private static final String SERVICEUUID = "6ABC1C60693D11E1B86C0800200C9A66";
	private boolean listening=true;
	private boolean active = true;
	public BTServer(){
		
	}
	
	public void init() throws IOException {
		LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
		
		L2CAPConnectionNotifier notifier = (L2CAPConnectionNotifier)Connector.open("btl2cap://localhost:" + SERVICEUUID + ";name=RemoteHCIServer");
		while (active){
			L2CAPConnection con = notifier.acceptAndOpen();
			System.out.println("Connection Received");
			while (listening) {
	            if (con.ready()){
	            	byte[] recv=new byte[1024];
	                con.receive(recv);
	                String s = new String(recv, 0, recv.length);
	                System.out.println("Received from server: " + s.trim());
	                
	                listening = false;
	            }
	        }	
		}
				
	}
	
	
}
