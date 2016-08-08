import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class HttpRequestHandler implements Runnable {
	
	final static String CRLF = "\r\n";
	 Socket socket = null;
	 InputStream input = null;
	 OutputStream output = null;
	 BufferedReader br = null;
	 // 构造方法
	 public HttpRequestHandler(Socket socket) throws Exception{
	  this.socket = socket;
	  this.input = socket.getInputStream();
	  this.output = socket.getOutputStream();
	  this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	 }
// 实现Runnable 接口的run()方法
 public void run()
 {
	 try {
		 	processRequest();
	 	}catch(Exception e) 
	 	{e.printStackTrace();
	 		System.out.println("客户端请求处理失败!");
	 		System.exit(-1);
	 		
	 	}
 }
//响应请求
 private void processRequest() throws Exception 
 {
	 String i="";//一列整型字符串
	 String i2[];//去除逗号后的字符串
	 int m=0;//记录循环行数
	  while(true) {
		  m+=1;
	   //读取并显示Web 浏览器提交的请求信息
	   String headerLine = br.readLine();
	
	   if(headerLine.equals(CRLF) || headerLine.equals("")) 
		   	break;
	   System.out.println("The client request is " + headerLine);
	   //提取第一行传入的查询字符串
	   try{
	    if(m==1){
	    	//截取i的数值
	    	i =headerLine.substring(headerLine.indexOf('=')+1, headerLine
					.lastIndexOf('/') - 5);
	    	  System.out.println("The client request is " + i);
	    }}catch(Exception e){
	    	e.printStackTrace();
	    }
	   
		  }
	  /*
	   * 求product主要算法
	   */
	  i2=i.split(",");
	  int n=i2.length;//数组长度
	  int [] a=new int[n];//整型数组
	  int [] b=new int[n];//求得的答案
	  //字符串数组转换为整型数组
      for(int j=0;j<n;j++){
          a[j]=Integer.parseInt(i2[j]);
      }
      //求积
      b[0]=1;
      for(int j = 1; j < n; ++j)  
      {  
          b[j] = b[j-1] * a[j-1]; // 分水岭的前半段乘积  
      }  
      b[0] = a[n - 1];  
      for(int j = n - 2; j >= 1; --j)  
     {  
          b[j] *= b[0];  
          b[0] *= a[j]; // 分水岭的后半段乘积，是从数组尾部向前循环的  
      }  
      
      
      
		  //关闭套接字和流
		  try {
			  PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);
			  out.println("HTTP/1.0 200 OK");//返回应答消息,并结束应答
				out.println("Content-Type:text/html;charset=UTF-8");
				out.println();// 根据 HTTP 协议, 空行将结束头信息

				out.println("<h1> 所得乘积结果如下：</h1>");
				out.println("<h2>");
				for(int j=0;j<n;j++){
					out.println(b[j]);
				}
				out.println("</h2>");
				
				  if(out != null)
					  output.close();
		   if(output != null)
			  output.close();
		   if(br != null)
			   br.close();
		   if(br != null)
			   socket.close();
		  }
		  catch(Exception e) {
			  e.printStackTrace();
		  }
 	}
  
}
