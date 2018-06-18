/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.ejbjmoordb.pojos.Configuracion;
import com.avbravo.ejbjmoordb.services.RevisionHistoryServices;
import com.avbravo.ejbjmoordb.services.UserInfoServices;
import com.avbravo.transporteejb.repository.AutoincrementableTransporteejbRepository;
import com.avbravo.transporteejb.repository.ConfiguracionTransporteejbRepository;
import com.avbravo.transporteejb.repository.RevisionHistoryTransporteejbRepository;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class ConfiguracionTransporteejbServices { 

    @Inject
    ConfiguracionTransporteejbRepository repository;
    //Repository
    @Inject
    AutoincrementableTransporteejbRepository autoincrementableRepository;
    @Inject
    AutoincrementableTransporteejbServices autoincrementableTransporteejbServices;
    @Inject
    UserInfoServices userInfoServices;
  @Inject
    RevisionHistoryServices revisionHistoryServices;
    @Inject
    RevisionHistoryTransporteejbRepository revisionHistoryTransporteejbRepository;

    List<Configuracion> configuracionList = new ArrayList<>();

    public List<Configuracion> complete(String query) {
        List<Configuracion> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<Configuracion> getConfiguracionList() {
        try {
            configuracionList = repository.findAll(new Document("configuracion", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getConfiguracionList() " + e.getLocalizedMessage());
        }
        return configuracionList;
    }

    public void setConfiguracionList(List<Configuracion> configuracionList) {
        this.configuracionList = configuracionList;
    }

    /**
     * devuelve la configuracion inicial si no existe crea una con los
     * parametros basicos
     *
     * @return
     */
    public Configuracion generarConfiguracionInicial(String username) {
        Configuracion configuracion = new Configuracion();
        try {
            List<Configuracion> list = repository.findBy(new Document("activo", "si"));
            if (list.isEmpty()) {
                Integer id = autoincrementableTransporteejbServices.getContador("configuracion");
                configuracion.setIdconfiguracion(id);
                configuracion.setActivo("si");
                configuracion.setDecimales(2);
                configuracion.setFormatodecimal("#0.00");
                configuracion.setFormatofecha("dd/MM/yyyy");
                configuracion.setFormatofechahora("dd/MM/yyyy HH:mm:ss");
                configuracion.setItbms(0.07);
                configuracion.setMinQueryLengthAutocompleteSmall(1);
                configuracion.setMinQueryLengthAutocompleteMedium(3);
                configuracion.setMinQueryLengthAutocompleteLarge(4);

                configuracion.setUserInfo(userInfoServices.generateListUserinfo(username, "create"));
                if (repository.save(configuracion)) {
                    revisionHistoryTransporteejbRepository.save(revisionHistoryServices.getRevisionHistory(configuracion.getIdconfiguracion().toString(), username,
                            "create", "configuracion", repository.toDocument(configuracion).toString()));
                    
                  
                } else {
                    JsfUtil.successMessage("save() " + repository.getException().toString());
                }
            }
            else{
                configuracion=configuracionList.get(0);
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("generarConfiguracionInicial() " + e.getLocalizedMessage());
        }
        return configuracion;
    }
}
