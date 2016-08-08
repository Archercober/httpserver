
import java.net.*;
import java.io.*;
import java.util.*;

public class HttpServer
{
	public static void main(String args[]) 
	{
		int port;
		ServerSocket ss = null;
		//读取Server端口号
		try {
				port = Integer.parseInt(args[0]);
			}catch (Exception e) 
			{
				//默认的端口号8080
				port = 8080;
			}
  
		try {
			//监听Server端口，等待连接请求
				ss = new ServerSocket(port);
				System.out.println("httpServer running on port : " + ss.getLocalPort());
				//显示启动信息
			   while(true) 
			   {
				    Socket socket = ss.accept();
				    System.out.println("A new connection accepted " + socket.getInetAddress() +":" + socket.getPort());
				    //创建分线程
				    try {
				    		HttpRequestHandler request = new HttpRequestHandler(socket);
				    		Thread thread = new Thread(request);
				    		//启动线程
				    		thread.start();
				    	}catch(Exception e) 
				    	{
				    		//e.printStackTrace();
				    		System.out.println("The Thread is failed on Loading...");
				    		System.exit(-1);
				    	}
			   }
			}catch (IOException e) 
			{
				System.out.println("Port Monitoring is failed!");
				System.exit(-1);
			}
	}
}

