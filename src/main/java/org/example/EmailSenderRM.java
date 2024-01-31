package org.example;
import java.util.*;
import java.lang.CloneNotSupportedException;
import javax.mail.*;

public class EmailSenderRM {
    private String template;
    private List<String> recipientList;

    public EmailSenderRM(String template) {
        this.template = template;
        this.recipientList = new ArrayList<>();
    }

    public void importRecipients(List<String> recipients) {
        recipientList.addAll(recipients);
    }

    public void addRecipient(String recipient) {
        recipientList.add(recipient);
    }

    public void editRecipient(int index, String newRecipient) {
        recipientList.set(index, newRecipient);
    }

    public void removeRecipient(String recipient) {
        recipientList.remove(recipient);
    }

    public void sendPersonalizedEmails() throws MessagingException {
        for (String recipient : recipientList) {
            Map<String, String> placeholders = new HashMap<>();
            // Customize placeholders for each recipient
            placeholders.put("name", getNameFromRecipient(recipient));
            placeholders.put("email", recipient);

            // Filling the template with actual values for each recipient
            String filledEmail = fillTemplate(placeholders);

            // Sending the personalized email
            sendEmail(recipient, "Your Subject", filledEmail);
        }
    }

    private String getNameFromRecipient(String recipient) {
        // Implement logic to extract the name from the recipient (e.g., from an email address)
        // For simplicity, assuming the recipient is in the format "Name:-Siddhant Gupta <email>"
        String[] parts = recipient.split("\\s+");
        return parts[0].substring(parts[0].indexOf(":") + 1);
    }

    private String fillTemplate(Map<String, String> placeholders) {
        String filledTemplate = template;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            String value = entry.getValue();
            filledTemplate = filledTemplate.replace(placeholder, value);
        }
        return filledTemplate;
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        // Your existing sendEmail implementation
        // ...
    }

    public static void main(String[] args) throws Exception {
        // Example template with placeholders
        String templateContent = "Hello {name},\n\nThank you. Your email is {email}.";

        // Creating an instance of EmailTemplate
        EmailSenderRM emailSender = new EmailSenderRM(templateContent);

        // Managing recipients
        List<String> recipients = Arrays.asList(
                "Name:-John Doe <john@example.com>",
                "Name:-Alice Smith <alice@example.com>"
        );

        emailSender.importRecipients(recipients);
        emailSender.addRecipient("Name:-Bob Johnson <bob@example.com>");
        emailSender.editRecipient(0, "Name:-Updated Name <updated@example.com>");
        emailSender.removeRecipient("Name:-Alice Smith <alice@example.com>");

        // Sending personalized emails
        try {
            emailSender.sendPersonalizedEmails();
            System.out.println("Emails sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Failed to send emails. Error: " + e.getMessage());
        }
    }
}
