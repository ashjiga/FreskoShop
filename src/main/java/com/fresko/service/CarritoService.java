/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.fresko.service;

import com.fresko.domain.Carrito;
import com.fresko.domain.Usuario;
import java.util.List;

public interface CarritoService {
    List<Carrito> getCarritoPorUsuario(Usuario usuario);
    void agregarProducto(Usuario usuario, Long idProducto);
    void eliminarProducto(Usuario usuario, Long idProducto);
    void comprar(Usuario usuario);
}
