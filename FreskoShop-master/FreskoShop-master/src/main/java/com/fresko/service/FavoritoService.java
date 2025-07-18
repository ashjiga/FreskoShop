package com.fresko.service;

import com.fresko.domain.Favorito;
import com.fresko.domain.Usuario;
import java.util.List;

public interface FavoritoService {
    
    List<Favorito> getFavoritosPorUsuario(Usuario usuario);

    void guardar(Favorito favorito);

    void eliminarPorUsuarioYProducto(Usuario usuario, Long idProducto);

    
    boolean existeFavorito(Usuario usuario, Long idProducto);
}
