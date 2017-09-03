package com.huat.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 连接服务器
 * @author HE
 *
 */
public class Service {
	public static void main(String[] args){
		ServerSocket ss=null;
		int i=0;
		try {
			ss=new ServerSocket(9999);
			while(true){
				Socket socket=ss.accept();
				System.out.println("当前连接数"+i++);
				ServiceThread serverthread=new ServiceThread(socket);
				Thread thread=new Thread(serverthread);
				thread.start();
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
