package com.fresko.service.impl;

import com.fresko.service.FirebaseStorageService;
import com.fresko.dao.ProductoDao;
import com.fresko.domain.Producto;
import com.fresko.domain.Categoria;
import com.fresko.service.ProductoService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private FirebaseStorageService storageService;

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
    
   
 @Override
    @Transactional
    public void save(Producto p, MultipartFile imagen) {
        if (imagen != null && !imagen.isEmpty()) {
            String url = storageService.uploadFile(imagen, "productos");
            p.setImagenUrl(url);
        }
        productoDao.save(p);
    }

    @Override
    @Transactional
    public void update(Producto p, MultipartFile imagen) {
        Producto existente = productoDao.findById(p.getIdProducto()).orElseThrow();
        if (imagen != null && !imagen.isEmpty()) {
            if (existente.getImagenUrl() != null) {
                storageService.deleteByUrl(existente.getImagenUrl());
            }
            String url = storageService.uploadFile(imagen, "productos");
            p.setImagenUrl(url);
        } else {
            p.setImagenUrl(existente.getImagenUrl());
        }
        productoDao.save(p);
    }
}
    
