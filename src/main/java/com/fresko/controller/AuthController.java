package com.fresko.controller;

import com.fresko.domain.Usuario;
import com.fresko.service.UsuarioService;
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
    public String guardar(@ModelAttribute("usuarioForm") Usuario usuarioForm) {
        usuarioService.createUsuario(usuarioForm);       
        return "redirect:/auth/login?exito";              
    }

    /* ---------------- Formulario de login ---------------- */
    @GetMapping("/login")
    public String login() {
        return "usuario/login";  
    }
}