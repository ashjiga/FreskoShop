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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

 @Override
public Usuario createUsuario(Usuario nuevoUsuario) {
    if (usuarioDao.existsByUsername(nuevoUsuario.getUsername())) {
        throw new IllegalArgumentException("El nombre de usuario ya existe.");
    }
    nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
    nuevoUsuario.setActivo(true);

    if (!nuevoUsuario.getRol().startsWith("ROLE_")) {
        nuevoUsuario.setRol("ROLE_" + nuevoUsuario.getRol());
    }

    return usuarioDao.save(nuevoUsuario);
}

    @Override
    public void guardarUsuario(Usuario usuario) {
        if (!usuario.getPassword().startsWith("$2a")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        usuarioDao.save(usuario);
    }

    @Override
    public Usuario getUsuario(Usuario usuario) {
        return usuarioDao.findById(usuario.getIdUsuario()).orElse(null);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioDao.delete(usuario);
    }
    
    @Override
    public void eliminarUsuario(Long idUsuario) {
        usuarioDao.deleteById(idUsuario);
    }
}