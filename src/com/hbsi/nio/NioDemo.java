package com.hbsi.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/**
 * Nio的基本操作
 * 0<=mark<=position<=limit<=capacity
 * @author qqq
 *
 */
public class NioDemo {
	@Test
	public void NioDemo2(){
		//直接缓冲区
		ByteBuffer direct = ByteBuffer.allocateDirect(1024);
		String bys = "qwerty";
		direct.put(bys.getBytes());
		direct.flip();
		byte[] dst = new byte[direct.limit()];
		direct.get(dst);
		System.out.println(new String(dst,0,dst.length));
	}
	
	@Test
	public void Niodemo1(){
		//创建一个缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024); //普通的非直接内存缓冲区
		String bys = "qwerty";
		buf.put(bys.getBytes());
/*		System.out.println(buf.position()); //指针位置
		System.out.println(buf.limit()); //最大位置
		System.out.println(buf.capacity()); //容量
*/		//读取的话 需要flip,执行get必须flip
		buf.flip();
	/*	System.out.println("--------get方法---------");
		String s = new String(bys,0,buf.limit());
		System.out.println(s);
		System.out.println(buf.position()); //指针位置
		System.out.println(buf.limit()); //最大位置
		System.out.println(buf.capacity()); //容量
*/
		byte[] dst = new byte[buf.limit()];
		buf.get(dst); //buf.get(dst,2,4);
		String s = new String(dst,0,buf.limit());
		System.out.println(s);
		System.out.println("--------get方法---------");
		//System.out.println(s);
		buf.mark();
		System.out.println(buf.position()); //指针位置
		System.out.println(buf.limit()); //最大位置
		System.out.println(buf.capacity()); //容量
		/*buf.rewind(); //重复读
		System.out.println(buf.position()); //指针位置
		System.out.println(buf.limit()); //最大位置
		System.out.println(buf.capacity()); //容量
*/	}
}
