/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.entity;

import com.avbravo.jmoordb.anotations.Id;
import java.util.Date;

/**
 *
 * @author avbravo
 */
public class VistoBuenoSecretarioAdministrativo {
    @Id
    private String idvistobuenosecretarioadministrativo;
    private Usuario usuario;
    private String aprobado;
    private Date fecha;

    public VistoBuenoSecretarioAdministrativo() {
    }

    public VistoBuenoSecretarioAdministrativo(String idvistobuenosecretarioadministrativo, Usuario usuario, String aprobado, Date fecha) {
        this.idvistobuenosecretarioadministrativo = idvistobuenosecretarioadministrativo;
        this.usuario = usuario;
        this.aprobado = aprobado;
        this.fecha = fecha;
    }

  
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdvistobuenosecretarioadministrativo() {
        return idvistobuenosecretarioadministrativo;
    }

    public void setIdvistobuenosecretarioadministrativo(String idvistobuenosecretarioadministrativo) {
        this.idvistobuenosecretarioadministrativo = idvistobuenosecretarioadministrativo;
    }

    
 

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAprobado() {
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        this.aprobado = aprobado;
    }
    
}
