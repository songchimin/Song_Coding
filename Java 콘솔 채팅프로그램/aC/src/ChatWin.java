import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWin extends JFrame  implements ActionListener{ 
	Scanner sc = new Scanner(System.in);
    private static final long serialVersionUID = 1L;
    JTextField tf;
    JPanel p,p2;
    JTextArea txtLog;
//    TextHandler handler = null;
    JScrollPane scrollPane;
    Socket socket;
    PrintWriter out = null;
    String name;

    ChatWin(Socket socket) {
    	
        this.setTitle("Chat Window");
        this.setSize(500, 530);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setLineWrap(false);
        scrollPane = new JScrollPane(txtLog);
        setContentPane(scrollPane);
        scrollPane = new JScrollPane(txtLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p = new JPanel();
        p.setLayout(new BorderLayout());
        
        tf = new JTextField(40);

        tf.setSize(40,10);
        p.add(scrollPane,BorderLayout.CENTER);
        p2.add(p);
        p.add(tf,BorderLayout.SOUTH);
        

        this.setContentPane(p);
        this.setVisible(true);

//        TextHandler();
        tf.addActionListener(this);

        //-------------------------------------------------------------------
        
        this.socket = socket;        
        try {
            out = new PrintWriter(this.socket.getOutputStream(), true);
        } catch(Exception e) {
            System.out.println("예외S3:"+e);
        }
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        String msg = tf.getText();
        if ("".equals(msg)) return;
        
        if ( msg.equals("q") || msg.equals("Q") ) {
            try {
                out.close();
                socket.close();
            } catch (IOException e1) {
            }
        } else {
            try {
                out.println(URLEncoder.encode(msg, "UTF-8"));
                //scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                //txtLog.append(msg+"\n");
            } catch (UnsupportedEncodingException e1) {}
        }

        tf.setText("");
		
	}
    
}


