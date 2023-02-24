<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Bank account transfer</title>
    <base href=${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/>
  </head>
  <body>
  <form action="transfer" method="post">
    <label>from:
      <input type="text" name="fromActno"><br>
  </label>
    <label>
      to: <input type="text" name="toActno"><br>
    </label>
    <label>
      number:<input type="text" name="money"><br>
    </label>
    <input type="submit" value="transfer">

  </form>
  </body>
</html>
