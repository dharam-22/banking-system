import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class deposit extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String accountno=request.getParameter("accountno");
		String deposit=request.getParameter("deposit");
		int balance=0;
		try{
			ServletContext ctx=getServletContext();
			Connection con=(Connection)ctx.getAttribute("con");			

			PreparedStatement ps=con.prepareStatement("Select balance from account where accountno=?");
			ps.setString(1,accountno);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				balance=rs.getInt("balance");
				
				int amount=Integer.parseInt(deposit);
				balance+=amount;
				
				ps=con.prepareStatement("Update account set balance="+balance);
				ps.executeUpdate();
				pw.println("Amount Submitted. Go back to check ur Balance.......<hr>");
				RequestDispatcher rd=request.getRequestDispatcher("deposit.html");
				rd.include(request,response);

			}
			else{

				pw.println("Sorry for inconvinience<hr>");
				RequestDispatcher rd=request.getRequestDispatcher("deposit.html");
				rd.include(request,response);
			}
		


		}catch(Exception e){}



}







}