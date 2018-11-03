package com.sucre.function;

import com.sucre.mainUtil.MyUtil;
import com.sucre.myThread.Thread4Net;

public class MainFunction extends Thread4Net {

	// 传递参数到父类，以定义是否循环。
	protected MainFunction(int u, boolean isCircle) {
		super(u, isCircle);
	}

	// 发送网络数据主要逻辑。
	@Override
	public int doWork(int index) {
		MyUtil.print("正在登录！" + (index + 1), null);
		if (MyUtil.listId.getSize() != 0) {
		String[] temp = MyUtil.listId.get(index).split("[^@.\\w]");
		String id = temp[0];
		String pass = temp[1];
		Login weibo = new Login(id, pass);
		int ret = weibo.logins(index);

		}
		return index;
	}

}
