package com.avbravo.transporteejb.entity;

import com.avbravo.ejbjmoordb.anotations.Embedded;
import com.avbravo.ejbjmoordb.anotations.Id;
import com.avbravo.ejbjmoordb.anotations.Referenced;
import com.avbravo.ejbjmoordb.pojos.UserInfo;
import java.util.List;

public class Usuario {

    @Id
    private String username;
    private String password;    
    private String nombre;
    private String cedula;
    private String celular;
    private String cargo;
    private String email;
    @Referenced(documment = "Rol",
            field = "idrol", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.RolRepository")
    private List<Rol> rol;
    @Referenced(documment = "Unidad",
            field = "idunidad", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.UnidadRepository")
    private Unidad unidad;
 
    private String activo;
    @Embedded
    List<UserInfo> userInfo;

    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario{" + "username=" + username + ", password=" + password + ", nombre=" + nombre + ", celular=" + celular + ", cargo=" + cargo + ", email=" + email + ", rol=" + rol + ", userInfo=" + userInfo + '}';
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rol> getRol() {
        return rol;
    }

    public void setRol(List<Rol> rol) {
        this.rol = rol;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }

    
    
    
}
