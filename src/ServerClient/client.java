package ServerClient;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class client {

	public static void main(String[] args) {
		
		final File[] filetosend = new File[1];
		
		JFrame jframe = new JFrame("Client");
		jframe.setSize(450,450);
		jframe.setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel jtitle = new JLabel("File Sender");
		jtitle.setFont(new Font("Arial",Font.BOLD,25));
		jtitle.setBorder(new EmptyBorder(20,0,10,0));
		jtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		final JLabel jfilename = new JLabel ("Choose a file to send");
		jfilename.setFont(new Font("Arial",Font.BOLD,20));
		jfilename.setBorder(new EmptyBorder(50,0,0,0));
		jfilename.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel jpbutton = new JPanel();
		jpbutton.setBorder(new EmptyBorder(75,0,10,0));
		
		JButton jsendbutton = new JButton("Send File");
		jsendbutton.setPreferredSize(new Dimension(150,75));
		jsendbutton.setFont(new Font("Arial",Font.BOLD,20));
		
		JButton jchoosebutton = new JButton("Choose File");
		jchoosebutton.setPreferredSize(new Dimension(150,75));
		jchoosebutton.setFont(new Font("Arial",Font.BOLD,20));
		
		jpbutton.add(jsendbutton);
		jpbutton.add(jchoosebutton);
		
		jchoosebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfilechooser = new JFileChooser();
				jfilechooser.setDialogTitle("Choose a file to send");
				
				if (jfilechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					filetosend[0] = jfilechooser.getSelectedFile();
					jfilename.setText("The file you want to send is:"+ filetosend[0].getName());
				}
				
			}
			
		});
		
		jsendbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (filetosend[0] == null) {
					jfilename.setText("Please choose a file");
				}else {
					try {
					FileInputStream fileinputstream = new FileInputStream(filetosend[0].getAbsolutePath());
					Socket socket = new Socket("localhost",1235);
					
					DataOutputStream dataoutputstream = new DataOutputStream(socket.getOutputStream());
					
					String filename = filetosend[0].getName();
					byte[] fileNameBytes = filename.getBytes();
					byte[] fileContentBytes = new byte[(int)filetosend[0].length()];
					fileinputstream.read(fileContentBytes);
					
					dataoutputstream.writeInt(fileNameBytes.length);
					dataoutputstream.write(fileNameBytes);
					
					dataoutputstream.writeInt(fileContentBytes.length);
					dataoutputstream.write(fileContentBytes);
					}catch(IOException error) {
						error.printStackTrace();
					}
				}
				
			}
			
		});
		
		jframe.add(jtitle);
		jframe.add(jfilename);
		jframe.add(jpbutton);
		jframe.setVisible(true);

	}

}
