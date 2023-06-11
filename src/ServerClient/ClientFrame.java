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
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ClientFrame {
	private static Socket socket;
	private static List<String> updateEvents = new ArrayList<>();
	 private static JTextArea updateTextArea;
	
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Client");
        frame.setSize(450, 307);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel jtitle = new JLabel("Server connecter");
        jtitle.setBounds(138, 0, 159, 54);
    	jtitle.setFont(new Font("Arial",Font.BOLD,20));
    	jtitle.setBorder(new EmptyBorder(20,0,10,0));
    	jtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
    	JPanel panel = new JPanel();
    	panel.setBounds(0, 54, 436, 90);
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
        frame.getContentPane().setLayout(null);       
        frame.getContentPane().add(jtitle);
		frame.getContentPane().add(panel);
		
		updateTextArea = new JTextArea();
        updateTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        updateTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(updateTextArea);
        scrollPane.setBounds(0, 164, 436, 106);
        frame.getContentPane().add(scrollPane);
		
		JLabel eventtitle = new JLabel("Events");
		eventtitle.setFont(new Font("Tahoma", Font.BOLD, 15));
		eventtitle.setBounds(10, 139, 67, 24);
		frame.getContentPane().add(eventtitle);
		
		frame.setVisible(true);
		
		String directoryPath = "C:\\Users\\amiru\\git\\repository\\ClientServerFIleShare";
        try {
            watchDirectory(directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
        	updateTextArea.setText(getUpdateText());
        }
    }
		
		  private static void watchDirectory(String directoryPath) throws IOException {
		        Path path = Paths.get(directoryPath);
		        WatchService watchService = FileSystems.getDefault().newWatchService();
		        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
		                StandardWatchEventKinds.ENTRY_MODIFY);

		        Thread watchThread = new Thread(() -> {
		            try {
		                while (true) {
		                    WatchKey key = watchService.take();
		                    for (WatchEvent<?> event : key.pollEvents()) {
		                        Path updatedFilePath = (Path) event.context();
		                        String updateType = event.kind().name();
		                        String updateEvent;

		                        if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
		                            updateEvent = "File " + updatedFilePath + " " + updateType;
		                        } else if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
		                            updateEvent = "File " + updatedFilePath + " " + updateType;
		                        } else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
		                            Path oldFilePath = path.resolve(updatedFilePath);
		                            updateEvent = "File " + oldFilePath + " renamed to " + updatedFilePath;
		                        } else {
		                            continue; 
		                        }
		                        
		                        
		                        updateEvents.add(updateEvent);
		                    }
		                    key.reset();
		                    updateTextArea.setText(getUpdateText());
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        });

		        watchThread.setDaemon(true);
		        watchThread.start();
		    }

		  private static String getUpdateText() {
			    StringBuilder sb = new StringBuilder();
			    for (String updateEvent : updateEvents) {
			        sb.append(updateEvent).append("\n");
			    }
			    return sb.toString();

		
    }
}