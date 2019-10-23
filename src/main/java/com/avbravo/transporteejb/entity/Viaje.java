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
public class Viaje {

    @Id
    private Integer idviaje;
    @Referenced(collection = "Vehiculo",
            field = "idvehiculo", javatype = "Integer", lazy = false,
            repository = "com.avbravo.transporteejb.repository.VehiculoRepository")
    private Vehiculo vehiculo;
    @Referenced(collection = "Conductor",
            field = "idconductor", javatype = "Integer", lazy = false,
            repository = "com.avbravo.transporteejb.repository.ConductorRepository")
    private Conductor conductor;

    private String mision;
    private String comentarios;

    private Double kmestimados;
    private Double costocombustible;

    private Date fechahorainicioreserva;
    private Date fechahorafinreserva;
    private String activo;

    private String lugarpartida;
    private String lugardestino;
    private String realizado;
 @Referenced(collection = "EstatusViaje",
            field = "idestatusviaje", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.EstatusViajeRepository")
    private EstatusViaje estatusViaje;
    private Integer asientosdisponibles;
    
    private String mensajeWarning;

    public Integer getAsientosdisponibles() {
        return asientosdisponibles;
    }

    public void setAsientosdisponibles(Integer asientosdisponibles) {
        this.asientosdisponibles = asientosdisponibles;
    }

    @Embedded
    List<UserInfo> userInfo;

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    
    
    public String getRealizado() {
        return realizado;
    }

    public String getMensajeWarning() {
        return mensajeWarning;
    }

    public void setMensajeWarning(String mensajeWarning) {
        this.mensajeWarning = mensajeWarning;
    }

    
    
    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }

    public String getLugarpartida() {
        return lugarpartida;
    }

    public void setLugarpartida(String lugarpartida) {
        this.lugarpartida = lugarpartida;
    }

    public String getLugardestino() {
        return lugardestino;
    }

    public void setLugardestino(String lugardestino) {
        this.lugardestino = lugardestino;
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

    public EstatusViaje getEstatusViaje() {
        return estatusViaje;
    }

    public void setEstatusViaje(EstatusViaje estatusViaje) {
        this.estatusViaje = estatusViaje;
    }

   

    
    
}
