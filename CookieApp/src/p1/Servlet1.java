package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet1
 */
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

   public static Map<String,String> hm=new HashMap<String,String>();
   
   
   static {
	   hm.put("sakshi","xxx");
	   hm.put("shalini", "yyy");
   }
    public  Servlet1() {
    	
    	
    }

    public  boolean isValidate(String username,String password) {
    	
    	String savedPassword = hm.get(username);
		if(savedPassword == null) return false;
		else if(savedPassword.equals(password)) return true;
		else return false;
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		

		Cookie arr[] = request.getCookies();
		String cookieValue = null;
		if (arr != null) 
		{
			for (Cookie cookie : arr) {
				String cookieName = cookie.getName();
				if (cookieName.equals("cookiename")) {
					cookieValue = cookie.getValue();
					break;
				}
			}
		}
		out.print("<html><body>");
		out.print("<form method='post' action='Servlet1'>");

		if(cookieValue==null)
		{
		out.print("Username<input type='text'name='username''>");
	}
	else
	{
		out.print("Username<input type='text'name='username' value='"+cookieValue+"'>");
		
	}
		out.print("Password<input type='password' name='password'>");
		out.print("<input type='checkbox' name='remember' value='remember me'>remember me ");
		out.print("<input type='submit'>");
		out.print("</form></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String tick=request.getParameter("remember me");
		
		//calling the validate
		Servlet1 obj=new Servlet1();
		System.out.println("whsdwe");
		boolean result=obj.isValidate(username,password);
		
		if(result)
		{
			
			out.print("valid success");
			
			//make the cookie
			Cookie ck=new Cookie("cookiename",username);
			response.addCookie(ck);
			ck.setMaxAge(1000*60*5);
			out.print("cookie added");
			
			
			//setting the attribute
			request.setAttribute("username",username);
			request.getRequestDispatcher("Servlet2").forward(request, response);
			
		}else {
			response.sendRedirect("Servlet1");
		}
		
		
	}

}
