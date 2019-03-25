package com.hbsi.nio;

import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.junit.Test;

/**
 * Nio分散读取，聚合写入
 * 
 * @author qqq
 *
 */
public class 分散读取聚合写入 {
	@Test
	public void demo1() throws Exception {
		// 分散读取
		RandomAccessFile file = new RandomAccessFile("Niodemo.txt", "rw");
		FileChannel channel = file.getChannel();
		// 创建2个buffer,并且读到2个buffer中
		ByteBuffer buf1 = ByteBuffer.allocate(1024);
		ByteBuffer buf2 = ByteBuffer.allocate(15);
		ByteBuffer[] by = { buf1, buf2 }; // 将不同的buffer数据正好到一个通道
		channel.read(by);
		for (ByteBuffer byteBuffer : by) {
			byteBuffer.flip(); // 把所有的buffer都变成可读模式，必须遍历
		}
		System.out.println(new String(by[0].array(), 0, by[0].limit()));
		System.out.println(new String(by[1].array(), 0, by[1].limit()));
		// 聚合写入
		RandomAccessFile ra1 = new RandomAccessFile("nio2.txt", "rw");
		FileChannel channel2 = ra1.getChannel();
		channel2.write(by);
	}
	@Test
	// 字符问题转换
	public void fff() throws Exception {
		Charset name = Charset.forName("GBK"); //获取编码器
		CharsetEncoder encoder = name.newEncoder(); // 得到编码器
		CharsetDecoder decoder = name.newDecoder(); // 得到解码器
		String s = "就算发积分";
		//创建一个buffer接收
		CharBuffer cbf = CharBuffer.allocate(1024);
		cbf.put(s);
		//变成可读
		cbf.flip();
		ByteBuffer encode = encoder.encode(cbf); //编码
		System.out.println(encode.position());
		System.out.println(encode.limit());
		//使用解码器解码
		encode.rewind(); //必须重新读重读
		CharBuffer charBuffer = decoder.decode(encode);
		System.out.println(charBuffer.toString());
		System.out.println("------------------------");
		//拿着utf-8解析GBK编码  产生乱码
		Charset cf = Charset.forName("UTF-8");
		encode.flip();
		CharBuffer cb2 = cf.decode(encode);
		System.out.println(cb2.toString());
	}

}
