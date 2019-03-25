package com.hbsi.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

import org.junit.Test;

public class Fdfasf {
	// 客户端
		@Test
		public void client() throws Exception {
			String name = "大马哥";
			SocketChannel skc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8082));
			skc.configureBlocking(false);
			while (true) {
				// 通过管道读取
				ByteBuffer buf = ByteBuffer.allocate(1024);
				buf.put("打飞机啊是".getBytes());
				buf.flip();
				skc.write(buf);
				buf.clear(); // 清空
			}
		}
}
