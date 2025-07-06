package com.fresko.service.impl;

import com.fresko.dao.FavoritoDao;
import com.fresko.domain.Favorito;
import com.fresko.domain.Usuario;
import com.fresko.service.FavoritoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritoServiceImpl implements FavoritoService {

    @Autowired
    private FavoritoDao favoritoDao;

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
        favoritoDao.deleteByUsuarioAndProducto_IdProducto(usuario, idProducto);
    }

    @Override
    public boolean existeFavorito(Usuario usuario, Long idProducto) {
        return favoritoDao.existsByUsuarioAndProducto_IdProducto(usuario, idProducto);
    }
}
