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
    
}
