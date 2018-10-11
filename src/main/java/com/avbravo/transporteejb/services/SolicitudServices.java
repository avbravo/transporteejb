/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Solicitud;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.ViajesRepository;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @authoravbravo
 */
@Stateless
public class SolicitudServices {

    @Inject
    SolicitudRepository repository;
      @Inject
    ViajesRepository viajesRepository;
List<Solicitud> solicitudList = new ArrayList<>();
    public List<Solicitud> complete(String query) {
        List<Solicitud> suggestions = new ArrayList<>();
            try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    
    // <editor-fold defaultstate="collapsed" desc="getSolicitudList()">
    public List<Solicitud> getSolicitudList() {
        try {
           solicitudList= repository.findAll(new Document("solicitud",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getSolicitudList() " + e.getLocalizedMessage());
        }
        return solicitudList;
    }// </editor-fold>

    public void setSolicitudList(List<Solicitud> solicitudList) {
        this.solicitudList = solicitudList;
    }
    
       // <editor-fold defaultstate="collapsed" desc="isDeleted(Solicitud solicitud)">
    public Boolean isDeleted(Solicitud solicitud) {
        Boolean found = false;
        try {
            Document doc = new Document("solicitud.idsolicitud", solicitud.getIdsolicitud());
            Integer count = viajesRepository.count(doc);
            if (count > 0) {
                return false;
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    
    public Solicitud findById(Integer id){
           Solicitud solicitud = new Solicitud();
        try {
         
            solicitud.setIdsolicitud(id);
            Optional<Solicitud> optional = repository.findById(solicitud);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return solicitud;
    }
  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="coincidenciaEnRango(Solicitud solicitud)">
    
    /**
     * coincide en el rango con la orden que se devuelve o un entity si no coincide
     * @param solicitud
     * @return 
     */
    
  public  Optional<Solicitud> coincidenciaEnRango(Solicitud solicitud) {
        Integer idsolicitud=0;
        try {
             
            
            Bson filter_1 =Filters.eq("usuario.0.username",solicitud.getUsuario().get(1).getUsername());

              List<Solicitud> list = repository.filters(filter_1,new Document("idsolicitud", -1));
//              List<Solicitud> list = repository.findBy(new Document("usuario.username", solicitud.getUsuario().getUsername()), new Document("idsolicitud", -1));
            if (!list.isEmpty()) {
                for (Solicitud s : list) {
                    if (JsfUtil.dateBetween(solicitud.getFechahorapartida(), s.getFechahorapartida(), s.getFechahoraregreso())
                            || JsfUtil.dateBetween(solicitud.getFechahoraregreso(), s.getFechahorapartida(), s.getFechahoraregreso())) {

// coincide en el rango de fecha y hora con la solicitud s
                        return Optional.of(s);
                    }
                }
            }
        } catch (Exception e) {
              JsfUtil.errorMessage("coincidenciaEnRango() " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
           
    // </editor-fold>
}
