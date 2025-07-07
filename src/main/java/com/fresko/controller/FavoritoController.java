
package com.fresko.controller;

import com.fresko.domain.Favorito;
import com.fresko.domain.Usuario;
import com.fresko.service.FavoritoService;
import com.fresko.service.UsuarioService;
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

    @GetMapping("/listado")
    public String listadoFavoritos(Model model) {
        model.addAttribute("favoritos", List.of());
        return "/favorito/listado";
    }
}