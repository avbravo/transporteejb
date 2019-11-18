/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
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
    ErrorInfoServices errorServices;
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
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }

        return suggestions;
    }

    // <editor-fold defaultstate="collapsed" desc="getViajesList()">
    public List<Viaje> getViajesList() {
        try {
            solicitudList = repository.findAll(new Document("solicitud", 1));
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
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
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
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
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
            if (JmoordbUtil.dateBetween(solicitud.getFechahorapartida(), viajes.getFechahorainicioreserva(), viajes.getFechahorainicioreserva())
                    || JmoordbUtil.dateBetween(solicitud.getFechahoraregreso(), viajes.getFechahorainicioreserva(), viajes.getFechahorainicioreserva())) {
                return true;
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
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
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }
        return disponible;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isValid()">
    public Boolean isValid(Viaje viajes, Boolean...kmCombustibleMayorCero) {
        try {
 Boolean isKmCombustibleMayorCero = true;
            if (kmCombustibleMayorCero.length != 0) {
               isKmCombustibleMayorCero = kmCombustibleMayorCero[0];
            }
            if (JmoordbUtil.fechaMenor(viajes.getFechahorafinreserva(), viajes.getFechahorainicioreserva())) {

                JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }

            if (JmoordbUtil.fechaMenor(viajes.getFechahorafinreserva(), viajes.getFechahorainicioreserva())) {

                JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }
            if (JmoordbUtil.fechaIgual(viajes.getFechahorafinreserva(), viajes.getFechahorainicioreserva())) {

                JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso es igual a la  fecha de partida");
                return false;
            }

            if (JmoordbUtil.horaDeUnaFecha(viajes.getFechahorainicioreserva()) == 0
                    && JmoordbUtil.minutosDeUnaFecha(viajes.getFechahorainicioreserva()) == 0) {
                JmoordbUtil.warningDialog("Advertencia", "La hora de partida no debe ser cero");
                return false;
            }

            if (JmoordbUtil.horaDeUnaFecha(viajes.getFechahorafinreserva()) == 0
                    && JmoordbUtil.minutosDeUnaFecha(viajes.getFechahorafinreserva()) == 0) {
                JmoordbUtil.warningDialog("Advertencia", "La hora de llegada no debe ser cero");
                return false;
            }
if(isKmCombustibleMayorCero){
    if (viajes.getKmestimados() <= 0) {
                JmoordbUtil.warningDialog("Advertencia", "Numero de km menor que cero");
                return false;
            }

            if (viajes.getCostocombustible() <= 0) {
                JmoordbUtil.warningDialog("Advertencia", "Costo de combustible debe ser mayor que cero");
                return false;
            }
}else{
    if (viajes.getKmestimados() < 0) {
                JmoordbUtil.warningDialog("Advertencia", "Numero de km menor que cero");
                return false;
            }

            if (viajes.getCostocombustible() < 0) {
                JmoordbUtil.warningDialog("Advertencia", "Costo de combustible debe ser mayor que cero");
                return false;
            }
}
            
       
            if (viajes.getVehiculo().getActivo().equals("no")) {
                JmoordbUtil.warningDialog("Advertencia", "El vehiculo no esta activo");
                return false;
            }
            if (viajes.getVehiculo().getEnreparacion().equals("si")) {
                JmoordbUtil.warningDialog("Advertencia", "El vehiculo esta en reparacion");
                return false;
            }
            if (viajes.getConductor().getActivo().equals("no")) {
                JmoordbUtil.warningDialog("Advertencia", "El Conductor no esta activo");
                return false;
            }

            return true;
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="isValidDate()">
    public Boolean isValidDate(Viaje viaje) {
        try {

            if (viaje.getFechahorainicioreserva() == null) {
                JmoordbUtil.warningDialog("Advertencia", "Fecha de inicio no se ha seleccionado");
                return false;
            }

            if (viaje.getFechahorafinreserva() == null) {
                JmoordbUtil.warningDialog("Advertencia", "Fecha de fin no se ha seleccionado");
                return false;
            }
            if (JmoordbUtil.fechaMenor(viaje.getFechahorafinreserva(), viaje.getFechahorainicioreserva())) {

                JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }

            if (JmoordbUtil.fechaMenor(viaje.getFechahorafinreserva(), viaje.getFechahorainicioreserva())) {

                JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }
            if (JmoordbUtil.fechaIgual(viaje.getFechahorafinreserva(), viaje.getFechahorainicioreserva())) {

                JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso es igual a la  fecha de partida");
                return false;
            }

            if (JmoordbUtil.horaDeUnaFecha(viaje.getFechahorainicioreserva()) == 0
                    && JmoordbUtil.minutosDeUnaFecha(viaje.getFechahorainicioreserva()) == 0) {
                JmoordbUtil.warningDialog("Advertencia", "La hora de partida no debe ser cero");
                return false;
            }

            if (JmoordbUtil.horaDeUnaFecha(viaje.getFechahorafinreserva()) == 0
                    && JmoordbUtil.minutosDeUnaFecha(viaje.getFechahorafinreserva()) == 0) {
                JmoordbUtil.warningDialog("Advertencia", "La hora de llegada no debe ser cero");
                return false;
            }

            return true;
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
            Bson filter = Filters.and(eq("vehiculo.idvehiculo", viaje.getVehiculo().getIdvehiculo()), eq("activo", "si"));

            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="vehiculoDisponibleExcluyendoMismoViaje(Viajes viajes)">
    /**
     * *
     * busca si el vehiculo tiene un viaje en todas las fechas excluyendo el
     * mismo viaje para permitir la actualizacion
     *
     * @param viajes
     * @return
     */
    public Boolean vehiculoDisponibleExcluyendoMismoViaje(Viaje viaje) {
        try {
            Bson filter = Filters.and(eq("vehiculo.idvehiculo", viaje.getVehiculo().getIdvehiculo()), eq("activo", "si"), ne("idviaje", viaje.getIdviaje()));

            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
            Bson filter = Filters.and(eq("vehiculo.idvehiculo", viaje.getVehiculo().getIdvehiculo()), eq("activo", "si"));
//            return repository.notAvailableBetweenDateHour(eq("vehiculo.idvehiculo", viaje.getVehiculo().getIdvehiculo()),
//                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
//            
            return repository.notAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
            Bson filter = Filters.and(eq("vehiculo.idvehiculo", vehiculo.getIdvehiculo()), eq("activo", "si"));
//           
            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
    public Boolean vehiculoDisponibleExcluyendoMismoViaje(Vehiculo vehiculo, Date fechahorainicioreserva, Date fechahorafinreserva, Integer idviaje) {
        try {
            Bson filter = Filters.and(eq("vehiculo.idvehiculo", vehiculo.getIdvehiculo()), eq("activo", "si"), ne("idviaje", idviaje));

            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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

            Bson filter = Filters.and(eq("vehiculo.idvehiculo", vehiculo.getIdvehiculo()), eq("activo", "si"));
            return repository.notAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
            Bson filter = Filters.and(eq("conductor.idconductor", viaje.getConductor().getIdconductor()), eq("activo", "si"));
            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
            Bson filter = Filters.and(eq("conductor.idconductor", viaje.getConductor().getIdconductor()), eq("activo", "si"), ne("idviaje", viaje.getIdviaje()));
            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
            Bson filter = Filters.and(eq("conductor.idconductor", viaje.getConductor().getIdconductor()), eq("activo", "si"));
//            return repository.notAvailableBetweenDateHour(eq("conductor.idconductor", viaje.getConductor().getIdconductor()),
//                   "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());
//            
            return repository.notAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", viaje.getFechahorainicioreserva(), "fechahorafinreserva", viaje.getFechahorafinreserva());

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
            Bson filter = Filters.and(eq("conductor.idconductor", conductor.getIdconductor()), eq("activo", "si"), ne("idviaje", idviaje));
            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorainicioreserva", fechahorainicioreserva, "fechahorafinreserva", fechahorafinreserva);

        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
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
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
        }
        return list;
    }
    // </editor-fold>

    
     // <editor-fold defaultstate="collapsed" desc="String columnColor(String realizado, String activo)"> 
    public String columnColor(String realizado, String activo) {
        String color = "";
        try {
            switch (realizado.toLowerCase()) {
                case "si":
                    color = "green";
                    break;
                case "no":
                    color = "black";
                    break;
                default:
                    color = "blue";
            }
            if (activo.equals("no")) {
                color = "red";
            }
        } catch (Exception e) {
                 errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }
        return color;
    }
// </editor-fold>
    
          // <editor-fold defaultstate="collapsed" desc="String showDate(Date date)">
    public String showDate(Date date) {
        String h = "";
        try {
            h = JmoordbUtil.dateFormatToString(date, "dd/MM/yyyy");
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
        }
        return h;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="String showHour(Date date)">

    public String showHour(Date date) {
        String h = "";
        try {
            h = JmoordbUtil.hourFromDateToString(date);
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }
        return h;
    }// </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="isValidDates()">
    public Boolean isValidDates(Viaje viaje, Boolean showdialog, Boolean ...compararConFechaActual) {
        try {
              Boolean isFechaActual = true;
            if (compararConFechaActual.length != 0) {
               isFechaActual = compararConFechaActual[0];
            }
            if (viaje.getFechahorainicioreserva()== null) {
                if (showdialog) {
                    JmoordbUtil.warningDialog("Advertencia", "Fecha de partida no seleccionada");
                } else {
                    JmoordbUtil.warningMessage("Fecha de partida no seleccionada");
                }

                return false;
            }

            if (viaje.getFechahorafinreserva() == null) {
                if (showdialog) {
                    JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso no seleccionada");
                } else {
                    JmoordbUtil.warningMessage("Fecha de regreso no seleccionada");
                }

                return false;
            }

            if (JmoordbUtil.fechaMenor(viaje.getFechahorafinreserva(), viaje.getFechahorainicioreserva())) {
                if (showdialog) {
                    JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                } else {
                    JmoordbUtil.warningMessage("Fecha de regreso menor que la fecha de partida");
                }

                return false;
            }

            if (JmoordbUtil.fechaMenor(viaje.getFechahorafinreserva(), viaje.getFechahorainicioreserva())) {
                if (showdialog) {
                    JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                } else {
                    JmoordbUtil.warningMessage("Fecha de regreso menor que la fecha de partida");
                }

                return false;
            }
            if (JmoordbUtil.fechaIgual(viaje.getFechahorafinreserva(), viaje.getFechahorainicioreserva())) {
                if (showdialog) {
                    JmoordbUtil.warningDialog("Advertencia", "Fecha de regreso es igual que la fecha de partida");
                } else {
                    JmoordbUtil.warningMessage("Fecha de regreso es igual que la fecha de partida");
                }

                return false;
            }

            if (JmoordbUtil.horaDeUnaFecha(viaje.getFechahorainicioreserva()) == 0
                    && JmoordbUtil.minutosDeUnaFecha(viaje.getFechahorainicioreserva()) == 0) {
                if (showdialog) {
                    JmoordbUtil.warningDialog("Advertencia", "La hora de partida no debe ser cero");
                } else {
                    JmoordbUtil.warningMessage("La hora de partida no debe ser cero");
                }

                return false;
            }

            if (JmoordbUtil.horaDeUnaFecha(viaje.getFechahorafinreserva()) == 0
                    && JmoordbUtil.minutosDeUnaFecha(viaje.getFechahorafinreserva()) == 0) {
                if (showdialog) {
                    JmoordbUtil.warningDialog("Advertencia", "La hora de llegada no debe ser cero");
                } else {
                    JmoordbUtil.warningMessage("La hora de llegada no debe ser cero");
                }

                return false;
            }

            if(isFechaActual){
                   if (JmoordbUtil.fechaMenor(viaje.getFechahorainicioreserva(), JmoordbUtil.fechaActual())) {
                if (showdialog) {
                    JmoordbUtil.warningDialog("Advertencia", "Fecha de partida es menor que la fecha actual");
                } else {
                    JmoordbUtil.warningMessage("Fecha de partida es menor que la fecha actual");
                }

                return false;
            } 
            }
        
            return true;
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
        }
        return false;
    }
    // </editor-fold>

    
      // <editor-fold defaultstate="collapsed" desc="isVehiculoActivoDisponible(Vehiculo vehiculo)">
    public Boolean isVehiculoActivoDisponible(Vehiculo vehiculo, Solicitud solicitud, Viaje viaje) {
        Boolean valid = false;
        try {
            if(!vehiculo.getTipovehiculo().getIdtipovehiculo().equals(solicitud.getTipovehiculo().get(0).getIdtipovehiculo())){
                return valid;
            }
            if (vehiculo.getActivo().equals("no") && vehiculo.getEnreparacion().equals("si") ) {

            } else {
                if (vehiculoDisponible(vehiculo, viaje.getFechahorainicioreserva(), viaje.getFechahorafinreserva())) {
                    valid = true;
                }
            }

        } catch (Exception e) {
                      errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
            //errorServices.errorDialog(nameOfClass(), nameOfMethod(), nameOfMethod(), e.getLocalizedMessage());
        }
        return valid;
    }

    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="isVehiculoActivoDisponibleExcluyendoMismoViaje(Vehiculo vehiculo)">
    public Boolean isVehiculoActivoDisponibleExcluyendoMismoViaje(Vehiculo vehiculo, Solicitud solicitud, Viaje viaje) {
        Boolean valid = false;
        try {
             if(!vehiculo.getTipovehiculo().getIdtipovehiculo().equals(solicitud.getTipovehiculo().get(0).getIdtipovehiculo())){
                return valid;
            }

            if (vehiculo.getActivo().equals("no") && vehiculo.getEnreparacion().equals("si")) {

            } else {
                if (vehiculoDisponibleExcluyendoMismoViaje(vehiculo, viaje.getFechahorainicioreserva(), viaje.getFechahorafinreserva(), viaje.getIdviaje())) {
                    valid = true;
                }
            }

        } catch (Exception e) {
                         errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);
        }
        return valid;
    }

    // </editor-fold>
    
   
}
