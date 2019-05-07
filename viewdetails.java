import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class viewdetails extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String into=request.getParameter("accountno");
		try{
			ServletContext ctx=getServletContext();
			Connection con=(Connection)ctx.getAttribute("con");
			PreparedStatement ps=con.prepareStatement("Select * from account where accountno=?");
			ps.setString(1,into);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()){
				pw.println(into);
				pw.println(rs.getString("name"));
				pw.println(rs.getString("fathername"));
				pw.println(rs.getString("mothername"));
				pw.println(rs.getString("address"));
				pw.println(rs.getString("city"));
				pw.println(rs.getString("state"));
				pw.println(rs.getString("phoneno"));
				pw.println(rs.getString("type"));
				pw.println(rs.getString("balance"));
				pw.println(rs.getString("zipcode"));
				
			}

		}catch(Exception e){
			e.printStackTrace();
		}
}
}