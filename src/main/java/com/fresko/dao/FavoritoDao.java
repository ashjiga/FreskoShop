package com.fresko.dao;

import com.fresko.domain.Favorito;
import com.fresko.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritoDao extends JpaRepository<Favorito, Long> {
    
    List<Favorito> findByUsuario(Usuario usuario);

    boolean existsByUsuarioAndProducto_IdProducto(Usuario usuario, Long idProducto);

    void deleteByUsuarioAndProducto_IdProducto(Usuario usuario, Long idProducto);
}
