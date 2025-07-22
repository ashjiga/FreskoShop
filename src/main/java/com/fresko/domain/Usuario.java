package com.fresko.domain;

import jakarta.persistence.*;  
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;

    @Column(name = "ruta_imagen")
    private String rutaImagen;

    private Boolean activo;        

    /* --- Campo de rol único ---  */
    @Column(nullable = false, length = 20)
    private String rol;               

@PrePersist
@PreUpdate
private void validarRol() {
    if (this.rol == null || !this.rol.matches("ROLE_(CLIENTE|TRABAJADOR|ADMIN)")) {
        this.rol = "ROLE_CLIENTE";
    }  
}
    
public static final List<String> ROLES_DISPONIBLES = Arrays.asList(
            "ROLE_CLIENTE",
            "ROLE_TRABAJADOR",
            "ROLE_ADMIN");
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
 
}

