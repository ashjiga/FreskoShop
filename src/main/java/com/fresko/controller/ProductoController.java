package com.fresko.controller;

import com.fresko.domain.Producto;
import com.fresko.domain.Usuario;
import com.fresko.service.CarritoService;
import com.fresko.service.ProductoService;
import com.fresko.service.CategoriaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;

import com.fresko.service.UsuarioService; 
import com.fresko.service.FavoritoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final CarritoService carritoService;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;
    private final FavoritoService favoritoService;


    // Listar productos para ADMIN o TRABAJADOR
    @GetMapping("/producto/listado")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRABAJADOR')")
    public String listado(Model model) {
        List<Producto> productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        return "producto/listado";
    }

    // Mostrar todos los productos al usuario en el index
//    @GetMapping("/")
//    public String mostrarIndex(Model model) {
//        List<Producto> productos = productoService.getProductos(true);
//        model.addAttribute("productos", productos);
//        return "index";
//    }
//    
    @GetMapping({"/", "/index"})
    public String index(Model model, Authentication auth) {
        var productos = productoService.getProductos(true);
        model.addAttribute("productos", productos);

        if (auth != null && auth.isAuthenticated()) {
            var usuario = usuarioService.getUsuarioPorUsername(auth.getName());
            var favs = favoritoService.getFavoritosDeUsuario(usuario.getIdUsuario());
            var favoritosIds = favs.stream()
                    .map(f -> f.getProducto().getIdProducto())
                    .collect(Collectors.toSet());
            model.addAttribute("favoritosIds", favoritosIds);
        }
        return "index";
    }
    

    // Crear nuevo producto
    @GetMapping("/producto/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRABAJADOR')")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.getCategorias(true));
        return "producto/formulario";
    }

    // Guardar producto
    @PostMapping("/producto/guardar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRABAJADOR')")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.getCategorias(true));
            return "producto/formulario";
        }
        productoService.save(producto);
        redirectAttributes.addFlashAttribute("success", "Producto guardado correctamente.");
        return "redirect:/producto/listado";
    }

    // Editar product
    @GetMapping("/producto/editar/{idProducto}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRABAJADOR')")
    public String editar(@PathVariable Long idProducto, Model model) {
        Producto producto = productoService.getProductoPorId(idProducto);
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.getCategorias(true));
        return "producto/formulario";
    }

    // Eliminar producto
    @GetMapping("/producto/eliminar/{idProducto}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRABAJADOR')")
    public String eliminar(@PathVariable Long idProducto, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.getProductoPorId(idProducto);
        productoService.delete(producto);
        redirectAttributes.addFlashAttribute("success", "Producto eliminado correctamente.");
        return "redirect:/producto/listado";
    }

    // Ver detalles del producto (usado por el index y los links de cada tarjeta)
  @GetMapping("/producto/{id}")
    public String verDetalleProducto(@PathVariable("id") Long id, Model model, Authentication auth) {
        Producto producto = productoService.getProductoPorId(id);
        model.addAttribute("producto", producto);

        if (auth != null && auth.isAuthenticated()) {
            Usuario usuario = usuarioService.getUsuarioPorUsername(auth.getName());
            int cantidad = carritoService.getCantidadProductos(usuario);
            model.addAttribute("cantidadCarrito", cantidad);
        }
        return "producto/detallesProducto";
    }

    // Filtrar por categoría como /carnes, /bebidas, etc.
    @GetMapping("/{categoria}")
    public String filtrarPorCategoria(@PathVariable("categoria") String categoria, Model model) {
        List<Producto> productos = productoService.getProductosPorCategoria(categoria);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaSeleccionada", categoria);
        return "index";
    }
    
    
    @GetMapping("/productos")
    public String productosRedirect() {
        return "redirect:/";
    }
    
}
