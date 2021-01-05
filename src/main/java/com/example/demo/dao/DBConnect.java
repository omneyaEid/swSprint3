package com.example.demo.dao;
import com.example.demo.model.NotificationTemplat;
import com.example.demo.model.language;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBConnect {
    public static List<NotificationTemplat> EMAIL=new ArrayList<>();
    public static List<NotificationTemplat> SmS=new ArrayList<>();
    public static List<NotificationTemplat> EmailAndSms=new ArrayList<>();
    void add(NotificationTemplat notification) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();
            System.out.println("yes");
            if(notification.getType().equals("Email")) {
                String line = "INSERT INTO Email(id,content,type,language,subject) VALUES("
                        + "'" + notification.getId() + "',"
                        + "'" + notification.getContent() + "',"
                        + "'" + notification.getType() + "',"
                        + "'" + notification.getLanguage() + "',"
                        + "'" + notification.getSubject() + "')";

                Stat.executeUpdate(line);
            }
            else
            {
                String line = "INSERT INTO SMS(id,content,type,language,subject) VALUES("
                        + "'" + notification.getId() + "',"
                        + "'" + notification.getContent() + "',"
                        + "'" + notification.getType() + "',"
                        + "'" + notification.getLanguage() + "',"
                        + "'" + notification.getSubject() + "')";

                Stat.executeUpdate(line);
            }
            Stat.close();
            Con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("no");
        }
    }

    List<NotificationTemplat>selectAllEmail() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();

            String email= "Select * from Email ";
            ResultSet rs = Stat.executeQuery(email);

            while ( rs.next()) {
                int id = rs.getInt("id");
                String content = rs.getString("content");
                String type = rs.getString("type");
                String langu = rs.getString("language");
                String subject = rs.getString("subject");

                String p = id + " " + content + " " + type + " " + langu + " " + subject + " " ;
                //Enum to String using Enum.valueOf()
                Enum lang2 = Enum.valueOf(language.class, langu);

                //Enum to String using Currency.valueOf()
                lang2 = language.valueOf(langu);

                EMAIL.add(new NotificationTemplat(id,content
                        ,type, (language) lang2,subject));


                EmailAndSms.add(new NotificationTemplat(id,content
                        ,type, (language) lang2,subject));

            }

            Stat.close();
            Con.close();
            return EmailAndSms;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("no");
        }
        return null;
    }
    List<NotificationTemplat>selectAllSMS()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();
            String sms = "Select * from SMS";
            ResultSet rs1 = Stat.executeQuery(sms);

            while ( rs1.next()) {
                int id = rs1.getInt("id");
                String content = rs1.getString("content");
                String type = rs1.getString("type");
                String language2 = rs1.getString("language");
                String subject = rs1.getString("subject");

                String p2 = id + " " + content + " " + type + " " + language2 + " " + subject + " " ;
                Enum langsms = Enum.valueOf(language.class, language2);

                //Enum to String using Currency.valueOf()
                langsms = language.valueOf(language2);

                SmS.add(new NotificationTemplat(id,content
                        ,type, (language) langsms,subject));


                EmailAndSms.add(new NotificationTemplat(id,content
                        ,type, (language) langsms,subject));

            }

            Stat.close();
            Con.close();
            return EmailAndSms;


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("no");
        }
        return null;
    }
    List<NotificationTemplat> selectAll()
    {
        selectAllSMS();
        selectAllEmail();
        return EmailAndSms;
    }
    Optional<NotificationTemplat> selectNotificationById(int id)
    {
        return EmailAndSms.stream().filter(notification -> notification.getId()==(id)).findFirst();
    }
    public void DeleteFromDataBase(String type,int ids)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();
            System.out.println("uuu");
            System.out.println(type);
            if(type.equals("SMS")) {

                String sql1 = "DELETE FROM SMS WHERE id ="+ids;
                Stat.execute(sql1);
            }
            else if(type.equals("Email"))
            {
                String sql = "DELETE FROM Email WHERE id =" +ids ;
                Stat.execute(sql);
            }
            System.out.println("hello");
            Stat.close();
            Con.close();
        }catch (ClassNotFoundException | SQLException e) {
            System.out.println("nooooo");
        }
    }
    public int  Delete(int id)
    {
        Optional<NotificationTemplat>notificationMaybe=selectNotificationById(id);
        if(notificationMaybe.isEmpty())
        {
            return 0;
        }
        if(notificationMaybe.get().getType().equals("SMS"))
        {
            DeleteFromDataBase("SMS",id);
        }
        else if(notificationMaybe.get().getType().equals("Email"))
        {
            DeleteFromDataBase("Email",id);
        }
        EmailAndSms.remove(notificationMaybe.get());
        return 1;
    }
    public void UpdateToDataBase(int id,NotificationTemplat update)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();
            PreparedStatement pstatement;
            //System.out.println("uuu");
            if(update.getType().equals("SMS")) {
                String sql = "UPDATE SMS set id=?,content=?,type=?,language=?,subject=?  where id="+id;
                PreparedStatement preparedStmt = Con.prepareStatement(sql);
                preparedStmt.setInt   (1, update.getId());
                preparedStmt.setString(2, update.getContent());
                preparedStmt.setString(3, update.getType());

                preparedStmt.setString(4, update.getLanguage().toString());
                preparedStmt.setString(5, update.getSubject());
                preparedStmt.executeUpdate();
            }
            else if(update.getType().equals("Email"))
            {
                String sql = "UPDATE Email set id=?,content=?,type=?,language=?,subject=?  where id="+id;
                PreparedStatement preparedStmt = Con.prepareStatement(sql);
                preparedStmt.setInt   (1, update.getId());
                preparedStmt.setString(2, update.getContent());
                preparedStmt.setString(3, update.getType());

                preparedStmt.setString(4, update.getLanguage().toString());
                preparedStmt.setString(5, update.getSubject());
                preparedStmt.executeUpdate();
            }
            System.out.println("hello");

        }catch (ClassNotFoundException | SQLException e) {
            System.out.println("nooooo");
        }
    }
    public int update(int id,NotificationTemplat update)
    {
        if(update.getType().equals("SMS"))
        {
            UpdateToDataBase( id, update);
        }
        else if(update.getType().equals("Email"))
        {
            UpdateToDataBase( id, update);
        }
        return 1;
    }
    public void UpdateToDataBaseWithPlaceHolder(int id,PlaceHolder hold)
    {
        try {
            //int idH=1;
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();
            PreparedStatement pstatement;
            System.out.println(hold.getType());
            if(hold.getType().equals("SMS")) {
                String sms = "Select * from SMS";
                ResultSet rs1 = Stat.executeQuery(sms);

                while (rs1.next()) {
                    int ids = rs1.getInt("id");
                    String content = rs1.getString("content");
                    String type = rs1.getString("type");
                    String language2 = rs1.getString("language");
                    String subject = rs1.getString("subject");
                    int idHold=hold.getId();

                    String NewContent = content.replace("{x}", hold.getName());
                    NewContent = NewContent.replace("{y}", hold.getBook());
                    //insert(idHold,NewContent,subject,hold.getMailOrSMS());
                    updateDataBase(hold.getType(),id,NewContent,hold.getMailOrSMS());



                }
            }
            else if(hold.getType().equals("Email"))
            {
                String email = "Select * from Email";
                ResultSet rs1 = Stat.executeQuery(email);
                while (rs1.next()) {
                    int ids = rs1.getInt("id");
                    String content = rs1.getString("content");
                    String type = rs1.getString("type");
                    String language2 = rs1.getString("language");
                    String subject = rs1.getString("subject");
                    int idHold=hold.getId();

                    String p2 = ids + " " + content + " " + type + " " + language2 + " " + subject + " ";
                    String NewContent = content.replace("{x}", hold.getName());
                    NewContent = NewContent.replace("{y}", hold.getBook());

                    updateDataBase(hold.getType(),id,NewContent,hold.getMailOrSMS());
                    ToSendMail(hold.getType());
                }
            }
        }catch (ClassNotFoundException | SQLException e) {
            System.out.println("nooooo");
        }
    }
    public void ToSendMail(String type)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();
            PreparedStatement pstatement;
            if(type.equals("SMS")) {
                String sms = "Select * from SMS";
                ResultSet rs1 = Stat.executeQuery(sms);

                while (rs1.next()) {
                    int ids=rs1.getInt("id");
                    String content = rs1.getString("content");
                    String subject = rs1.getString("subject");
                    String mail = rs1.getString("sms");
                    //Send_Mail(mail, subject, content);

                }
            }
            else if(type.equals("Email")) {
                String EMAIL = "Select * from Email";
                ResultSet rs1 = Stat.executeQuery(EMAIL);

                while (rs1.next()) {
                    int ids=rs1.getInt("id");
                    String content = rs1.getString("content");
                    String subject = rs1.getString("subject");
                    String mail = rs1.getString("email");
                    Send_Mail(mail, subject, content);
                    deleteRow(type, ids);
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteRow(String type,int ids)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();
            PreparedStatement pstatement;
            if (type.equals("SMS")) {
                String sql1 = "DELETE FROM SMS WHERE id =" + ids;
                Stat.execute(sql1);
            } else if (type.equals("Email")) {
                String sql = "DELETE FROM Email WHERE id =" + ids;
                Stat.execute(sql);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updateDataBase(String type,int id,String NewContent,String MailOrSMS)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();
            PreparedStatement pstatement;
            System.out.println(id);
            if (type.equals("Email")) {
                String sql = "UPDATE Email set content=?,email=? where id=" + id;
                PreparedStatement preparedStmt = Con.prepareStatement(sql);
                preparedStmt.setString(1,NewContent);
                preparedStmt.setString(2,MailOrSMS);
                preparedStmt.executeUpdate();
            } else {

                String sql = "UPDATE SMS set content=?,sms=? where id=" + id;
                PreparedStatement preparedStmt = Con.prepareStatement(sql);
                preparedStmt.setString(1,NewContent);
                preparedStmt.setString(2,MailOrSMS);
                preparedStmt.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    void insert(int idH,String NewContent,String MailOrSMS,String subject)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/tom";
            String user = "root";
            String password = "12345";
            Connection Con = null;
            Statement Stat = null;
            Con = DriverManager.getConnection(url, user, password);
            Stat = Con.createStatement();
            PreparedStatement pstatement;
            String sql = "INSERT INTO PlaceHolder(id,content,subject,mailOrSMS)VALUES("
                    + "'" + idH + "',"
                    + "'" + NewContent + "',"
                    + "'" + subject + "',"
                    + "'" +  MailOrSMS+ "')";

            Stat.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int update2(int id,PlaceHolder hold)
    {
        UpdateToDataBaseWithPlaceHolder(id,hold);

        return 1;
    }
    public void Send_Mail(String email , String Subject , String Content)
    {
        String USER_NAME = "advancedswmail@gmail.com";
        String PASSWORD = "advancedsw12345";
        String RECIPIENT = email;

        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT };
        String subject = Subject;
        String body = Content;

        sendFromGMail(from, pass, to, subject, body);
    }
    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (InternetAddress toAddres : toAddress) {
                message.addRecipient(Message.RecipientType.TO, toAddres);
            }

            message.setSubject(subject);
            message.setText(body);
            try (Transport transport = session.getTransport("smtp")) {
                transport.connect(host, from, pass);
                transport.sendMessage(message, message.getAllRecipients());
            }
        }
        catch (AddressException e1) {
            e1.getStackTrace();
        }
        catch (MessagingException e2) {
            e2.getStackTrace();
        }
}
}