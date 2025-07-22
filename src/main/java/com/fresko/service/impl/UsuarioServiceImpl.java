package com.fresko.service.impl;

import com.fresko.dao.UsuarioDao;
import com.fresko.domain.Usuario;
import com.fresko.service.UsuarioService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDao usuarioDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioDao usuarioDao,
            PasswordEncoder passwordEncoder) {
        this.usuarioDao = usuarioDao;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    public Usuario getUsuarioPorId(Long idUsuario) {
        return usuarioDao.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario));
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
        if (usuario.getIdUsuario() == null) {
            // Validaciones para nuevo usuario
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password es requerido para nuevos usuarios");
            }
            
            if (usuarioDao.existsByUsername(usuario.getUsername())) {
                throw new IllegalArgumentException("El nombre de usuario ya existe");
            }
            
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuario.setActivo(true);
            
            if (usuario.getRol() == null) {
                usuario.setRol("ROLE_CLIENTE");
            }
        } else {
            Usuario usuarioExistente = usuarioDao.findById(usuario.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                usuario.setPassword(usuarioExistente.getPassword());
            } else if (!usuario.getPassword().startsWith("$2a")) {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }  
            usuario.setActivo(usuarioExistente.getActivo());
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
    
    @Override
public boolean existeUsername(String username) {
    return usuarioDao.existsByUsername(username);
}


}