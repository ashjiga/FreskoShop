package com.fresko.controller;

import com.fresko.domain.Usuario;
import com.fresko.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    Usuario usuario = usuarioService.getUsuarioPorId(idUsuario);
    model.addAttribute("usuario", usuario); // Asegúrate que el nombre coincide con th:object
    return "usuario/formulario";
}

@PostMapping("/guardar")
public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, 
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
        return "usuario/formulario";
    }
    usuarioService.guardarUsuario(usuario);
    redirectAttributes.addFlashAttribute("success", "Usuario actualizado correctamente");
    return "redirect:/usuario/listado";
}
    
    private boolean usernameCambiado(Usuario usuario) {
    if (usuario.getIdUsuario() != null) {
        Usuario existente = usuarioService.getUsuarioPorId(usuario.getIdUsuario());
        return !existente.getUsername().equals(usuario.getUsername());
    }
    return true;
}
    
   
        @GetMapping("/eliminar/{idUsuario}")
    public String eliminarUsuario(@PathVariable Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return "redirect:/usuario/listado";
    }
    
}