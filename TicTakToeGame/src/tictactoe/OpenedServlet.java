package tictactoe;

import Utils.PMF;

import com.google.appengine.api.datastore.KeyFactory;

import entity.Game;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenedServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String gameId = req.getParameter("gamekey"); 
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Game game = pm.getObjectById(Game.class, KeyFactory.stringToKey(gameId));
    if (gameId != null && req.getUserPrincipal() != null) {
      game.sendUpdateToClients();
      resp.setContentType("text/plain");
      resp.getWriter().println("ok");
    } else {
      resp.setStatus(401);
    }
  }
}