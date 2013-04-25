<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Player" %>
<%@ page import="Utils.PlayerUtils" %>
<%@ page import="java.util.List" %>

<html><body>

<%
String firstName = request.getParameter("firstName");
if (firstName == null) firstName = "Barney";

List<Player> players = PlayerUtils.getPlayersOf(firstName);
%>
<h2><%= firstName %> 
<%if (players.isEmpty()) {%>
<div>No players.</div>
<%} else {
    for (Player player : players) {%>
      <div>
        <a href="/players.jsp?firstName=<%= player.getFirstName() %>"><%= player.getFirstName() %></a>
        <span>
          Connects to 
          <%
            List<String> connectedPlayers = player.getGames();
            if (connectedPlayers.isEmpty()) {%>
              no games
          <%} else {
              for (String name : player.getGames()) {%>
                <span><%=name %>;</span>
            <%}
            }%>
        </span>
      </div>	

  <%}
  }%>


<h2>Lookup</h2>
<form action="/players.jsp" method="get">
  <p>
    First name: <input name="firstName" value="<%= firstName %>">
    <input type="submit" value="Lookup">
  </p>
</form>

</body></html>
