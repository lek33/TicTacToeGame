package tictactoe;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Utils.PMF;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AccountServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    String gameKey = req.getParameter("g");
    if (userService.getCurrentUser() == null) {
      //String thisURL = req.getRequestURL().toString();      
      resp.getWriter().println("<p>Please <a href=\"" +
    		  userService.createLoginURL(req.getRequestURI()) + "\">sign in</a>.</p>");
      resp.sendRedirect("index.jsp");
      return;
    }
    PersistenceManager pm = PMF.get().getPersistenceManager();
	}
}
