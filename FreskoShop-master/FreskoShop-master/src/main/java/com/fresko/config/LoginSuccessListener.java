package com.fresko.config;

import com.fresko.domain.Usuario;
import com.fresko.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class LoginSuccessListener {

    @Autowired
    private UsuarioService usuarioService;

    @EventListener
    public void handleLoginSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        Usuario usuario = usuarioService.getUsuarioPorUsername(username); // este método debes tenerlo
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuario);
    }
}
