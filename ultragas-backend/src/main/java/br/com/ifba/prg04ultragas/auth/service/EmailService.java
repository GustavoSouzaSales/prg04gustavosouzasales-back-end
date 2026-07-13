package br.com.ifba.prg04ultragas.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Envia o código de verificação para o e-mail
    public void enviarCodigo(String email, String codigo) {

        System.out.println("====================================");
        System.out.println("TENTANDO ENVIAR EMAIL");
        System.out.println("Destino: " + email);
        System.out.println("Código: " + codigo);
        System.out.println("====================================");

        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setTo(email);
        mensagem.setSubject("Código de verificação - Ultragaz");
        mensagem.setText(
                "Olá!\n\n" +
                        "Recebemos uma solicitação para criar uma conta na Ultragaz.\n\n" +
                        "Seu código de verificação é:\n\n" +
                        codigo + "\n\n" +
                        "Este código expira em 1 minuto.\n\n" +
                        "Se você não fez esse cadastro, ignore esta mensagem.\n\n" +
                        "Equipe Ultragaz"
        );

        mailSender.send(mensagem);

        System.out.println("EMAIL ENVIADO COM SUCESSO PARA: " + email);
    }

    // Envia o link para redefinição de senha
    public void enviarRecuperacaoSenha(String email, String link) {

        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setTo(email);
        mensagem.setSubject("Redefinição de senha - Ultragaz");

        mensagem.setText(
                "Olá!\n\n" +
                        "Recebemos uma solicitação para redefinir a senha da sua conta Ultragaz.\n\n" +
                        "Acesse o link abaixo para criar uma nova senha:\n\n" +
                        link + "\n\n" +
                        "Por segurança, este link expira em 15 minutos.\n\n" +
                        "Se você não solicitou a redefinição de senha, ignore este e-mail.\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe Ultragaz"
        );

        mailSender.send(mensagem);
    }
}