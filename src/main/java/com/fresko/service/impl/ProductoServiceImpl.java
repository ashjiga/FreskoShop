package com.fresko.service.impl;

import com.fresko.dao.ProductoDao;
import com.fresko.domain.Producto;
import com.fresko.domain.Categoria;
import com.fresko.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Override
    public List<Producto> getProductos(boolean activos) {
        var lista = productoDao.findAll();
        if (activos) {
            lista.removeIf(p -> !p.isActivo());
        }
        return lista;
    }

    @Override
    public Producto getProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    @Override
    public void save(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    public void delete(Producto producto) {
        productoDao.delete(producto);
    }

    @Override
    public List<Producto> getPorCategoria(Categoria categoria) {
        return productoDao.findByCategoria(categoria);
    }

    @Override
    public List<Producto> getProductosPorCategoria(String categoria) {
        return productoDao.findByCategoriaDescripcionIgnoreCase(categoria);
    }

    @Override
    public Producto getProductoPorId(Long id) {
        return productoDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }
}
