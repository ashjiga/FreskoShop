package com.fresko.service;

import com.fresko.domain.Usuario;
import java.util.List;

public interface UsuarioService {
    
    List<Usuario> getUsuarios();

    Usuario getUsuario(Usuario usuario);

    Usuario getUsuarioPorUsername(String username);

    Usuario getUsuarioPorId(Long idUsuario);

    Usuario createUsuario(Usuario nuevoUsuario);

    void guardarUsuario(Usuario usuario);

    void eliminarUsuario(Usuario usuario);
    
    void eliminarUsuario(Long idUsuario);
    
   boolean existeUsername(String username);
   
     // MÉTODO PARA FILTRAR POR CATEGORÍA
    List<Producto> getProductosPorCategoria(String categoria);
}
