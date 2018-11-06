package com.sucre.function;

import com.sucre.mainUtil.Info;
import com.sucre.mainUtil.MyUtil;
import com.sucre.myNet.Nets;
import com.sucre.myThread.Thread4Net;
import com.sucre.properties.accounts;

public class GuessS extends Thread4Net {

	// 传递参数到父类，以定义是否循环。
	protected GuessS(int u, boolean isCircle) {
		super(u, isCircle);

	}

	@Override
	public int doWork(int index) {
		Nets net = new Nets();
		int sIndex = 0;
		MyUtil.print("开始穷举S参数：" + gets(sIndex) +"<>"+ (index +1), null);
		if (MyUtil.listCookie.getSize() != 0) {
			String[] temp = MyUtil.listCookie.get(index).split("\\|");
			String cookie = temp[0];
			String uid = temp[1];
			String id=temp[2];
			String pass=temp[3];
			String ret;
			while (true) {
				ret = net.goPost("api.weibo.cn", 443, checkIn(cookie, gets(sIndex), uid));
				//穷举成功
				if (ret.indexOf("签到") > 0||ret.indexOf("帐号异常")>0) {
					MyUtil.print("成功猜到S：" + gets(sIndex) +"<>"+ (index +1), null);
					MyUtil.outPutData("cookies.txt", cookie +"|" + uid + "|" + id +"|" + pass +"|" + gets(sIndex));
					
					if((index +1) % 50 ==0){
						MyUtil.changIp();
					}
					return 1;
					//不成功，继续试
				}else{
					sIndex++;
					MyUtil.print("继续穷举S参数：" + gets(sIndex) +"<>"+ (index +1), null);
				}
			}
		}
		return 0;
	}

	private String gets(int index) {
		String key = "0123456789abcdef";
		String ret = "";
		for (int i = 0; i < 8; i++) {
			ret += key.substring(index, index + 1);
		}

		return ret;
	}

	private byte[] checkIn(String cookie, String s, String uid) {
		cookie=MyUtil.midWord("SUB=", ";", cookie);
		StringBuilder data = new StringBuilder(900);
		data.append(
				"GET https://api.weibo.cn/2/page/button?request_url=http%3A%2F%2Fi.huati.weibo.com%2Fmobile%2Fsuper%2Factive_checkin%3Fpageid%3D10080817c0fee819b9c79696a382f9634dbd87&networktype=wifi&extparam=tabbar_follow%234296204685089364&accuracy_level=0&uicode=10000011&moduleID=700&wb_version=3654&c=android&i=f842b7a&s="
						+ s
						+ "&ft=0&ua=HUAWEI-Che2-TL00__weibo__8.6.3__android__android4.4.2&wm=9006_2001&aid=01Anlv2XwdpcqURzkYptXmiLgF3XZdgmTqaHowQEvwWF5xAFc.&fid=10080817c0fee819b9c79696a382f9634dbd87&v_f=2&v_p=62&from=1086395010&gsid="
						+ cookie
						+ "&lang=zh_CN&lfid=100803_-_recentvisit&skin=default&oldwm=9006_2001&sflag=1&cum=E0214B2C HTTP/1.1\r\n");
		data.append("Host: api.weibo.cn\r\n");
		data.append("Connection: keep-alive\r\n");
		data.append("X-Log-Uid: " + uid + "\r\n");
		data.append("User-Agent: Che2-TL00_4.4.2_weibo_8.6.3_android\r\n");
		data.append("\r\n");
		data.append("\r\n");
		return data.toString().getBytes();
	}

}
