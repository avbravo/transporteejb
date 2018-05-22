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
public class Viajes {

    @Id
    private Integer idviaje;
    @Referenced(documment = "Solicitud",
            field = "idsolicitud", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.SolicitudRepository")
    private Solicitud solicitud;
    @Referenced(documment = "Vehiculo",
            field = "idvehiculo", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.VehiculoRepository")
    private Vehiculo vehiculo;
    @Referenced(documment = "Conductor",
            field = "idconductor", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.ConductorRepository")
    private Conductor conductor;
    
    
    private Date fechahorasalida;

    private Date fechahorallegada;
    private String comentarios;
    
    private Double kmestimados;
    private String activo;

    @Embedded
    List<UserInfo> userInfo;
}
