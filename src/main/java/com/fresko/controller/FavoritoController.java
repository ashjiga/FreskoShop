
package com.fresko.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/favorito")
public class FavoritoController {
    @GetMapping("/listado")
    public String listadoFavoritos(Model model) {
        model.addAttribute("vistaFavoritos", true);
        model.addAttribute("favoritos", List.of());
        return "/favorito/listado";
    }
}