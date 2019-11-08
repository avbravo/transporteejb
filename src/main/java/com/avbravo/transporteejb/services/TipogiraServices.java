/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Tipogira;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.TipogiraRepository;
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
public class TipogiraServices {
 @Inject
    ErrorInfoServices errorServices;
    @Inject
    TipogiraRepository repository;
      @Inject
   SolicitudRepository giraRepository;
    List<Tipogira> tipogiraList = new ArrayList<>();
     public List<Tipogira> complete(String query) {
        List<Tipogira> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    public List<Tipogira> getTipogiraList() {
          try {
        tipogiraList= repository.findAll(new Document("idtipogira",1));
        } catch (Exception e) {
               errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("getTipogiraList() " + e.getLocalizedMessage());
        }

        return tipogiraList;
    }

    public void setTipogiraList(List<Tipogira> tipogiraList) {
        this.tipogiraList = tipogiraList;
    }
     
     
         // <editor-fold defaultstate="collapsed" desc="isDeleted(Tipogira tipogira)">
  
    public Boolean isDeleted(Tipogira tipogira){
        Boolean found=false;
        try {
            Document doc = new Document("tipogira.idtipogira",tipogira.getIdtipogira());
            Integer count = giraRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
     
     // <editor-fold defaultstate="collapsed" desc="findById(String id)">

        public Tipogira findById(String id){
           Tipogira tipogira = new Tipogira();
        try {
         
            tipogira.setIdtipogira(id);
            Optional<Tipogira> optional = repository.findById(tipogira);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return tipogira;
    }
    // </editor-fold>
}
