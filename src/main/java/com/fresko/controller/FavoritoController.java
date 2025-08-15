package com.fresko.controller;

import com.fresko.domain.Usuario;
import com.fresko.service.FavoritoService;
import com.fresko.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/favorito")
public class FavoritoController {

    private final UsuarioService usuarioService;
    private final FavoritoService favoritoService;
    
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
    
 
    @GetMapping("/toggle/{idProducto}")
    public String toggle(@PathVariable Long idProducto, Authentication auth, HttpServletRequest req) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        var u = usuarioService.getUsuarioPorUsername(auth.getName());
        if (favoritoService.existeFavorito(u.getIdUsuario(), idProducto)) {
            favoritoService.eliminarFavorito(u.getIdUsuario(), idProducto);
        } else {
            favoritoService.agregarFavorito(u.getIdUsuario(), idProducto);
        }
        var back = req.getHeader("Referer");
        return "redirect:" + (back != null ? back : "/");
    }

}
    


