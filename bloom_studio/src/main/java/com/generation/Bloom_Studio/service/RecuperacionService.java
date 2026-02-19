package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.dto.CambioPasswordDto;
import com.generation.Bloom_Studio.model.TokenRecuperacion;
import com.generation.Bloom_Studio.model.Usuario;
import com.generation.Bloom_Studio.repository.TokenRecuperacionRepository;
import com.generation.Bloom_Studio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RecuperacionService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRecuperacionRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender; // Inyección del enviador SMTP

    @Autowired
    public RecuperacionService(UsuarioRepository usuarioRepository,
                               TokenRecuperacionRepository tokenRepository,
                               PasswordEncoder passwordEncoder,
                               JavaMailSender mailSender) { // Agregamos al constructor
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    // PASO 1: Generar Token y "Enviar Correo"
    @Transactional
    public void generarTokenRecuperacion(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No existe usuario con ese email"));

        // Generar código único
        String token = UUID.randomUUID().toString();

        // Guardar en BD (Expira en 15 minutos)
        TokenRecuperacion tokenRecuperacion = new TokenRecuperacion(
                token,
                usuario,
                LocalDateTime.now().plusMinutes(15)
        );
        tokenRepository.save(tokenRecuperacion);

        String urlFrontend = "http://127.0.0.1:5502/Login/reset_password.html"; // La ruta del FRONTEND en GoLive
        String link = urlFrontend + "?token=" + token;

        sendEmail(email, "Recuperación de Contraseña - Bloom Studio",
                "Hola " + usuario.getNombre() + ",\n\n" +
                        "Haz click en el siguiente enlace para restablecer tu contraseña:\n" + link +
                        "\n\nEl enlace expira en 15 minutos.");
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message); // Correo
    }

    // PASO 2: Validar Token y Cambiar Contraseña
    @Transactional
    public void cambiarPassword(CambioPasswordDto dto) {
        TokenRecuperacion tokenRecuperacion = tokenRepository.findByToken(dto.getToken())
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        // Validaciones
        if (tokenRecuperacion.getUsado()) {
            throw new RuntimeException("Este token ya fue utilizado");
        }
        if (tokenRecuperacion.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }

        // Actualizar contraseña del usuario
        Usuario usuario = tokenRecuperacion.getUsuario();
        usuario.setContrasena(passwordEncoder.encode(dto.getNewPassword()));
        usuarioRepository.save(usuario);

        // Marcar token como usado
        tokenRecuperacion.setUsado(true);
        tokenRepository.save(tokenRecuperacion);
    }
}
