package com.huat.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.huat.entity.File;
import com.huat.entity.User;
import com.huat.server.Fileserver;
import com.huat.server.Userserver;
import com.huat.util.Command;

/**
 * �ڷ���˽��н��պͷ�������
 * 
 * @author HE
 *
 */
public class ServiceThread implements Runnable {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Command cmd;
	private boolean fc = false;

	public ServiceThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		execute(socket);
	}

	public void execute(Socket soc) {
		try {
			ois = new ObjectInputStream(soc.getInputStream());
			oos = new ObjectOutputStream(soc.getOutputStream());
			cmd = (Command) ois.readObject();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if ("login".equals(cmd.getCmd())) {
			Userserver userService = new Userserver();
			User user = (User) cmd.getData();
			cmd.setFlag(userService.checklogin(user));
			if (cmd.isFlag()) {
				cmd.setResult("��½�ɹ�����");
			} else {
				cmd.setResult("oh my god!!��¼ʧ���ˡ���");
			}
		} else if ("register".equals(cmd.getCmd())) {
			Userserver userService = new Userserver();
			User user = (User) cmd.getData();
			cmd.setFlag(userService.register(user));
			if (cmd.isFlag()) {
				cmd.setResult("��ϲ�㡣ע��ɹ������¼��");
			} else {
				cmd.setResult("oh my god!!ע��ʧ�ܣ���");
			}
		} else if ("uploadFile".equals(cmd.getCmd())) {
			Fileserver fileserver = new Fileserver();
			File file = (File) cmd.getData();
			cmd.setFlag(fileserver.upload(file));
			if (cmd.isFlag()) {
				cmd.setResult("��ϲ�㣬�ϴ��ɹ���");
			} else {
				cmd.setResult("���ź����ϴ�ʧ�ܣ�");
			}
		} else if ("Stopserver".equals(cmd.getCmd())) {
			fc = true;
		}
		try {
			oos.writeObject(cmd);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (fc) {
			try {
				ois.close();
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
