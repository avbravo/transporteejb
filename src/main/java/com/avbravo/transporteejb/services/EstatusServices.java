/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Estatus;
import com.avbravo.transporteejb.repository.EstatusRepository;
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
public class EstatusServices {

    @Inject
    EstatusRepository repository;

    List<Estatus> estatusList = new ArrayList<>();

    public List<Estatus> complete(String query) {
        List<Estatus> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<Estatus> getEstatusList() {
        try {
            estatusList = repository.findAll(new Document("estatus", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getEstatusList() " + e.getLocalizedMessage());
        }

        return estatusList;
    }

    public void setEstatusList(List<Estatus> estatusList) {
        this.estatusList = estatusList;
    }

}
