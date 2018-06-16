/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Tiposolicitud;
import com.avbravo.transporteejb.repository.TiposolicitudRepository;
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
public class TiposolicitudServices {

    @Inject
    TiposolicitudRepository repository;
    List<Tiposolicitud> tiposolicitudList = new ArrayList<>();
     public List<Tiposolicitud> complete(String query) {
        List<Tiposolicitud> suggestions = new ArrayList<>();
            try {
            query = query.trim();
            String field = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");
            String fromstart = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("fromstart");
            String fielddropdown = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("fielddropdown");
            String fieldquerylenth = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("fieldquerylenth");

            if (fielddropdown.equals("false")) {
                if (query.length() < Integer.parseInt(fieldquerylenth)) {
                    return suggestions;
                }
                if (fromstart.equals("true")) {
                    suggestions = repository.findRegex(field, query, true, new Document(field, 1));
                } else {
                    suggestions = repository.findRegexInText(field, query, false, new Document(field, 1));
                }
            } else {
                suggestions = repository.findRegexInText(field, query, false, new Document(field, 1));

            }

        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }
           return suggestions;
    }

    public List<Tiposolicitud> getTiposolicitudList() {
          try {
        tiposolicitudList= repository.findAll(new Document("idtiposolicitud",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getTiposolicitudList() " + e.getLocalizedMessage());
        }

        return tiposolicitudList;
    }

    public void setTiposolicitudList(List<Tiposolicitud> tiposolicitudList) {
        this.tiposolicitudList = tiposolicitudList;
    }
     
     
     
     
     
}
