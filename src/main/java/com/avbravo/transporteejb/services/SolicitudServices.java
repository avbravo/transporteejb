/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordbutils.DateUtil;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Solicitud;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.entity.Vehiculo;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.VehiculoRepository;
import com.avbravo.transporteejb.repository.ViajeRepository;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import java.util.ArrayList;
import java.util.Comparator;
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
public class SolicitudServices {

    @Inject
    SolicitudRepository repository;
    @Inject
    ViajeRepository viajesRepository;
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
                    if (DateUtil.dateBetween(solicitud.getFechahorapartida(), s.getFechahorapartida(), s.getFechahoraregreso())
                            || DateUtil.dateBetween(solicitud.getFechahoraregreso(), s.getFechahorapartida(), s.getFechahoraregreso())) {

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
                    if (DateUtil.dateBetween(solicitud.getFechahorapartida(), s.getFechahorapartida(), s.getFechahoraregreso())
                            || DateUtil.dateBetween(solicitud.getFechahoraregreso(), s.getFechahorapartida(), s.getFechahoraregreso())) {

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
            if (solicitud.getFechahorapartida() == null) {
                JsfUtil.warningDialog("Advertencia", "Fecha de partida no seleccionada");
                return false;
            }

            if (solicitud.getFechahoraregreso() == null) {
                JsfUtil.warningDialog("Advertencia", "Fecha de regreso no seleccionada");
                return false;
            }
            if (solicitud.getRangoagenda() == null || solicitud.getRangoagenda().isEmpty()) {
                JsfUtil.warningDialog("Advertencia", "Por favor indique el rango para la agenad");
                return false;
            }

            if (DateUtil.fechaMenor(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }

            if (DateUtil.fechaMenor(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                return false;
            }
            if (DateUtil.fechaIgual(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {

                JsfUtil.warningDialog("Advertencia", "Fecha de regreso es igual que la fecha de partida");
                return false;
            }

            if (DateUtil.horaDeUnaFecha(solicitud.getFechahorapartida()) == 0
                    && DateUtil.minutosDeUnaFecha(solicitud.getFechahorapartida()) == 0) {
                JsfUtil.warningDialog("Advertencia", "La hora de partida no debe ser cero");
                return false;
            }

            if (DateUtil.horaDeUnaFecha(solicitud.getFechahoraregreso()) == 0
                    && DateUtil.minutosDeUnaFecha(solicitud.getFechahoraregreso()) == 0) {
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
            Integer totalvehiculos = vehiculoRepository.count(new Document("activo", "si").append("enreparacion", "no"));

            if (solicitud.getNumerodevehiculos() >= totalvehiculos) {
                JsfUtil.warningDialog("Advertencia", "Numero de vehiculos es mayor que la cantidad de vehiculos disponibles");
                return false;
            }

            if (DateUtil.fechaMenor(solicitud.getFechahorapartida(), DateUtil.fechaActual())) {
                JsfUtil.warningDialog("Advertencia", "Fecha de partida es menor que la fecha actual");
                return false;
            }
            return true;
        } catch (Exception e) {
            JsfUtil.errorDialog("isValid() ", e.getLocalizedMessage().toString());
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isValidDates()">
    public Boolean isValidDates(Solicitud solicitud, Boolean showdialog) {
        try {
            if (solicitud.getFechahorapartida() == null) {
                if (showdialog) {
                    JsfUtil.warningDialog("Advertencia", "Fecha de partida no seleccionada");
                } else {
                    JsfUtil.warningMessage("Fecha de partida no seleccionada");
                }

                return false;
            }

            if (solicitud.getFechahoraregreso() == null) {
                if (showdialog) {
                    JsfUtil.warningDialog("Advertencia", "Fecha de regreso no seleccionada");
                } else {
                    JsfUtil.warningMessage("Fecha de regreso no seleccionada");
                }

                return false;
            }

            if (DateUtil.fechaMenor(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {
                if (showdialog) {
                    JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                } else {
                    JsfUtil.warningMessage("Fecha de regreso menor que la fecha de partida");
                }

                return false;
            }

            if (DateUtil.fechaMenor(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {
                if (showdialog) {
                    JsfUtil.warningDialog("Advertencia", "Fecha de regreso menor que la fecha de partida");
                } else {
                    JsfUtil.warningMessage("Fecha de regreso menor que la fecha de partida");
                }

                return false;
            }
            if (DateUtil.fechaIgual(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {
                if (showdialog) {
                    JsfUtil.warningDialog("Advertencia", "Fecha de regreso es igual que la fecha de partida");
                } else {
                    JsfUtil.warningMessage("Fecha de regreso es igual que la fecha de partida");
                }

                return false;
            }

            if (DateUtil.horaDeUnaFecha(solicitud.getFechahorapartida()) == 0
                    && DateUtil.minutosDeUnaFecha(solicitud.getFechahorapartida()) == 0) {
                if (showdialog) {
                    JsfUtil.warningDialog("Advertencia", "La hora de partida no debe ser cero");
                } else {
                    JsfUtil.warningMessage("La hora de partida no debe ser cero");
                }

                return false;
            }

            if (DateUtil.horaDeUnaFecha(solicitud.getFechahoraregreso()) == 0
                    && DateUtil.minutosDeUnaFecha(solicitud.getFechahoraregreso()) == 0) {
                if (showdialog) {
                    JsfUtil.warningDialog("Advertencia", "La hora de llegada no debe ser cero");
                } else {
                    JsfUtil.warningMessage("La hora de llegada no debe ser cero");
                }

                return false;
            }

            if (DateUtil.fechaMenor(solicitud.getFechahorapartida(), DateUtil.fechaActual())) {
                if (showdialog) {
                    JsfUtil.warningDialog("Advertencia", "Fecha de partida es menor que la fecha actual");
                } else {
                    JsfUtil.warningMessage("Fecha de partida es menor que la fecha actual");
                }

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
            solicitud.setFechahorapartida(solicitudCopiar.getFechahorapartida());
            solicitud.setFechahoraregreso(solicitudCopiar.getFechahoraregreso());

        } catch (Exception ex) {
            JsfUtil.errorDialog("copiarDesde() ", ex.getLocalizedMessage().toString());
        }
        return solicitud;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean esMismoDiaSolicitud()">
    /**
     * si el dia de partida es el mismo que el de regreso
     *
     * @return
     */
    public Boolean esMismoDiaSolicitud(Solicitud solicitud) {
        try {

            Integer dia = DateUtil.diaDeUnaFecha(solicitud.getFechahorapartida());
            Integer mes = DateUtil.diaDeUnaFecha(solicitud.getFechahorapartida());
            Integer anio = DateUtil.diaDeUnaFecha(solicitud.getFechahorapartida());
            Integer diaf = DateUtil.diaDeUnaFecha(solicitud.getFechahoraregreso());
            Integer mesf = DateUtil.diaDeUnaFecha(solicitud.getFechahoraregreso());
            Integer aniof = DateUtil.diaDeUnaFecha(solicitud.getFechahoraregreso());
// ES EN LA MISMA FECHA

            if (anio == aniof && mes == mesf && dia == diaf) {
                return true;
            }
        } catch (Exception e) {
            JsfUtil.errorDialog("esMismoDiaSolicitud() ", e.getLocalizedMessage().toString());
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean solicitudDisponible(Solicitud solicitud, Date fechahorapartida, Date fechahoraregreso)">
    /**
     * *
     * busca si el vehiculo tiene un viaje en esas fechas
     *
     * @param viajes
     * @return
     */
    public Boolean solicitudDisponible(Solicitud solicitud, Date fechahorapartida, Date fechahoraregreso) {
        try {

            Bson filter = Filters.and(eq("usuario.1.username", solicitud.getUsuario().get(1).getUsername()), eq("activo", "si"));
//           
            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorapartida", fechahorapartida, "fechahoraregreso", fechahoraregreso);

        } catch (Exception e) {
            JsfUtil.errorDialog("solicitudDisponible() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="completeSolicitudParaCopiar(String query, String tipoSolicitud)">
    public List<Solicitud> completeSolicitudParaCopiar(String query, String tipoSolicitud) {
        List<Solicitud> suggestions = new ArrayList<>();
        try {
            Usuario jmoordb_user = (Usuario) JmoordbContext.get("jmoordb_user");
            List<Solicitud> list = new ArrayList<>();
            list = repository.complete(query);
            if (!list.isEmpty()) {
                for (Solicitud s : list) {
                    if (s.getTiposolicitud().getIdtiposolicitud().equals(tipoSolicitud)
                            && (s.getUsuario().get(0).getUsername().equals(jmoordb_user.getUsername())
                            || s.getUsuario().get(1).getUsername().equals(jmoordb_user.getUsername()))) {
                        suggestions.add(s);
                    }
                }
            }
            if (!suggestions.isEmpty()) {

                suggestions.sort(Comparator.comparing(Solicitud::getIdsolicitud)
                        .reversed()
                        .thenComparing(Comparator.comparing(Solicitud::getIdsolicitud)
                                .reversed())
                );
            }

        } catch (Exception e) {
            JsfUtil.errorDialog("completeSolicitudParaCopiar() ", e.getLocalizedMessage().toString());
        }

        return suggestions;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="completeByEstatus(String query, String estatus)">
    /**
     * Devuelve la lista en base al estatus
     *
     * @param query
     * @param estatus
     * @return
     */
    public List<Solicitud> completeByEstatus(String query, String estatus) {
        List<Solicitud> suggestions = new ArrayList<>();
        try {
            List<Solicitud> list = new ArrayList<>();
            list = repository.complete(query);
            if (!list.isEmpty()) {
                for (Solicitud s : list) {
                    if (s.getEstatus().getIdestatus().equals(estatus)) {
                        suggestions.add(s);
                    }
                }
            }
            if (!suggestions.isEmpty()) {

                suggestions.sort(Comparator.comparing(Solicitud::getIdsolicitud)
                        .reversed()
                        .thenComparing(Comparator.comparing(Solicitud::getIdsolicitud)
                                .reversed())
                );
            }

        } catch (Exception e) {
            JsfUtil.errorDialog("completeByEstatus() ", e.getLocalizedMessage().toString());
        }

        return suggestions;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="completeSolicitudParaCopiarAll(String query, String tipoSolicitud)">
    /**
     * Devuelve un list para copiar
     *
     * @param query
     * @param tipoSolicitud
     * @return
     */
    public List<Solicitud> completeAllSolicitudParaCopiar(String query, String tipoSolicitud) {
        List<Solicitud> suggestions = new ArrayList<>();
        try {
            //  Usuario jmoordb_user = (Usuario) JmoordbContext.get("jmoordb_user");
            List<Solicitud> list = new ArrayList<>();
            list = repository.complete(query);
            if (!list.isEmpty()) {
                for (Solicitud s : list) {
                    if (s.getTiposolicitud().getIdtiposolicitud().equals(tipoSolicitud)) {
                        suggestions.add(s);
                    }
                }
            }
            if (!suggestions.isEmpty()) {

                suggestions.sort(Comparator.comparing(Solicitud::getIdsolicitud)
                        .reversed()
                        .thenComparing(Comparator.comparing(Solicitud::getIdsolicitud)
                                .reversed())
                );
            }

        } catch (Exception e) {
            JsfUtil.errorDialog("completeSolicitudParaCopiar() ", e.getLocalizedMessage().toString());
        }

        return suggestions;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String showDate(Date date)">
    public String showDate(Date date) {
        String h = "";
        try {
            h = DateUtil.dateFormatToString(date, "dd/MM/yyyy");
        } catch (Exception e) {
            JsfUtil.errorMessage("showDate() " + e.getLocalizedMessage());
        }
        return h;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String showHour(Date date)">

    public String showHour(Date date) {
        String h = "";
        try {
            h = DateUtil.hourFromDateToString(date);
        } catch (Exception e) {
            JsfUtil.errorMessage("showHour() " + e.getLocalizedMessage());
        }
        return h;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean solicitudDisponibleParaViajes(Solicitud solicitud, Date fechahorapartida, Date fechahoraregreso)">
    /**
     * *
     * busca si el vehiculo tiene un viaje en esas fechas
     *
     * @param viajes
     * @return
     */
    public Boolean solicitudDisponibleParaViajes(Solicitud solicitud, Date fechahorapartida, Date fechahoraregreso) {
        try {

            Bson filter = Filters.and(eq("solicitud.idsolicitud", solicitud.getIdsolicitud()),eq("activo", "si"));
//           
            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorapartida", fechahorapartida, "fechahoraregreso", fechahoraregreso);

        } catch (Exception e) {
            JsfUtil.errorDialog(" solicitudDisponibleParaViajes() ", e.getLocalizedMessage().toString());
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
    public Boolean solicitudDisponibleExcluyendoMismoViaje(Solicitud solicitud, Date fechahorapartida, Date fechahoraregreso, Integer idviaje) {
        try {
            Bson filter = Filters.and(eq("solicitud.idsolicitud", solicitud.getIdsolicitud()), eq("activo", "si"));

            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorapartida", fechahorapartida, "fechahoraregreso", fechahoraregreso);

        } catch (Exception e) {
            JsfUtil.errorDialog("solicitudDisponibleExcluyendoMismoViaje() ", e.getLocalizedMessage().toString());
        }
        return false;
    }

    // </editor-fold>
}
