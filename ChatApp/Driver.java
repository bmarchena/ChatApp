import java.net.*;
import java.sql.Time;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Driver {

	private static InetAddress myAddress;
	private static final int MY_PORT_NUMBER = 64000;
	protected static HashMap<InetAddress, ChatGUI> openChats = new HashMap<>();

	protected static Socket mySocket;


	public static void main(String[] args) {
		openSocket();

		System.out.println("My IP Address = " + myAddress.getHostAddress());
		
		ConGUI test = new ConGUI();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setSize(250, 250);
		test.setVisible(true);

		receiveMessage();


	}

	private static void openSocket() {
		mySocket = new Socket(MY_PORT_NUMBER, Socket.SocketType.NoBroadcast);

		try {
			myAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Socket open.");

	}

	private static void receiveMessage() {
		DatagramPacket inPacket;
		while(true){
		if (mySocket.check() != false){
			System.out.println("We got here again");

				inPacket = mySocket.receive();
				byte[] inBuffer = inPacket.getData();
				System.out.println(inBuffer);
				String inMessage = new String(inBuffer);
				System.out.println("SO CLOSE: " + inMessage);
				if(openChats.containsKey(inPacket.getAddress())){
					openChats.get(inPacket.getAddress()).addIncoming(inMessage);
				} else {
					ChatGUI chatWin = new ChatGUI(inPacket.getAddress(), inPacket.getPort());
					chatWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					chatWin.setSize(500, 500);
					chatWin.setVisible(true); openChats.put(inPacket.getAddress(), chatWin);
					chatWin.addIncoming(inMessage);}
		} else {
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}
	}

}
