package com.fresko.dao;

import com.fresko.domain.Producto;
import com.fresko.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDao extends JpaRepository<Producto, Long> {

    // Existentes
    List<Producto> findByCategoria(Categoria categoria);

    List<Producto> findByActivoTrue();

    List<Producto> findByCategoriaDescripcionIgnoreCase(String descripcion);

    // Búsquedas (para index, categoría e interfaz admin)
    List<Producto> findByActivoTrueAndDescripcionContainingIgnoreCase(String descripcion);

    List<Producto> findByCategoriaDescripcionIgnoreCaseAndActivoTrueAndDescripcionContainingIgnoreCase(
            String categoria, String descripcion);

    // Útil si alguna vista necesitara incluir inactivos
    List<Producto> findByDescripcionContainingIgnoreCase(String descripcion);
}
