package com.BLCMWEB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarConfirmacionAudicion(String correoDestino, String nombre, String apellidos,
            String seccion, String telefono, Long cedula, String comentarios) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correoDestino);
        mensaje.setSubject("¡Recibimos tu solicitud, " + nombre + "!");

        StringBuilder texto = new StringBuilder();
        texto.append("Hola ").append(nombre).append(" ").append(apellidos).append(",\n\n")
                .append("¡Gracias por tu interés en unirte a la Banda Lírica Cantonal de Moravia! \n\n")
                .append("Hemos recibido tu solicitud de audición para la sección: ").append(seccion).append("\n\n")
                .append("Datos registrados:\n")
                .append("- Cédula: ").append(cedula).append("\n")
                .append("- Correo: ").append(correoDestino).append("\n")
                .append("- Teléfono: ").append(telefono).append("\n");

        if (comentarios != null && !comentarios.isBlank()) {
            texto.append("- Comentarios: ").append(comentarios).append("\n");
        }

        texto.append("\nTu solicitud ya está en proceso de revisión. Muy pronto alguien de nuestro equipo ")
                .append("se pondrá en contacto contigo para coordinar los siguientes pasos.\n\n")
                .append("¡Nos vemos pronto en los ensayos!\n\n")
                .append("— Banda Lírica Cantonal de Moravia");

        mensaje.setText(texto.toString());
        mailSender.send(mensaje);
    }
}
