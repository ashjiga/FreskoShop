package com.fresko.service.impl;

import com.fresko.dao.CarritoDao;
import com.fresko.dao.ProductoDao;
import com.fresko.dao.FacturaDao;
import com.fresko.dao.VentaDao;
import com.fresko.domain.*;
import com.fresko.service.CarritoService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoDao carritoDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private FacturaDao facturaDao;

    @Autowired
    private VentaDao ventaDao;

    @Override
    public List<Carrito> getCarritoPorUsuario(Usuario usuario) {
        return carritoDao.findByUsuario(usuario);
    }

    @Override
    @Transactional
    public void agregarProducto(Usuario usuario, Long idProducto, int cantidad) {
        Producto producto = productoDao.findById(idProducto).orElse(null);
        if (producto != null && cantidad > 0 && cantidad <= producto.getExistencias()) {
            Carrito carrito = carritoDao.findByUsuarioAndProducto(usuario, producto);
            if (carrito != null) {
                carrito.setCantidad(carrito.getCantidad() + cantidad);
            } else {
                carrito = new Carrito();
                carrito.setUsuario(usuario);
                carrito.setProducto(producto);
                carrito.setCantidad(cantidad);
            }
            carritoDao.save(carrito);
        }
    }

    @Override
    @Transactional
    public void eliminarProducto(Usuario usuario, Long idProducto) {
        carritoDao.deleteByUsuarioAndProducto_IdProducto(usuario, idProducto);
    }

    @Override
    @Transactional
    public void comprar(Usuario usuario) {
        List<Carrito> carrito = carritoDao.findByUsuario(usuario);
        if (carrito.isEmpty()) {
            return;
        }

        Factura factura = new Factura();
        factura.setUsuario(usuario);
        factura.setFecha(LocalDate.now());
        double total = carrito.stream().mapToDouble(c -> c.getProducto().getPrecio() * c.getCantidad()).sum();
        factura.setTotal(total);
        factura.setEstado(1);
        facturaDao.save(factura);

        for (Carrito item : carrito) {
            Venta venta = new Venta();
            venta.setFactura(factura);
            venta.setProducto(item.getProducto());
            venta.setPrecio(item.getProducto().getPrecio());
            venta.setCantidad(item.getCantidad());
            ventaDao.save(venta);
        }

        carritoDao.deleteAll(carrito);
    }

    @Override
    public int contarProductosEnCarrito(Usuario usuario) {
        List<Carrito> carrito = carritoDao.findByUsuario(usuario);
        return carrito.stream().mapToInt(Carrito::getCantidad).sum();
    }

    @Override
    public int getCantidadProductos(Usuario usuario) {
        List<Carrito> carrito = carritoDao.findByUsuario(usuario);
        return carrito.stream()
                .mapToInt(Carrito::getCantidad)
                .sum();
    }
}