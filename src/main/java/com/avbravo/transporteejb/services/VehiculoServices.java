/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Tipovehiculo;
import com.avbravo.transporteejb.entity.Vehiculo;
import com.avbravo.transporteejb.repository.VehiculoRepository;
import com.avbravo.transporteejb.repository.ViajeRepository;
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
public class VehiculoServices {
    @Inject
    VehiculoRepository repository;
      @Inject
   ViajeRepository viajesRepository;
    
    List<Vehiculo> vehiculoList = new ArrayList<>();

    public List<Vehiculo> complete(String query) {
        List<Vehiculo> suggestions = new ArrayList<>();
         try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // <editor-fold defaultstate="collapsed" desc="getVehiculoList()">
    public List<Vehiculo> getVehiculoList() {
        try {
            vehiculoList = repository.findAll(new Document("vehiculo", 1));
        } catch (Exception e) {
            JmoordbUtil.errorMessage("getVehiculoList() " + e.getLocalizedMessage());
        }
        return vehiculoList;
    }// </editor-fold>

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }
    
    
        // <editor-fold defaultstate="collapsed" desc="isDeleted(Vehiculo vehiculo)">
  
    public Boolean isDeleted(Vehiculo vehiculo){
        Boolean found=false;
        try {
            Document doc = new Document("vehiculo.idvehiculo",vehiculo.getIdvehiculo());
            Integer count = viajesRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
             JmoordbUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    public Vehiculo findById(Integer id){
           Vehiculo vehiculo = new Vehiculo();
        try {
         
            vehiculo.setIdvehiculo(id);
            Optional<Vehiculo> optional = repository.findById(vehiculo);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JmoordbUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return vehiculo;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Integer cantidadBusesPorTipo(Tipovehiculo tipovehiculo)">
    /**
     * Devuelve la cantidad de vehiculos por tipo
     * @param tipovehiculo
     * @return 
     */
    
   public Integer cantidadVehiculosPorTipo(Tipovehiculo tipovehiculo){
        Integer cantidad=0;
        try {
            
         cantidad=   repository.count(new Document("tipovehiculo.idtipovehiculo",tipovehiculo.getIdtipovehiculo()));
        } catch (Exception e) {
           JmoordbUtil.errorMessage("cantidadVehiculosPorTipo() " + e.getLocalizedMessage());
        }
       
        return cantidad;
    }
    // </editor-fold> 
   
   
   /**
     * Devuelve la lista de pasajeros recomendados para cada viaje
     *
     * @param vehiculoDisponiblesList
     * @return
     */
  public List<Integer> generarPasajerosPorViajes(List<Vehiculo> vehiculoDisponiblesList, Integer pasajeros) {

        List<Integer> pasajerosRecomendadosList = new ArrayList<>();
        try {
            Integer mayorCapacidad = vehiculoDisponiblesList.get(0).getPasajeros();
            Integer pasajerosPendientes = pasajeros;

            if (pasajeros <= mayorCapacidad) {
                //Si es igual o menor que la capacidad del bus con mayor capacidad
                pasajerosRecomendadosList.add(pasajeros);
            } else {
                for (Vehiculo v : vehiculoDisponiblesList) {
                    if (pasajerosPendientes > 0) {
                        if (pasajerosPendientes >= v.getPasajeros()) {
                            pasajerosPendientes -= v.getPasajeros();
                            pasajerosRecomendadosList.add(v.getPasajeros());
                        } else {

                            pasajerosRecomendadosList.add(pasajerosPendientes);
                            pasajerosPendientes = 0;
                        }

                    }

                }
                // revisa los pendientes

                if (pasajerosPendientes <= mayorCapacidad) {
                    pasajerosRecomendadosList.add(pasajerosPendientes);
                } else {
                    Integer residuo = pasajerosPendientes % mayorCapacidad;
                    Integer divisor = pasajerosPendientes / mayorCapacidad;

                    if (residuo > 0) {
                        divisor++;
                    }

                    for (Integer i = 1; i <= divisor; i++) {
                        if (i < divisor) {
                            pasajerosRecomendadosList.add(mayorCapacidad);
                        } else {
                            pasajerosRecomendadosList.add(residuo);
                        }
                    }

                }
            }
        } catch (Exception e) {
            JmoordbUtil.errorMessage("generarPasajerosPorViajes() " + e.getLocalizedMessage());
        }
        return pasajerosRecomendadosList;
    }
    // </editor-fold>
  
  
   // <editor-fold defaultstate="collapsed" desc="vehiculosRecomendados(List<Vehiculo> vehiculoDisponiblesList)">
    /**
     * Devuelve la cantidad de vehiculos recomendados en base a los disponibles
     *
     * @param vehiculoDisponiblesList
     * @return
     */
    public Integer vehiculosRecomendados(List<Vehiculo> vehiculoDisponiblesList, Integer pasajeros) {
        Integer totalVehiculos = 0;
        try {
            Integer mayorCapacidad = vehiculoDisponiblesList.get(0).getPasajeros();
            Integer pasajerosPendientes = pasajeros;
            for (Vehiculo v : vehiculoDisponiblesList) {

                if (pasajerosPendientes > 0) {
                    totalVehiculos++;
                    pasajerosPendientes -= v.getPasajeros();
                    if (pasajerosPendientes < 0) {
                        pasajerosPendientes = 0;
                    }
                }
            }
            if (pasajerosPendientes > 0) {
                if (pasajerosPendientes <= mayorCapacidad) {
                    totalVehiculos++;
                } else {
                    Integer residuo = pasajerosPendientes % mayorCapacidad;
                    Integer divisor = pasajerosPendientes / mayorCapacidad;
                    if (residuo > 0) {
                        divisor++;
                    }
                    totalVehiculos += divisor;
                }

            }
        } catch (Exception e) {
               JmoordbUtil.errorMessage("vehiculosRecomendados " + e.getLocalizedMessage());
        }
        return totalVehiculos;
    }

    // </editor-fold>
    
    
     // <editor-fold defaultstate="collapsed" desc="Integer pasajerosRecomendados(List<Vehiculo> vehiculoDisponiblesList, Integer pasajeros)">
    /**
     * Devuelve la cantidad de pasajeros que quedan pendientes
     *
     * @param vehiculoDisponiblesList
     * @return
     */
    public Integer pasajerosRecomendados(List<Vehiculo> vehiculoDisponiblesList, Integer pasajeros) {
        Integer pasajerosPendientes = pasajeros;
        try {
            Integer mayorCapacidad = vehiculoDisponiblesList.get(0).getPasajeros();
            for (Vehiculo v : vehiculoDisponiblesList) {

                if (pasajerosPendientes > 0) {
                    pasajerosPendientes -= v.getPasajeros();
                    if (pasajerosPendientes < 0) {
                        pasajerosPendientes = 0;
                    }

                }
            }

        } catch (Exception e) {
             JmoordbUtil.errorMessage("pasajerosRecomendados() " + e.getLocalizedMessage());
        }
        return pasajerosPendientes;
    }

    // </editor-fold>
}
