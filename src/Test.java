import java.io.IOException;

public class Test {
	public static void main(String args[]) throws IOException{

		String serverArg = args[0];
		String clientArg = args[1];

        
		Thread serverThread = new Thread() {
			@Override
			public void run() {
				try {
					KnockKnockServer.main(serverArg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Thread clientThread = new Thread() {
			@Override
			public void run() {
				try {
					KnockKnockClient.main(clientArg, serverArg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		serverThread.start();
		
		try{
			Thread.sleep(1000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
		clientThread.start();
	}
}
