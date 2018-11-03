package com.sucre.function;

import com.sucre.mainUtil.Info;
import com.sucre.mainUtil.MyUtil;
import com.sucre.properties.accounts;

public class SinaLogin {

	public static void main(String[] args) {
		int threadNum = Integer.parseInt(args.length != 0 && !"".equals(args[0]) ? args[0] : "1");
		// 导入adsl.properties的信息
		load();

		 if(MyUtil.listId.getSize()!=0){
		 MyUtil.print("开始任务。线程数量：" + threadNum,null);
		 MainFunction m = new MainFunction(MyUtil.listId.getSize() - 1,
		 false);
		
		 for (int i = 0; i < threadNum; i++) {
		
		 Thread t = new Thread(m);
		 t.start();
		 }
		 }else{
		 MyUtil.print("账号导入失败！",null);
		
		 }

//		if (MyUtil.listCookie.getSize() != 0) {
//			MyUtil.print("开始任务。线程数量：" + threadNum, null);
//			GuessS m = new GuessS(MyUtil.listCookie.getSize() - 1, false);
//
//			for (int i = 0; i < threadNum; i++) {
//
//				Thread t = new Thread(m);
//				t.start();
//			}
//		} else {
//			MyUtil.print("cookie导入失败！", null);
//
//		}
	}

	public static void load() {
		try {
			// 导入换ip配置
			Info info = accounts.getInstance();
			MyUtil.loadADSL("adsl.properties", accounts.getInstance());
			System.out.println(info.getADSL() + "<>" + info.getADSLname() + "<>" + info.getADSLpass());
			// 导入cookie
//			MyUtil.loadList("cookie.txt", MyUtil.listCookie);
//			MyUtil.print("导入cookie数量：" + MyUtil.listCookie.getSize(), null);
			// // 导入vid
			// MyUtil.loadList("vid.txt", MyUtil.listVid);
			// System.out.println("导入vid数量：" + MyUtil.listVid.getSize());

			// 导入
			MyUtil.loadList("id.txt", MyUtil.listId);
			MyUtil.print("导入id数量：" + MyUtil.listId.getSize(),null);

		} catch (Exception e) {
			MyUtil.print("导入文件出错：" + e.getMessage(), null);
		}

	}
}
