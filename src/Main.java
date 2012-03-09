import java.io.IOException;



public class Main {
	public static void main(String[] args) {
		
		//BTDiscovery btd = new BTDiscovery();
		//btd.discover();
		BTServer s=new BTServer();
		try {
			s.init();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
