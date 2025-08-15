package com.fresko.service.impl;

import com.fresko.dao.FavoritoDao;
import com.fresko.dao.ProductoDao;
import com.fresko.dao.UsuarioDao;
import com.fresko.domain.Favorito;
import com.fresko.domain.Usuario;
import com.fresko.service.FavoritoService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class FavoritoServiceImpl implements FavoritoService {
    
    @Autowired
    private FavoritoDao favoritoDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ProductoDao productoDao;



    @Override
    public List<Favorito> getFavoritosPorUsuario(Usuario usuario) {
        return favoritoDao.findByUsuario(usuario);
    }

    @Override
    public void guardar(Favorito favorito) {
        favoritoDao.save(favorito);
    }

    @Override
public void eliminarPorUsuarioYProducto(Usuario usuario, Long idProducto) {
    favoritoDao.deleteByUsuarioIdUsuarioAndProductoIdProducto(usuario.getIdUsuario(), idProducto);
}

@Override
public boolean existeFavorito(Usuario usuario, Long idProducto) {
    return favoritoDao.existsByUsuarioIdUsuarioAndProductoIdProducto(usuario.getIdUsuario(), idProducto);
}

@Override
public java.util.List<Favorito> getFavoritosDeUsuario(Long idUsuario) {
    return favoritoDao.findByUsuarioIdUsuario(idUsuario);
}

    @Override
    public boolean existeFavorito(Long usuarioId, Long productoId) {
        return favoritoDao.existsByUsuarioIdUsuarioAndProductoIdProducto(usuarioId, productoId);
    }

    @Override
    @Transactional
    public void agregarFavorito(Long usuarioId, Long productoId) {
        var usuario  = usuarioDao.findById(usuarioId).orElseThrow();
        var producto = productoDao.findById(productoId).orElseThrow();
        favoritoDao.save(new Favorito(usuario, producto));
    }

    @Override
    @Transactional
    public void eliminarFavorito(Long usuarioId, Long productoId) {
        favoritoDao.deleteByUsuarioIdUsuarioAndProductoIdProducto(usuarioId, productoId);
    }

    
}
