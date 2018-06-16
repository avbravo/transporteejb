/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Vehiculo;
import com.avbravo.transporteejb.repository.VehiculoRepository;
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
public class VehiculoServices {

    @Inject
    VehiculoRepository repository;
    List<Vehiculo> vehiculoList = new ArrayList<>();

    public List<Vehiculo> complete(String query) {
        List<Vehiculo> suggestions = new ArrayList<>();
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

    // <editor-fold defaultstate="collapsed" desc="getVehiculoList()">
    public List<Vehiculo> getVehiculoList() {
        try {
            vehiculoList = repository.findAll(new Document("vehiculo", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getVehiculoList() " + e.getLocalizedMessage());
        }
        return vehiculoList;
    }// </editor-fold>

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }
}
