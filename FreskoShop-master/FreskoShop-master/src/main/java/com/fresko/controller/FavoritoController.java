package com.fresko.controller;

import com.fresko.domain.Usuario;
import com.fresko.service.FavoritoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/favorito")
public class FavoritoController {

        @Autowired
    private FavoritoService favoritoService;
    
    @GetMapping("/listado")
    public String listadoFavoritos(Model model, HttpSession session) {
        model.addAttribute("vistaFavoritos", true);

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            System.out.println("Usuario autenticado: " + usuario.getUsername() + " (id: " + usuario.getIdUsuario() + ")");
            var favoritos = favoritoService.getFavoritosPorUsuario(usuario);
            System.out.println("Cantidad de favoritos: " + favoritos.size());
            model.addAttribute("favoritos", favoritos);
        } else {
            System.out.println("No hay usuario en sesión");
            model.addAttribute("favoritos", List.of());
        }

        return "/favorito/listado";
    }
}
    


