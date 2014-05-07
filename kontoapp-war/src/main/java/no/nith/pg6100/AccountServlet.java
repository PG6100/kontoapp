package no.nith.pg6100;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    @EJB
    AccountEJB ejb;

    @Resource(lookup = "jms/__defaultConnectionFactory")
    ConnectionFactory conFac;

    @Resource(lookup = "jms/Topic")
    Topic topic;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter out = resp.getWriter();
        try {
            logRequest(req);
            final Account a = constructAccountFromRequest(req);
            ejb.save(a);
            out.println("Saved!");
            final Connection con = conFac.createConnection();
            try {
                sendMessage(a, con);
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }
        out.close();
    }

    private void sendMessage(Account a, Connection con) throws JMSException {
        final Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        try {
            final MessageProducer producer = session.createProducer(topic);
            try {
                final TextMessage tm = session.createTextMessage();
                tm.setText(a.toString());
                producer.send(tm);
            } finally {
                producer.close();
            }
        } finally {
            session.close();
        }
    }

    private Account constructAccountFromRequest(HttpServletRequest req) {
        final Account a=new Account();
        a.setAccountName(req.getParameter("name"));
        a.setPin(Integer.parseInt(req.getParameter("pin")));
        a.setAccountNumber(Integer.parseInt(req.getParameter("accountNumber")));
        a.setAccountType(req.getParameter("accountType"));
        return a;
    }

    private void logRequest(HttpServletRequest req) {
        System.out.println("========================================");
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> en : parameterMap.entrySet()) {
            System.out.println(en.getKey()+":"+ Arrays.toString(en.getValue()));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter out = resp.getWriter();
        try {
            final List<Account> allAccounts = ejb.getAllAccounts();
            StringBuffer s = new StringBuffer("<html><body>");
            for (Account a : allAccounts) {
                s.append(String.format("<div>%s (%d), %s</div>", a.getAccountName(), a.getAccountNumber(), a.getAccountType()));
            }
            s.append("</body></html>");
            out.println(s);
        } catch (Exception e) {
            out.println(e);
        } finally {
            out.close();
        }

    }
}
