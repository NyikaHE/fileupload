package com.huat.socket;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.huat.entity.File;
import com.huat.entity.User;
import com.huat.util.Command;

/**
 * �ͻ������ӷ������ˣ���ʵ�����ݵĽ��պͷ���
 * 
 * @author HE
 *
 */
public class Client {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Scanner ss;
	private User user;
	private String username;
	private Command cmd;
	private FileInputStream fis;

	public void showmenu() {
		ss = new Scanner(System.in);
		try {
			
			while (true) {
				System.out.println("��������Ҫ���еĲ���\n1.��¼\n2.ע��\n3.�ر�����");
				int i = ss.nextInt();
				switch (i) {
				case 1:
					socket = new Socket("127.0.0.1", 9999);
					oos = new ObjectOutputStream(socket.getOutputStream());
					ois = new ObjectInputStream(socket.getInputStream());
					ShowLogin();
					//���е�¼
					cmd = (Command) ois.readObject();
					System.out.println(cmd.getResult());
					if (cmd.isFlag()) {
						//�����¼�ɹ�������ļ��ϴ�
						try {
							socket = new Socket("127.0.0.1", 9999);
							oos = new ObjectOutputStream(socket.getOutputStream());
							ois = new ObjectInputStream(socket.getInputStream());
							ShowUpload();							
							Command ctd = (Command) ois.readObject();
							System.out.println(ctd.getResult());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 2:
					socket = new Socket("127.0.0.1", 9999);
					oos = new ObjectOutputStream(socket.getOutputStream());
					ois = new ObjectInputStream(socket.getInputStream());
					ShowRegister();
					//����ע��
					Command cmd = (Command) ois.readObject();
					System.out.println(cmd.getResult());
					break;	
				case 3:
					socket = new Socket("127.0.0.1", 9999);
					oos = new ObjectOutputStream(socket.getOutputStream());
					ois = new ObjectInputStream(socket.getInputStream());
					closelink();
					ois.close();
					oos.close();
					socket.close();
				}
			}
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void closelink() {
		// TODO Auto-generated method stub
		cmd =new Command();
		cmd.setCmd("Stopserver");
		try {
			oos.writeObject(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//��¼
	private void ShowLogin() {
		cmd = new Command();
		System.out.println("�������û���������");
		user = new User();
		username = ss.next();
		user.setuname(username);
		user.setpsw(ss.next());

		cmd.setCmd("login");
		cmd.setData(user);
		try {
			oos.writeObject(cmd);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//ע��
	private void ShowRegister() {
		cmd = new Command();
		String psw, ppsw;
		String usn;
		do {
			System.out.println("�������û���������");
			usn = ss.next();
			psw = ss.next();
			System.out.println("���ٴ���������");
			ppsw = ss.next();
		} while (!psw.equals(ppsw));
		User user = new User(usn, psw);
		cmd.setCmd("register");
		cmd.setData(user);
		try {
			oos.writeObject(cmd);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�ļ��ϴ�
	private void ShowUpload() {
		Command cad = new Command();
		System.out.println("�������ļ�·��");
		String path = ss.next();
		String filename = path.substring(path.lastIndexOf("/") + 1);
		try {
			fis = new FileInputStream(filename);
			byte[] content = new byte[fis.available()];
			BufferedInputStream bis=new BufferedInputStream(fis);			
			File file=new File(filename,"hello",content);
			cad.setCmd("uploadFile");
			cad.setData(file);
			oos.writeObject(cad);
			oos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
