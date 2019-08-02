/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordbutils.JsfUtil;

import com.avbravo.transporteejb.entity.Lugares;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.repository.LugaresRepository;
import com.avbravo.transporteejb.repository.SolicitudRepository;
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
public class LugaresServices {

    @Inject
    LugaresRepository repository;
 @Inject
   SolicitudRepository solicitudRepository;
    List<Lugares> lugaresList = new ArrayList<>();

    public List<Lugares> complete(String query) {
        List<Lugares> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }
    /**
     * Se usa para ingresar mediante el autocomplete si no existe
     * el usuario da un espacio en blanco con la barra para que este se inserte en la base de datos
     * @param query
     * @return 
     */
    public List<Lugares> completeWithInsert(String query) {
        List<Lugares> suggestions = new ArrayList<>();
           try {
          
          suggestions=repository.complete(query);
          if(suggestions == null || suggestions.isEmpty()){
              if (JsfUtil.totalEspaciosAlfinalCadena(query)>=2){
                  Lugares lugares = new Lugares();
                  lugares.setActivo("si");
                  lugares.setIdlugares(query.trim());
                   Usuario jmoordb_user = (Usuario) JmoordbContext.get("jmoordb_user");
                  lugares = repository.addUserInfoForSaveMethod(lugares, jmoordb_user.getUsername(), "create from autocomplete");
                  repository.save(lugares);
              }
               suggestions= completeWithInsert(query.trim());
          }
        } catch (Exception e) {
            JsfUtil.errorMessage("completeWithInsert() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<Lugares> getLugaresList() {
        try {
            lugaresList = repository.findAll(new Document("idlugares", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getLugaresList() " + e.getLocalizedMessage());
        }

        return lugaresList;
    }

    public void setLugaresList(List<Lugares> lugaresList) {
        this.lugaresList = lugaresList;
    }
    
        // <editor-fold defaultstate="collapsed" desc="isDeleted(Lugares lugares)">
  
    public Boolean isDeleted(Lugares lugares){
        Boolean found=false;
        try {
            Document doc = new Document("lugares.idlugares",lugares.getIdlugares());
            Integer count = solicitudRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    public Lugares findById(String id){
           Lugares lugares = new Lugares();
        try {
         
            lugares.setIdlugares(id);
            Optional<Lugares> optional = repository.findById(lugares);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return lugares;
    }
    // </editor-fold>

}
