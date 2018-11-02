package com.sucre.function;

import com.sucre.mainUtil.Info;
import com.sucre.mainUtil.MyUtil;
import com.sucre.myNet.Nets;
import com.sucre.myThread.Thread4Net;
import com.sucre.properties.accounts;

public class MainFunction extends Thread4Net {

	// 传递参数到父类，以定义是否循环。
	protected MainFunction(int u, boolean isCircle) {
		super(u, isCircle);
	}
	//发送网络数据主要逻辑。
	@Override
	public int doWork(int index) {
		Nets net = new Nets();
		MyUtil.print("正在登录！"+ (index +1),null);
		if (MyUtil.listId.getSize() != 0) {
			String[] temp = MyUtil.listId.get(index).split("[^@.\\w]");
			String id = temp[0];
			String pass = temp[1];
			String ret = net.goPost("passport.sina.cn", 443, login(id, pass));
			if (!MyUtil.isEmpty(ret)) {
				String cookie = MyUtil.getAllCookie(ret);
				String uid = MyUtil.midWord("uid\":\"", "\",\"", ret);
				MyUtil.outPutData("cookie.txt", cookie +"|" + uid + "|" + id +"|" + pass);
				MyUtil.print("登录成功！"+ (index +1),null);
				if((index+1) % 50 == 0){
					MyUtil.print("准备换ip",null);
					Info info = accounts.getInstance();
					MyUtil.cutAdsl(info.getADSL());
					MyUtil.sleeps(2000);
					MyUtil.connAdsl(info.getADSL(), info.getADSLname(), info.getADSLpass());
					MyUtil.sleeps(2000);
					
				}
			}

		}
		return index;
	}
	//登录接口
	private byte[] login(String id, String pass) {
		StringBuilder data = new StringBuilder(900);
		String temp = "savestate=3650&username=" + id + "&password=" + pass
				+ "&pagerefer=https://sina.cn/&entry=wapsso&loginfrom=1\r\n";
		data.append("POST /sso/login HTTP/1.1\r\n");
		data.append("Host: passport.sina.cn\r\n");
		data.append("Connection: keep-alive\r\n");
		data.append("Content-Length: " + temp.length() + "\r\n");
		data.append("Origin: http://my.sina.cn\r\n");
		data.append(
				"User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36\r\n");
		data.append("Content-Type: application/x-www-form-urlencoded\r\n");
		data.append("Accept: */*\r\n");
		data.append("Referer: http://my.sina.cn/?vt=4&pos=108\r\n");
		data.append("Accept-Language: en-US,en;q=0.9\r\n");
		data.append("Cookie: \r\n");
		data.append("\r\n");
		data.append(temp);
		data.append("\r\n");

		return data.toString().getBytes();
	}
}
