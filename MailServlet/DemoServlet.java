import java.io.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.oreilly.servlet.*;

public class DemoServlet extends HttpServlet{
public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
	res.setContentType("text/html");
	PrintWriter out=res.getWriter();
	String path=getServletContext().getRealPath("image");
	MultipartRequest mpr=new MultipartRequest(req,path,500*1024*1024);
	String path1=mpr.getOriginalFileName("file");
	String path2=path+"/"+path1;
	FileInputStream fin=new FileInputStream(path2);
	String email=mpr.getParameter("email");
	String subject=mpr.getParameter("subject");
	String message1=mpr.getParameter("message");
	
	
	
String to=email;
	Properties props=new Properties();
	props.put("mail.smtp.host","smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port","465");
	props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth","true");
	props.put("mail.smtp.port","465");
	try{
		out.println("ddddddd");
		Session session=Session.getInstance(props,new MyAuth());
		MimeMessage message=new MimeMessage(session);
		message.setFrom(new InternetAddress("shwetk214@gmail.com"));
		message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		out.println("ddddddd");
		message.setSubject(subject);
		Multipart body=new MimeMultipart();
		MimeBodyPart part1=new MimeBodyPart();
		part1.setText(message1);
		body.addBodyPart(part1);
		
		MimeBodyPart part2= new MimeBodyPart();
		FileDataSource fds=new FileDataSource(path2);
		part2.setDataHandler(new DataHandler(fds));
		part2.setFileName(fds.getName());
		body.addBodyPart(part2);
		message.setContent(body);
		Transport.send(message);
		out.println("success");
	}
	catch(Exception e){out.println(e.getMessage());}
	out.println("mail send success");
}

}