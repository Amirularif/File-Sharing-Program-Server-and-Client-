package ServerClient;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class client {
    static ArrayList<String> clientList = new ArrayList<>();
    static Serverconnector sc;
    static int logicalClock = 0; 
    static ArrayList<String> sentFiles = new ArrayList<>();

    public static void main(String[] args) {
        int clientid = 1;
        final File[] filetosend = new File[1];

        JFrame jframe = new JFrame("Client");
        jframe.setSize(450, 490);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jtitle = new JLabel("Send File to Server");
        jtitle.setBounds(102, 0, 238, 60);
        jtitle.setFont(new Font("Arial", Font.BOLD, 25));
        jtitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        jtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel jfilename = new JLabel("Choose a file to send");
        jfilename.setHorizontalAlignment(SwingConstants.CENTER);
        jfilename.setBounds(0, 58, 436, 66);
        jfilename.setFont(new Font("Arial", Font.BOLD, 15));
        jfilename.setBorder(new EmptyBorder(50, 0, 0, 0));
        jfilename.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpbutton = new JPanel();
        jpbutton.setBounds(0, 134, 436, 106);
        jpbutton.setBorder(new EmptyBorder(75, 0, 10, 0));

        JButton jsendbutton = new JButton("Send File");
        jsendbutton.setBounds(65, 10, 150, 75);
        jsendbutton.setPreferredSize(new Dimension(150, 75));
        jsendbutton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jchoosebutton = new JButton("Choose File");
        jchoosebutton.setBounds(223, 10, 150, 75);
        jchoosebutton.setPreferredSize(new Dimension(150, 75));
        jchoosebutton.setFont(new Font("Arial", Font.BOLD, 20));
        jpbutton.setLayout(null);

        jpbutton.add(jsendbutton);
        jpbutton.add(jchoosebutton);

        jchoosebutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfilechooser = new JFileChooser();
                jfilechooser.setDialogTitle("Choose a file to send");

                if (jfilechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    filetosend[0] = jfilechooser.getSelectedFile();
                    jfilename.setText("The file you want to send is:" + filetosend[0].getName());
                }

            }

        });

        jsendbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (filetosend[0] == null) {
                    jfilename.setText("Please choose a file");
                } else {
                    FileInputStream fileinputstream = null;
                    Socket socket = null;
                    DataOutputStream dataoutputstream = null;
                    try {
                        fileinputstream = new FileInputStream(filetosend[0].getAbsolutePath());
                        socket = new Socket("localhost", 1235);
                        dataoutputstream = new DataOutputStream(socket.getOutputStream());
                        String filename = filetosend[0].getName();

                        // Check if the file with the same name exists 
                        if (sentFiles.contains(filename)) {
                            JOptionPane.showMessageDialog(jframe, "The same file already exists on the server", "File Existence", JOptionPane.WARNING_MESSAGE);
                            return; 
                        }

                        byte[] fileNameBytes = filename.getBytes();
                        byte[] fileContentBytes = new byte[(int) filetosend[0].length()];
                        fileinputstream.read(fileContentBytes);
                        dataoutputstream.writeInt(fileNameBytes.length);
                        dataoutputstream.write(fileNameBytes);
                        dataoutputstream.writeInt(fileContentBytes.length);
                        dataoutputstream.write(fileContentBytes);
                        jfilename.setText("File sent successfully!");
                        logicalClock++;
                        sentFiles.add(filename); 
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        // Close the resources
                        try {
                            if (dataoutputstream != null)
                                dataoutputstream.close();
                            if (fileinputstream != null)
                                fileinputstream.close();
                            if (socket != null)
                                socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        jframe.getContentPane().setLayout(null);

        jframe.getContentPane().add(jtitle);
        jframe.getContentPane().add(jfilename);
        jframe.getContentPane().add(jpbutton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 279, 436, 174);
        jframe.getContentPane().add(scrollPane);

        JPanel clientpanel = new JPanel();
        scrollPane.setViewportView(clientpanel);

        JLabel Clientlisttitle = new JLabel("Client List");
        Clientlisttitle.setFont(new Font("Tahoma", Font.BOLD, 15));
        Clientlisttitle.setBounds(10, 250, 110, 30);
        jframe.getContentPane().add(Clientlisttitle);

        JButton refreshbutton = new JButton("Refresh");
        refreshbutton.setBounds(348, 252, 80, 21);
        jframe.getContentPane().add(refreshbutton);
        jframe.setVisible(true);

        refreshbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	updateClientList(clientpanel);
            }
        });

        JButton deleteButton = new JButton("Delete File");
        deleteButton.setBounds(233, 252, 100, 21);
        jframe.getContentPane().add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String directoryPath = "C:\\Users\\amiru\\git\\repository\\ClientServerFIleShare";
                showFilesInDirectory(directoryPath);
            }
        });
    }

    private static void showFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null && files.length > 0) {
            JFrame fileFrame = new JFrame("Files in Directory");
            fileFrame.setSize(400, 400);
            fileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            DefaultListModel<String> fileListModel = new DefaultListModel<>();
            for (File file : files) {
                if (file.isFile()) {
                    fileListModel.addElement(file.getName());
                }
            }

            JList<String> fileList = new JList<>(fileListModel);
            fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fileList.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        int index = fileList.locationToIndex(evt.getPoint());
                        String selectedFile = fileList.getModel().getElementAt(index);
                        int confirmed = JOptionPane.showConfirmDialog(fileFrame,
                                "Do you want to delete the file: " + selectedFile + "?",
                                "Confirmation",
                                JOptionPane.YES_NO_CANCEL_OPTION);
                        if (confirmed == JOptionPane.YES_OPTION) {
                            deleteFile(directoryPath, selectedFile);
                            fileListModel.remove(index);
                            JOptionPane.showMessageDialog(fileFrame, "File deleted successfully.");
                        } else if (confirmed == JOptionPane.NO_OPTION) {
                        
                        }
                    }
                }

				private void deleteFile(String directoryPath, String selectedFile) {
					 File file = new File(directoryPath + File.separator + selectedFile);
					    if (file.delete()) {
					        JOptionPane.showMessageDialog(null, "File deleted successfully.");
					    } else {
					        JOptionPane.showMessageDialog(null, "Failed to delete the file.");
					    }
				}
            });

            JScrollPane scrollPane = new JScrollPane(fileList);
            fileFrame.getContentPane().add(scrollPane);

            fileFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No files found in the directory.");
        }
    }

    private static void updateClientList(JPanel clientpanel) {
        if (sc.getConnectedClients().isEmpty()) {
        	JOptionPane.showMessageDialog(null, "No client Connected");
            sc.addition();
        } else {
            sc.addition();
            ArrayList<String> list = sc.getConnectedClients();
            clientList = list; // Update client list
            clientpanel.removeAll();
            clientpanel.setLayout(new BoxLayout(clientpanel, BoxLayout.Y_AXIS));
            for (String client : list) {
                JLabel clientLabel = new JLabel(client);
                clientLabel.setFont(new Font("Arial", Font.BOLD, 15));
                clientpanel.add(clientLabel);
                clientLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int confirmed = JOptionPane.showConfirmDialog(clientpanel,
                                "Do you want to send files to " + client + "?",
                                "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (confirmed == JOptionPane.YES_OPTION) {
                            ClientHandler.main(null);
                        }
                    }
                });
            }

            clientpanel.revalidate();
            clientpanel.repaint();
            System.out.println(list);
        }
    }

}
