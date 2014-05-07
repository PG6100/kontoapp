package no.nith.pg6100;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.swing.*;
import java.awt.*;

public class Main implements MessageListener {

    public static void main(String[] args) {
        new Main().launch();
    }

    private void launch() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
        startConnection();
    }

    private void startConnection() {
        try {
            InitialContext context = new InitialContext();
            ConnectionFactory conFactory = (ConnectionFactory) context.lookup("jms/__defaultConnectionFactory");
            Connection con = conFactory.createConnection();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE); // alt. con.createSession()
            Topic topic = (Topic) context.lookup("jms/Topic");
            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(this);
            con.start();
            System.out.println("Listening to connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createGUI() {
        JFrame frame=new JFrame("Enterprise Programmering");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,500));
        frame.setContentPane(getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    protected JTextArea status=new JTextArea();

    private Container getContentPane() {
        final JPanel container=new JPanel();
        container.setLayout(new BorderLayout());
        container.add(new JLabel("Account monitor"),BorderLayout.NORTH);
        container.add(new JScrollPane(status),BorderLayout.CENTER);
        return container;
    }


    @Override
    public void onMessage(Message message) {
        TextMessage msg=(TextMessage) message;
        try {
            status.append(msg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
            status.append(e.toString());
        }
        Toolkit.getDefaultToolkit().beep();
    }
}
