/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;

import com.avbravo.transporteejb.entity.SalvoConductoNotas;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.repository.SalvoConductoNotasRepository;
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
public class SalvoConductoNotasServices {
 @Inject
    ErrorInfoServices errorServices;
    @Inject
    SalvoConductoNotasRepository repository;
 @Inject
   SolicitudRepository solicitudRepository;
    List<SalvoConductoNotas> salvoConductoNotasList = new ArrayList<>();

    public List<SalvoConductoNotas> complete(String query) {
        List<SalvoConductoNotas> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }

        return suggestions;
    }
    /**
     * Se usa para ingresar mediante el autocomplete si no existe
     * el usuario da un espacio en blanco con la barra para que este se inserte en la base de datos
     * @param query
     * @return 
     */
    public List<SalvoConductoNotas> completeWithInsert(String query) {
        List<SalvoConductoNotas> suggestions = new ArrayList<>();
           try {
          
          suggestions=repository.complete(query);
          if(suggestions == null || suggestions.isEmpty()){
              if (JmoordbUtil.totalEspaciosAlfinalCadena(query)>=2){
                  SalvoConductoNotas salvoConductoNotas = new SalvoConductoNotas();
                  salvoConductoNotas.setActivo("si");
                  salvoConductoNotas.setIdsalvoconductonotas(query.trim());
                   Usuario jmoordb_user = (Usuario) JmoordbContext.get("jmoordb_user");
                  salvoConductoNotas = repository.addUserInfoForSaveMethod(salvoConductoNotas, jmoordb_user.getUsername(), "create from autocomplete");
                  repository.save(salvoConductoNotas);
              }
               suggestions= completeWithInsert(query.trim());
          }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }

        return suggestions;
    }

    public List<SalvoConductoNotas> getSalvoConductoNotasList() {
        try {
            salvoConductoNotasList = repository.findAll(new Document("idsalvoConductoNotas", 1));
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }

        return salvoConductoNotasList;
    }

    public void setSalvoConductoNotasList(List<SalvoConductoNotas> salvoConductoNotasList) {
        this.salvoConductoNotasList = salvoConductoNotasList;
    }
    
        // <editor-fold defaultstate="collapsed" desc="isDeleted(SalvoConductoNotas salvoConductoNotas)">
  
    public Boolean isDeleted(SalvoConductoNotas salvoConductoNotas){
        Boolean found=false;
        try {
            Document doc = new Document("salvoConductoNotas.idsalvoconductonotas",salvoConductoNotas.getIdsalvoconductonotas());
            Integer count = solicitudRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }
        return true;
    }  // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    public SalvoConductoNotas findById(String id){
           SalvoConductoNotas salvoConductoNotas = new SalvoConductoNotas();
        try {
         
            salvoConductoNotas.setIdsalvoconductonotas(id);
            Optional<SalvoConductoNotas> optional = repository.findById(salvoConductoNotas);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }
      
      return salvoConductoNotas;
    }
    // </editor-fold>

}
