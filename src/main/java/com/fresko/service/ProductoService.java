package com.fresko.service;
 
import com.fresko.domain.Producto;
import com.fresko.domain.Categoria;
import java.util.List;
 
public interface ProductoService {
 
    List<Producto> getProductos(boolean activos);
 
    Producto getProducto(Producto producto);
 
    Producto getProductoPorId(Long id);
 
    void save(Producto producto);
 
    void delete(Producto producto);
 
    List<Producto> getPorCategoria(Categoria categoria);
 
    // MÉTODO PARA FILTRAR POR CATEGORÍA
    List<Producto> getProductosPorCategoria(String categoria);
}