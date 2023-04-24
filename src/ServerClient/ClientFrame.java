package ServerClient;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ClientFrame {
	private static Socket socket;
	public static void main(String[] args) {
    	
        final JFrame frame = new JFrame("Client");
        frame.setSize(450, 200);
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel jtitle = new JLabel("Server connecter");
    	jtitle.setFont(new Font("Arial",Font.BOLD,20));
    	jtitle.setBorder(new EmptyBorder(20,0,10,0));
    	jtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
    	JPanel panel = new JPanel();
    	panel.setBorder(new EmptyBorder(10,0,10,0));

        JButton connectButton = new JButton("Connect");
        connectButton.setPreferredSize(new Dimension(145,60));
    	connectButton.setFont(new Font("Arial",Font.BOLD,13));
    	
    	JButton openButton = new JButton("Open File Sender");
        openButton.setPreferredSize(new Dimension(145,60));
    	openButton.setFont(new Font("Arial",Font.BOLD,13));
    	
    	panel.add(connectButton);
    	panel.add(openButton);
        
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
                    socket = new Socket("localhost", 1234);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("Connected".getBytes());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //client.main(null);
            }
        });
        
        openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.main(null);
			}
        	
        });
        
        frame.add(jtitle);
		frame.add(panel);
		frame.setVisible(true);
    }
}
	

