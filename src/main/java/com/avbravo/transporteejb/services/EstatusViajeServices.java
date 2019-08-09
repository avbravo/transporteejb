/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordbutils.JsfUtil;

import com.avbravo.transporteejb.entity.Estatus;
import com.avbravo.transporteejb.entity.EstatusViaje;
import com.avbravo.transporteejb.repository.EstatusViajeRepository;
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
public class EstatusViajeServices {

    @Inject
    EstatusViajeRepository repository;
 @Inject
   SolicitudRepository solicitudRepository;
    List<EstatusViaje> estatusViajeList = new ArrayList<>();

    public List<EstatusViaje> complete(String query) {
        List<EstatusViaje> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<EstatusViaje> getEstatusList() {
        try {
            estatusViajeList = repository.findAll(new Document("idestatusviaje", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getEstatusList() " + e.getLocalizedMessage());
        }

        return estatusViajeList;
    }

    public void setEstatusViajeList(List<EstatusViaje> estatusViajeList) {
        this.estatusViajeList = estatusViajeList;
    }
    
        // <editor-fold defaultstate="collapsed" desc="isDeleted(Estatus estatus)">
  
    public Boolean isDeleted(EstatusViaje estatusViaje){
        Boolean found=false;
        try {
            Document doc = new Document("estatusviaje.idestatusviaje",estatusViaje.getIdestatusviaje());
            Integer count = solicitudRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    public EstatusViaje findById(String id){
           EstatusViaje estatusViaje = new EstatusViaje();
        try {
         
            estatusViaje.setIdestatusviaje(id);
            Optional<EstatusViaje> optional = repository.findById(estatusViaje);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return estatusViaje;
    }
    // </editor-fold>

}