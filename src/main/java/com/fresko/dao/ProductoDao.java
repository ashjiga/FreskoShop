package com.fresko.dao;

import com.fresko.domain.Producto;
import com.fresko.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDao extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(Categoria categoria);

    List<Producto> findByActivoTrue();
}
