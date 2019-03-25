package com.hbsi.nio;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

/**
 * SokectChannel聊天室
 * 
 * @author qqq
 *
 */
public class SokcetChannel聊天室   {
	// 客户端
		public static void client(String name) throws Exception {
			//String name = "方法";
			SocketChannel skc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8082));
			skc.configureBlocking(false);
			// 缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			Scanner sc = new Scanner(System.in);
			while (sc.hasNext()) {
				String str = sc.next();
				//System.out.println(str);
				if(str.equals("退出")){
					buf.put((name + "在" + new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss").format(new Date()) + "时间退出了聊天室").getBytes());
					buf.flip();
					skc.write(buf);
					buf.clear();
					System.out.println(name + "已退出聊天");
					Thread.interrupted();
				}
				buf.put((name + "在" + new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss").format(new Date()) + "说了:" + "\n" + str ).getBytes());
				// 通过管道读取
				buf.flip();
				skc.write(buf);
				buf.clear(); // 清空
			}
			skc.close();
		}
	@Test
	// 服务器
	public void servlet() throws Exception {
		//服务器套接字通道
		ServerSocketChannel ssc = ServerSocketChannel.open();
		// 设置非阻塞
		ssc.configureBlocking(false);
		ssc.bind(new InetSocketAddress(8082)); // 绑定端口
		// 创建一个选择器
		Selector selector = Selector.open();
		// 注册选择器
		ssc.register(selector, SelectionKey.OP_ACCEPT); // 接收
		while (selector.select() > 0) { // 轮询
			// 得到是什么选择器然后进行选择
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();
				// 判断选择器是否有效
				if (key.isAcceptable()) { // 如果是连接的
					SocketChannel channel = ssc.accept(); // 接收
					channel.configureBlocking(false); // 变成非阻塞
					//可以对检查接收的channel是不是为null 判断一下
					// 注册选择器
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					SocketChannel channel = (SocketChannel)key.channel();
					// 缓冲区
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					int len = 0;
					// 从管道中读取数据
					while ((len = channel.read(buffer)) > 0) { //只要是大于0的数据就读取
						buffer.flip(); // 变成读操作
						//buffer.get();
						System.out.println(new String(buffer.array(), 0, len));
						buffer.clear(); // 清空缓冲区
					}
				} else {
					System.out.println("错误退出聊天");
					System.exit(-1);
				}
			}
					it.remove();// 取消选择器
		}

	}
}
