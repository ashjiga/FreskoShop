package com.fresko.service;

import com.fresko.domain.Usuario;
import java.util.List;

public interface UsuarioService {
    
    List<Usuario> getUsuarios();
    
    Usuario getUsuarioPorUsername(String username);

    Usuario getUsuarioPorId(Long idUsuario);
    
    Usuario createUsuario(Usuario nuevoUsuario);
}