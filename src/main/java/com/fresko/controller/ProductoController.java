package com.fresko.controller;

import com.fresko.domain.Producto;
import com.fresko.service.CarritoService;
import com.fresko.service.CategoriaService;
import com.fresko.service.ProductoService;
import com.fresko.service.UsuarioService;
import com.fresko.service.FavoritoService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductoController {

    private final ProductoService productoService;
    private final CarritoService carritoService;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;
    private final FavoritoService favoritoService;

    public ProductoController(ProductoService productoService,
            CarritoService carritoService,
            CategoriaService categoriaService,
            UsuarioService usuarioService,
            FavoritoService favoritoService) {
        this.productoService = productoService;
        this.carritoService = carritoService;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
        this.favoritoService = favoritoService;
    }

    @GetMapping("/producto/listado")
    @Secured({"ROLE_ADMIN", "ROLE_TRABAJADOR"})
    public String listado(@RequestParam(value = "q", required = false) String q,
            Model model) {
        var productos = (q != null && !q.isBlank())
                ? productoService.buscarProductos(q, true)
                : productoService.getProductos(true);
        model.addAttribute("productos", productos);
        model.addAttribute("q", q);
        return "producto/listado";
    }

    @GetMapping("/producto/detalles/{id}")
    public String detalles(@PathVariable("id") Long id, Model model, Authentication auth) {
        var producto = productoService.getProductoPorId(id);
        model.addAttribute("producto", producto);

        // Productos relacionados (excluye el actual)
        var productosRelacionados = productoService
                .getProductosPorCategoria(producto.getCategoria().getDescripcion())
                .stream()
                .filter(p -> !p.getIdProducto().equals(id))
                .collect(Collectors.toList());
        model.addAttribute("productosRelacionados", productosRelacionados);

        if (auth != null && auth.isAuthenticated()) {
            var usuario = usuarioService.getUsuarioPorUsername(auth.getName());
            var favs = favoritoService.getFavoritosDeUsuario(usuario.getIdUsuario());
            var favoritosIds = favs.stream()
                    .map(f -> f.getProducto().getIdProducto())
                    .collect(Collectors.toSet());
            model.addAttribute("favoritosIds", favoritosIds);
        }
        return "producto/detallesProducto";
    }

    @GetMapping("/producto/editar/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TRABAJADOR"})
    public String editar(@PathVariable("id") Long id, Model model) {
        var producto = productoService.getProductoPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.getCategorias(true));
        return "producto/formulario";
    }

    @GetMapping("/producto/nuevo")
    @Secured({"ROLE_ADMIN", "ROLE_TRABAJADOR"})
    public String productoNuevo(Producto producto, Model model) {
        model.addAttribute("categorias", categoriaService.getCategorias(true));
        return "producto/formulario";
    }

    @PostMapping("/producto/guardar")
    @Secured({"ROLE_ADMIN", "ROLE_TRABAJADOR"})
    public String productoGuardar(@Valid @ModelAttribute Producto producto,
            BindingResult result,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            Model model,
            RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.getCategorias(true));
            return "producto/formulario";
        }

        // Elige el archivo que realmente viene con contenido
        MultipartFile fileParaSubir = null;
        if (imagenFile != null && !imagenFile.isEmpty() && imagenFile.getSize() > 0) {
            fileParaSubir = imagenFile;
        } else if (archivo != null && !archivo.isEmpty() && archivo.getSize() > 0) {
            fileParaSubir = archivo;
        }

        if (producto.getIdProducto() == null) {
            productoService.save(producto, fileParaSubir);
            ra.addFlashAttribute("mensaje", "Producto creado exitosamente!");
        } else {
            productoService.update(producto, fileParaSubir);
            ra.addFlashAttribute("mensaje", "Producto actualizado exitosamente!");
        }
        return "redirect:/producto/listado";
    }

    @PostMapping("/producto/modificar/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TRABAJADOR"})
    public String productoModificar(@PathVariable Long id,
            @Valid @ModelAttribute Producto producto,
            BindingResult result,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            Model model,
            RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.getCategorias(true));
            return "producto/formulario";
        }
        producto.setIdProducto(id);

        MultipartFile fileParaSubir = null;
        if (imagenFile != null && !imagenFile.isEmpty() && imagenFile.getSize() > 0) {
            fileParaSubir = imagenFile;
        } else if (archivo != null && !archivo.isEmpty() && archivo.getSize() > 0) {
            fileParaSubir = archivo;
        }

        productoService.update(producto, fileParaSubir);
        ra.addFlashAttribute("mensaje", "Producto actualizado exitosamente!");
        return "redirect:/producto/listado";
    }

    @GetMapping("/producto/eliminar/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TRABAJADOR"})
    public String productoEliminar(@PathVariable("id") Long id) {
        var producto = productoService.getProductoPorId(id);
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }
    
    @GetMapping({"/", "/index"})
    public String index(@RequestParam(value = "q", required = false) String q,
            Model model, Authentication auth) {

        var productos = (q != null && !q.isBlank())
                ? productoService.buscarProductos(q, true)
                : productoService.getProductos(true);
        model.addAttribute("productos", productos);
        model.addAttribute("q", q);

        var favoritosIds = java.util.Collections.<Long>emptySet();
        var idsProductosEnCarrito = java.util.Collections.<Long>emptySet();

        if (auth != null && auth.isAuthenticated()) {
            var usuario = usuarioService.getUsuarioPorUsername(auth.getName());

            var favs = favoritoService.getFavoritosDeUsuario(usuario.getIdUsuario());
            favoritosIds = favs.stream()
                    .map(f -> f.getProducto().getIdProducto())
                    .collect(java.util.stream.Collectors.toSet());

            var productosEnCarrito = carritoService.getCarritoPorUsuario(usuario);
            idsProductosEnCarrito = productosEnCarrito.stream()
                    .map(item -> item.getProducto().getIdProducto())
                    .collect(java.util.stream.Collectors.toSet());
        }

        model.addAttribute("favoritosIds", favoritosIds);
        model.addAttribute("idsProductosEnCarrito", idsProductosEnCarrito);
        return "index";
    }

    @GetMapping("/{categoria}")
    public String filtrarPorCategoria(@PathVariable("categoria") String categoria,
            @RequestParam(value = "q", required = false) String q,
            Model model) {
        List<Producto> productos = (q != null && !q.isBlank())
                ? productoService.buscarProductosPorCategoria(categoria, q)
                : productoService.getProductosPorCategoria(categoria);

        model.addAttribute("productos", productos);
        model.addAttribute("categoriaSeleccionada", categoria);
        model.addAttribute("q", q);
        return "index";
    }

    @GetMapping("/productos")
    public String productosRedirect() {
        return "redirect:/";
    }
}
