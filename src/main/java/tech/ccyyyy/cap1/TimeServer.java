package tech.ccyyyy.cap1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author seed
 * @date 2019年3月23日 下午8:08:04
 */
public class TimeServer {
	
	public static void main(String[] args) throws IOException {
		int port=8888;
		if(args!=null && args.length>0) {
			port=Integer.valueOf(args[0]);
		}
		
		ServerSocket serverSocket=null;
		
		try {
			serverSocket=new ServerSocket(port);
			System.out.println("The timeServer is start at:"+port);
			Socket socket=null;
			while(true) {
				socket=serverSocket.accept();
			}
		} finally {
			// TODO: handle finally clause
		}
	}

}
