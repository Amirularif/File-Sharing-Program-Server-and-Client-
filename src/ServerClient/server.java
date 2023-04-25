package ServerClient;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class server {
	static ArrayList<MyFile> myfiles = new ArrayList<>();
	
	public static void main (String[]args) throws IOException {
		
		int fileid = 0;
		
		JFrame jframe = new JFrame("Server");
		jframe.setSize(450,450);
		jframe.setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BoxLayout(jpanel,BoxLayout.Y_AXIS));
		
		JScrollPane jscrollpane = new JScrollPane(jpanel);
		jscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel jtitle = new JLabel("File Receiver");
		jtitle.setFont(new Font("Arial",Font.BOLD,25));
		jtitle.setBorder(new EmptyBorder(20,0,10,0));
		jtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		jframe.add(jtitle);
		jframe.add(jscrollpane);
		jframe.setVisible(true);
		
		ServerSocket serversocket = new ServerSocket(1235);
		
		while(true) {
			try {
				Socket socket = serversocket.accept();
				DataInputStream datainputstream = new DataInputStream(socket.getInputStream());
				
				int filenameLength = datainputstream.readInt();
				
				if(filenameLength>0) {
					byte[] fileNameBytes = new byte[filenameLength];
					datainputstream.readFully(fileNameBytes, 0, fileNameBytes.length);
					String filename = new String(fileNameBytes);
					
					int filecontentlength = datainputstream.readInt();
					
					if(filecontentlength>0) {
						byte[] filecontentbytes = new byte[filecontentlength];
						datainputstream.readFully(filecontentbytes, 0, filecontentlength);
						
						JPanel jpfilerow = new JPanel();
						jpfilerow.setLayout(new BoxLayout(jpfilerow,BoxLayout.Y_AXIS));
						
						JLabel jfilename = new JLabel (filename);
						jfilename.setFont(new Font("Arial",Font.BOLD,20));
						jfilename.setBorder(new EmptyBorder(10,0,10,0));
						jfilename.setAlignmentX(Component.CENTER_ALIGNMENT);
						
						if (getFileExtension(filename).equalsIgnoreCase("txt")) {
							jpfilerow.setName(String.valueOf(fileid));
							jpfilerow.addMouseListener(getMyMouseListener());
							
							jpfilerow.add(jfilename);
							jpanel.add(jpfilerow);
							jframe.validate();
						}else {
							jpfilerow.setName(String.valueOf(fileid));
							jpfilerow.addMouseListener(getMyMouseListener());
							
							jpfilerow.add(jfilename);
							jpanel.add(jpfilerow);
							
							jframe.validate();
						}
						myfiles.add(new MyFile(fileid,filename,filecontentbytes,getFileExtension(filename)));
						fileid++;
					}
							
					}
			}catch(IOException error) {
				error.printStackTrace();
			}
		}
	}

	private static MouseListener getMyMouseListener() {
		
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				JPanel jpanel = (JPanel) e.getSource();
				int fileid = Integer.parseInt(jpanel.getName());
				for (MyFile myfile:myfiles) {
					if (myfile.getId()==fileid) {
						JFrame jfpreview = createFrame(myfile.getName(), myfile.getData(), myfile.getFileextension());
						jfpreview.setVisible(true);
					}
				}
			}

			private JFrame createFrame(final String filename,final byte[] filedata,String fileextension) {
				
				final JFrame jframe = new JFrame("File Downloader");
				jframe.setSize(450,450);
				
				final JPanel jpanel = new JPanel();
				jpanel.setLayout(new BoxLayout(jpanel,BoxLayout.Y_AXIS));
				
				JLabel jtitle = new JLabel("File Downloader");
				jtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
				jtitle.setFont(new Font("Arial",Font.BOLD,25));
				jtitle.setBorder(new EmptyBorder(20,0,10,0));
				
				JLabel jprompt = new JLabel("Are you sure you want to download: "+ filename);
				jprompt.setAlignmentX(Component.CENTER_ALIGNMENT);
				jprompt.setFont(new Font("Arial",Font.BOLD,15));
				jprompt.setBorder(new EmptyBorder(20,0,10,0));
				
				final JButton jyesbutton = new JButton("Yes");
				jyesbutton.setPreferredSize(new Dimension(100,60));
				jyesbutton.setFont(new Font("Arial",Font.BOLD,15));
				
				JButton jnobutton = new JButton("No");
				jnobutton.setPreferredSize(new Dimension(100,60));
				jnobutton.setFont(new Font("Arial",Font.BOLD,15));
				
				JButton jdeletebutton = new JButton("Delete");
				jdeletebutton.setPreferredSize(new Dimension(100,60));
				jdeletebutton.setFont(new Font("Arial",Font.BOLD,15));
				
				JLabel jfilecontent = new JLabel();
				jfilecontent.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				JPanel jpbutton = new JPanel();
				jpbutton.setBorder(new EmptyBorder(20,0,10,0));
				
				jpbutton.add(jyesbutton);
				jpbutton.add(jnobutton);
				jpbutton.add(jdeletebutton);
				
				if (fileextension.equalsIgnoreCase("txt")) {
					jfilecontent.setText("<html>"+ new String(filedata)+"<html>");
				}else {
					jfilecontent.setIcon(new ImageIcon(filedata));
				}
				
				jyesbutton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(jframe, "file downloaded successfully");
						File filetodownload = new File(filename);
						try {
							FileOutputStream fileoutputstream = new FileOutputStream(filetodownload);
							fileoutputstream.write(filedata);
							fileoutputstream.close();
							
							jframe.dispose();
						}catch(IOException error) {
							error.printStackTrace();
						}
						
					}
					
				});
			
				jnobutton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						jframe.dispose();
					}
				});
				
				jdeletebutton.addActionListener(new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        int confirmed = JOptionPane.showConfirmDialog(jframe,
				                "Are you sure you want to delete this file?",
				                "Delete File",
				                JOptionPane.YES_NO_OPTION);

				        if (confirmed == JOptionPane.YES_OPTION) {
				        	if (filename != null) {
				                deleteFile(filename);
				                jframe.dispose();
				                
				            } else {
				                JOptionPane.showMessageDialog(jframe, "Cannot delete null file");
				            }
				        }
				    }

					private void deleteFile(String filename) {
						File file = new File(filename);
					    if (file.delete()) {
					        System.out.println("File deleted successfully");
					    } else {
					    	JOptionPane.showMessageDialog(jframe, "file deleted successfully");
					    }
						
					}
				});
				
				jpanel.add(jtitle);
				jpanel.add(jprompt);
				jpanel.add(jfilecontent);
				jpanel.add(jpbutton);
				
				jframe.add(jpanel);
				return jframe;
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
				
			}
			
		};
	}

	public static String getFileExtension(String filename) {
		
		int i = filename.lastIndexOf(".");
		if (i>0) {
			return filename.substring(i+1);
		}else {
			return "No extension found";
		}
	}
	

}
