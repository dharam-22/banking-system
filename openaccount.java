import javax.servlet.*;
import java.sql.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
public class openaccount extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String fname=request.getParameter("fname");
		String mname=""+request.getParameter("mname");
		String lname=request.getParameter("lname");
		String  address=request.getParameter("address");
		String fathername=request.getParameter("fathername");
		String mothername=request.getParameter("mothername");
		String city=request.getParameter("city");
		String state=request.getParameter("state");
		String zipcode=request.getParameter("zipcode");
		String phoneno=request.getParameter("phoneno");
		String type=request.getParameter("account");
		
            
		try{
			ServletContext ctx=getServletContext();
			Connection con=(Connection)ctx.getAttribute("con");
			PreparedStatement ps=con.prepareStatement("insert into account(name,address,fathername,mothername,city,state,zipcode,phoneno,type) values(?,?,?,?,?,?,?,?,?)");
		
		ps.setString(1,fname+" "+mname+" "+lname);
		ps.setString(2,address);
		ps.setString(3,fathername);
		ps.setString(4,mothername);
		ps.setString(5,city);
		ps.setString(6,state);
		ps.setString(7,zipcode);
		ps.setString(8,phoneno);
		ps.setString(9,type);
		ps.executeUpdate();

		 ps=con.prepareStatement("select accountno from account where fathername=? and mothername=? and phoneno=?");
		ps.setString(1,fathername);
		ps.setString(2,mothername);
		ps.setString(3,phoneno);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			pw.println("<br/>"+fname+" your account created successfully & your account no is :"+rs.getInt("accountno"));
			RequestDispatcher rd=request.getRequestDispatcher("openaccount.html");
			rd.include(request,response);	
		}
		else{
			pw.println("Sry no match found!!!!!!!!");
			RequestDispatcher rd=request.getRequestDispatcher("openaccount.html");
			rd.include(request,response);		
		}
		}catch(Exception e){e.printStackTrace();}			
		
}


}