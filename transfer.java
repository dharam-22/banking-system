import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class transfer extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String from=request.getParameter("from");
		String to=request.getParameter("to");
		pw.println(from);
		pw.println(to);
		String amount=request.getParameter("amount");
		pw.println(amount);
		try{
			ServletContext ctx=getServletContext();
			Connection con=(Connection)ctx.getAttribute("con");
			PreparedStatement ps=con.prepareStatement("Select balance from account where accountno=?");
			ps.setString(1,from);
			ResultSet rs=ps.executeQuery();
			int balanceone=-1,balancetwo=-1;
			
			if(rs.next()){
				balanceone=rs.getInt("balance");
			}
			
			ps=con.prepareStatement("Select balance from account where accountno=?");
			ps.setString(1,to);
			rs=ps.executeQuery();
			if(rs.next()){
				
				balancetwo=rs.getInt("balance");
			}

			if(balanceone>(Integer.parseInt(amount))){
					balanceone=(balanceone-(Integer.parseInt(amount)));
					balancetwo=(balancetwo+(Integer.parseInt(amount)));
					ps=con.prepareStatement("Update account set balance="+balanceone+" where accountno=?");
					ps.setString(1,from);
					ps.executeUpdate();
					ps=con.prepareStatement("Update account set balance="+balancetwo+" where accountno=?");
					
					ps.setString(1,to);
					
					ps.executeUpdate();
					pw.println("transaction completed successfully");
					RequestDispatcher rd=request.getRequestDispatcher("transfer.html");
					rd.include(request,response);
				
				}
				else{
					pw.println("Balance insufficient");
					RequestDispatcher rd=request.getRequestDispatcher("transfer.html");
					rd.include(request,response);
				}
				

		}catch(Exception e){
			e.printStackTrace();
			pw.println(e);
		}



}







}