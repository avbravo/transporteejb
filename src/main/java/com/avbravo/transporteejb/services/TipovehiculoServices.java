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
          suggestions=repository.complete(query);
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
