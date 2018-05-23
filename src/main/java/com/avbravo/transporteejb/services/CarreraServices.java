/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Carrera;
import com.avbravo.transporteejb.repository.CarreraRepository;
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
public class CarreraServices {

    @Inject
    CarreraRepository carreraRepository;

     List<Carrera> carreraList = new ArrayList<>();
     public List<Carrera> complete(String query) {
        List<Carrera> suggestions = new ArrayList<>();
           try {
               query = query.trim();
               if (query.length() < 1) {
                   return suggestions;
               }   
               String field = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");               
               suggestions=  carreraRepository.findRegex(field,query,true,new Document(field,1));

           } catch (Exception e) {
                    JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
           }
           return suggestions;
    }

    public List<Carrera> getCarreraList() {
          try {
          carreraList= carreraRepository.findAll(new Document("carrera",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getCarreraList() " + e.getLocalizedMessage());
        }

        return carreraList;
    }

    public void setCarreraList(List<Carrera> carreraList) {
        this.carreraList = carreraList;
    }
     
     
     
     
}
