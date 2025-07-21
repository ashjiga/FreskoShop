package com.fresko.controller;

import com.fresko.domain.Producto;
import com.fresko.domain.Categoria;
import com.fresko.service.ProductoService;
import com.fresko.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    //Listar productos
    @GetMapping("/listado")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRABAJADOR')")
    public String listado(Model model) {
        List<Producto> productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        return "producto/listado";
    }

   
    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRABAJADOR')")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.getCategorias(true));
        return "producto/formulario";
    }


    @PostMapping("/guardar")
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

    @GetMapping("/editar/{idProducto}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRABAJADOR')")
    public String editar(@PathVariable Long idProducto, Model model) {
        Producto producto = productoService.getProductoPorId(idProducto);
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.getCategorias(true));
        return "producto/formulario";
    }

    @GetMapping("/eliminar/{idProducto}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRABAJADOR')")
    public String eliminar(@PathVariable Long idProducto, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.getProductoPorId(idProducto);
        productoService.delete(producto);
        redirectAttributes.addFlashAttribute("success", "Producto eliminado correctamente.");
        return "redirect:/producto/listado";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleProducto(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.getProductoPorId(id);
        model.addAttribute("producto", producto);
        return "producto/detallesProducto";
    }

    @GetMapping("/categoria/{categoria}")
    public String filtrarPorCategoria(@PathVariable("categoria") String categoria, Model model) {
        List<Producto> productos = productoService.getProductosPorCategoria(categoria);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaSeleccionada", categoria);
        return "index";
    }
}




