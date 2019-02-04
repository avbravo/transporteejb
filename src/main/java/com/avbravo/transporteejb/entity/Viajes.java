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
    @Referenced(documment = "Vehiculo",
            field = "idvehiculo", javatype = "Integer", lazy = false,
            repository = "com.avbravo.transporteejb.repository.VehiculoRepository")
    private Vehiculo vehiculo;
    @Referenced(documment = "Conductor",
            field = "idconductor", javatype = "Integer", lazy = false,
            repository = "com.avbravo.transporteejb.repository.ConductorRepository")
    private Conductor conductor;

    private String comentarios;

    private Double kmestimados;
    private Double costocombustible;

    private Date fechahorainicioreserva;
    private Date fechahorafinreserva;
    private String activo;

    private String lugarpartida;
    private String lugarregreso;
    private String realizado;

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }

    @Embedded
    List<UserInfo> userInfo;

    public String getLugarpartida() {
        return lugarpartida;
    }

    public void setLugarpartida(String lugarpartida) {
        this.lugarpartida = lugarpartida;
    }

    public String getLugarregreso() {
        return lugarregreso;
    }

    public void setLugarregreso(String lugarregreso) {
        this.lugarregreso = lugarregreso;
    }

    public Integer getIdviaje() {
        return idviaje;
    }

    public Double getCostocombustible() {
        return costocombustible;
    }

    public void setCostocombustible(Double costocombustible) {
        this.costocombustible = costocombustible;
    }

    public void setIdviaje(Integer idviaje) {
        this.idviaje = idviaje;
    }

//    public List<Solicitud> getSolicitud() {
//        return solicitud;
//    }
//
//    public void setSolicitud(List<Solicitud> solicitud) {
//        this.solicitud = solicitud;
//    }
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

    public Date getFechahorainicioreserva() {
        return fechahorainicioreserva;
    }

    public void setFechahorainicioreserva(Date fechahorainicioreserva) {
        this.fechahorainicioreserva = fechahorainicioreserva;
    }

    public Date getFechahorafinreserva() {
        return fechahorafinreserva;
    }

    public void setFechahorafinreserva(Date fechahorafinreserva) {
        this.fechahorafinreserva = fechahorafinreserva;
    }

}
