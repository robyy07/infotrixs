package org.example;
import java.lang.CloneNotSupportedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map;

public class EmailSenderTemplate {

    private String template;

    public EmailSenderTemplate(String template) {
        this.template = template;
    }

    public String fillTemplate(Map<String, String> placeholders) {
        String filledTemplate = template;

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            String value = entry.getValue();
            filledTemplate = filledTemplate.replace(placeholder, value);
        }

        return filledTemplate;
    }

    public void sendEmail(String to, String subject, String content) throws MessagingException {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");//Add/check the SMTP server is running on the system
        properties.setProperty("mail.smtp.port", "587");// and ensure that the localhost is free to use by running telnet command on commandprompt

        // Create a Session object
        Session session = Session.getDefaultInstance(properties);

        // Create a MimeMessage object
        MimeMessage message = new MimeMessage(session);

        // Set the sender and recipient addresses
        message.setFrom(new InternetAddress("robinmothe7474@gmail.com"));//put any example gmail for test and use
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("robinmothe7474@gmail.com"));

        // Set the subject and content of the email
        message.setSubject(subject);
        message.setText(content);


        Transport.send(message);// this is to send the send the email
    }

    public static void main(String[] args)  {
        // Example template with placeholders
        String templateContent = "Hello {name},\n\nThank you And any placeholder message as weell Your email is {email}.";

        // Creating an instance of EmailTemplate
        EmailSenderTemplate emailTemplate = new EmailSenderTemplate(templateContent);

        // Creating a map of placeholders and their values
        Map<String, String> placeholders = new Map<String, String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public String get(Object key) {
                return null;
            }

            @Override
            public String put(String key, String value) {
                return null;
            }

            @Override
            public String remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends String> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<String> values() {
                return null;
            }

            @Override
            public Set<Entry<String, String>> entrySet() {
                return null;
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };
        placeholders.put("name", "Name:-Robin");
        placeholders.put("email", "email:-robinmothe7474@gmail.com");

        // Filling the template with actual values
        String filledEmail = emailTemplate.fillTemplate(placeholders);

        // Sending the email
        try {
            emailTemplate.sendEmail("recipient@example.com", "Any Message as a placeholder for the User to see", filledEmail);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }

}
