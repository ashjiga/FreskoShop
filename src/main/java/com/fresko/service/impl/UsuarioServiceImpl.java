package com.fresko.service.impl;

import com.fresko.dao.UsuarioDao;
import com.fresko.domain.Usuario;
import com.fresko.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    public Usuario getUsuarioPorId(Long idUsuario) {
        return usuarioDao.findById(idUsuario).orElse(null);
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioDao.findAll();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario createUsuario(Usuario nuevoUsuario) {
        if (usuarioDao.existsByUsername(nuevoUsuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe.");
        }

        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));

        nuevoUsuario.setActivo(true);
        return usuarioDao.save(nuevoUsuario);
    }

}
