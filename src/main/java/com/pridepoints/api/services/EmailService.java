package com.pridepoints.api.services;

import com.pridepoints.api.entities.Fisica;
import com.pridepoints.api.entities.Funcionario;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    public void enviarNotificacaoAlterarSenha(Funcionario usuario) throws AddressException {
        // Configurações do servidor de e-mail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Configure o servidor SMTP apropriado
        properties.put("mail.smtp.port", "587"); // Porta SMTP
        properties.put("mail.smtp.auth", "true"); // Ativar autenticação
        properties.put("mail.smtp.starttls.enable", "true"); // Ativar TLS

        // Credenciais de e-mail
        final String username = usuario.getEmail(); // Seu e-mail
        final String password = usuario.getSenha(); // Sua senha

        // Configuração da sessão de e-mail
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crie uma mensagem de e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Remetente
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username)); // Destinatário
            message.setSubject("Troca de Senha Obrigatória"); // Assunto
            message.setText("Prezado " + usuario.getNome() + ",\n\n" +
                    "Você precisa trocar sua senha devido a motivos de segurança.\n" +
                    "Por favor, acesse nosso sistema e siga as instruções para redefinir sua senha.\n\n" +
                    "Atenciosamente,\n" +
                    "Sua Equipe de Suporte");

            // Envie a mensagem de e-mail
            Transport.send(message);

            System.out.println("E-mail de notificação enviado com sucesso para " + username);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erro ao enviar e-mail de notificação para " + username);
        }
    }
}
