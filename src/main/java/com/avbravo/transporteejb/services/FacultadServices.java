/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Facultad;
import com.avbravo.transporteejb.repository.FacultadRepository;
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
public class FacultadServices {

    @Inject
    FacultadRepository facultadRepository;

    List<Facultad> facultadList = new ArrayList<>();

    public List<Facultad> complete(String query) {
        List<Facultad> suggestions = new ArrayList<>();
        try {
            query = query.trim();
            if (query.length() < 1) {
                return suggestions;
            }

            String field = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");
            if (field.equals("idfacultad")) {
                List<Facultad> facultadList = facultadRepository.findAll();
                if (!facultadList.isEmpty()) {
                    for (Facultad f : facultadList) {
                        if (String.valueOf(f.getIdfacultad()).toLowerCase().startsWith(query.toLowerCase())) {
                            suggestions.add(f);
                        }
                    }
                }

            } else {
                suggestions = facultadRepository.findRegex(field, query, true, new Document(field, 1));
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }
        return suggestions;
    }

    public List<Facultad> getFacultadList() {
        try {
            facultadList = facultadRepository.findAll(new Document("facultad", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getFacultadList() " + e.getLocalizedMessage());
        }

        return facultadList;
    }

    public void setFacultadList(List<Facultad> facultadList) {
        this.facultadList = facultadList;
    }

}
