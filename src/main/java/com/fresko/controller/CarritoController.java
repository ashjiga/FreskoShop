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
    private ProductoService productoService;
      
  @Autowired
    private CarritoService carritoService;
  
@GetMapping("/listado")
public String listadoCarrito(HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario != null) {
        var lista = carritoService.getCarritoPorUsuario(usuario);
        model.addAttribute("carrito", lista);

        int cantidad = lista.stream().mapToInt(item -> item.getCantidad()).sum();
        model.addAttribute("cantidadCarrito", cantidad);

        double subtotal = lista.stream()
                .mapToDouble(item -> item.getCantidad() * item.getProducto().getPrecio())
                .sum();
        
        double impuestos = subtotal * 0.13; 
        double total = subtotal + impuestos;
        
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("impuestos", impuestos);
        model.addAttribute("total", total);
    }
    return "carrito/listado";
}
    
    @PostMapping("/aumentar")
public String aumentarProducto(@RequestParam("idProducto") Long idProducto, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario != null) {
        carritoService.aumentarCantidad(usuario, idProducto);
    }
    return "redirect:/carrito/listado";
}

@PostMapping("/restar")
public String restarProducto(@RequestParam("idProducto") Long idProducto, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario != null) {
        carritoService.restarCantidad(usuario, idProducto);
    }
    return "redirect:/carrito/listado";
}
    
    

    @PostMapping("/agregar")
    public String agregarProducto(HttpSession session,
            @RequestParam("idProducto") Long idProducto,
            @RequestParam("cantidad") int cantidad,
            Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null && cantidad > 0) {
            carritoService.agregarProducto(usuario, idProducto, cantidad);
        } else {
            return "redirect:/auth/login";
        }
        return "redirect:/";
    }

//    @PostMapping("/eliminar")
//    public String eliminarProducto(HttpSession session, @RequestParam("idProducto") Long idProducto) {
//        Usuario usuario = (Usuario) session.getAttribute("usuario");
//        if (usuario != null) {
//            carritoService.eliminarProductoPorIdProducto(usuario, idProducto);
//        }
//        return "listado :: #seccion-carrito";
//    }
    
        @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, HttpSession session) {
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

    @GetMapping("/checkout")
    public String mostrarCheckout(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        int cantidad = carritoService.getCantidadProductos(usuario);
        model.addAttribute("cantidadCarrito", cantidad);
        return "carrito/checkout";
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
        return "carrito/compra";
    }
}
