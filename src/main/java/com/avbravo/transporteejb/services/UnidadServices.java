/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Unidad;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.UnidadRepository;
import com.avbravo.transporteejb.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class UnidadServices {

    @Inject
    UnidadRepository repository;
     @Inject
   SolicitudRepository solicitudRepository;
    @Inject
    UsuarioRepository usuarioRepository;

     List<Unidad> unidadList = new ArrayList<>();
     public List<Unidad> complete(String query) {
        List<Unidad> suggestions = new ArrayList<>();
            try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    public List<Unidad> getUnidadList() {
          try {
          unidadList= repository.findAll(new Document("unidad",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getUnidadList() " + e.getLocalizedMessage());
        }

        return unidadList;
    }

    public void setUnidadList(List<Unidad> unidadList) {
        this.unidadList = unidadList;
    }
     
     
         // <editor-fold defaultstate="collapsed" desc="isDeleted(Unidad unidad)">
  
    public Boolean isDeleted(Unidad unidad){
        Boolean found=false;
        try {
            Document doc = new Document("unidad.idunidad",unidad.getIdunidad());
            Integer count = solicitudRepository.count(doc);
            if (count > 0){
                return false;
            }
             count = usuarioRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
     
}
