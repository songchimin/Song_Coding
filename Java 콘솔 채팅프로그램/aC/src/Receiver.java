import java.io.*;
import java.net.*;
  
public class Receiver extends Thread { 
	Socket socket;
	BufferedReader in = null;
	ChatWin cw ;
	public Receiver(Socket socket) {
		this.socket = socket;
		cw = new ChatWin(socket);
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (Exception e) {
			System.out.println("예외1" + e);
		}
	}
 
	public void run() {
		while (in != null) {
			//cw.scrollPane.getVerticalScrollBar().setValue(cw.scrollPane.getVerticalScrollBar().getMaximum());
			try {
				cw.txtLog.append("" + URLDecoder.decode(in.readLine(),"UTF-8") + "\n");
				cw.txtLog.setCaretPosition(cw.txtLog.getDocument().getLength());
			} catch (java.net.SocketException ne) {
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			in.close();
		} catch (Exception e) {
			System.out.println("예외2:"+e);
		}
	}
	
	
}
