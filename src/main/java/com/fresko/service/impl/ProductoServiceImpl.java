package com.fresko.service.impl;

import com.fresko.service.FirebaseStorageService;
import com.fresko.dao.ProductoDao;
import com.fresko.domain.Producto;
import com.fresko.domain.Categoria;
import com.fresko.service.ProductoService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoServiceImpl implements ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

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
        // Subir SOLO si viene archivo real
        if (imagen != null && !imagen.isEmpty() && imagen.getSize() > 0) {
            try {
                String url = storageService.uploadFile(imagen, "productos");
                p.setImagenUrl(url);
            } catch (Exception e) {
                // No tumbar el flujo si la subida falla
                log.warn("No se pudo subir la imagen del producto nuevo: {}", e.getMessage());
            }
        }
        productoDao.save(p);
    }

    @Override
    @Transactional
    public void update(Producto p, MultipartFile imagen) {
        Producto existente = productoDao.findById(p.getIdProducto()).orElseThrow();

        String urlNueva = null;
        if (imagen != null && !imagen.isEmpty() && imagen.getSize() > 0) {
            try {
                urlNueva = storageService.uploadFile(imagen, "productos");
            } catch (Exception e) {
                log.warn("No se pudo subir la nueva imagen del producto {}: {}", p.getIdProducto(), e.getMessage());
            }
        }

        if (urlNueva != null) {
            // Subida OK -> borrar la anterior (si existía) y asignar la nueva
            if (existente.getImagenUrl() != null) {
                try {
                    storageService.deleteByUrl(existente.getImagenUrl());
                } catch (Exception e) {
                    // No interrumpir la actualización si falla el borrado
                    log.warn("No se pudo borrar la imagen anterior del producto {}: {}", p.getIdProducto(), e.getMessage());
                }
            }
            p.setImagenUrl(urlNueva);
        } else {
            // No hubo subida nueva: conservar la URL existente
            p.setImagenUrl(existente.getImagenUrl());
        }

        productoDao.save(p);
    }

    //Búsqueda
    @Override
    public List<Producto> buscarProductos(String q, boolean activos) {
        if (q == null || q.isBlank()) {
            return getProductos(activos);
        }
        String texto = q.trim();
        if (activos) {
            return productoDao.findByActivoTrueAndDescripcionContainingIgnoreCase(texto);
        } else {
            return productoDao.findByDescripcionContainingIgnoreCase(texto);
        }
    }

    @Override
    public List<Producto> buscarProductosPorCategoria(String categoria, String q) {
        if (q == null || q.isBlank()) {
            return getProductosPorCategoria(categoria);
        }
        return productoDao.findByCategoriaDescripcionIgnoreCaseAndActivoTrueAndDescripcionContainingIgnoreCase(
                categoria, q.trim());
    }
}
