package com.hbsi.nio;

public class fgfwa {
	public static void main(String[] args) {
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					SokcetChannel聊天室.client("祥弟");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t2.start();
	}
}
