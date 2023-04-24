package ServerClient;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class Serverconnector {
	//private static List<ClientHandler> clients = new ArrayList<ClientHandler>();

		public static void main (String[]args) throws IOException {
		
			int clientid = 1;
    	
	        final JFrame frame = new JFrame("Server");
	        frame.setSize(450, 200);
	        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        JLabel jtitle = new JLabel("Clients");
	    	jtitle.setFont(new Font("Arial",Font.BOLD,20));
	    	jtitle.setBorder(new EmptyBorder(20,0,10,0));
	    	jtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	    	JPanel jpanel = new JPanel();
			jpanel.setLayout(new BoxLayout(jpanel,BoxLayout.Y_AXIS));
			
			JScrollPane jscrollpane = new JScrollPane(jpanel);
			jscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        
	        frame.add(jtitle);
			frame.add(jscrollpane);
			frame.setVisible(true);
			
			ServerSocket serverSocket = new ServerSocket(1234);
	
	        while (true) {
	            // block and wait for a client to connect
	            final Socket clientSocket = serverSocket.accept();
	            
	            JPanel jpfilerow = new JPanel();
				jpfilerow.setLayout(new BoxLayout(jpfilerow,BoxLayout.Y_AXIS));
				
				final JLabel connectionstatus = new JLabel ("Client "+clientid+ " connected.."
						+ "[info : "+clientSocket+" ]");
				connectionstatus.setFont(new Font("Arial",Font.BOLD,10));
				connectionstatus.setBorder(new EmptyBorder(10,0,10,0));
				connectionstatus.setAlignmentX(Component.CENTER_ALIGNMENT);
	            System.out.println("New client connected: " + clientSocket);
	            
	            jpfilerow.add(connectionstatus);
	            jpfilerow.addMouseListener(new MouseAdapter() {
	            	public void mouseClicked(MouseEvent e) {
	            		int confirm = JOptionPane.showConfirmDialog(frame, "Do you want to disconnect the client?");
	                    if (confirm == JOptionPane.YES_OPTION) {
		            		Container parent = connectionstatus.getParent();
		            		parent.remove(connectionstatus);
		            		parent.revalidate();
		                    parent.repaint();
		                    System.out.println("client disconnected: " + clientSocket);
	                    }
	            	}
	            });
	            jpanel.add(jpfilerow);
	            frame.validate();
	            clientid++;
	        }
        }
	  
    
}

