package com.fresko.dao;

import com.fresko.domain.Favorito;
import com.fresko.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritoDao extends JpaRepository<Favorito, Long> {
    
    List<Favorito> findByUsuario(Usuario usuario);

    boolean existsByUsuarioIdUsuarioAndProductoIdProducto(Long idUsuario, Long idProducto);

    void deleteByUsuarioIdUsuarioAndProductoIdProducto(Long idUsuario, Long idProducto);

    java.util.List<com.fresko.domain.Favorito> findByUsuarioIdUsuario(Long idUsuario);

}
