/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.rules;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Tipovehiculo;
import com.avbravo.transporteejb.repository.VehiculoRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class TipovehiculoRules {

    @Inject
   VehiculoRepository vehiculoRepository;
    
   

    // <editor-fold defaultstate="collapsed" desc="isDeleted(Tipovehiculo tipovehiculo)">
  
    public Boolean isDeleted(Tipovehiculo tipovehiculo){
        Boolean found=false;
        try {
            Document doc = new Document("tipovehiculo.idtipovehiculo",tipovehiculo.getIdtipovehiculo());
            Integer count = vehiculoRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
   
}
