package com.hbsi.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import org.junit.Test;

/**
 * 通道Channel
 * 
 * @author qqq
 *
 */
public class FileChannelDemo {
	static FileChannel channel1;
	static FileChannel channel2;
	static MappedByteBuffer buffer1;
	static MappedByteBuffer buffer2;

	// 传输图片文件
	@Test
	public void channelDemnos() throws Exception {
		channel1 = FileChannel.open(Paths.get("QQ图片20181011202809.jpg"), StandardOpenOption.READ);
		channel2 = FileChannel.open(Paths.get("22.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ,
				StandardOpenOption.CREATE);
		buffer1 = channel1.map(FileChannel.MapMode.READ_ONLY, 0, channel1.size());
		buffer2 = channel2.map(FileChannel.MapMode.READ_WRITE, 0, channel1.size());
		byte[] bytes = new byte[buffer1.limit()];
		buffer1.get(bytes);
		buffer2.put(bytes);

	}

	public static void main(String[] args) throws IOException {
		// 创建管道
		FileChannel channel1 = FileChannel.open(Paths.get("QQ图片20181011202809.jpg"), StandardOpenOption.READ);
		FileChannel channel2 = FileChannel.open(Paths.get("分期分期ccc.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ,
				StandardOpenOption.CREATE);
		// 存入到缓冲区
		MappedByteBuffer buffer1 = channel1.map(FileChannel.MapMode.READ_ONLY, 0, channel1.size());
		MappedByteBuffer buffer2 = channel2.map(FileChannel.MapMode.READ_WRITE, 0, channel1.size());
		byte[] file = new byte[buffer1.capacity()];
		// 存取
		buffer1.get(file); //先获取管道信息
		buffer2.put(file); //然后在取出来信息
		System.out.println("完事");
	}
	@Test
	public void sdad() throws Exception{
		  	//创建管道
			FileChannel inputFile1 = FileChannel.open(Paths.get("QQ图片20181011202809.jpg"), StandardOpenOption.READ);
			FileChannel outputFile2 = FileChannel.open(Paths.get("2csadasdac.jpg"),StandardOpenOption.WRITE,StandardOpenOption.READ,
					StandardOpenOption.CREATE);
			//存入到缓冲区
			MappedByteBuffer buffer1 = inputFile1.map(FileChannel.MapMode.READ_ONLY, 0, inputFile1.size());
			MappedByteBuffer buffer2 = outputFile2.map(FileChannel.MapMode.READ_WRITE, 0, inputFile1.size());
			byte[] file = new byte[buffer1.capacity()];
			//存取
			buffer1.get(file);
			buffer2.put(file);
	}
	@Test
	public void sdadvvv() throws Exception{
		  	//创建管道
			FileChannel inputFile1 = FileChannel.open(Paths.get("QQ图片20181011202809.jpg"), StandardOpenOption.READ,StandardOpenOption.WRITE
					,StandardOpenOption.CREATE);
			FileChannel outputFile2 = FileChannel.open(Paths.get("cc1rrrrr.jpg"),StandardOpenOption.WRITE,StandardOpenOption.READ,
					StandardOpenOption.CREATE);
			//存入到缓冲区
			MappedByteBuffer buffer1 = inputFile1.map(FileChannel.MapMode.READ_WRITE, 0, inputFile1.size());
			MappedByteBuffer buffer2 = outputFile2.map(FileChannel.MapMode.READ_WRITE, 0, inputFile1.size());
			
			byte[] file = new byte[buffer1.capacity()];
			/*System.out.println(file.length);
			buffer1.get(file);
			buffer1.flip();
			buffer1.put(file);*/
			//buffer1.clear();
			//存取
			//buffer1.get();
			buffer1.get(file);
			buffer2.put(file);
	}
	@Test
	public void sdada() throws Exception{
		FileInputStream fis = new FileInputStream("QQ图片20181011202809.jpg");
		FileOutputStream fos = new FileOutputStream("aaaa.jpg");
		FileChannel channel1 = fis.getChannel();
		FileChannel channel2 = fos.getChannel();
		//缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		while(channel1.read(buf) !=-1){
			//修改成可修改的
			buf.flip();
			channel2.write(buf);
			buf.clear(); //循环清空缓冲区往上面写,不清空的话，只能写一次,缓冲区一直堵塞
		}
		System.out.println("完事");
	}
	
	/**
	 * 给缓冲区发数据 通过管道来传输接收
	 * @throws Exception 
	 * 
	 * 
	 * 弊端无法给文件末尾写 可以采用 flieoutput / RandomAccess fc.position(fc.size())。把指针移到最后
	 */
	@Test
	public void fff() throws Exception{
		//一个通道一个缓冲区
		Scanner sc = new Scanner(System.in);
		FileChannel fc = FileChannel.open(Paths.get("Niodemo.txt"), StandardOpenOption.READ,StandardOpenOption.WRITE);
		CharBuffer buf = CharBuffer.allocate(1024);
		//MappedByteBuffer map = fc.map(MapMode.READ_WRITE, 0, 1024);//buffer
		//变换字节码
		//CharsetEncoder encoder = Charset.defaultCharset().newEncoder();
		Charset forName = Charset.forName("UTF-8");
		CharsetEncoder encoder = forName.newEncoder();
		CharsetDecoder decoder = forName.newDecoder();
		//定义一个接收字符串的
		//byte[] str = new byte[1024];
		String str = null;
		//判断是否是退出
		while(!(str = sc.next()).equals("退出")){
			/*
			失败，字节数组无法输入进去
			map.get(str);
			map.flip();
			fc.write(map);
			map.clear();*/
			buf.put(str+"\r\n"); //存放加工科
			buf.flip();
			ByteBuffer buffer = encoder.encode(buf); //转换
			fc.write(buffer);
			//buffer.flip();
			//CharBuffer charBuffer = decoder.decode(buffer);
			//System.out.print(charBuffer.toString());
			buf.clear(); //不清空管道后面无法输入，溢出
		}
	}
	@Test
	public void dasd() throws Exception{
		FileChannel fc = FileChannel.open(Paths.get("nio2.txt"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
		ByteBuffer srcs = ByteBuffer.allocate(1024);
		srcs.put("世界美好2".getBytes());
		srcs.flip(); //写完变成读
		fc.write(srcs);
		
		FileChannel channel = FileChannel.open(Paths.get("nio2.txt"), StandardOpenOption.READ);
		ByteBuffer dst = ByteBuffer.allocate(1000);
		//byte[] str = new byte[dst.limit()];
		//dst.get(str);
		channel.read(dst);
		
		System.out.println(new String(dst.array(),0,dst.limit()));
	}
}
