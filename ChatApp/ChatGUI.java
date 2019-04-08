import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import javax.swing.*;

public class ChatGUI extends JFrame {

	private JTextArea txt_chatLog;
	private JScrollPane scrollPane;
	private JTextField txt_messageOut;
	private String messageOut;
	private JButton bt_send;

	private InetAddress dInet;
	private int dPort;

	private myHandler handler = new myHandler();

	public ChatGUI(InetAddress destinationIP, int destinationPort) {
		super("IP: " + destinationIP.getHostAddress() + " / Port: " + String.valueOf(destinationPort));
		dInet = destinationIP;
		dPort = destinationPort;

		setLayout(new FlowLayout());
		
		txt_chatLog = new JTextArea(25,35);
		txt_chatLog.setEditable(false);
		scrollPane = new JScrollPane(txt_chatLog);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);
		
		txt_messageOut = new JTextField("Enter a message here...", 25);
		add(txt_messageOut);

		txt_messageOut.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				txt_messageOut.setText("");
			}
		});
		
		bt_send = new JButton("Send");
		add(bt_send);
		bt_send.addActionListener(handler);


	}

	protected void addIncoming(String messageIn) {
		txt_chatLog.append("Them: " + messageIn + "\n" );
	}

	private class myHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {


			if(e.getSource() == bt_send){
				messageOut = txt_messageOut.getText();

				Driver.mySocket.send(messageOut, dInet, dPort);
				System.out.println("WE CALLED SEND");
				txt_chatLog.append("Me: " + messageOut + "\n");
				System.out.println(txt_chatLog);
				System.out.println("MESSAGE OUT: " + messageOut);
				txt_messageOut.setText("Enter a message here...");

//				byte[] inBuffer = Driver.mySocket.receive().getData();
//				String inMessage = new String(inBuffer);
//
//				txt_chatLog.append("Them: " + inMessage + "\n");



			}

		}
	}

	public InetAddress getdInet(){ return dInet; }

	public int getdPort() { return dPort; }
	

}
