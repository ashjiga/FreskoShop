package com.fresko.controller;

import com.fresko.domain.Usuario;
import com.fresko.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class CarritoAdvice {

    @Autowired
    private CarritoService carritoService;

    @ModelAttribute
    public void agregarCantidadCarrito(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            int cantidad = carritoService.getCantidadProductos(usuario);
            model.addAttribute("cantidadCarrito", cantidad);
        }
    }
}