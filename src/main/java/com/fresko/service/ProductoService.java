package com.fresko.service;

import com.fresko.domain.Producto;
import com.fresko.domain.Categoria;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ProductoService {

    List<Producto> getProductos(boolean activos);

    Producto getProducto(Producto producto);

    Producto getProductoPorId(Long id);

    void save(Producto producto);

    void delete(Producto producto);

    List<Producto> getPorCategoria(Categoria categoria);

    // Filtrar por categoría (existente)
    List<Producto> getProductosPorCategoria(String categoria);

    // Guardado/actualización con imagen (existentes)
    void save(Producto producto, MultipartFile imagen);

    void update(Producto producto, MultipartFile imagen);

    // Busca por descripción; si activos=true, solo activos.
  
    List<Producto> buscarProductos(String q, boolean activos);

    //Busca por categoría + descripción (solo activos).
    List<Producto> buscarProductosPorCategoria(String categoria, String q);
}
