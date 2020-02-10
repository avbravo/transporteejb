/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;

import com.avbravo.transporteejb.entity.Estatus;
import com.avbravo.transporteejb.repository.EstatusRepository;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class EstatusServices {
 @Inject
    ErrorInfoServices errorServices;
    @Inject
    EstatusRepository repository;
    @Inject
    SolicitudRepository solicitudRepository;
    List<Estatus> estatusList = new ArrayList<>();

    public List<Estatus> complete(String query) {
        List<Estatus> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }

        return suggestions;
    }

    public List<Estatus> getEstatusList() {
        try {
            estatusList = repository.findAll(new Document("idestatus", 1));
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }

        return estatusList;
    }

    public void setEstatusList(List<Estatus> estatusList) {
        this.estatusList = estatusList;
    }

    // <editor-fold defaultstate="collapsed" desc="isDeleted(Estatus estatus)">
    public Boolean isDeleted(Estatus estatus) {
        Boolean found = false;
        try {
            Document doc = new Document("estatus.idestatus", estatus.getIdestatus());
            Integer count = solicitudRepository.count(doc);
            if (count > 0) {
                return false;
            }

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }
        return true;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">
    public Estatus findById(String id) {
        Estatus estatus = new Estatus();
        try {

            estatus.setIdestatus(id);
            Optional<Estatus> optional = repository.findById(estatus);
            if (optional.isPresent()) {
                return optional.get();
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }

        return estatus;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="columnColor(Estatus estatus)">
    /**
     * Devuelve el color en base al estatus
     *
     * @param estatus
     * @return
     */
    public String columnColor(Estatus estatus) {
        String color = "";
        try {
            switch (estatus.getIdestatus()) {
                case "RECHAZADO":
                    color = "red";
                    break;
                case "CANCELADO":
                    color = "pink";
                    break;
                case "APROBADO":
                    color = "green";
                    break;
                case "SOLICITADO":
                    color = "black";
                    break;
                default:
                    color = "black";
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }
        return color;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean isSolicitado(Estatus estatus)">
    /**
     * Verifica si el estatus es solicitado se usa mucho en las solicitudes
     *
     * @param estatus
     * @return
     */
    public Boolean isSolicitado(Estatus estatus) {
        Boolean habilitado = true;
        try {
            switch (estatus.getIdestatus()) {
                case "RECHAZADO":
                    habilitado = false;
                    break;
                case "CANCELADO":
                    habilitado = false;
                    break;
                case "APROBADO":
                    habilitado = false;
                    break;
                case "SOLICITADO":
                default:
                    habilitado = true;
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }
        return habilitado;
    } // </editor-fold>

}
