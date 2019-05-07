import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
public class Signup extends HttpServlet {
	public static void sendemail(Session session,String to,String subject
    ,String body){
      		  try{
        			    MimeMessage msg=new MimeMessage(session);
            			    msg.setFrom(new InternetAddress("revelations843@gmail.com"));
            			    msg.setSubject(subject);
          			  msg.setText(body);
          			  msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse			(to,false));
			Transport.send(msg);
     		   }catch(Exception e){
        			    e.printStackTrace();
        		}
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String email=request.getParameter("email");
		String pass=request.getParameter("password");
		
		try{
			ServletContext ctx=getServletContext();
			Connection con=(Connection)ctx.getAttribute("con");
			PreparedStatement ps=con.prepareStatement("insert into employee								(name,email,password)  values(?,?,?)");
			ps.setString(1,fname+" "+lname);
			ps.setString(2,email);
			ps.setString(3,pass);
			ps.executeUpdate();
			ps=con.prepareStatement("Select id,email from employee where 					name=? and email=?  and password=?");
			ps.setString(1,fname+" "+lname);
			ps.setString(2,email);
			ps.setString(3,pass);
			ResultSet rs=ps.executeQuery();
			String getID=null,To=null;
			while(rs.next()){
				getID=Integer.toString(rs.getInt("id"));
				To=rs.getString("email");
			}
			
			final String from="revelations843@gmail.com";
            			final String password="rishabh@1234";
            			String to=To;
            			String subject="CID";
            			String body="your cid is: "+getID;
            			Properties p=new Properties();
        		                   p.put("mail.smtp.host", "smtp.gmail.com");
       		                   p.put("mail.smtp.starttls.enable", "true");
                                                          p.put("mail.smtp.auth", "true");
       		                   p.put("mail.smtp.port", "587");
            
            
            
            			Authenticator auth=new Authenticator() {
                    			protected PasswordAuthentication getPasswordAuthentication(){
                     			   return new  PasswordAuthentication(from , password);
                   				 }
           			 };
            			Session session=Session.getDefaultInstance(p, auth);
           				 sendemail(session, to, subject, body);
            			
			pw.println("<body style=\"color:white;\">");
			pw.println(fname+" registered successfully");
			RequestDispatcher rd=request.getRequestDispatcher("signup.html");
			rd.include(request,response);
			ps.close();
		}catch(Exception e){
			pw.println("Srry Server Is too Busy!!!!!!!!!!!");
			RequestDispatcher rd=request.getRequestDispatcher("signup.html");
			rd.include(request,response);
		}
			
			pw.close();
	}

}
