package com.fresko.dao;

import com.fresko.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    
    Usuario findByUsername(String username);
    boolean existsByUsername(String username);

@Component
public class ProbarConexion {

    @Bean
    public CommandLineRunner test(UsuarioDao dao) {
        return args -> {
            System.out.println("Total usuarios en BD: " + dao.count());
        };
    }
}

}