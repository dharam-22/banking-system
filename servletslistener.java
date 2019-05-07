import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class servletslistener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent e){
		try{
			ServletContext context=e.getServletContext();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","system");
			context.setAttribute("con",con);
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}
	public void contextDestroyed(ServletContextEvent e2){
	}



}