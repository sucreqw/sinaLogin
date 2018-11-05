package com.sucre.function;


import com.sucre.mainUtil.MyUtil;
import com.sucre.myNet.Nets;


public class Login extends Weibo {

	
	@Override
	public String getId() {
		return super.getId();
	}

	@Override
	public void setId(String id) {
		super.setId(id);
	}

	@Override
	public String getPass() {
		return super.getPass();
	}

	@Override
	public void setPass(String pass) {
		super.setPass(pass);
	}

	@Override
	public String getCookie() {
		return super.getCookie();
	}

	@Override
	public void setCookie(String cookie) {
		super.setCookie(cookie);
	}

	@Override
	public String getUid() {
		return super.getUid();
	}

	@Override
	public void setUid(String uid) {
		super.setUid(uid);
	}

	@Override
	public String getS() {
		return super.getS();
	}

	@Override
	public void setS(String s) {
		super.setS(s);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public Login() {
		super();
	}

	public Login(String id, String pass) {
		super(id, pass);
	}

	public Login(String inputdata) {
		super(inputdata);
	}

	public int logins(int index) {
		Nets net = new Nets();
	
			//String[] temp = MyUtil.listId.get(index).split("[^@.\\w]");
			//String id = temp[0];
			//String pass = temp[1];
			String ret = net.goPost("passport.sina.cn", 443, login(super.getId(), super.getPass()));
			if (!MyUtil.isEmpty(ret)) {
				if( ret.indexOf("20000000") != -1){
					super.setCookie(MyUtil.getAllCookie(ret));
					super.setUid( MyUtil.midWord("uid\":\"", "\",\"", ret));
					MyUtil.outPutData("cookie.txt", super.getCookie() + "|" + super.getUid() + "|" + super.getId() + "|" + super.getPass());
					MyUtil.print("登录成功！" + (index + 1), null);
					return 1;
				}else {
					MyUtil.print("登录失败！" + (index + 1), null);
					MyUtil.print(ret, null);
				}
				if ((index + 1) % 3 == 0) {
					MyUtil.changIp();

				}
			}

		
		return 0;
	}
	
	// 登录接口
	private byte[] login(String id, String pass) {
		StringBuilder data = new StringBuilder(900);
		String temp = "savestate=3650&username=" + id + "&password=" + pass
				+ "&pagerefer=https://sina.cn/&entry=wapsso&loginfrom=1\r\n";
		data=data.append("POST /sso/login HTTP/1.1\r\n");
		data=data.append("Host: passport.sina.cn\r\n");
		data=data.append("Connection: keep-alive\r\n");
		data=data.append("Content-Length: " + temp.length() + "\r\n");
		data=data.append("Origin: http://my.sina.cn\r\n");
		data=data.append(
				"User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36\r\n");
		data=data.append("Content-Type: application/x-www-form-urlencoded\r\n");
		data=data.append("Accept: */*\r\n");
		data=data.append("Referer: http://my.sina.cn/?vt=4&pos=108\r\n");
		data=data.append("Accept-Language: en-US,en;q=0.9\r\n");
		data=data.append("Cookie: \r\n");
		data=data.append("\r\n");
		data=data.append(temp);
		data=data.append("\r\n");

		return data.toString().getBytes();
	}
}
