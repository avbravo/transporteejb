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
          suggestions=repository.complete(query);
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
