
package com.fresko.controller;

import com.fresko.domain.Favorito;
import com.fresko.domain.Usuario;
import com.fresko.service.FavoritoService;
import com.fresko.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/favorito/lista")
    public String listarFavoritos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            var favoritos = favoritoService.getFavoritosPorUsuario(usuario);
            model.addAttribute("favoritos", favoritos);
        }
        
        return "favorito/listado";
    }
}