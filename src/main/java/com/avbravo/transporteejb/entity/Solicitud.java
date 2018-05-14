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
    private String carrera;
    private String facultad;
   
    
    @Referenced(documment = "Usuario",
            field = "username", javatype = "String", lazy = false,
           repository = "com.avbravo.transporteejb.repository.UsuarioRepository")
    Usuario usuario;
    
    private String objetivo;
    private String lugares;
    
    private Date fechahorapartida;
    private String lugarpartida;
    
    private Date fechahoraregreso;
    
    private String lugarllegada;
    private String recursossolicitados;
    private String observaciones;
    
     @Referenced(documment = "Estatus",
            field = "idestatus", javatype = "String", lazy = false,
           repository = "com.avbravo.transporteejb.repository.EstatusRepository")
    Estatus estatus;
    
    private Date fechaestatus;
    
    private Integer pasajeros;
    
    @Embedded
    List<UserInfo> userInfo;
    
}
