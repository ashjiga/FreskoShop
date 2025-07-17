package com.fresko.controller;

import com.fresko.domain.Usuario;
import com.fresko.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/nuevo")
public String nuevoUsuario(Model model) {
    model.addAttribute("usuario", new Usuario());
    return "usuario/formulario";
}

@GetMapping("/modificar/{idUsuario}")
public String modificarUsuario(@PathVariable Long idUsuario, Model model) {
    var usuario = usuarioService.getUsuarioPorId(idUsuario);
    model.addAttribute("usuario", usuario);
    return "usuario/formulario";
}

@GetMapping("/eliminar/{idUsuario}")
public String eliminarUsuario(@PathVariable Long idUsuario) {
    usuarioService.eliminarUsuario(idUsuario);
    return "redirect:/usuario/listado";
}

@PostMapping("/guardar")
public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
    usuarioService.createUsuario(usuario);
    return "redirect:/usuario/listado";
}
    
    
}
