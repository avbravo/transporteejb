/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Tipovehiculo;
import com.avbravo.transporteejb.repository.TipovehiculoRepository;
import com.avbravo.transporteejb.repository.VehiculoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class TipovehiculoServices {

    @Inject
    TipovehiculoRepository repository;
       @Inject
   VehiculoRepository vehiculoRepository;
List<Tipovehiculo> tipovehiculoList = new ArrayList<>();
    public List<Tipovehiculo> complete(String query) {
        List<Tipovehiculo> suggestions = new ArrayList<>();
             try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    
    // <editor-fold defaultstate="collapsed" desc="getTipovehiculoList()">
    public List<Tipovehiculo> getTipovehiculoList() {
        try {
           tipovehiculoList= repository.findAll(new Document("tipovehiculo",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getTipovehiculoList() " + e.getLocalizedMessage());
        }
        return tipovehiculoList;
    }// </editor-fold>

    public void setTipovehiculoList(List<Tipovehiculo> tipovehiculoList) {
        this.tipovehiculoList = tipovehiculoList;
    }
    
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
    
      // <editor-fold defaultstate="collapsed" desc="findById(String id)">

    public Tipovehiculo findById(String id){
           Tipovehiculo tipovehiculo = new Tipovehiculo();
        try {
         
            tipovehiculo.setIdtipovehiculo(id);
            Optional<Tipovehiculo> optional = repository.findById(tipovehiculo);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return tipovehiculo;
    }
    // </editor-fold>
}
