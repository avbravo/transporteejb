/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.entity;

import com.avbravo.commonejb.entity.Carrera;
import com.avbravo.commonejb.entity.Facultad;
import com.avbravo.commonejb.entity.Semestre;
import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import com.avbravo.jmoordb.anotations.Referenced;
import com.avbravo.jmoordb.anotations.Secondary;
import com.avbravo.jmoordb.pojos.UserInfo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author avbravo
 */
public class Solicitud {

    @Id
    private Integer idsolicitud;
    private String periodoacademico;
    @Secondary
    private Date fecha;
    private List<String> numerogrupo;
    private Integer numerodevehiculos;
    private List<String> rangoagenda;

    @Referenced(collection = "Facultad",
            field = "idfacultad", javatype = "Integer", lazy = false,
            repository = "com.avbravo.commonejb.repository.FacultadRepository")
    private List<Facultad> facultad;
    @Referenced(collection = "Carrera",
            field = "idcarrera", javatype = "Integer", lazy = false,
            repository = "com.avbravo.commonejb.repository.CarreraRepository")
    private List<Carrera> carrera;

    @Referenced(collection = "Semestre",
            field = "idsemestre", javatype = "String", lazy = false,
            repository = "com.avbravo.commonejb.repository.SemestreRepository")
    private Semestre semestre;

    @Referenced(collection = "Tipovehiculo",
            field = "idtipovehiculo", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.TipovehiculoRepository")
    private List<Tipovehiculo> tipovehiculo;

    @Referenced(collection = "Tipogira",
            field = "idtipogira", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.TipogiraRepository")
    private Tipogira tipogira;

    private String mision;

    @Referenced(collection = "Usuario",
            field = "username", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.UsuarioRepository")
    List<Usuario> usuario;

    private String objetivo;
    private List<String> lugares;

    private Date fechahorapartida;
    private String lugarpartida;
    private String lugarllegada;

    private Date fechahoraregreso;

    private List<String> recursossolicitados;
    private String observaciones;

    @Referenced(collection = "Estatus",
            field = "idestatus", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.EstatusRepository")
    Estatus estatus;

    private Date fechaestatus;

    private Integer pasajeros;
    @Referenced(collection = "Tiposolicitud",
            field = "idtiposolicitud", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.TiposolicitudRepository")
    Tiposolicitud tiposolicitud;

    @Referenced(collection = "Unidad",
            field = "idunidad", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.UnidadRepository")
    private List<Unidad> unidad;
    private String activo;

    @Referenced(collection = "Viaje",
            field = "idviaje", javatype = "Integer", lazy = false,
            repository = "com.avbravo.transporteejb.repository.ViajeRepository")
    private List<Viaje> viaje;

    @Referenced(collection = "EstatusViaje",
            field = "idestatusviaje", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.EstatusViajeRepository")
    private EstatusViaje estatusViaje;

    private Integer solicitudpadre;
    @Embedded
   VistoBueno vistoBueno;

    @Embedded
    List<UserInfo> userInfo;

    public Solicitud() {
    }

    public VistoBueno getVistoBueno() {
        return vistoBueno;
    }

    public void setVistoBueno(VistoBueno vistoBueno) {
        this.vistoBueno = vistoBueno;
    }

   

   

   

    public Integer getSolicitudpadre() {
        return solicitudpadre;
    }

    public void setSolicitudpadre(Integer solicitudpadre) {
        this.solicitudpadre = solicitudpadre;
    }

    public List<Viaje> getViaje() {
        return viaje;
    }

    public void setViaje(List<Viaje> viaje) {
        this.viaje = viaje;
    }

    public List<String> getRangoagenda() {
        return rangoagenda;
    }

    public void setRangoagenda(List<String> rangoagenda) {
        this.rangoagenda = rangoagenda;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public List<Tipovehiculo> getTipovehiculo() {
        return tipovehiculo;
    }

    public void setTipovehiculo(List<Tipovehiculo> tipovehiculo) {
        this.tipovehiculo = tipovehiculo;
    }

    public Tipogira getTipogira() {
        return tipogira;
    }

    public void setTipogira(Tipogira tipogira) {
        this.tipogira = tipogira;
    }

    public List<Facultad> getFacultad() {
        return facultad;
    }

    public void setFacultad(List<Facultad> facultad) {
        this.facultad = facultad;
    }

    public Integer getNumerodevehiculos() {
        return numerodevehiculos;
    }

    public void setNumerodevehiculos(Integer numerodevehiculos) {
        this.numerodevehiculos = numerodevehiculos;
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

    public List<String> getNumerogrupo() {
        return numerogrupo;
    }

    public void setNumerogrupo(List<String> numerogrupo) {
        this.numerogrupo = numerogrupo;
    }

    public List<Carrera> getCarrera() {
        return carrera;
    }

    public void setCarrera(List<Carrera> carrera) {
        this.carrera = carrera;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
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

    public List<String> getLugares() {
        return lugares;
    }

    public void setLugares(List<String> lugares) {
        this.lugares = lugares;
    }

    public List<String> getRecursossolicitados() {
        return recursossolicitados;
    }

    public void setRecursossolicitados(List<String> recursossolicitados) {
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

    public EstatusViaje getEstatusViaje() {
        return estatusViaje;
    }

    public void setEstatusViaje(EstatusViaje estatusViaje) {
        this.estatusViaje = estatusViaje;
    }

}
