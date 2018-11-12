/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Solicitud;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.VehiculoRepository;
import com.avbravo.transporteejb.repository.ViajesRepository;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.primefaces.event.SelectEvent;

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
    @Inject
    VehiculoRepository vehiculoRepository;

    List<Solicitud> solicitudList = new ArrayList<>();

    public List<Solicitud> complete(String query) {
        List<Solicitud> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // <editor-fold defaultstate="collapsed" desc="getSolicitudList()">
    public List<Solicitud> getSolicitudList() {
        try {
            solicitudList = repository.findAll(new Document("solicitud", 1));
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
    public Solicitud findById(Integer id) {
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

    // <editor-fold defaultstate="collapsed" desc="coincidenciaSolicitadoEnRango(Solicitud solicitud)">
    /**
     * coincide en el rango con la orden que se devuelve o un entity si no
     * coincide
     *
     * @param solicitud
     * @return
     */
    public Optional<Solicitud> coincidenciaSolicitadoEnRango(Solicitud solicitud) {
        Integer idsolicitud = 0;
        try {

            Bson filter_1 = Filters.eq("usuario.0.username", solicitud.getUsuario().get(0).getUsername());

            List<Solicitud> list = repository.filters(filter_1, new Document("idsolicitud", -1));
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
    // <editor-fold defaultstate="collapsed" desc="coincidenciaSolicitadoEnRango(Solicitud solicitud)">
    /**
     * coincide en el rango con la orden que se devuelve o un entity si no
     * coincide
     *
     * @param solicitud
     * @return
     */
    public Optional<Solicitud> coincidenciaResponsableEnRango(Solicitud solicitud) {
        Integer idsolicitud = 0;
        try {

            Bson filter_1 = Filters.eq("usuario.1.username", solicitud.getUsuario().get(1).getUsername());

            List<Solicitud> list = repository.filters(filter_1, new Document("idsolicitud", -1));
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
    // <editor-fold defaultstate="collapsed" desc="solicitadoPor(Solicitud solicitud)">
    public Usuario solicitadoPor(Solicitud solicitud) {
        Usuario usuario = new Usuario();
        try {
            usuario = solicitud.getUsuario().get(0);
        } catch (Exception e) {
            JsfUtil.errorMessage("solicitadoPor() " + e.getLocalizedMessage());
        }
        return usuario;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="responsable(Solicitud solicitud)">
    public Usuario responsable(Solicitud solicitud) {
        Usuario usuario = new Usuario();
        try {
            usuario = solicitud.getUsuario().get(1);
        } catch (Exception e) {
            JsfUtil.errorMessage("responsable() " + e.getLocalizedMessage());
        }
        return usuario;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isValid()">
    public Boolean isValid(Solicitud solicitud) {
        try {

            if (JsfUtil.fechaMenor(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }

            //guarda el contenido actualizado
            if (JsfUtil.fechaMenor(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }

            if (JsfUtil.getHoraDeUnaFecha(solicitud.getFechahorapartida()) == 0
                    && JsfUtil.getMinutosDeUnaFecha(solicitud.getFechahorapartida()) == 0) {
                JsfUtil.warningDialog("Advertencia", "La hora de partida no debe ser cero");
                return false;
            }

            if (JsfUtil.getHoraDeUnaFecha(solicitud.getFechahoraregreso()) == 0
                    && JsfUtil.getMinutosDeUnaFecha(solicitud.getFechahoraregreso()) == 0) {
                JsfUtil.warningDialog("Advertencia", "La hora de llegada no debe ser cero");
                return false;
            }

            if (solicitud.getPasajeros() <= 0) {
                JsfUtil.warningDialog("Advertencia", "Numero de pasajeros menor que cero");
                return false;
            }

            if (solicitud.getNumerodevehiculos() <= 0) {
                JsfUtil.warningDialog("Advertencia", "Numero de vehiculos debe ser mayor que cero");
                return false;
            }
            Integer totalvehiculos = vehiculoRepository.count(new Document("activo", "si"));

            if (solicitud.getNumerodevehiculos() >= totalvehiculos) {
                JsfUtil.warningDialog("Advertencia", "Numero de vehiculos es mayor que la cantidad de vehiculos disponibles");
                return false;
            }

            return true;
        } catch (Exception e) {
            JsfUtil.errorDialog("isValid() ", e.getLocalizedMessage().toString());
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="copiarDesde(Solicitud fuente, Solicitud destino)">

   public Solicitud copiarDesde(Solicitud solicitudCopiar, Solicitud solicitud) {
        try {
            solicitud.setFacultad(solicitudCopiar.getFacultad());
            solicitud.setCarrera(solicitudCopiar.getCarrera());
            solicitud.setLugares(solicitudCopiar.getLugares());
            solicitud.setLugarllegada(solicitudCopiar.getLugarllegada());
            solicitud.setLugarpartida(solicitudCopiar.getLugarpartida());
            solicitud.setMision(solicitudCopiar.getMision());
            solicitud.setNumerodevehiculos(solicitudCopiar.getNumerodevehiculos());
            solicitud.setNumerogrupo(solicitudCopiar.getNumerogrupo());
            solicitud.setObjetivo(solicitudCopiar.getObjetivo());
            solicitud.setObservaciones(solicitudCopiar.getObservaciones());
            solicitud.setSemestre(solicitudCopiar.getSemestre());
            solicitud.setPeriodoacademico(solicitudCopiar.getPeriodoacademico());
            solicitud.setPasajeros(solicitudCopiar.getPasajeros());
            solicitud.setTipogira(solicitudCopiar.getTipogira());
            solicitud.setTipovehiculo(solicitudCopiar.getTipovehiculo());
            solicitud.setTiposolicitud(solicitudCopiar.getTiposolicitud());
            solicitud.setRecursossolicitados(solicitudCopiar.getRecursossolicitados());

        } catch (Exception ex) {
            JsfUtil.errorDialog("copiarDesde() ", ex.getLocalizedMessage().toString());
        }
        return solicitud;
    }
    // </editor-fold>
}
