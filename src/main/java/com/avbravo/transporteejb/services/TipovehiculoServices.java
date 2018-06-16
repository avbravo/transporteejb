/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Tipovehiculo;
import com.avbravo.transporteejb.repository.TipovehiculoRepository;
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
public class TipovehiculoServices {

    @Inject
    TipovehiculoRepository repository;
List<Tipovehiculo> tipovehiculoList = new ArrayList<>();
    public List<Tipovehiculo> complete(String query) {
        List<Tipovehiculo> suggestions = new ArrayList<>();
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

    
    // <editor-fold defaultstate="collapsed" desc="getTipovehiculoList()">
    public List<Tipovehiculo> getTipovehiculoList() {
        try {
           tipovehiculoList= repository.findAll(new Document("tipovehiculo",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getTipovehiculoList() " + e.getLocalizedMessage());
        }
        return tipovehiculoList;
    }// </editor-fold>

    public void setTipovehiculoList(List<Tipovehiculo> tipovehiculoList) {
        this.tipovehiculoList = tipovehiculoList;
    }
}
