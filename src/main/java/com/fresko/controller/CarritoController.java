package com.fresko.controller;

import com.fresko.domain.Carrito;
import com.fresko.domain.Producto;
import com.fresko.domain.Usuario;
import com.fresko.service.CarritoService;
import com.fresko.service.ProductoService;
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

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    public String listadoCarrito(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            var lista = carritoService.getCarritoPorUsuario(usuario);
            model.addAttribute("carrito", lista);

            int cantidad = carritoService.getCantidadProductos(usuario);
            model.addAttribute("cantidadCarrito", cantidad);
        }
        return "listado";
    }

    @PostMapping("/agregar")
    public String agregarProducto(HttpSession session,
            @RequestParam("idProducto") Long idProducto,
            @RequestParam("cantidad") int cantidad) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null && cantidad > 0) {
            carritoService.agregarProducto(usuario, idProducto, cantidad);
        } else {
            return "redirect:/auth/login";
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

    @GetMapping("/producto/{id}")
    public String verDetalleProducto(@PathVariable("id") Long id, HttpSession session, Model model) {
        Producto producto = productoService.getProductoPorId(id);
        if (producto == null) {
            return "redirect:/"; // Redirigir si no se encuentra el producto
        }

        model.addAttribute("producto", producto);

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            int cantidad = carritoService.getCantidadProductos(usuario);
            model.addAttribute("cantidadCarrito", cantidad);
        }

        return "producto/detallesProducto";
    }

    @GetMapping("/checkout")
    public String mostrarCheckout(HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        return "redirect:/auth/login";
    }
    int cantidad = carritoService.getCantidadProductos(usuario);
    model.addAttribute("cantidadCarrito", cantidad);
    return "checkout";
}

    @PostMapping("/checkout")
    public String procesarCheckout(HttpSession session,
            @RequestParam("nombre") String nombre,
            @RequestParam("tarjeta") String tarjeta,
            @RequestParam("expiracion") String expiracion,
            @RequestParam("cvv") String cvv) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario != null) {
        System.out.println("Nombre: " + nombre);
        System.out.println("Tarjeta: " + tarjeta);
        System.out.println("Expiración: " + expiracion);
        System.out.println("CVV: " + cvv);

        carritoService.comprar(usuario);
    }
    return "compra";
    }
}

