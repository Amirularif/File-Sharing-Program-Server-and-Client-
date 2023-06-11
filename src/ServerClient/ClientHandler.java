package ServerClient;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ClientHandler {
    public static void main(String[] args) {
        final File[] filetosend = new File[1];

        JFrame jframe = new JFrame("Client");
        jframe.setSize(450, 332);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jtitle = new JLabel("Send File to Client");
        jtitle.setBounds(112, 0, 238, 60);
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
                    jfilename.setText("The file you want to send is: " + filetosend[0].getName());
                }
            }
        });

        jframe.getContentPane().setLayout(null);

        jframe.getContentPane().add(jtitle);
        jframe.getContentPane().add(jfilename);
        jframe.getContentPane().add(jpbutton);

        jframe.setVisible(true);
    }
}

