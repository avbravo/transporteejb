/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.entity;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import com.avbravo.jmoordb.anotations.Ignore;
import com.avbravo.jmoordb.anotations.Referenced;
import com.avbravo.jmoordb.anotations.Secondary;
import com.avbravo.jmoordb.pojos.UserInfo;
import java.util.List;

/**
 *
 * @author avbravo
 */
public class Vehiculo {

    @Id
    private Integer idvehiculo;
   @Secondary
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;

    @Referenced(collection = "Tipovehiculo",
            field = "idtipovehiculo", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.TipovehiculoRepository")
    Tipovehiculo tipovehiculo;

    private Integer pasajeros;

    private String combustible;
    private String chasis;
    private Double km;
    private String enreparacion;
    private String activo;
    @Embedded
    List<UserInfo> userInfo;
    @Ignore
    private Double totalkm;
    @Ignore
    Double totalconsumo;
    @Ignore
    Integer totalviajes;

    public Vehiculo() {
    }

    public Integer getTotalviajes() {
        return totalviajes;
    }

    public void setTotalviajes(Integer totalviajes) {
        this.totalviajes = totalviajes;
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

    
    
    
    
    
    public Integer getIdvehiculo() {
        return idvehiculo;
    }

    public void setIdvehiculo(Integer idvehiculo) {
        this.idvehiculo = idvehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEnreparacion() {
        return enreparacion;
    }

    public void setEnreparacion(String enreparacion) {
        this.enreparacion = enreparacion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Tipovehiculo getTipovehiculo() {
        return tipovehiculo;
    }

    public void setTipovehiculo(Tipovehiculo tipovehiculo) {
        this.tipovehiculo = tipovehiculo;
    }

    public Integer getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(Integer pasajeros) {
        this.pasajeros = pasajeros;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
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
