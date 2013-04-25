package Utils;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import entity.Player;

public class PlayerUtils {

  public static Key getKeyForName(String firstName) {
    return KeyFactory.createKey(Player.class.getSimpleName(),
                                firstName);
  }

  public static void addPlayerTo( String firstName,
                                    String playerFirstName) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    String meKey = KeyFactory.keyToString(getKeyForName(firstName));
    String otherKey = KeyFactory.keyToString(
        getKeyForName(playerFirstName));

    Player other = null;
    try {
      pm.currentTransaction().begin();
      try {
        other = pm.getObjectById(Player.class, otherKey);
        List<String> replacementPlayers = new ArrayList<String>(
            other.getGameKeys());
        replacementPlayers.add(meKey);
        other.setGameKeys(replacementPlayers);
      } catch (JDOObjectNotFoundException e) {
        other = new Player(playerFirstName);
        List<String> replacementPlayers = new ArrayList<String>(
            other.getGameKeys());
        replacementPlayers.add(meKey);
        other.setGameKeys(replacementPlayers);
        pm.makePersistent(other);
      }
      pm.currentTransaction().commit();
    } finally {
      if (pm.currentTransaction().isActive()) {
        pm.currentTransaction().rollback();
      }
    }

    pm.close();
    pm = PMF.get().getPersistenceManager();

    Player me = null;
    try {
      pm.currentTransaction().begin();
      try {
        me = pm.getObjectById(Player.class, meKey);
        List<String> replacementPlayers = new ArrayList<String>(
            me.getGameKeys());
        replacementPlayers.add(otherKey);
        me.setGameKeys(replacementPlayers);
      } catch (JDOObjectNotFoundException e) {
        me = new Player(firstName);
        List<String> replacementPlayers = new ArrayList<String>(
            me.getGameKeys());
        replacementPlayers.add(otherKey);
        me.setGameKeys(replacementPlayers);
        pm.makePersistent(me);
      }
      pm.currentTransaction().commit();
    } finally {
      if (pm.currentTransaction().isActive()) {
        pm.currentTransaction().rollback();
      }
    }
  }

  public static List<Player> getPlayersOf(String firstName) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    Query query = pm.newQuery(Player.class);
    String myKey = KeyFactory.keyToString(getKeyForName(firstName));
    query.declareParameters("String myKey");
    query.setFilter("games == myKey");
    query.setOrdering("firstName ASC");
    List<Player> players = (List<Player>) query.execute(myKey);

    return players;
  }
}
