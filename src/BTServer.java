import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GuardedObject;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;

import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;


public class BTServer  implements Runnable{
	private static final String SERVICEUUID = "6ABC1C60693D11E1B86C0800200C9A66";
	private boolean listening=true;
	private boolean active=true;
	private StreamConnection con;
	public BTServer(){
		
	}
	
	public void init() throws IOException {
		Thread t = new Thread(this);
		t.start();
		
	}

	@Override
	public void run() {
		try {
			LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
		
			StreamConnectionNotifier service =  (StreamConnectionNotifier) Connector.open( "btspp://localhost:" + SERVICEUUID + ";name=RemoteHCIServer" );
			
			while (active){
	            con = (StreamConnection) service.acceptAndOpen();
	            InputStream is = con.openInputStream();
	            System.out.println("Connection Received");
	            this.send("hello from Server".getBytes());
	            while (listening){
	            	byte[] recv= new byte[1024];
	            	is.read(recv);
	            	String s = new String(recv, 0, recv.length);
	            	System.out.println("rec: "+s);
	            }
				
			}
		} catch (BluetoothStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	

	public void send(byte[] b) throws IOException{
        OutputStream os = con.openOutputStream();
        os.write(b);
	}
}







