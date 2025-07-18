package com.fresko.controller;

import com.fresko.domain.Usuario;
import com.fresko.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    /* ---------------- Formulario de registro ---------------- */
    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        return "usuario/registro";  
    }

    /* ---------------- Procesar registro ---------------- */
  @PostMapping("/registro")
public String guardar(
    @ModelAttribute("usuarioForm") Usuario usuarioForm,
    HttpSession session // para verificar rol
) {
    // Validar username
    if (usuarioService.getUsuarioPorUsername(usuarioForm.getUsername()) != null) {
        return "redirect:/auth/registro?error=usuario";
    }
        // Asignar ROLE_CLIENTE si no es administrador
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        if (usuarioSesion == null || !usuarioSesion.getRol().equals("ROLE_ADMIN")) {
            // Usuario normal - siempre ROLE_CLIENTE
            usuarioForm.setRol("ROLE_CLIENTE");
        } else {
            // Admin - verificar que el rol sea válido
            if (!usuarioForm.getRol().matches("ROLE_(CLIENTE|TRABAJADOR|ADMIN)")) {
                usuarioForm.setRol("ROLE_CLIENTE"); // Valor por defecto si es inválido
            }
        }

        usuarioService.createUsuario(usuarioForm);

        return (usuarioSesion != null && usuarioSesion.getRol().equals("ROLE_ADMIN"))
                ? "redirect:/usuario/listado"
                : "redirect:/auth/login?exito";
    }

    /* ---------------- Formulario de login ---------------- */
    @GetMapping("/login")
    public String login() {
        return "usuario/login";  
    }
}