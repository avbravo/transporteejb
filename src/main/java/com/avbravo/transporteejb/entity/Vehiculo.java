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
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author avbravo
 */
@Getter
@Setter 
public class Vehiculo {
    @Id
    private String idvehiculo;
    private String marca;
    private String modelo;
    private Integer anio;
    private String activo;
      @Referenced(documment = "Tipovehiculo",
            field = "idtipovehiculo", javatype = "String", lazy = false,
           repository = "com.avbravo.transporteejb.repository.TipovehiculoRepository")
   Tipovehiculo tipovehiculo;
      
      private Integer pasajeros;
  @Embedded
    List<UserInfo> userInfo;
   
}
