<%@ page import="no.nith.pg6100.Account" %>
<%@ page import="java.util.List" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Alle konti</title>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0-rc-1/pure-min.css">
</head>
<body>
<div><h3>Alle registrerte konti</h3></div>
<div style="margin:10px;">
    <table class="pure-table pure-table-horizontal">
        <thead>
        <tr>
            <th>#</th>
            <th>Kontonavn</th>
            <th>Kontonummer</th>
            <th>Kontotype</th>
        </tr>
        </thead>
        <tbody>

    <%
       final List<Account> allAccounts = (List<Account>) request.getAttribute("allAccounts");
       if (allAccounts != null) {
           for (int i=0;i<allAccounts.size();i++) {
               final Account account = allAccounts.get(i);
    %>
                    <tr>
                        <td><%=i+1%></td>
                        <td><%=account.getAccountName()%></td>
                        <td><%=account.getAccountNumber()%></td>
                        <td><%=account.getAccountType()%></td>
                    </tr>
               <%
           }
       }
   %>
        </tbody>
    </table>
</div>
</body>
</html>
