/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Rol;
import com.avbravo.transporteejb.repository.RolRepository;
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
public class RolServices {

    @Inject
    RolRepository repository;
List<Rol> rolList = new ArrayList<>();
    public List<Rol> complete(String query) {
        List<Rol> suggestions = new ArrayList<>();
             try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }
    public List<Rol> completeFiltrado(String query) {
        List<Rol> suggestions = new ArrayList<>();
           try {
               query = query.trim();
               if (query.length() < 1) {
                   return suggestions;
               }   
               String field = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");               
               suggestions=  repository.findRegex(field,query,true,new Document(field,1));

           } catch (Exception e) {
                    JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
           }
           return suggestions;
    }

    
    // <editor-fold defaultstate="collapsed" desc="getRolList()">
    public List<Rol> getRolList() {
        try {
           rolList= repository.findAll(new Document("rol",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getRolList() " + e.getLocalizedMessage());
        }
        return rolList;
    }// </editor-fold>

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }
}
