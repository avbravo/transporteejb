/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordbutils.DateUtil;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Conductor;
import com.avbravo.transporteejb.entity.Solicitud;
import com.avbravo.transporteejb.entity.Vehiculo;
import com.avbravo.transporteejb.entity.Viaje;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.ViajeRepository;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public class ViajeServices {

    @Inject
    ViajeRepository repository;
    List<Viaje> solicitudList = new ArrayList<>();
    @Inject
    SolicitudRepository solicitudRepository;

    public List<Viaje> complete(String query) {
        List<Viaje> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // <editor-fold defaultstate="collapsed" desc="getViajesList()">
    public List<Viaje> getViajesList() {
        try {
            solicitudList = repository.findAll(new Document("solicitud", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getViajesList() " + e.getLocalizedMessage());
        }
        return solicitudList;
    }// </editor-fold>

    public void setViajesList(List<Viaje> solicitudList) {
        this.solicitudList = solicitudList;
    }

    // <editor-fold defaultstate="collapsed" desc="isDeleted(Viajes viajes)">
    public Boolean isDeleted(Viaje viaje) {
        Boolean found = false;
        try {
            Document doc = new Document("viaje.idviaje", viaje.getIdviaje());
            Integer count = solicitudRepository.count(doc);
            if (count > 0) {
                return false;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(String id)">
    public Viaje findById(Integer id) {
        Viaje viajes = new Viaje();
        try {

            viajes.setIdviaje(id);
            Optional<Viaje> optional = repository.findById(viajes);
            if (optional.isPresent()) {
                return optional.get();
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }

        return viajes;
    }
    // </editor-fold

    // <editor-fold defaultstate="collapsed" desc="viajesVariosDias(Vehiculo v) ">
    /**
     * devuelve la lista de viajes entre varios dias considerar que el busca
     * entre la fecha de partida y la fecha de regreso por lo que muchos viajes
     * puede que tengan fecha de partida y no de regreso en viajess y en otros
     * casos no esten en la de partida y si en la de regreso
     *
     * @return
     */
    public List<Viaje> viajesVariosDias(Vehiculo v, Solicitud solicitud) {
        List<Viaje> viajesList = new ArrayList<>();
        try {
            viajesList = repository.filterDayWithoutHour("vehiculo.idvehiculo", v.getIdvehiculo(), "fechahorainicioreserva", solicitud.getFechahorapartida());
            List<Viaje> viajesStart = repository.filterDayWithoutHour("vehiculo.idvehiculo", v.getIdvehiculo(), "fechahorainicioreserva", solicitud.getFechahorapartida());
            List<Viaje> viajesEnd = repository.filterDayWithoutHour("vehiculo.idvehiculo", v.getIdvehiculo(), "fechahorafinreserva", solicitud.getFechahoraregreso());
            viajesList = new ArrayList<>();
            if (viajesStart.isEmpty() && viajesEnd.isEmpty()) {
                // NO HAY VIAJES EN ESAS FECHAS

            } else {
                if (!viajesStart.isEmpty() && !viajesEnd.isEmpty()) {
                    viajesList = viajesStart;
                    for (Viaje vjs : viajesEnd) {
                        Boolean foundv = false;
                        for (Viaje vje : viajesList) {
                            if (vjs.getIdviaje() == vje.getIdviaje()) {
                                foundv = true;
                                break;
                            }
                        }
                        if (!foundv) {
                            viajesList.add(vjs);
                        }
                    }
                } else {
                    if (viajesStart.isEmpty() && !viajesEnd.isEmpty()) {
                        viajesList = viajesEnd;
                    } else {
                        if (!viajesStart.isEmpty() && viajesEnd.isEmpty()) {
                            viajesList = viajesStart;
                        }
                    }
                }
                Collections.sort(viajesList,
                        (Viaje a, Viaje b) -> a.getIdviaje().compareTo(b.getIdviaje()));
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("viajesVariosDias() " + e.getLocalizedMessage());
        }
        return viajesList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="esOcupadoEseDiaHora(Solicitud solicitud, Viajes viajes) ">
    /**
     * verifica si esa solicitud esta ocupada
     *
     * @param solicitud
     * @param viajes
     * @return
     */
    public Boolean esOcupadoEseDiaHora(Solicitud solicitud, Viaje viajes) {
        try {
            if (DateUtil.dateBetween(solicitud.getFechahorapartida(), viajes.getFechahorainicioreserva(), viajes.getFechahorainicioreserva())
                    || DateUtil.dateBetween(solicitud.getFechahoraregreso(), viajes.getFechahorainicioreserva(), viajes.getFechahorainicioreserva())) {
                return true;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("esOcupadoEseDiaHora() " + e.getLocalizedMessage());
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="tieneDisponibilidadViaje(List<Viajes> viajesList)">
    /**
     * recorre el list de viajes y verifica si esta ocupado
     *
     * @param viajesList
     * @return
     */
    public Boolean tieneDisponibilidadViaje(List<Viaje> viajesList, Solicitud solicitud) {
        Boolean disponible = true;
        try {

            for (Viaje vj : viajesList) {
                if (esOcupadoEseDiaHora(solicitud, vj)) {
                    disponible = false;
                    break;
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("tieneDisponibilidadViaje() " + e.getLocalizedMessage());
        }
        return disponible;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isValid()">
    public Boolean isValid(Viaje viajes) {
        try {

            if (DateUtil.fechaMenor(viajes.getFechahorafinreserva(), viajes.getFechahorainicioreserva())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }

            if (DateUtil.fechaMenor(viajes.getFechahorafinreserva(), viajes.getFechahorainicioreserva())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }
            if (DateUtil.fechaIgual(viajes.getFechahorafinreserva(), viajes.getFechahorainicioreserva())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso es igual a la  fecha de partida");
                return false;
            }

            if (DateUtil.horaDeUnaFecha(viajes.getFechahorainicioreserva()) == 0
                    && DateUtil.minutosDeUnaFecha(viajes.getFechahorainicioreserva()) == 0) {
                JsfUtil.warningDialog("Advertencia", "La hora de partida no debe ser cero");
                return false;
            }

            if (DateUtil.horaDeUnaFecha(viajes.getFechahorafinreserva()) == 0
                    && DateUtil.minutosDeUnaFecha(viajes.getFechahorafinreserva()) == 0) {
                JsfUtil.warningDialog("Advertencia", "La hora de llegada no debe ser cero");
                return false;
            }
            
            

            if (viajes.getKmestimados() <= 0) {
                JsfUtil.warningDialog("Advertencia", "Numero de km menor que cero");
                return false;
            }

            if (viajes.getCostocombustible() <= 0) {
                JsfUtil.warningDialog("Advertencia", "Costo de combustible debe ser mayor que cero");
                return false;
            }
            if (viajes.getVehiculo().getActivo().equals("no")) {
                JsfUtil.warningDialog("Advertencia", "El vehiculo no esta activo");
                return false;
            }
            if (viajes.getVehiculo().getEnreparacion().equals("si")) {
                JsfUtil.warningDialog("Advertencia", "El vehiculo esta en reparacion");
                return false;
            }
            if (viajes.getConductor().getActivo().equals("no")) {
                JsfUtil.warningDialog("Advertencia", "El Conductor no esta activo");
                return false;
            }

            return true;
        } catch (Exception e) {
            JsfUtil.errorDialog("isValid() ", e.getLocalizedMessage().toString());
        }
        return false;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="isValidDate()">
    public Boolean isValidDate(Viaje viaje) {
        try {

            if (DateUtil.fechaMenor(viaje.getFechahorafinreserva(), viaje.getFechahorainicioreserva())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }

            if (DateUtil.fechaMenor(viaje.getFechahorafinreserva(), viaje.getFechahorainicioreserva())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }
            if (DateUtil.fechaIgual(viaje.getFechahorafinreserva(), viaje.getFechahorainicioreserva())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso es igual a la  fecha de partida");
                return false;
            }

            if (DateUtil.horaDeUnaFecha(viaje.getFechahorainicioreserva()) == 0
                    && DateUtil.minutosDeUnaFecha(viaje.getFechahorainicioreserva()) == 0) {
                JsfUtil.warningDialog("Advertencia", "La hora de partida no debe ser cero");
                return false;
            }

            if (DateUtil.horaDeUnaFecha(viaje.getFechahorafinreserva()) == 0
                    && DateUtil.minutosDeUnaFecha(viaje.getFechahorafinreserva()) == 0) {
                JsfUtil.warningDialog("Advertencia", "La hora de llegada no debe ser cero");
                return false;
            }
            
            return true;
        } catch (Exception e) {
            JsfUtil.errorDialog("isValid() ", e.getLocalizedMessage().toString());
        }
        return false;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="isValidDateEdit(Viaje viajes)">
    
    
    
    
    

    // <editor-fold defaultstate="collapsed" desc="vehiculoDisponible(Viajes viajes)">
    /**
     * *
     * busca si el vehiculo tiene un viaje en esas fechas
     *
     * @param viajes
     * @return
     */
    public Boolean vehiculoDisponible(Viaje viaje) {
        try {
  Bson filter= Filters.and(eq("vehiculo.idvehiculo",viaje.getVehiculo().getIdvehiculo()),eq("activo","si"));
  
           return repository.isAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
            
        } catch (Exception e) {
            JsfUtil.errorDialog("vehiculoDisponible() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="vehiculoDisponibleExcluyendoMismoViaje(Viajes viajes)">
    /**
     * *
     * busca si el vehiculo tiene un viaje en todas las fechas excluyendo el mismo viaje
     * para permitir la actualizacion
     *
     * @param viajes
     * @return
     */
    public Boolean vehiculoDisponibleExcluyendoMismoViaje(Viaje viaje) {
        try {
  Bson filter= Filters.and(eq("vehiculo.idvehiculo",viaje.getVehiculo().getIdvehiculo()),eq("activo","si"),ne("idviaje",viaje.getIdviaje()));
  
           return repository.isAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
            
        } catch (Exception e) {
            JsfUtil.errorDialog("vehiculoDisponible() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="List<Viajes> viajesVehiculoChoques(Viajes viajes)">
    /**
     * *
     * devuelve la lista de viajes en que choca el vehiculo
     *
     * @param viajes
     * @return
     */
    public List<Viaje> viajesVehiculoChoques(Viaje viaje) {
        List<Viaje> list = new ArrayList<>();
        try {
              Bson filter= Filters.and(eq("vehiculo.idvehiculo",viaje.getVehiculo().getIdvehiculo()),eq("activo","si"));
//            return repository.notAvailableBetweenDateHour(eq("vehiculo.idvehiculo", viaje.getVehiculo().getIdvehiculo()),
//                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
//            
            return repository.notAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
            

        } catch (Exception e) {
            JsfUtil.errorDialog("viajesVehiculoChoques() ", e.getLocalizedMessage().toString());
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="vehiculoDisponible(Vehiculo vehiculo, Date fechahorainicio, Date fechahorafin)">
    /**
     * *
     * busca si el vehiculo tiene un viaje en esas fechas
     *
     * @param viajes
     * @return
     */
    public Boolean vehiculoDisponible(Vehiculo vehiculo, Date fechahorainicioreserva, Date fechahorafinreserva) {
        try {
           Bson filter= Filters.and(eq("vehiculo.idvehiculo",vehiculo.getIdvehiculo()),eq("activo","si"));
//           
              return repository.isAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);
            
        } catch (Exception e) {
            JsfUtil.errorDialog("vehiculoDisponible() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="vehiculoDisponibleExcluyendoMismoViaje(Vehiculo vehiculo, Date fechahorainicioreserva, Date fechahorafinreserva,Integer idviaje)">
    /**
     * *
     * busca si el vehiculo tiene un viaje en esas fechas
     *
     * @param viajes
     * @return
     */
    public Boolean vehiculoDisponibleExcluyendoMismoViaje(Vehiculo vehiculo, Date fechahorainicioreserva, Date fechahorafinreserva,Integer idviaje) {
        try {
           Bson filter= Filters.and(eq("vehiculo.idvehiculo",vehiculo.getIdvehiculo()),eq("activo","si"),ne("idviaje",idviaje));
     
              return repository.isAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);
            
        } catch (Exception e) {
            JsfUtil.errorDialog("vehiculoDisponible() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Viajes> viajesVehiculoChoques(Vehiculo vehiculo, Date fechahorainicio, Date fechahorafin)">
    /**
     * *
     * Devuelve la lista de viajes en que choca ese vehiculo
     *
     * @param viajes
     * @return
     */
    public List<Viaje> viajesVehiculoChoques(Vehiculo vehiculo, Date fechahorainicioreserva, Date fechahorafinreserva) {
        List<Viaje> list = new ArrayList<>();
        try {
            
             Bson filter= Filters.and(eq("vehiculo.idvehiculo",vehiculo.getIdvehiculo()),eq("activo","si"));
             return repository.notAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);
            

        } catch (Exception e) {
            JsfUtil.errorDialog("viajesVehiculoChoques() ", e.getLocalizedMessage().toString());
        }
        return list;
    }
    // </editor-fold>

    
     // <editor-fold defaultstate="collapsed" desc="conductorDisponible(Viajes viajes)">
    /**
     * *
     * busca si el conductor tiene un viaje en esas fechas
     *
     * @param viajes
     * @return
     */
    public Boolean conductorDisponible(Viaje viaje) {
        try {
            //Conductors en viajes
  Bson filter= Filters.and(eq("conductor.idconductor",viaje.getConductor().getIdconductor()),eq("activo","si"));
           return repository.isAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
            
        } catch (Exception e) {
            JsfUtil.errorDialog("conductorDisponible() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
     // <editor-fold defaultstate="collapsed" desc="conductorDisponibleExcluyendoMismoViaje(Viajes viajes)">
    /**
     * *
     * busca si el conductor tiene un viaje en esas fechas
     *
     * @param viajes
     * @return
     */
    public Boolean conductorDisponibleExcluyendoMismoViaje(Viaje viaje) {
        try {
            //Conductors en viajes
  Bson filter= Filters.and(eq("conductor.idconductor",viaje.getConductor().getIdconductor()),eq("activo","si"),ne("idviaje",viaje.getIdviaje()));
           return repository.isAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
            
        } catch (Exception e) {
            JsfUtil.errorDialog("conductorDisponible() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="List<Viajes> viajesConductorChoques(Viajes viajes)">
    /**
     * *
     * devuelve la lista de viajes en que choca el conductor
     *
     * @param viajes
     * @return
     */
    public List<Viaje> viajesConductorChoques(Viaje viaje) {
        List<Viaje> list = new ArrayList<>();
        try {
             Bson filter= Filters.and(eq("conductor.idconductor",viaje.getConductor().getIdconductor()),eq("activo","si"));
//            return repository.notAvailableBetweenDateHour(eq("conductor.idconductor", viaje.getConductor().getIdconductor()),
//                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
//            
            return repository.notAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
            

        } catch (Exception e) {
            JsfUtil.errorDialog("viajesConductorChoques() ", e.getLocalizedMessage().toString());
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="conductorDisponible(Conductor conductor, Date fechahorainicio, Date fechahorafin)">
    /**
     * *
     * busca si el conductor tiene un viaje en esas fechas
     *
     * @param viajes
     * @return
     */
    public Boolean conductorDisponible(Conductor conductor, Date fechahorainicioreserva, Date fechahorafinreserva) {
        try {
           
              return repository.isAvailableBetweenDateHour(eq("conductor.idconductor", conductor.getIdconductor()),
                   "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);
            
        } catch (Exception e) {
            JsfUtil.errorDialog("conductorDisponible() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="conductorDisponibleExcluyendoMismoViaje(Conductor conductor, Date fechahorainicio, Date fechahorafin)">
    /**
     * *
     * busca si el conductor tiene un viaje en esas fechas
     *
     * @param viajes
     * @return
     */
    public Boolean conductorDisponibleExcluyendoMismoViaje(Conductor conductor, Date fechahorainicioreserva, Date fechahorafinreserva, Integer idviaje) {
        try {
           Bson filter= Filters.and(eq("conductor.idconductor",conductor.getIdconductor()),eq("activo","si"),ne("idviaje",idviaje));
              return repository.isAvailableBetweenDateHour(filter,
                   "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);
            
        } catch (Exception e) {
            JsfUtil.errorDialog("conductorDisponible() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Viajes> viajesConductorChoques(Conductor conductor, Date fechahorainicio, Date fechahorafin)">
    /**
     * *
     * Devuelve la lista de viajes en que choca ese conductor
     *
     * @param viajes
     * @return
     */
    public List<Viaje> viajesConductorChoques(Conductor conductor, Date fechahorainicioreserva, Date fechahorafinreserva) {
        List<Viaje> list = new ArrayList<>();
        try {
             return repository.notAvailableBetweenDateHour(eq("conductor.idconductor", conductor.getIdconductor()),
                   "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);
            

        } catch (Exception e) {
            JsfUtil.errorDialog("viajesConductorChoques() ", e.getLocalizedMessage().toString());
        }
        return list;
    }
    // </editor-fold>
  
}
