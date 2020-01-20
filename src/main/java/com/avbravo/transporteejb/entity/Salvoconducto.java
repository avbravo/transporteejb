/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.entity;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import com.avbravo.jmoordb.anotations.Referenced;
import com.avbravo.jmoordb.pojos.UserInfo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author avbravo
 */
public class Salvoconducto {
      @Id
    private Integer idsalvoconducto;
  
    @Referenced(collection = "Usuario",
            field = "username", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.UsuarioRepository")
    Usuario usuario;
    
       @Referenced(collection = "Viaje",
            field = "idviaje", javatype = "Integer", lazy = false,
            repository = "com.avbravo.transporteejb.repository.ViajeRepository")
    private Viaje viaje;
       
       private Date fecha;
    
      @Embedded
    List<UserInfo> userInfo;

    public Salvoconducto() {
    }

    public Salvoconducto(Integer idsalvoconducto, Usuario usuario, Viaje viaje, Date fecha) {
        this.idsalvoconducto = idsalvoconducto;
        this.usuario = usuario;
        this.viaje = viaje;
        this.fecha = fecha;
    }

      
      
      
      
    public Integer getIdsalvoconducto() {
        return idsalvoconducto;
    }

    public void setIdsalvoconducto(Integer idsalvoconducto) {
        this.idsalvoconducto = idsalvoconducto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }
      
      
      
      
      
}
