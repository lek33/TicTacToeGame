package entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Player {

  @PrimaryKey
  private String key;
  
  @Persistent
  private String firstName;

  @Persistent(defaultFetchGroup = "true")
  private Collection<String> games;
  
  public Player( String firstName) {
    this.key = firstName;
    this.firstName = firstName;
    this.games = new ArrayList<String>();
  }
  
  public String getKey() {
    return key;
  }
  
  
  public String getFirstName() {
    return firstName;
  }

  public Collection<String> getGameKeys() {
    return games;
  }

  public void setGameKeys(Collection<String> players) {
    this.games = players;
  }
  
  public List<String> getGames() {
    List<String> gameList = new ArrayList<String>();
    for (String gameKey : games) {
      gameList.add(KeyFactory.stringToKey(gameKey).getName());
    }
    return gameList;
  }
}
