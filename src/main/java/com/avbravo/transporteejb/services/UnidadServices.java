/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Unidad;
import com.avbravo.transporteejb.repository.UnidadRepository;
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
    UnidadRepository unidadRepository;

     List<Unidad> unidadList = new ArrayList<>();
     public List<Unidad> complete(String query) {
        List<Unidad> suggestions = new ArrayList<>();
           try {
               query = query.trim();
               if (query.length() < 1) {
                   return suggestions;
               }   
               String field = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");               
               suggestions=  unidadRepository.findRegex(field,query,true,new Document(field,1));

           } catch (Exception e) {
                    JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
           }
           return suggestions;
    }

    public List<Unidad> getUnidadList() {
          try {
          unidadList= unidadRepository.findAll(new Document("unidad",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getUnidadList() " + e.getLocalizedMessage());
        }

        return unidadList;
    }

    public void setUnidadList(List<Unidad> unidadList) {
        this.unidadList = unidadList;
    }
     
     
     
     
}