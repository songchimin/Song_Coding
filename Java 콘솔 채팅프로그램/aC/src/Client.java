import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
  
public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException{
				
		
		try {
			String ServerIp = "localhost";
			if(args.length > 0)
				ServerIp = args[0];
			Socket socket = new Socket(ServerIp, 9999);		//소켓 객체 생성
			System.out.println("서버와 연결 되었습니다.....");
			
			Thread receiver = new Receiver(socket);		//리시버
			receiver.start();
			
			//new ChatWin(socket);
					
		} catch (Exception e) {
			System.out.println("예외[MultiClien class]:"+e);
		}
	}
}







