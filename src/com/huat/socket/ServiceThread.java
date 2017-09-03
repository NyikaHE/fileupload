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
 * 在服务端进行接收和发送数据
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
				cmd.setResult("登陆成功！！");
			} else {
				cmd.setResult("oh my god!!登录失败了。。");
			}
		} else if ("register".equals(cmd.getCmd())) {
			Userserver userService = new Userserver();
			User user = (User) cmd.getData();
			cmd.setFlag(userService.register(user));
			if (cmd.isFlag()) {
				cmd.setResult("恭喜你。注册成功，请登录！");
			} else {
				cmd.setResult("oh my god!!注册失败！！");
			}
		} else if ("uploadFile".equals(cmd.getCmd())) {
			Fileserver fileserver = new Fileserver();
			File file = (File) cmd.getData();
			cmd.setFlag(fileserver.upload(file));
			if (cmd.isFlag()) {
				cmd.setResult("恭喜你，上传成功！");
			} else {
				cmd.setResult("很遗憾，上传失败！");
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
