/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.entity;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import com.avbravo.jmoordb.anotations.Ignore;
import com.avbravo.jmoordb.anotations.Secondary;
import com.avbravo.jmoordb.pojos.UserInfo;
import java.util.List;

/**
 *
 * @author avbravo
 */

public class Conductor {

    @Id
    private Integer idconductor;
    @Secondary
    private String cedula;
    private String nombre;
    private String celular;
    private String email;
    private String activo;
    private String escontrol;

   @Embedded
    List<UserInfo> userInfo;

    @Ignore
    private Double totalkm;
    @Ignore
    Double totalconsumo;
    @Ignore
    Integer totalviajes;

    
    
    public Conductor() {
    }

    
    
    public Double getTotalkm() {
        return totalkm;
    }

    public void setTotalkm(Double totalkm) {
        this.totalkm = totalkm;
    }

    public Double getTotalconsumo() {
        return totalconsumo;
    }

    public void setTotalconsumo(Double totalconsumo) {
        this.totalconsumo = totalconsumo;
    }

    public Integer getTotalviajes() {
        return totalviajes;
    }

    public void setTotalviajes(Integer totalviajes) {
        this.totalviajes = totalviajes;
    }
    
    
    
    
    
    
   
    public Integer getIdconductor() {
        return idconductor;
    }

    public String getEscontrol() {
        return escontrol;
    }

    public void setEscontrol(String escontrol) {
        this.escontrol = escontrol;
    }

    
    
    
    
    public void setIdconductor(Integer idconductor) {
        this.idconductor = idconductor;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
