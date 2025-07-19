package com.fresko.controller;

import com.fresko.domain.Producto;
import com.fresko.domain.Usuario;
import com.fresko.service.CarritoService;
import com.fresko.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CarritoService carritoService;

    // Mostrar todos los productos en el index
    @GetMapping("/")
    public String mostrarIndex(Model model) {
        List<Producto> productos = productoService.getProductos(true);
        model.addAttribute("productos", productos);
        return "index";
    }

    // Filtrar productos por categoría (ej: /abarrotes)
    @GetMapping("/{categoria}")
    public String filtrarPorCategoria(@PathVariable("categoria") String categoria, Model model) {
        List<Producto> productos = productoService.getProductosPorCategoria(categoria);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaSeleccionada", categoria);
        return "index";
    }

    @GetMapping("/producto/{id}")
    public String verDetalleProducto(@PathVariable("id") Long id, Model model, HttpSession session) {
        Producto producto = productoService.getProductoPorId(id);
        model.addAttribute("producto", producto);

        // Mostrar cantidad del carrito si el usuario está autenticado
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            int cantidad = carritoService.getCantidadProductos(usuario);
            model.addAttribute("cantidadCarrito", cantidad);
        }
        return "producto/detallesProducto";
    }
}
