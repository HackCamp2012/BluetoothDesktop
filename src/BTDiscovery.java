import java.io.IOException;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import com.intel.bluetooth.BlueCoveConfigProperties;
import com.intel.bluetooth.BlueCoveImpl;


public class BTDiscovery implements DiscoveryListener{
	private Vector<RemoteDevice> devices;
	private final Object inquiryCompletedEvent = new Object();
	
	public void clearDevices(){
		if (devices != null)
			devices.clear();
	}

	public BTDiscovery(){
		
		devices=new Vector<RemoteDevice>(0); 
		System.out.println("Bluetooth is "+(LocalDevice.isPowerOn() ? "on" : "off"));
		
		
		
	}
	@Override
	public void deviceDiscovered(RemoteDevice device, DeviceClass dclass) {
		System.out.println("DEVICE FOUND: "+device.getBluetoothAddress()+ " "+ dclass);
		devices.addElement(device);
		try{
			System.out.println(" "+device.getFriendlyName(false));
		}catch(IOException noname){
			noname.printStackTrace();
		}
	}

	@Override
	public void inquiryCompleted(int arg0) {
		System.out.println("inquire complete "+arg0);
		synchronized(inquiryCompletedEvent){
            inquiryCompletedEvent.notifyAll();
        }
		
	}

	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		System.out.println("service search complete");
		
	}

	@Override
	public void servicesDiscovered(int id, ServiceRecord[] rec) {
		System.out.println("service!");
		
	}
	
	
	public void discover(){
		synchronized (inquiryCompletedEvent) {
			
			try {
				boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC,this);
				
				if (started){
					System.out.println("device discovery started...");
					inquiryCompletedEvent.wait();
					
					System.out.println(devices.size() +" devices found!");
				}
			} catch (BluetoothStateException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

