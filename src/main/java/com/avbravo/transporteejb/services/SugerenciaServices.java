/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Sugerencia;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.SugerenciaRepository;
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
public class SugerenciaServices {

    @Inject
    SugerenciaRepository repository;
      @Inject
   SolicitudRepository solicitudRepository;
    List<Sugerencia> sugerenciaList = new ArrayList<>();
     public List<Sugerencia> complete(String query) {
        List<Sugerencia> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    public List<Sugerencia> getSugerenciaList() {
          try {
        sugerenciaList= repository.findAll(new Document("idsugerencia",1));
        } catch (Exception e) {
              JmoordbUtil.errorMessage("getSugerenciaList() " + e.getLocalizedMessage());
        }

        return sugerenciaList;
    }

    public void setSugerenciaList(List<Sugerencia> sugerenciaList) {
        this.sugerenciaList = sugerenciaList;
    }
     
     
         // <editor-fold defaultstate="collapsed" desc="isDeleted(Sugerencia sugerencia)">
  
    public Boolean isDeleted(Sugerencia sugerencia){
        Boolean found=false;
        try {
            Document doc = new Document("sugerencia.idsugerencia",sugerencia.getIdsugerencia());
            Integer count = solicitudRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
             JmoordbUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
     
     // <editor-fold defaultstate="collapsed" desc="findById(String id)">

        public Sugerencia findById(String id){
           Sugerencia sugerencia = new Sugerencia();
        try {
         
            sugerencia.setIdsugerencia(id);
            Optional<Sugerencia> optional = repository.findById(sugerencia);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JmoordbUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return sugerencia;
    }
    // </editor-fold>
}
