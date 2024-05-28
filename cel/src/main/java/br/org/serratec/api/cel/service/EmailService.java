package br.org.serratec.api.cel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String remetente;

	
	public String enviarEmailTexto(String destinatario,String assunto, String mensagem) {
			
		try {
				SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
				
				simpleMailMessage.setFrom(remetente);
				simpleMailMessage.setTo(destinatario);
				simpleMailMessage.setSubject(assunto);
				simpleMailMessage.setText(mensagem);				
				javaMailSender.send(simpleMailMessage);
				System.out.println(remetente);
				return "Email enviado";
			}catch (Exception e) {
				System.out.println("============");
				return "Error ao tentar enviar email" + e.getLocalizedMessage();
			
			}
	}
	
	public String enviarEmailHtml(String destinatario, String assunto, String mensagem) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(mensagem, true); // O segundo parâmetro define se o texto é HTML

            javaMailSender.send(mimeMessage);
            System.out.println(remetente);
            return "Email enviado";
        } catch (MessagingException e) {
            System.out.println("============");
            return "Erro ao tentar enviar email: " + e.getLocalizedMessage();
        }
    }
}
