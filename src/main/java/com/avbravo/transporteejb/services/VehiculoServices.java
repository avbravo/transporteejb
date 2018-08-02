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
          suggestions=repository.complete(query);
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
