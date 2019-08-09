/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.entity;

import com.avbravo.jmoordb.anotations.Id;

/**
 *
 * @author avbravo
 */
public class VistoBueno {
    @Id
    private String idvistobueno;
    private Usuario usuario;
    private String aprobado;

    public VistoBueno() {
    }

    public VistoBueno(String idvistobueno, Usuario usuario, String aprobado) {
        this.idvistobueno = idvistobueno;
        this.usuario = usuario;
        this.aprobado = aprobado;
    }

    
    
    
    public String getIdvistobueno() {
        return idvistobueno;
    }

    public void setIdvistobueno(String idvistobueno) {
        this.idvistobueno = idvistobueno;
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
