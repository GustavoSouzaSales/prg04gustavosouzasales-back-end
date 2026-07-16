package br.com.ifba.prg04ultragas.auth.service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Resend resend;
    private final String remetente;

    public EmailService(
            @Value("${resend.api.key}") String apiKey,
            @Value("${resend.from}") String remetente
    ) {
        this.resend = new Resend(apiKey);
        this.remetente = remetente;
    }

    // Envia o código utilizado na verificação da conta
    public void enviarCodigo(String email, String codigo) {

        String html = """
                <div style="
                    max-width: 560px;
                    margin: auto;
                    padding: 32px;
                    font-family: Arial, sans-serif;
                    background-color: #071b33;
                    color: #ffffff;
                    border-radius: 16px;
                ">
                    <h1 style="
                        color: #00aaff;
                        margin-bottom: 24px;
                    ">
                        Verificação de conta
                    </h1>

                    <p>Olá!</p>

                    <p>
                        Recebemos uma solicitação para criar uma conta
                        no sistema Ultragaz.
                    </p>

                    <p>Digite o código abaixo para concluir seu cadastro:</p>

                    <div style="
                        margin: 28px 0;
                        padding: 18px;
                        text-align: center;
                        background-color: #0c2948;
                        border: 1px solid #00aaff;
                        border-radius: 12px;
                        font-size: 32px;
                        font-weight: bold;
                        letter-spacing: 10px;
                        color: #00e5b4;
                    ">
                        %s
                    </div>

                    <p>
                        Este código expira em <strong>1 minuto</strong>.
                    </p>

                    <p style="color: #9bb2ca;">
                        Caso você não tenha solicitado esse cadastro,
                        ignore esta mensagem.
                    </p>

                    <hr style="
                        margin: 28px 0;
                        border: none;
                        border-top: 1px solid #23415f;
                    ">

                    <p style="color: #9bb2ca;">
                        Equipe Ultragaz
                    </p>
                </div>
                """.formatted(codigo);

        enviarEmail(
                email,
                "Código de verificação - Ultragaz",
                html
        );
    }

    // Envia o link utilizado na recuperação da senha
    public void enviarRecuperacaoSenha(String email, String link) {

        String html = """
                <div style="
                    max-width: 560px;
                    margin: auto;
                    padding: 32px;
                    font-family: Arial, sans-serif;
                    background-color: #071b33;
                    color: #ffffff;
                    border-radius: 16px;
                ">
                    <h1 style="
                        color: #00aaff;
                        margin-bottom: 24px;
                    ">
                        Redefinição de senha
                    </h1>

                    <p>Olá!</p>

                    <p>
                        Recebemos uma solicitação para redefinir
                        a senha da sua conta Ultragaz.
                    </p>

                    <p>
                        Clique no botão abaixo para criar uma nova senha:
                    </p>

                    <div style="margin: 30px 0;">
                        <a
                            href="%s"
                            style="
                                display: inline-block;
                                padding: 14px 24px;
                                background-color: #008cff;
                                color: #ffffff;
                                font-weight: bold;
                                text-decoration: none;
                                border-radius: 10px;
                            "
                        >
                            Redefinir minha senha
                        </a>
                    </div>

                    <p>
                        Por segurança, este link expira em
                        <strong>15 minutos</strong>.
                    </p>

                    <p style="color: #9bb2ca;">
                        Caso você não tenha solicitado a redefinição,
                        ignore esta mensagem.
                    </p>

                    <hr style="
                        margin: 28px 0;
                        border: none;
                        border-top: 1px solid #23415f;
                    ">

                    <p style="color: #9bb2ca;">
                        Equipe Ultragaz
                    </p>
                </div>
                """.formatted(link);

        enviarEmail(
                email,
                "Redefinição de senha - Ultragaz",
                html
        );
    }

    private void enviarEmail(
            String destinatario,
            String assunto,
            String html
    ) {
        CreateEmailOptions email = CreateEmailOptions.builder()
                .from(remetente)
                .to(destinatario)
                .subject(assunto)
                .html(html)
                .build();

        try {
            CreateEmailResponse resposta = resend.emails().send(email);

            System.out.println("====================================");
            System.out.println("E-MAIL ENVIADO PELO RESEND");
            System.out.println("Destino: " + destinatario);
            System.out.println("ID do envio: " + resposta.getId());
            System.out.println("====================================");

        } catch (Exception exception) {
            System.err.println("Erro ao enviar e-mail pelo Resend:");
            System.err.println(exception.getMessage());

            throw new RuntimeException(
                    "Não foi possível enviar o e-mail.",
                    exception
            );
        }
    }
}