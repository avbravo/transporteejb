/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Salvoconducto;
import com.avbravo.transporteejb.repository.SalvoconductoRepository;
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
public class SalvoconductoConverter implements Converter {
 @Inject
    ErrorInfoServices errorServices;
    @Inject
    SalvoconductoRepository salvoconductoRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Salvoconducto salvoconducto = new Salvoconducto();
        try {
            if (!s.equals("null")) {
                Salvoconducto b = new Salvoconducto();
                b.setIdsalvoconducto(Integer.parseInt(s));
                Optional<Salvoconducto> optional = salvoconductoRepository.findById(b);
                if (optional.isPresent()) {
                    salvoconducto = optional.get();
                }
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }
        return salvoconducto;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Salvoconducto) {
                Salvoconducto salvoconducto = (Salvoconducto) o;
                r = String.valueOf(salvoconducto.getIdsalvoconducto());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }
        return r;
    }

}
