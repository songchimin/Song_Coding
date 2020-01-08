import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class Server {
	ServerSocket serverSocket = null;
	Socket socket = null;
	HashMap<String, PrintWriter> adminMap;
	HashMap<String, PrintWriter> clientMap;
	HashMap<String, PrintWriter> clientMapCopy;
	HashMap<String, info> clientInfo;
	Statement stmt = null;
	Connection con = null;
	ResultSet rs = null;
	rm r[] = new rm[5];
	String banwords ="";
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	// 생성자
	public Server() {
		Connect();
		// 클라이언트의 출력스트림을 저장할 해쉬맵 생성.
		adminMap = new HashMap<String, PrintWriter>();
		clientMap = new HashMap<String, PrintWriter>();
		clientMapCopy = new HashMap<String, PrintWriter>();
		clientInfo = new HashMap<String , info>();
		r[0] = new rm();
		r[1] = new rm();
		r[2] = new rm();
		r[3] = new rm();
		r[4] = new rm();

		// 해쉬맵 동기화 설정.
		Collections.synchronizedMap(adminMap);
		Collections.synchronizedMap(clientMap);
		Collections.synchronizedMap(clientMapCopy);
		Collections.synchronizedMap(clientInfo);
		
		Collections.synchronizedMap(r[0].room);
		Collections.synchronizedMap(r[1].room);
		Collections.synchronizedMap(r[2].room);
		Collections.synchronizedMap(r[3].room);
		Collections.synchronizedMap(r[4].room);
	}

	public void Connect() {
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger"); // 1521 오라클 포트번호
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		try {
			serverSocket = new ServerSocket(9999); // 9999포트로 서버소켓 객체생성.
			System.out.println("서버가 시작되었습니다.");

			while (true) {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + ":" + socket.getPort());

				Thread mst1 = new MultiServer(socket);
				mst1.start();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 접속자 리스트 보내기
	public void WaitRoomlist(PrintWriter out) {
		// 출력스트림을 순차적으로 얻어와서 해당 메시지를 출력한다.
		Iterator<String> it = clientMap.keySet().iterator();
		String msg = "대기실 사용자 리스트 [";
		while (it.hasNext()) {
			String name = it.next();
			if(!name.equals("관리자"))
				msg += (String) name + ",";
		}
		msg = msg.substring(0, msg.length() - 1) + "]";
		try {
			out.println(URLEncoder.encode(msg, "UTF-8"));
		} catch (Exception e) {
		}
	}

	public void MyRoomlist(String id, PrintWriter out) {
		// 출력스트림을 순차적으로 얻어와서 해당 메시지를 출력한다.
		int k = find(id);
		Iterator<String> it = r[k].room.keySet().iterator();
		String msg = r[k].name + "방 사용자 리스트 [";
		while (it.hasNext()) {
			String name = it.next();
			if(!name.equals("관리자"))
				msg += (String) name + ",";
		}
		msg = msg.substring(0, msg.length() - 1) + "]";
		try {
			out.println(URLEncoder.encode(msg, "UTF-8"));
		} catch (Exception e) {
		}
	}

	// 관리자 공지때 사용하자!
	// 접속된 모든 클라이언트들에게 메시지를 전달.
   public void sendAllMsg(String user, String msg)
   {
      //출력스트림을 순차적으로 얻어와서 해당 메시지를 출력한다.
      Iterator<String> it = clientMapCopy.keySet().iterator();
      
      while(it.hasNext()) {
         try {
            PrintWriter it_out = (PrintWriter) clientMapCopy.get(it.next());
            if (user.equals(""))
               it_out.println(URLEncoder.encode(msg, "UTF-8"));
            else
            {   
            	it_out.println("※※※※※※※※※※ [공지사항] ※※※※※※※※※※");
            	it_out.println(" [" + URLEncoder.encode(user, "UTF-8") + "] " + URLEncoder.encode(msg, "UTF-8"));
            	it_out.println("※※※※※※※※※※※※※※※※※※※※※※※※※");
            	
            }
         } catch(Exception e) {
            System.out.println("예외:" + e);
         }
      }
   }
   

	public void sendMessage(String user, String msg) {		//대화차단.. it.next()이 차단된 이름이 나오면 pass if문 하나 추가하면 끝
		String next;
		Iterator<String> it = clientMap.keySet().iterator();
		
		while (it.hasNext()) {
			next = it.next();
			
			try {
				PrintWriter it_out = (PrintWriter) clientMap.get(next);
		
				if (user.equals(""))
					it_out.println(URLEncoder.encode(msg, "UTF-8"));
				else
				{	
					if(clientInfo.get(user).block2.indexOf(next) < 0)
						it_out.println("[" + URLEncoder.encode(user, "UTF-8") + "] " + URLEncoder.encode(msg, "UTF-8"));
				}
			} catch (Exception e) {
				System.out.println("예외:sendMessage" + e);
			}
		}
	}
	
	public void sendMessageR(String user, String msg) {
		int k = find(user);
		String next;
		Iterator<String> it = r[k].room.keySet().iterator();
		while (it.hasNext()) {
			next = it.next();
			try {
				PrintWriter it_out = (PrintWriter) r[k].room.get(next);
				if (user.equals(""))
					it_out.println(URLEncoder.encode(msg, "UTF-8"));
				else
				{
					if(clientInfo.get(user).block2.indexOf(next) < 0)
						it_out.println("[" + URLEncoder.encode(user, "UTF-8") + "] " + URLEncoder.encode(msg, "UTF-8"));
				}
					
			} catch (Exception e) {
				System.out.println("예외:sendMessage" + e);
			}
		}
	}
	
	
	public int find(String user) {
		int k = -1;
		for (int i = 0; i < 5; i++) {
			Iterator<String> it = r[i].room.keySet().iterator();
			try {
				while (it.hasNext()) {
					if (user.equals(it.next())) {
						k = i;
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("예외:" + e);
			}
		}
		return k;
	}

	
	public void toMsg(String user, String user2, String msg) {
		try {
			PrintWriter it_outR = (PrintWriter) clientMapCopy.get(user2);
			if (user.equals(""))
				it_outR.println(URLEncoder.encode(msg, "UTF-8"));
			else
			{
				if(clientInfo.get(user).block2.indexOf(user2) < 0)
					it_outR.println(">>[" + URLEncoder.encode(user, "UTF-8") + "]의 귓속말 " + URLEncoder.encode(msg, "UTF-8"));
			}
				
		} catch (Exception e) {
			System.out.println("예외:" + e);
		}
	}

	public void opsend(String user, String msg) {
		// 출력스트림을 순차적으로 얻어와서 해당 메시지를 출력한다.
		try {
			PrintWriter it_outR = (PrintWriter) clientMapCopy.get(user);
			it_outR.println(URLEncoder.encode(msg, "UTF-8"));
		} catch (Exception e) {
			System.out.println("예외:" + e);
		}
	}
	
	
//	public void opsend2(String user, String msg) {
//		// 출력스트림을 순차적으로 얻어와서 해당 메시지를 출력한다.
//		int k = find(user);
//
//		Iterator<String> it = r[k].room.keySet().iterator();
//		while (it.hasNext()) {
//			try {
//				PrintWriter it_out = (PrintWriter) r[k].room.get(it.next());
//				if (user.equals(""))
//					it_out.println(URLEncoder.encode(msg, "UTF-8"));
//				else
//					it_out.println(URLEncoder.encode(msg, "UTF-8"));
//			} catch (Exception e) {
//				System.out.println("예외:sendMessage" + e);
//			}
//		}
//		
//	}

	public static void main(String[] args) {
		// 서버객체 생성.
		Server ms = new Server();
		ms.init();
	}

////////////////////////////////////////////////////////////////
// 내부 클래스
// 클라이언트로부터 읽어온 메시지를 다른 클라이언트(socket)에 보내는 역할을 하는 메서드

	class MultiServer extends Thread {
		Socket socket;
		PrintWriter out = null;
		BufferedReader in = null;
		boolean isRun = true;
		int location = -1;
		int state = 0;
		info fo ;
		
		// 생성자.
		public MultiServer(Socket socket) {
			this.socket = socket;
			try {
				out = new PrintWriter(this.socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
			} catch (Exception e) {
				System.out.println("예외:" + e);
			}
		}

		@Override
		public void run() {
			fo = new info(socket, out);
			String id = "";
			String password = "";
			String name = "";
			String user="";
			
			try {
				String op;
				String s = "";
				while (in != null) {
					String str="";

					if(location == -1)
						out.println(logoinmenu());
					
					if(location == 0 && clientInfo.get(id).ban == 1)
					{
						s = "/out";
					}
					else
					{
						s = in.readLine();
						s = URLDecoder.decode(s, "UTF-8");
					}
					
					System.out.println(id +" : " + s);

					StringTokenizer a = new StringTokenizer(s, " ");
					
					if (String.valueOf(s.charAt(0)).equals("/")) {
						// 토크나이저 안에 넣기
						op = a.nextToken();
						
						if(state == 0)
						{
							if (op.equals("/1") && a.countTokens() == 3 && location == -1 ) {
								id = a.nextToken();
								password = a.nextToken();
								name = a.nextToken();

								sign_up(id, password, name);
							} else if (op.equals("/2") && a.countTokens() == 2 && location == -1) {
								id = a.nextToken();
								password = a.nextToken();
								
								log_in(id, password);
							}else if (op.equals("/3") && a.countTokens() == 2 && location == -1) {
								id = a.nextToken();
								password = a.nextToken();
								log_in(id, password);
							}
						}
						else
						{
							
							if (op.equals("/list") && a.countTokens() == 0 &&(clientInfo.get(id).loc == 0 || clientInfo.get(id).loc == 1)){
								WaitRoomlist(out);
							}
							else if (op.equals("/to") && a.countTokens() >= 2 &&(clientInfo.get(id).loc == 0 || clientInfo.get(id).loc == 1)) {
								out.println(s);
								user = URLDecoder.decode(a.nextToken(), "UTF-8");
								while (a.hasMoreTokens())
									str = str + " " + URLDecoder.decode(a.nextToken(), "UTF-8");

								toMsg(id, user, str);
							} else if (op.equals("/toto") && a.countTokens() == 1 &&(clientInfo.get(id).loc == 0 || clientInfo.get(id).loc == 1)) {
								out.println(s);
								user = URLDecoder.decode(a.nextToken(), "UTF-8");
								
								Iterator<String> it = clientMapCopy.keySet().iterator();
								
								while(it.hasNext())
								{
									if(user.equals(it.next()))
										clientInfo.get(id).whisper = user;
								}
									
								if(clientInfo.get(id).whisper.length() > 0)
								{
									out.println("※"+user + "에게 귓속말 중(끝내려면 /end 입력)※");
								}
								
							}else if (s.equals("/end")) {
								out.println(user + "에게 귓속말 끝");
								clientInfo.get(id).whisper = "";
							}else if (op.equals("/a") && a.countTokens() == 2 && clientInfo.get(id).loc == 0) {
								createroom(id, a.nextToken(), "", a.nextToken());
							} else if (op.equals("/b") && a.countTokens() == 3 && clientInfo.get(id).loc == 0) {
								createroom(id, a.nextToken(), a.nextToken(), a.nextToken());
							} else if (op.equals("/c") && a.countTokens() == 0 && (clientInfo.get(id).loc == 1 || clientInfo.get(id).loc == 0 || (clientInfo.get(id).loc == -1 && id.equals("관리자")))) {
								roomlist();
							} else if (op.equals("/d") && a.countTokens() == 1 && clientInfo.get(id).loc == 0) {
								join(id, a.nextToken());
							}else if (op.equals("/e") && a.countTokens() == 0 && clientInfo.get(id).loc == 0) {
								waitroomop();
							}else if (op.equals("/out") && a.countTokens() == 0 && clientInfo.get(id).loc == 1 || (id.equals("관리자") && clientInfo.get(id).loc == 0) || clientInfo.get(id).ban == 1) {
								out(id);
							} else if (op.equals("/rlist") && a.countTokens() == 0 && clientInfo.get(id).loc == 1) {
								MyRoomlist(id, out);
							} else if (op.equals("/master") && a.countTokens() == 1 && clientInfo.get(id).loc == 1) {
								int k = find(id);
								if (id.equals(r[k].master)) {
									r[k].master = a.nextToken();
									sendMessageR(r[k].master, "님이 방장이 되었습니다.");
									opsend(r[k].master, HostRoomMenu());
								} else
									out.println("※방장만 사용 할 수 있는 명령입니다※");

							} else if (op.equals("/bomb") && a.countTokens() == 0 && clientInfo.get(id).loc == 1){	
								bombRoom(id);
							}else if (op.equals("/kick") && a.countTokens() == 1 && clientInfo.get(id).loc == 1) {
								String next = a.nextToken();
								kickRoom(id,next);
							}else if (op.equals("/pkick") && a.countTokens() == 1 && clientInfo.get(id).loc == 1) {
								String next = a.nextToken();							
								PermanentkickRoom(id, next);
							}else if (op.equals("/unkick") && a.countTokens() == 1 && clientInfo.get(id).loc == 1) {
								String next = a.nextToken();							
								roomUnBan(id, next);
							}else if(op.equals("/invite") && a.countTokens() == 1 && clientInfo.get(id).loc == 1) {
								String next = a.nextToken();
								
								if (id.equals(r[clientInfo.get(id).rnum].master)) {
									opsend(next, id + "님이 방으로 초대를 했습니다. /y 이름  or /n 거절");
									clientInfo.get(next).invite = id +" "+ clientInfo.get(next).invite;
								}
								else
									out.println("※방장만 사용 할 수 있는 명령입니다※");
								
							}else if(op.equals("/y") && a.countTokens() == 1 && (clientInfo.get(id).loc == 1 || clientInfo.get(id).loc == 0)){
								String next = a.nextToken();
								
								if(clientInfo.get(id).invite.indexOf(next) != -1)
								{
									if(clientInfo.get(id).loc == 1)
										out(id);
									
									join(id, clientInfo.get(next).Rname);
									clientInfo.get(id).invite = "";
								}else
									out.println("※ 초대 받은적이 없습니다. ※");
								
							}else if(op.equals("/n") && a.countTokens() == 1 && (clientInfo.get(id).loc == 1 || clientInfo.get(id).loc == 0)){
								String next = a.nextToken();
								if(clientInfo.get(id).invite.length() > 1)
								{
									out.println(next + "초대 거절!");
									opsend(next, id+"님이 초대를 거절 하셨습니다.");
									clientInfo.get(id).invite = clientInfo.get(id).invite.replace(next+" ", "");
								}
								else
									out.println("※ 초대 받은적이 없습니다. ※");
							}
							else if(op.equals("/block") && a.countTokens() == 1 && (clientInfo.get(id).loc == 1 || clientInfo.get(id).loc == 0)) {
								
								//대화상대 차단
								//1. 차단할 상대에 정보객체에 나의 이름을 저장시킴
								//2. 차단당한 상대가 채팅을 칠때 전송하는 함수에서 나를 걸러내고 전송 하게함.
								String next = a.nextToken();
								
								Iterator<String> it = clientMapCopy.keySet().iterator();
								
								while(it.hasNext())
								{
									if(next.equals(it.next()))
									{
										out.println(next+"님을 차단 했습니다.");
										clientInfo.get(next).block2 = id + ","+clientInfo.get(next).block2;
									}
								}
							}
							else if(op.equals("/blockoff") && a.countTokens() == 1 && (clientInfo.get(id).loc == 1 || clientInfo.get(id).loc == 0)) {
								String next = a.nextToken();
								
								Iterator<String> it = clientMapCopy.keySet().iterator();
								
								while(it.hasNext())
								{
									if(next.equals(it.next()))
									{
										out.println(next+"님을 차단 해제 했습니다.");
										clientInfo.get(next).block2 = clientInfo.get(next).block2.replace(id+",", "");
									}
								}
							}
							else if(op.equals("/notify") && id.equals("관리자")) {
								while (a.hasMoreTokens())
									str = str + " " + URLDecoder.decode(a.nextToken(), "UTF-8");
								sendAllMsg(id,str);
								out.println("공지사항 전달 : "+str);
							}
							else if(op.equals("/monitor") && a.countTokens() == 1 && id.equals("관리자")) {
								monitoring(id, a.nextToken());
							}
							else if(op.equals("/ban") && a.countTokens() == 1 && id.equals("관리자")) {
								userBan(a.nextToken());
							}
							else if(op.equals("/unban") && a.countTokens() == 1 && id.equals("관리자")) {
								userUnBan(a.nextToken());
							}
							else if(op.equals("/words") && a.countTokens() == 1 && id.equals("관리자")) {
								wordsBan(a.nextToken());
							}
							else if(op.equals("/delwords") && a.countTokens() == 1 && id.equals("관리자")) {
								delwordsBan(a.nextToken());
							}else if(op.equals("/adbomb") && a.countTokens() == 0 && id.equals("관리자")) {
								adminBomb(id);
							}
							else
								out.println("※ 잘못된 입력입니다 ※");
						}
	
					}
					else
					{
						StringTokenizer a2 = new StringTokenizer(banwords, " ");
						while(a2.hasMoreTokens())
						{
							s = s.replace(a2.nextToken(), "**");
						}
						
						if(location == -1) {
							out.println("로그인을 해주세요.");
						}
						else if(clientInfo.get(id).whisper.length() > 0)			//고정 귓속말용
						{
							out.println(clientInfo.get(id).whisper+"에게 귓속말 >>" + s);
							toMsg(id, clientInfo.get(id).whisper, s);	 
						}
						else if(clientInfo.get(id).loc == 0 && !id.equals("관리자"))				//대기실 대화용
							sendMessage(id, s);
						else if(clientInfo.get(id).loc == 1 && !id.equals("관리자"))				//룸 대화용
							sendMessageR(id, s);
						else if(id.equals("관리자"))
							out.println(s);
					}
					
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 예외가 발생할때 퇴장. 해쉬맵에서 해당 데이터 제거.
				// 보통 종료하거나 나가면 java.net.SocketException: 예외발생

				// 수정 필요~
				//clientMapCopy.remove(id);
				
				int count = 0;
				for (int i = 0; i < 5; i++)
					count += r[i].room.size();

				System.out.println("현재 접속자 수는 " + (clientMap.size() + count) + "명 입니다.");

				String sql = "update LoginDB set state = '0' where id = '" + id + "'";

				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					in.close();
					out.close();

					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		

		public void wordsBan(String word) {
			banwords = word+" "+banwords;
			out.println("서버 금칙어에 "+word+"이 추가되었습니다.");
		}
		public void delwordsBan(String word) {
			banwords = banwords.replace(word+" ", "");
			out.println("서버 금칙어에 "+word+"이 삭제되었습니다.");
		}
		
		public void userBan(String user) {
			//존재하는 사용자인지 확인
			String sql = "update LoginDB set ban = '1' where id = '" + user + "'";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			clientInfo.get(user).out.println("※※※ 서버 ban처리 되었습니다. ※※※");
			clientInfo.get(user).ban = 1;	
		}
		
		public void userUnBan(String user) {
			//존재하는 사용자인지 확인
			String sql = "update LoginDB set ban = '0' where id = '" + user + "'";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String sql2 = "update LoginDB set state = '0' where id = '" + user + "'";
			try {
				stmt.executeUpdate(sql2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			out.println(user+"를 서버에서 ban 해제 했습니다.");
			System.out.println(user+"를 서버에서 ban 해제 했습니다.");
		}
		
		
		public void bombRoom(String id) {
			int k = find(id);
			
			if (id.equals(r[k].master)) {
				sendMessageR(id, "님이 방을 폭파 시켰습니다★★★★★★");
				
				Iterator<String> it = r[k].room.keySet().iterator();
				String next[] = new String[10];
				int i = 0;
				while(it.hasNext())
				{	//참조 도중에 안에 있는애들을 지우면 에러가 난다 2인은 상관 없지만 3인 이상일경우는 문제가 발생!
					next[i] = it.next();
					
					if(next[i].equals("관리자"))
					{
						clientInfo.get(next[i]).loc = -1;
						clientInfo.get(next[i]).rnum = -1;
						clientInfo.get(next[i]).Rname = "";

						adminMap.put(next[i], r[k].room.get(next[i]));
						r[k].admin = 0;
						opsend(next[i], adminMenu());	
						i++;
					}
					else if(!id.equals(next[i]))
					{
						clientInfo.get(next[i]).loc = 0;
						clientInfo.get(next[i]).rnum = -1;
						clientInfo.get(next[i]).Rname = "";
						clientMap.put(next[i], r[k].room.get(next[i]));	
						opsend(next[i], waitmenu());
						i++;
					}		
				}
				
				for(int j = 0 ; j < i ; j++)
					r[k].room.remove(next[j]);			
				
				out(id);
				
			} else
				out.println("※방장만 사용 할 수 있는 명령입니다※");	
		}
		
		public void adminBomb(String id) {
			int k = find(id);
			
			if (id.equals("관리자")) {
				sendMessageR(id, "님이 방을 폭파 시켰습니다★★★★★★");
				
				Iterator<String> it = r[k].room.keySet().iterator();
				String next[] = new String[10];
				int i = 0;
				while(it.hasNext())
				{	//참조 도중에 안에 있는애들을 지우면 에러가 난다 2인은 상관 없지만 3인 이상일경우는 문제가 발생!
					next[i] = it.next();
					
					if(next[i].equals("관리자"))
					{
						clientInfo.get(next[i]).loc = -1;
						clientInfo.get(next[i]).rnum = -1;
						clientInfo.get(next[i]).Rname = "";
						adminMap.put(next[i], r[k].room.get(next[i]));
						r[k].admin = 0;
						opsend(next[i], adminMenu());	
						i++;
					}
					else
					{
						clientInfo.get(next[i]).loc = 0;
						clientInfo.get(next[i]).rnum = -1;
						clientInfo.get(next[i]).Rname = "";
						clientMap.put(next[i], r[k].room.get(next[i]));	
						opsend(next[i], waitmenu());
						i++;
					}		
				}
				
				for(int j = 0 ; j < i ; j++)
					r[k].room.remove(next[j]);
			} else
				out.println("※관리자만 사용 할 수 있는 명령입니다※");	
		}
		
		
		
		public void kickRoom(String id, String user) {
			int k = find(id);
			
			if (id.equals(r[k].master)) {
				if(!user.equals("관리자"))
				{
					sendMessageR(id, user + "를 방에서 추방 시켰습니다※※");
					opsend(user, clientInfo.get(user).Rname+"방에서 추방 되었습니다.");
					clientInfo.get(user).loc = 0;
					clientInfo.get(user).rnum = -1;
					clientInfo.get(user).Rname = "";
					clientMap.put(user, r[k].room.get(user));
					r[k].room.remove(user);
					opsend(user, waitmenu());
				}
				else
					out.println("존재하지 않는 사용자 입니다.");
			} else
				out.println("※방장만 사용 할 수 있는 명령입니다※");
		}
		
		public void PermanentkickRoom(String id, String user) {
			int k = find(id);
			if (id.equals(r[k].master))
			{
				if(!user.equals("관리자"))
				{
					sendMessageR(id, user + "를 방에서 영구적으로 추방 시켰습니다※※");
					opsend(user, clientInfo.get(user).Rname+"방에서 영구적으로 추방 되었습니다.");
					clientInfo.get(user).loc = 0;
					clientInfo.get(user).rnum = -1;
					clientInfo.get(user).Rname = "";
					
					clientMap.put(user, r[k].room.get(user));
					r[k].room.remove(user);
	
					r[k].roombanlist = user + " " + r[k].roombanlist;
					
					opsend(user,waitmenu());
				}
				else
					out.println("존재하지 않는 사용자 입니다.");
			} else
				out.println("※방장만 사용 할 수 있는 명령입니다※");
		}
		
		public void roomUnBan(String id, String user) {
			int k = find(id);
			
			if (id.equals(r[k].master)) {
				out.println(user + "를 방에서 영구 추방 해제 했습니다※※");
				opsend(user, clientInfo.get(user).Rname+"방에서 영구 추방 해제 되었습니다.");

				r[k].roombanlist = r[k].roombanlist.replace(user+" ", "");
			} else
				out.println("※방장만 사용 할 수 있는 명령입니다※");
		}
				
		public void roomlist() {
			out.println("\n       ========대화방 목록========");
			
			for (int i = 0; i < 5; i++) {
				if (r[i].room.size() > 0) {
					try {
						System.out.println(r[i].room.size());
						if(r[i].admin == 1)		// 관리자가 접속 전에는 오류
						{
							if(r[i].password.length() > 0)
								out.println("        " + r[i].roomnum + ". " + r[i].name + " (" + (r[i].room.size() - 1) + "/"	+ r[i].max + ")       비공개방");
							else
								out.println("        " + r[i].roomnum + ". " + r[i].name + " (" + (r[i].room.size() - 1) + "/"	+ r[i].max + ")       공개방");
						}
						else
						{
							if(r[i].password.length() > 0)
								out.println("        " + r[i].roomnum + ". " + r[i].name + " (" + r[i].room.size() + "/"	+ r[i].max + ")       비공개방");
							else
								out.println("        " + r[i].roomnum + ". " + r[i].name + " (" + r[i].room.size() + "/"	+ r[i].max + ")       공개방");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (i == 4) {
					out.println("       =========================");
					out.println();
				}

			}
		}

		public void createroom(String id, String name, String password, String max) {

			if(Integer.parseInt(max) > 0 && Integer.parseInt(max) <10)
			{
				for (int i = 0; i < 5; i++) {
					if (r[i].room.size() == 0) {
						if (password.length() > 0)
							r[i].num = 1;
						else
							r[i].num = 0;
						clientInfo.get(id).rnum = i;
						clientInfo.get(id).Rname = name;
						clientInfo.get(id).loc = 1;
						//fo.Rloc=i;//수정중
						r[i].roomnum = i + 1;
						r[i].name = name;
						r[i].max = Integer.parseInt(max);
						r[i].room.put(id, out);
						r[i].master = id;
						r[i].password = password;
						clientMap.remove(id);
						out.println(r[i].name + " 방 생성");
						System.out.println(id + " - " + (i + 1) + "번 방(" + name + ") 생성");
						out.println(r[i].name + "방에 입장하였습니다.");
						//clientInfo.get(id).loc = 1;
						//isRun = false;
						out.println(HostRoomMenu());
						break;
					}

					if (i == 4)
						out.println("더이상 방을 개설 할 수 없습니다.");
				}
			}
			else
				out.println("입력오류! 정원을 1~10 사이로 입력하세요.");

		}

		public void bombroom(String id) {
			System.out.println(id);
			
			int k = find(id);
			
			sendMessageR(id, "님이 방에서 퇴장하였습니다.");
			System.out.println(id + " - " + r[k].name + "방에서 퇴장");

			if (r[k].room.size() == 1) {
		    	r[k].master = "";
		    	r[k].max=0;
		    	r[k].name="";
		    	r[k].num=0;
		    	r[k].password="";
		    	r[k].state = 0;
			}
			
			r[k].room.remove(id);
			clientMap.put(id, out);
			//clientInfo.get(id).loc = 0;
			location = 0;
			clientInfo.get(id).loc = 0;
			//fo.Rloc=0;//수정중

			out.println(waitmenu());
		}
		
		public void out(String id) {
			int k = find(id);

			if(id.equals("관리자") && clientInfo.get("관리자").loc == 1 )
			{
				out.println("\n※"+r[k].name + "방 모니터링 종료!※");
				out.println("※관리자 대기실로 이동※");
				adminMap.put("관리자", r[k].room.get("관리자"));
				r[k].room.remove(id);
				clientInfo.get("관리자").loc = -1;
				//-
    			clientInfo.get("관리자").rnum = -1;
    			r[k].admin = 0;
    			out.println(adminMenu());
			}
			else if(id.equals("관리자") && clientInfo.get("관리자").loc == 0) {
				out.println("※대기실 모니터링 종료!※");
				out.println("※관리자 대기실로 이동※");
				adminMap.put(id, clientMap.get(id));
				clientMap.remove(id);
				clientInfo.get("관리자").loc = -1;
				//-
    			clientInfo.get("관리자").rnum = -1;
    			out.println(adminMenu());
			}
			else
			{
				if(clientInfo.get(id).loc == 1)
				{
					sendMessageR(id, "님이 방에서 퇴장하였습니다.");
					System.out.println(id + " - " + r[k].name + "방에서 퇴장");
				
				
				if (r[k].master.equals(id)) {
					
					if (r[k].room.size() == 1 || (r[k].room.size() == 2 && r[k].admin == 1)) {
			    		r[k].master = "";
			    		r[k].max=0;
			    		r[k].name="";
			    		r[k].num=0;
			    		r[k].password="";
			    		r[k].state = 0;
			    		
			    		for(int i = 0 ; i < 5 ; i++)
			    			r[k].roombanlist = "";
			    		
			    		if(r[k].admin == 1)
			    		{	
			    			System.out.println("ㅇㅇㅇㅇㅇㅇ");
			    			adminMap.put("관리자", r[k].room.get("관리자"));
			    			r[k].room.remove("관리자");
			    			clientInfo.get("관리자").loc = -1;
			    			//-
			    			clientInfo.get("관리자").rnum = -1;
			    			r[k].admin = 0;
			    		}
					}
					else {
						Iterator<String> it = r[k].room.keySet().iterator();
						String next = it.next();
						
						if(next.equals(id))
						{
							next = it.next();
							if(next.equals("관리자"))
								r[k].master = it.next();
							else
								r[k].master = next;
						}
						else if(next.equals("관리자"))
						{
							next = it.next();
							if(next.equals(id))
								r[k].master = it.next();
							else
								r[k].master = next;
						}
						else
							r[k].master = next;
						
						sendMessageR(r[k].master, "님이 방장이 되었습니다.");
						opsend(r[k].master, HostRoomMenu());
					}
				}
				
				}

				if(clientInfo.get(id).ban == 1)
				{
					location = -1;
					clientMapCopy.remove(id);
					clientMap.remove(id);
					clientInfo.get(id).ban = 0;
					clientInfo.remove(id);
					state = 0;
				}
				else
				{
					r[k].room.remove(id);
					clientMap.put(id, out);
					clientInfo.get(id).loc = 0;
					location=0;
					clientInfo.get(id).rnum = -1;
					clientInfo.get(id).Rname = "";

					out.println(waitmenu());
				}
				
			}

		}

		public void monitoring(String id, String roomname) {
			
			int k = -1;
			for(int i = 0 ; i < 5 ; i++)
			{
				if(r[i].name.equals(roomname))
				{
					k = i;
					break;
				}
			}
			
			if(k != -1)
			{
				if(clientInfo.get(id).loc == 1)
				{
					r[clientInfo.get(id).rnum].admin = 0;
					r[clientInfo.get(id).rnum].room.remove(id); //어떤방에 방에있으면
				}
				
				out.println("※"+r[k].name + "방 모니터링 중...※");
				r[k].room.put(id, out);
				r[k].admin = 1;
				adminMap.remove(id);
				//location = 1;
				clientInfo.get(id).loc = 1;
				clientInfo.get(id).rnum = k;
				clientInfo.get(id).Rname = roomname;
			}
			else if(roomname.equals("대기실"))
			{
				if(clientInfo.get(id).loc == 1)
				{
					clientMap.put(id, out);
					r[k].room.remove(id);
					out.println("※대기실 모니터링 중...※");
				}
				else if(clientInfo.get(id).loc == -1)
				{
					clientMap.put(id, out);
					adminMap.remove(id);
					out.println("※대기실 모니터링 중...※");
				}
				else
					out.println("이미 대기실 모니터링 중입니다");
				
				//location = 0;
				clientInfo.get(id).loc = 0;
				clientInfo.get(id).rnum = -1;
				clientInfo.get(id).Rname = "대기실";
			}
		}
		
		public void join(String id, String roomname) {
			String pass;
			int k = -1;
			boolean kick = true;
			
			//존재하는 방인지 확인
			for(int i = 0 ; i < 5 ; i++)
			{
				if(r[i].name.equals(roomname))
				{
					k = i;
					break;
				}
			}
			
			if(k != -1)
			{
				//방에 차단이 되어있는지 확인
				for(int i = 0 ; i < 5 ; i++)
				{
					if(r[k].roombanlist.indexOf(id) != -1)
					{
						kick = false;
						break;
					}
				}
				
				if(kick) {
					if (r[k].name.equals(roomname) && r[k].num == 0) {
						if ((r[k].room.size()-r[k].admin) < r[k].max) {
							r[k].room.put(id, out);
							clientMap.remove(id);
							System.out.println(id + " - " + roomname + "방 입장");
							out.println(roomname + "방에 입장하였습니다.");
							sendMessageR(id, "님이 방에 입장하였습니다.");
							//location = 1;
							clientInfo.get(id).loc = 1;
							clientInfo.get(id).rnum = k;
							clientInfo.get(id).Rname = roomname;
							//fo.Rloc=k;//수정중
							out.println(GustRoomMenu());
						} else
							out.println(roomname + "방 인원 초과입니다.");
					} else if (r[k].name.equals(roomname) && r[k].num == 1) {
						if ((r[k].room.size()-r[k].admin) < r[k].max) {
							out.println("비밀번호를 입력하세요.");
							try {
								pass = in.readLine();
								pass = URLDecoder.decode(pass, "UTF-8");
								if (pass.equals(r[k].password)) {
									r[k].room.put(id, out);
									clientMap.remove(id);
									System.out.println(id + " - " + roomname + "방 입장");
									out.println(roomname + "방에 입장하였습니다.");
									sendMessageR(id, "님이 방에 입장하였습니다.");
									//location = 1;
									clientInfo.get(id).loc = 1;
									clientInfo.get(id).rnum = k;
									clientInfo.get(id).Rname = roomname;
									//fo.Rloc=k;//수정중
									out.println(GustRoomMenu());
								} else
									out.println("비밀번호가 틀렸습니다!!");
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else
							out.println(roomname + "방 인원 초과입니다.");
					}

				}
				else
					out.println("영구 퇴장된 방입니다.");	
						
			}
			else
				out.println("방 이름을 다시 확인하세요");	
			 

		}

		public void sign_up(String id, String password, String name) {
			String sql = "insert into LoginDB(id, password, name) " + "values ('" + id + "', '" + password + "', '"
					+ name + "')";
			try {
				stmt.executeUpdate(sql);
				out.println("\n★★회원가입 완료★★");
			} catch (SQLException e) {
				System.out.println("가입실패");
			}
		}
		
		public String logoinmenu() {
//			return 		"\n┌───────── [로그인 메뉴] ────────┐\n"
//						+ "│  1.회원가입       ( /1 id password name )	     │\n"
//						+ "│  2.로그인         ( /2 id password )	     │\n"
//						+ "│  3.관리자 로그인  ( /3 id password )	     │\n"
//						+ "└──────────────────────┘\n입력 :";
			return 		"\n┌──────────[로그인 메뉴]─────────┐\n"
						+ "│  1.회원가입      	( /1 id password name ) │\n"
						+ "│  2.로그인         	( /2 id password )             │\n"
						+ "│  3.관리자 로그인 	( /3 id password )             │\n"
						+ "└─────────────────────────┘\n입력 :";
			
			
		}
		
		public String adminMenu() {
			return 		"\n┌──────────── [관리자 메뉴] ───────────┐\n"
						+ "│  ※ 방 모니터링	/monitor	방이름, 대기실       │\n"
						+ "│  ※ 블랙리스트 추가	/ban	이름                         │\n"
						+ "│  ※ 블랙리스트 삭제	/unban	이름                         │\n"
						+ "│  ※ 방 폭파		/adbomb	방이름                     │\n"
						+ "│  ※ 공지사항		/notify	내용                         │\n"
						+ "│  ※ 금칙어 추가	/words	단어                         │\n"
						+ "│  ※ 금칙어 삭제	/delwords	단어                         │\n"
						+ "│  ※ 모니터링 종료	/out		    │\n"
						+ "│  ※ 방리스트보기	/c		    │\n"
						+ "└─────────────────────────────┘\n입력 :";
		}
		
		public String waitmenu() {
			return
					"\n┌────────────[대 기 실]────────────┐\n"
					+ "│  a. 공개방 만들기	( /a 방이름 정원)		│\n"
					+ "│  b. 비공개방 만들기	( /b 방이름 비밀번호 정원)	│\n"
					+ "│  c. 방리스트보기	( /c )		│\n"
					+ "│  d. 대화방 참여	( /d 방이름)		│\n"
					+ "│  e. 대기실 명령어 보기	( /e)		│\n"
					+ "└────────────────────────────┘\n"
					+ "	               < 채팅내용 >                    \n";
			

		}

		public void waitroomop() {
			out.println(
							"\n┌────────────[대기실 명령어]─────────────┐\n"
							+ "│  /to	userID	내용    	(귓속말)	              │\n"
							+ "│  /toto	userID		(귓속말 고정 설정)           │\n"
							+ "│  /block	userID		(대화 차단)	              │\n"
							+ "│  /blockoff	userID		(대화 차단 해제)	              │\n"
							+ "│  /c			(방리스트보기)	              │\n"
							+ "│  /y	userID		(초대 수락)	              │\n"
							+ "│  /n			(초대 거부)	              │\n"
							+ "│  /list			(대기실 인원 조회)           │\n"
							+ "└────────────────────────────────┘\n"
							+ "		< 채팅내용 >                   \n");
		}
		
		public String HostRoomMenu() {
			return							"\n┌─────────────  [룸 메 뉴] ──────────────┐\n"
											+ "│		<Host>		              │\n"
											+ "│  /master	userID		(방장 넘기기)	              │\n"
											+ "│  /kick	userID		(강퇴)	              │\n"
											+ "│  /pkick	userID		(영구 강퇴)	              │\n"
											+ "│  /unkick	userID		(영구 강퇴 해제)	              │\n"
											+ "│  /bomb			(방 폭파)	              │\n"
											+ "│  /invite	userID		(초대)	              │\n"
											+ "│  /to	userID	내용    	(귓속말)	              │\n"
											+ "│  /toto	userID		(귓속말 고정 설정)           │\n"
											+ "│  /block	userID		(대화 차단)	              │\n"
											+ "│  /blockoff	userID		(대화 차단 해제)	              │\n"
											+ "│  /c			(방리스트보기)	              │\n"
											+ "│  /out			(방 나가기)	              │\n"
											+ "│  /y	userID		(초대 수락)	              │\n"
											+ "│  /n			(초대 거부)	              │\n"
											+ "│  /list			(대기실 인원 조회)           │\n"
											+ "│  /rlist			(같은 방 인원 조회)          │\n"
											+ "└────────────────────────────────┘\n"
											+ "		< 채팅내용 >                   \n";
		}
		
		public String GustRoomMenu() {
			return	"\n┌─────────────  [룸 메 뉴] ──────────────┐\n"
					+ "│		<Guest>		              │\n"
					+ "│  /to	userID	내용    	(귓속말)	              │\n"
					+ "│  /toto	userID		(귓속말 고정 설정)           │\n"
					+ "│  /block	userID		(대화 차단)	              │\n"
					+ "│  /blockoff	userID		(대화 차단 해제)	              │\n"
					+ "│  /c			(방리스트보기)	              │\n"
					+ "│  /out			(방 나가기)	              │\n"
					+ "│  /y	userID		(초대 수락)	              │\n"
					+ "│  /n			(초대 거부)	              │\n"
					+ "│  /list			(대기실 인원 조회)           │\n"
					+ "│  /rlist			(같은 방 인원 조회)          │\n"
					+ "└────────────────────────────────┘\n"
					+ "		< 채팅내용 >                   \n";
		}
		
	

		
		public void log_in(String id, String password) {

			String str = "select * from LoginDB where id = '" + id + "'";

			try {
				rs = stmt.executeQuery(str);
				while (rs.next()) {
					if (rs.getString(4).equals("0")) {
						if (rs.getString(5).equals("0")) {
							
							if (rs.getString(2).equals(password) && rs.getString(6).equals("0")) {
								System.out.println(rs.getString(1) + " 채팅서버 로그인");
								out.println("\n★★로그인 성공★★");

								clientMap.put(id, out);
								clientMapCopy.put(id, out);

								String sql = "update LoginDB set state = '1' where id = '" + id + "'";

								try {
									stmt.executeUpdate(sql);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
								sendMessage(id, id + "님이 접속 하셨습니다.");
								clientInfo.put(id, fo);
								clientInfo.get(id).loc = 0;
								clientInfo.get(id).id = id;
								clientInfo.get(id).invite="";
								state = 1;
								location = 0;
								out.println(waitmenu());
								break;
								
							}else if(rs.getString(2).equals(password) && rs.getString(6).equals("1")) {
								System.out.println(rs.getString(1) + " 채팅서버 로그인");
								out.println("\n★★관리자 로그인 성공★★");
								
								adminMap.put(id, out);
								clientInfo.put(id, fo);
								clientInfo.get(id).loc = -1;
								location = -1;
								state = 1;
								//-
								clientInfo.get(id).id = id;
								
								String sql = "update LoginDB set state = '1' where id = '" + id + "'";
								try {
									stmt.executeUpdate(sql);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
								location = 0;	
								out.println(adminMenu());
								
							}else {
								out.println("비밀번호가 틀렸습니다.");
								System.out.println("비밀번호 틀렸습니다.");
							}

						} else
							out.println("이미 접속중인 아이디 입니다.");
					} else
						out.println("차단된 아이디 입니다.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}


class info {
	int	rnum = -1;				//현재 방 위치 초기값 -1  방:0~4
	int loc = -1;			//내 위치(로그인전 -1, 대기실 0, 방안 1);		이후 rnum, loc 수정해서 하나 없애기
	String id;				//내 id
	String Rname;			//참여중인 방 이름
	String invite="";			//초대자 id 저장  -> 여러명 저장으로 고치기
	String whisper="";		//고정 귓속말 상대 저장
	int ban = 0;
	Socket socket ;
	PrintWriter out ;
	BufferedReader in ;
	String[] block = {"","","","",""};		//나를 차단한 사용자 등록
	String block2 = "";
	info(Socket socket, PrintWriter out){
		this.socket = socket;
		this.out = out;
	}
}


// 방 정보 객체
class rm {
	int roomnum;
	int num; // 공개방 비공개방 구분
	String name="";
	String master;
	String password;
	int max;
	int state;
	int admin = 0;
	String roombanlist = "";
	String[] Xwords = {"","","","","","","","","",""}; //금칙어 저장?
	HashMap<String, PrintWriter> room = new HashMap<String, PrintWriter>();
}





//개인정보 데이터 객체
//id, out socket 상태 위치 등등

//
//create table LoginDB(  
//	    id          varchar2(15) UNIQUE NOT NULL,
//	    password    varchar2(15) NOT NULL,
//	    name        varchar2(10) NOT NULL,
//	             char(1) default 0,
//	    state       char(1) default 0,
//	    constraint LoginDB_ban_ck CHECK(ban IN('0','1')),
//	    constraint LoginDB_state_ck CHECK(state IN('0','1'))
//	);
//
//
//	commit;
//
//
//	drop table LoginDB;



//create table LoginDB(  
//	    id          varchar2(15) UNIQUE NOT NULL,
//	    password    varchar2(15) NOT NULL,
//	    name        varchar2(10) NOT NULL,
//	    ban         char(1) default 0,
//	    state       char(1) default 0,
//	    admin       char(1) default 0,
//	    constraint LoginDB_ban_ck CHECK(ban IN('0','1')),
//	    constraint LoginDB_state_ck CHECK(state IN('0','1')),
//	    constraint LoginDB_admin_ck CHECK(admin IN('0','1'))
//	);
//
//	commit;
//
//	drop table LoginDB;
//
//	insert into LoginDB(id,password,name)
//	values('관리자','admin','관리자');
//
//	update LoginDB
//	set admin = '1';