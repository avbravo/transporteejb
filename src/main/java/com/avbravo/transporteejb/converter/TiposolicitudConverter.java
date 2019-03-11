/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Tiposolicitud;
import com.avbravo.transporteejb.repository.TiposolicitudRepository;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@RequestScoped
public class TiposolicitudConverter implements Converter {
 
    @Inject
    TiposolicitudRepository tiposolicitudRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Tiposolicitud tiposolicitud = new Tiposolicitud();
        try {
            if (!s.equals("null")) {
                Tiposolicitud b = new Tiposolicitud();
                b.setIdtiposolicitud(s);
                Optional<Tiposolicitud> optional = tiposolicitudRepository.findById(b);
                if (optional.isPresent()) {
                    tiposolicitud = optional.get();
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
        }
        return tiposolicitud;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Tiposolicitud) {
                Tiposolicitud tiposolicitud = (Tiposolicitud) o;
                r = String.valueOf(tiposolicitud.getIdtiposolicitud());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
    }

}
