package com.fresko.controller;

import com.fresko.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = usuarioService.getUsuarios(); 
        model.addAttribute("usuarios", lista);
        model.addAttribute("totalUsuarios", lista.size());
        return "usuario/listado";
    }
}
