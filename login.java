import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class login extends HttpServlet {
	public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String id=request.getParameter("name");
		String password=request.getParameter("password");
		try{
			
			ServletContext ctx=getServletContext();
			Connection con=(Connection)ctx.getAttribute("con");
			
			
			
			PreparedStatement ps=con.prepareStatement("select id,name,password from employee where id=? and password=?");
			ps.setString(1,id);
			int getid=0; String getname=null;
			ps.setString(2,password);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				
				getid=rs.getInt("id");
				getname=rs.getString("name");
				
				pw.println("<h1>Welcome&nbsp;"+getname+"<br>CID: &emsp;"+getid+"</h1>");
				RequestDispatcher rd=request.getRequestDispatcher("home.html");
				rd.include(request,response);
			}
			else{
				RequestDispatcher rd=request.getRequestDispatcher("index.html");
				rd.include(request,response);

			}
		}catch(Exception e){}
		pw.close();
		
		
	}

}
