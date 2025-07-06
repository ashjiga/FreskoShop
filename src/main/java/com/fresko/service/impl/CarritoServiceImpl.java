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
    public void agregarProducto(Usuario usuario, Long idProducto) {
        Carrito existente = carritoDao.findByUsuarioAndProducto_IdProducto(usuario, idProducto);
        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + 1);
            carritoDao.save(existente);
        } else {
            Producto producto = productoDao.findById(idProducto).orElse(null);
            if (producto != null) {
                Carrito nuevo = new Carrito();
                nuevo.setUsuario(usuario);
                nuevo.setProducto(producto);
                nuevo.setCantidad(1);
                carritoDao.save(nuevo);
            }
        }
    }

    @Override
    public void eliminarProducto(Usuario usuario, Long idProducto) {
        carritoDao.deleteByUsuarioAndProducto_IdProducto(usuario, idProducto);
    }

    @Override
    public void comprar(Usuario usuario) {
        List<Carrito> carrito = carritoDao.findByUsuario(usuario);
        if (carrito.isEmpty()) return;

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
}
