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
public class Carrera {
    @Id
    private Integer idcarrera;
    private String carrera;
    @Referenced(documment = "Facultad",
            field = "idfacultad", javatype = "String", lazy = false,
            repository = "com.avbravo.transporteejb.repository.FacultadRepository")
            Facultad facultad;
    
          @Embedded
    List<UserInfo> userInfo;

    public Carrera() {
    }
          
          
}
