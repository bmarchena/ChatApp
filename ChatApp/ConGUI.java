import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;



public class ConGUI extends JFrame {
	
	private JTextField txt_inetAddress;
	private InetAddress inetAddress;
	
	private JTextField txt_portNumber;
	private int portNumber;
	
	private JButton bt_connect;
	
	
	public ConGUI() {
		super("Connect to Client");
		
		setLayout(new FlowLayout());
		
		txt_inetAddress = new JTextField("Destination IP address: ");
		add(txt_inetAddress);
		
		txt_portNumber = new JTextField("Destination port number: ");
		add(txt_portNumber);
		
		bt_connect = new JButton("Connect");
		add(bt_connect);

		txt_inetAddress.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				txt_inetAddress.setText("");
			}
		});
		txt_portNumber.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				txt_portNumber.setText("");
			}
		});
		
		myHandler handler = new myHandler();
		txt_inetAddress.addActionListener(handler);
		txt_portNumber.addActionListener(handler);
		bt_connect.addActionListener(handler);
		
	}
	
	private class myHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == bt_connect) {
				try {
					inetAddress = InetAddress.getByName(txt_inetAddress.getText());
				} catch (UnknownHostException ex) {
					ex.printStackTrace();
				}
				portNumber = Integer.parseInt(txt_portNumber.getText());
				initConnection(inetAddress, portNumber);
				txt_inetAddress.setText("Destination IP address: ");
				txt_portNumber.setText("Destination port number: ");

			}

		}

		private void initConnection(InetAddress targetAddress, int targetPort) {
			
			ChatGUI chatWin = new ChatGUI(targetAddress, targetPort);
			chatWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			chatWin.setSize(500, 500);
			chatWin.setVisible(true);


			Driver.openChats.put(targetAddress, chatWin);

		}
		
	}
	
	
	

}
