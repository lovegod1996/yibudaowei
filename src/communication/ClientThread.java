 package communication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ClientThread implements Runnable
{
	private Socket s;	
	private Handler handler;// 定义向UI线程发送消息的Handler对象	
	public Handler revHandler;// 定义接收UI线程的消息的Handler对象	
	BufferedReader br = null;// 该线程所处理的Socket所对应的输入流
	OutputStream os = null;
	BufferedWriter bw=null;
	public ClientThread(Handler handler)//客户端线程初始化
	{
		this.handler = handler;
	}
	
	
	
	public void run()
	{
		try
		{
			s = new Socket("192.168.155.1", 8144);
			s.setSoTimeout(0);
			br = new BufferedReader(new InputStreamReader(s.getInputStream(),"gbk"));
			os = s.getOutputStream();
		//	bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"gbk"));
		
			new Thread()
			{// 启动一条子线程来读取服务器响应的数据
				@Override
				public void run()
				{
					String content = null;
					// 不断读取Socket输入流中的内容
					try
					{
						while ((content = br.readLine()) != null)
						{
							// 每当读到来自服务器的数据之后，发送消息通知程序
							// 界面显示该数据
							System.out.println(br.readLine());
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = content;
							
							handler.sendMessage(msg);
						}
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				
				
			}.start();
			// 为当前线程初始化Looper
			Looper.prepare();
			// 创建revHandler对象
			revHandler = new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{		// 接收UI线程中用户输入的数据

					if (msg.what == 0x345)
					{	// 将用户在文本框内输入的内容写入网络
						try
						{
							//bw.write(msg.obj.toString());
							os.write((msg.obj.toString() + "\r\n").getBytes("gbk"));
						//	bw.flush();
						}
						catch (Exception e)
						{
							System.out.println("尝试重连");
							try {
							      s.close();
								s = new Socket("192.168.191.1", 8132);
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								System.out.println("io错误");
								e1.printStackTrace();
							}
							e.printStackTrace();
						}
						try {
							os.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					   
					   
					   
					   
				}
			};
			// 启动Looper
			Looper.loop();
		}
		catch (SocketTimeoutException e1)
		{
			System.out.println("网络连接超时！！");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

