/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.entity;

import com.avbravo.commonejb.entity.Carrera;
import com.avbravo.commonejb.entity.Facultad;
import com.avbravo.ejbjmoordb.anotations.Embedded;
import com.avbravo.ejbjmoordb.anotations.Id;
import com.avbravo.ejbjmoordb.anotations.Referenced;
import com.avbravo.ejbjmoordb.pojos.UserInfo;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author avbravo
 */
@Getter
@Setter
public class Solicitud {
    @Id
    private Integer idsolicitud;
    private String periodoacademico;
    private Date fecha;
    private String numerogrupo;
    
       @Referenced(documment = "Facultad",
            field = "idfacultad", javatype = "Integer", lazy = false,
            repository = "com.avbravo.commonejb.repository.FacultadRepository")
    private List<Facultad> facultad;
       @Referenced(documment = "Carrera",
            field = "idcarrera", javatype = "Integer", lazy = false,
            repository = "com.avbravo.commonejb.repository.CarreraRepository")
    private List<Carrera> carrera;

    private String responsable;
    private String telefono;
    private String email;
    private String mision;
   
    
    @Referenced(documment = "Usuario",
            field = "username", javatype = "String", lazy = false,
           repository = "com.avbravo.transporteejb.repository.UsuarioRepository")
    Usuario usuario;
   
    
    private String objetivo;
    private String lugares;
    
    private Date fechahorapartida;
    private String lugarpartida;
    private String lugarllegada;
    
    private Date fechahoraregreso;
    
    private String lugarregreso;
    private String recursossolicitados;
    private String observaciones;
    
     @Referenced(documment = "Estatus",
            field = "idestatus", javatype = "String", lazy = false,
           repository = "com.avbravo.transporteejb.repository.EstatusRepository")
    Estatus estatus;
    
    private Date fechaestatus;
    
    private Integer pasajeros;
      @Referenced(documment = "Tiposolicitud",
            field = "idtiposolicitud", javatype = "String", lazy = false,
           repository = "com.avbravo.transporteejb.repository.TiposolicitudRepository")
   Tiposolicitud tiposolicitud;
      
      
       @Referenced(documment = "Unidad",
            field = "idunidad", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.UnidadRepository")
    private List<Unidad> unidad;
      private String activo;
    
    @Embedded
    List<UserInfo> userInfo;

    public Solicitud() {
    }

    public List<Facultad> getFacultad() {
        return facultad;
    }

    public void setFacultad(List<Facultad> facultad) {
        this.facultad = facultad;
    }

    

    
    
    
    public Integer getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(Integer idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public String getPeriodoacademico() {
        return periodoacademico;
    }

    public void setPeriodoacademico(String periodoacademico) {
        this.periodoacademico = periodoacademico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNumerogrupo() {
        return numerogrupo;
    }

    public void setNumerogrupo(String numerogrupo) {
        this.numerogrupo = numerogrupo;
    }

    public List<Carrera> getCarrera() {
        return carrera;
    }

    public void setCarrera(List<Carrera> carrera) {
        this.carrera = carrera;
    }

  
    
    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getLugares() {
        return lugares;
    }

    public void setLugares(String lugares) {
        this.lugares = lugares;
    }

    public Date getFechahorapartida() {
        return fechahorapartida;
    }

    public void setFechahorapartida(Date fechahorapartida) {
        this.fechahorapartida = fechahorapartida;
    }

    public String getLugarpartida() {
        return lugarpartida;
    }

    public void setLugarpartida(String lugarpartida) {
        this.lugarpartida = lugarpartida;
    }

    public Date getFechahoraregreso() {
        return fechahoraregreso;
    }

    public void setFechahoraregreso(Date fechahoraregreso) {
        this.fechahoraregreso = fechahoraregreso;
    }

    public String getLugarregreso() {
        return lugarregreso;
    }

    public void setLugarregreso(String lugarregreso) {
        this.lugarregreso = lugarregreso;
    }

    public String getRecursossolicitados() {
        return recursossolicitados;
    }

    public void setRecursossolicitados(String recursossolicitados) {
        this.recursossolicitados = recursossolicitados;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Date getFechaestatus() {
        return fechaestatus;
    }

    public void setFechaestatus(Date fechaestatus) {
        this.fechaestatus = fechaestatus;
    }

    public Integer getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(Integer pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Tiposolicitud getTiposolicitud() {
        return tiposolicitud;
    }

    public void setTiposolicitud(Tiposolicitud tiposolicitud) {
        this.tiposolicitud = tiposolicitud;
    }

    public List<Unidad> getUnidad() {
        return unidad;
    }

    public void setUnidad(List<Unidad> unidad) {
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

    public String getLugarllegada() {
        return lugarllegada;
    }

    public void setLugarllegada(String lugarllegada) {
        this.lugarllegada = lugarllegada;
    }
    
    
    
    
}
