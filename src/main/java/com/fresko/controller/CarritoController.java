package com.fresko.controller;

import com.fresko.domain.Usuario;
import com.fresko.service.CarritoService;
import com.fresko.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            @RequestParam(name = "cantidad", defaultValue = "1") int cantidad,
            Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null && cantidad > 0) {
            carritoService.agregarProducto(usuario, idProducto, cantidad);
        } else {
            return "redirect:/auth/login";
        }
        return "redirect:/";
    }

    @PostMapping("/eliminar/{idProducto}")
    public String eliminarPorPath(@PathVariable("idProducto") Long idProducto, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            carritoService.eliminarProducto(usuario, idProducto);
        }
        return "redirect:/carrito/listado";
    }

    @PostMapping("/eliminar")
    public String eliminarPorParametro(@RequestParam("idProducto") Long idProducto, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            carritoService.eliminarProducto(usuario, idProducto);
        }
        return "redirect:/carrito/listado";
    }

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

    /* ========= Checkout simple con resumen ========= */
    @GetMapping("/checkout")
    public String mostrarCheckout(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/auth/login";
        }

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
            // Snapshot sencillo para la factura (en sesión)
            var items = carritoService.getCarritoPorUsuario(usuario);

            double subtotal = items.stream()
                    .mapToDouble(i -> i.getCantidad() * i.getProducto().getPrecio())
                    .sum();
            double impuestos = subtotal * 0.13;
            double total = subtotal + impuestos;

            List<Map<String, Object>> facturaItems = items.stream().map(i -> {
                Map<String, Object> row = new HashMap<>();
                row.put("descripcion", i.getProducto().getDescripcion());
                row.put("cantidad", i.getCantidad());
                row.put("precio", i.getProducto().getPrecio());
                row.put("total", i.getCantidad() * i.getProducto().getPrecio());
                return row;
            }).collect(Collectors.toList());

            session.setAttribute("facturaItems", facturaItems);
            session.setAttribute("facturaSubtotal", subtotal);
            session.setAttribute("facturaImpuestos", impuestos);
            session.setAttribute("facturaTotal", total);
            session.setAttribute("facturaFecha", LocalDateTime.now());
            session.setAttribute("facturaUsuario",
                    (usuario.getNombre() != null && !usuario.getNombre().isBlank())
                    ? usuario.getNombre() : usuario.getUsername());
            session.setAttribute("facturaTienda", "FreskoShop");

            // Completar compra (vacía carrito, etc.)
            carritoService.comprar(usuario);
        }
        return "carrito/compra";
    }

    // Factura
    @GetMapping("/factura")
    public String verFactura(HttpSession session, Model model) {
        if (session.getAttribute("facturaItems") == null) {
            return "redirect:/";
        }
        model.addAttribute("items", session.getAttribute("facturaItems"));
        model.addAttribute("subtotal", session.getAttribute("facturaSubtotal"));
        model.addAttribute("impuestos", session.getAttribute("facturaImpuestos"));
        model.addAttribute("total", session.getAttribute("facturaTotal"));
        model.addAttribute("fecha", session.getAttribute("facturaFecha"));
        model.addAttribute("cliente", session.getAttribute("facturaUsuario"));
        model.addAttribute("tienda", session.getAttribute("facturaTienda"));
        return "carrito/factura";
    }
}
