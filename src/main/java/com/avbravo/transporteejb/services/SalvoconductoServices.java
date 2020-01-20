/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Salvoconducto;
import com.avbravo.transporteejb.repository.SalvoconductoRepository;
import com.avbravo.transporteejb.repository.ViajeRepository;
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
public class SalvoconductoServices {
    
     @Inject
    ErrorInfoServices errorServices;
 @Inject
    ViajeRepository viajesRepository;
        
    @Inject
    SalvoconductoRepository repository;
    List<Salvoconducto> salvoconductoList = new ArrayList<>();
    
     public List<Salvoconducto> complete(String query) {
        List<Salvoconducto> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }

           return suggestions;
    }

    public List<Salvoconducto> getSalvoconductoList() {
          try {
        salvoconductoList= repository.findAll(new Document("idsalvoconducto",1));
        } catch (Exception e) {
               errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }

        return salvoconductoList;
    }

    public void setSalvoconductoList(List<Salvoconducto> salvoconductoList) {
        this.salvoconductoList = salvoconductoList;
    }
     
     // <editor-fold defaultstate="collapsed" desc="isDeleted(Salvoconducto salvoconducto)">
  
    public Boolean isDeleted(Salvoconducto salvoconducto){
        Boolean found=false;
        try {
            Document doc = new Document("salvoconducto.idsalvoconducto",salvoconducto.getIdsalvoconducto());
            Integer count = viajesRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }
        return true;
    }  // </editor-fold>
     
       // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    public Salvoconducto findById(Integer id){
           Salvoconducto salvoconducto = new Salvoconducto();
        try {
         
            salvoconducto.setIdsalvoconducto(id);
            Optional<Salvoconducto> optional = repository.findById(salvoconducto);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }
      
      return salvoconducto;
    }
    // </editor-fold>
     
}
