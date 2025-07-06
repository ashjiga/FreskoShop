package com.fresko.controller;

import com.fresko.domain.Carrito;
import com.fresko.domain.Usuario;
import com.fresko.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/listado")
    public String listadoCarrito(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            var lista = carritoService.getCarritoPorUsuario(usuario);
            model.addAttribute("carrito", lista);
        }
        return "listado"; // 
    }

    @PostMapping("/agregar")
    public String agregarProducto(HttpSession session,
                                  @RequestParam("idProducto") Long idProducto) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            carritoService.agregarProducto(usuario, idProducto);
        }
        return "redirect:/carrito/listado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            carritoService.eliminarProducto(usuario, id);
        }
        return "redirect:/carrito/listado";
    }

    @PostMapping("/comprar")
    public String comprar(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            carritoService.comprar(usuario);
        }
        return "redirect:/producto/listado";
    }
}
