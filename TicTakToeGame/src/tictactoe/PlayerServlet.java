package tictactoe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import Utils.PlayerUtils;

public class PlayerServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String firstName = req.getParameter("firstName");
    PlayerUtils.addPlayerTo(firstName,
                            req.getParameter("friendFirstName"));
    resp.sendRedirect(
        "/games.jsp?firstName=" + firstName);
  }
}
