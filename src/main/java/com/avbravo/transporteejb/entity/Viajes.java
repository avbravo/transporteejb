/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.entity;

import com.avbravo.ejbjmoordb.anotations.Embedded;
import com.avbravo.ejbjmoordb.anotations.Id;
import com.avbravo.ejbjmoordb.anotations.Referenced;
import com.avbravo.ejbjmoordb.pojos.UserInfo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author avbravo
 */

public class Viajes {

    @Id
    private Integer idviaje;
    @Referenced(documment = "Solicitud",
            field = "idsolicitud", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.SolicitudRepository")
    private Solicitud solicitud;
    @Referenced(documment = "Vehiculo",
            field = "idvehiculo", javatype = "Integer", lazy = false,
            repository = "com.avbravo.transporteejb.repository.VehiculoRepository")
    private Vehiculo vehiculo;
    @Referenced(documment = "Conductor",
            field = "idconductor", javatype = "Integer", lazy = false,
            repository = "com.avbravo.transporteejb.repository.ConductorRepository")
    private Conductor conductor;
    
    
    private Date fechahorasalida;
    

    private Date fechahorallegada;
    private String comentarios;
    
    private Double kmestimados;
    
    private Date fechahorainicioocupado;
    private Date fechahorafinocupado;
    private String activo;

    @Embedded
    List<UserInfo> userInfo;

    public Integer getIdviaje() {
        return idviaje;
    }

    public void setIdviaje(Integer idviaje) {
        this.idviaje = idviaje;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Date getFechahorasalida() {
        return fechahorasalida;
    }

    public void setFechahorasalida(Date fechahorasalida) {
        this.fechahorasalida = fechahorasalida;
    }

    public Date getFechahorallegada() {
        return fechahorallegada;
    }

    public void setFechahorallegada(Date fechahorallegada) {
        this.fechahorallegada = fechahorallegada;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Double getKmestimados() {
        return kmestimados;
    }

    public void setKmestimados(Double kmestimados) {
        this.kmestimados = kmestimados;
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
