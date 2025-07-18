/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.fresko.dao;

import com.fresko.domain.Carrito;
import com.fresko.domain.Producto;
import com.fresko.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoDao extends JpaRepository<Carrito, Long> {
    List<Carrito> findByUsuario(Usuario usuario);
    Carrito findByUsuarioAndProducto_IdProducto(Usuario usuario, Long idProducto);
    void deleteByUsuarioAndProducto_IdProducto(Usuario usuario, Long idProducto);
    Carrito findByUsuarioAndProducto(Usuario usuario, Producto producto);

}
