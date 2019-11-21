/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.configuration.JmoordbConfiguration;
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.mongodb.history.services.AutoincrementableServices;
import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.services.RevisionHistoryServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import static com.avbravo.jmoordb.util.JmoordbUtil.nameOfClass;
import static com.avbravo.jmoordb.util.JmoordbUtil.nameOfMethod;
import com.avbravo.transporteejb.entity.Solicitud;
import com.avbravo.transporteejb.entity.Tipovehiculo;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.entity.Viaje;
import com.avbravo.transporteejb.repository.EstatusViajeRepository;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.VehiculoRepository;
import com.avbravo.transporteejb.repository.ViajeRepository;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
    ErrorInfoServices errorServices;
    @Inject
    EstatusServices estatusServices;
    @Inject
    EstatusViajeServices estatusViajeServices;
    @Inject
    EstatusViajeRepository estatusViajeRepository;
    @Inject
    SolicitudRepository repository;
    @Inject
    ViajeRepository viajesRepository;
    @Inject
    VehiculoRepository vehiculoRepository;
    
    @Inject
    ViajeServices viajeServices;

    @Inject
    AutoincrementableServices autoincrementableServices;

    List<Solicitud> solicitudList = new ArrayList<>();

    public List<Solicitud> complete(String query) {
        List<Solicitud> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return suggestions;
    }

    // <editor-fold defaultstate="collapsed" desc="getSolicitudList()">
    public List<Solicitud> getSolicitudList() {
        try {
            solicitudList = repository.findAll(new Document("solicitud", 1));
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
                    if (JmoordbUtil.dateBetween(solicitud.getFechahorapartida(), s.getFechahorapartida(), s.getFechahoraregreso())
                            || JmoordbUtil.dateBetween(solicitud.getFechahoraregreso(), s.getFechahorapartida(), s.getFechahoraregreso())) {

// coincide en el rango de fecha y hora con la solicitud s
                        return Optional.of(s);
                    }
                }
            }
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
                    if (JmoordbUtil.dateBetween(solicitud.getFechahorapartida(), s.getFechahorapartida(), s.getFechahoraregreso())
                            || JmoordbUtil.dateBetween(solicitud.getFechahoraregreso(), s.getFechahorapartida(), s.getFechahoraregreso())) {

// coincide en el rango de fecha y hora con la solicitud s
                        return Optional.of(s);
                    }
                }
            }
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return usuario;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isValid()">
    public Boolean isValid(Solicitud solicitud, ResourceBundle mrb, ResourceBundle arb) {
        try {
            if (solicitud.getFechahorapartida() == null) {

                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fechapartidanoseleccionada"));
                return false;
            }

            if (solicitud.getFechahoraregreso() == null) {
                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fecharegresonoseleccionada"));
                return false;
            }
            if (solicitud.getRangoagenda() == null || solicitud.getRangoagenda().isEmpty()) {
                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.indiquerangoagenda"));
                return false;
            }

            if (JmoordbUtil.fechaMenor(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {

                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fecharegresomenorfechapartida"));
                return false;
            }
            if (JmoordbUtil.fechaIgual(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {

                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fecharegresoigualpartida"));
                return false;
            }

            if (JmoordbUtil.horaDeUnaFecha(solicitud.getFechahorapartida()) == 0
                    && JmoordbUtil.minutosDeUnaFecha(solicitud.getFechahorapartida()) == 0) {
                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.horapartidacero"));
                return false;
            }

            if (JmoordbUtil.horaDeUnaFecha(solicitud.getFechahoraregreso()) == 0
                    && JmoordbUtil.minutosDeUnaFecha(solicitud.getFechahoraregreso()) == 0) {
                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.horallegadacero"));
                return false;
            }

            if (solicitud.getPasajeros() <= 0) {
                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.pasajerosmenorcero"));
                return false;
            }

            if (solicitud.getNumerodevehiculos() <= 0) {
                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.numerovehiculoscero"));
                return false;
            }
            Integer totalvehiculos = vehiculoRepository.count(new Document("activo", "si").append("enreparacion", "no"));

            if (solicitud.getNumerodevehiculos() > totalvehiculos) {
                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.vehiculosmayorquedisponibles"));
                return false;
            }

            if (JmoordbUtil.fechaMenor(solicitud.getFechahorapartida(), JmoordbUtil.fechaActual())) {
                JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fechapartidamenoractual"));
                return false;
            }
            return true;
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isValidDates()">
    public Boolean isValidDates(Solicitud solicitud, Boolean showdialog, Boolean validateFechaActual, ResourceBundle mrb, ResourceBundle arb, Boolean... validarHoraCero) {
        try {
            Boolean validarHoraCeroLocal = true;
            if (validarHoraCero.length != 0) {
                validarHoraCeroLocal = validarHoraCero[0];

            }
            if (solicitud.getFechahorapartida() == null) {
                if (showdialog) {
                    JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fechapartidanoseleccionada"));
                } else {
                    JmoordbUtil.warningMessage(mrb.getString("warning.fechapartidanoseleccionada"));
                }

                return false;
            }

            if (solicitud.getFechahoraregreso() == null) {
                if (showdialog) {
                    JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fecharegresonoseleccionada"));
                } else {
                    JmoordbUtil.warningMessage(mrb.getString("warning.fecharegresonoseleccionada"));
                }

                return false;
            }

            if (JmoordbUtil.fechaMenor(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {
                if (showdialog) {
                    JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fecharegresomenorfechapartida"));
                } else {
                    JmoordbUtil.warningMessage(mrb.getString("warning.fecharegresomenorfechapartida"));
                }

                return false;
            }

            if (JmoordbUtil.fechaIgual(solicitud.getFechahoraregreso(), solicitud.getFechahorapartida())) {
                if (showdialog) {
                    JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fecharegresoigualpartida"));
                } else {
                    JmoordbUtil.warningMessage(mrb.getString("warning.fecharegresoigualpartida"));
                }

                return false;
            }
            if (validarHoraCeroLocal) {
                if (JmoordbUtil.horaDeUnaFecha(solicitud.getFechahorapartida()) == 0
                        && JmoordbUtil.minutosDeUnaFecha(solicitud.getFechahorapartida()) == 0) {
                    if (showdialog) {
                        JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.horapartidacero"));
                    } else {
                        JmoordbUtil.warningMessage(mrb.getString("warning.horapartidacero"));
                    }

                    return false;
                }
            }

            if (validarHoraCeroLocal) {
                if (JmoordbUtil.horaDeUnaFecha(solicitud.getFechahoraregreso()) == 0
                        && JmoordbUtil.minutosDeUnaFecha(solicitud.getFechahoraregreso()) == 0) {
                    if (showdialog) {
                        JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.horallegadaescero"));
                    } else {
                        JmoordbUtil.warningMessage(mrb.getString("warning.horallegadaescero"));
                    }

                    return false;
                }
            }

            if (validateFechaActual) {
                if (JmoordbUtil.fechaMenor(solicitud.getFechahorapartida(), JmoordbUtil.fechaActual())) {
                    if (showdialog) {
                        JmoordbUtil.warningDialog(arb.getString("warning.view"), mrb.getString("warning.fechapartidamenoractual"));
                    } else {
                        JmoordbUtil.warningMessage(mrb.getString("warning.fechapartidamenoractual"));
                    }

                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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

        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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

            Integer dia = JmoordbUtil.diaDeUnaFecha(solicitud.getFechahorapartida());
            Integer mes = JmoordbUtil.diaDeUnaFecha(solicitud.getFechahorapartida());
            Integer anio = JmoordbUtil.diaDeUnaFecha(solicitud.getFechahorapartida());
            Integer diaf = JmoordbUtil.diaDeUnaFecha(solicitud.getFechahoraregreso());
            Integer mesf = JmoordbUtil.diaDeUnaFecha(solicitud.getFechahoraregreso());
            Integer aniof = JmoordbUtil.diaDeUnaFecha(solicitud.getFechahoraregreso());
// ES EN LA MISMA FECHA

            if (anio == aniof && mes == mesf && dia == diaf) {
                return true;
            }
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return suggestions;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String showDate(Date date)">
    public String showDate(Date date) {
        String h = "";
        try {
            h = JmoordbUtil.dateFormatToString(date, "dd/MM/yyyy");
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return h;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String showHour(Date date)">

    public String showHour(Date date) {
        String h = "";
        try {
            h = JmoordbUtil.hourFromDateToString(date);
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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

            Bson filter = Filters.and(eq("idsolicitud", solicitud.getIdsolicitud()), eq("activo", "si"));
//           
            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorapartida", fechahorapartida, "fechahoraregreso", fechahoraregreso);

        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
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
    public Boolean solicitudDisponibleExcluyendoMismoViaje(Solicitud solicitud, Date fechahorapartida, Date fechahoraregreso) {
        try {
            Bson filter = Filters.and(eq("idsolicitud", solicitud.getIdsolicitud()), eq("activo", "si"), eq("estatus.idestatus", "SOLICITADO"));

            return repository.isAvailableBetweenDateHour(filter,
                    "fechahorapartida", fechahorapartida, "fechahoraregreso", fechahoraregreso);

        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Solicitud> solicitudPorViaje(Viaje viaje)">
    /**
     * devuelve la lista de solicitudes que contengan el viaje. Recuerde que es
     * un List<Viaje> q que contiene en la primera posicion el viaje de ida y en
     * la segunda el viaje de regreso
     *
     * @
     * @param viaje
     * @return
     */
    public List<Solicitud> solicituPorViaje(Viaje viaje) {
        List<Solicitud> list = new ArrayList<>();
        try {
            Document doc = new Document("viaje.idviaje", viaje.getIdviaje());
            list = repository.findBy(doc);

        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>  

   
    // <editor-fold defaultstate="collapsed" desc="Boolean insert(Tipovehiculo tipovehiculo)">
    public List<Object> insert(Solicitud solicitud, Tipovehiculo tipovehiculo,List<Solicitud> solicitudGuardadasList, ResourceBundle mrb, ResourceBundle arb) {
        List<Object> list = new ArrayList<>();
        Boolean valid = false;
        try {
            Usuario jmoordb_user = (Usuario) JmoordbContext.get("jmoordb_user");
            Integer idsolicitud = autoincrementableServices.getContador("solicitud");
            solicitud.setIdsolicitud(idsolicitud);
            //Se establece en 1 el numero de vehiculos solicitados a ser guardados
            solicitud.setNumerodevehiculos(1);
            Optional<Solicitud> optional = repository.findById(solicitud);
            if (optional.isPresent()) {
                JmoordbUtil.warningMessage(arb.getString("warning.idexist"));
                valid =false;
            }

//            //Viajes
//           List<Viaje> viajeList = new ArrayList<>();
//           
//         
//           viajeList.add(viajeServices.viajeInicializadoParaSolicitud());
//           viajeList.add(viajeServices.viajeInicializadoParaSolicitud());
//           solicitud.setViaje(viajeList);

            //Lo datos del usuario
            List<Tipovehiculo> tipovehiculoList = new ArrayList<>();

            tipovehiculoList.add(tipovehiculo);
            solicitud.setTipovehiculo(tipovehiculoList);
            solicitud.setUserInfo(repository.generateListUserinfo(jmoordb_user.getUsername(), "create"));
            if (repository.save(solicitud)) {
                Solicitud sol = new Solicitud();
                sol = (Solicitud) JmoordbUtil.copyBeans(sol, solicitud);
                solicitudGuardadasList.add(sol);

                //guarda el contenido anterior
                JmoordbConfiguration jmc = new JmoordbConfiguration();
                Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
                RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
                repositoryRevisionHistory.save(revisionHistoryServices.getRevisionHistory(solicitud.getIdsolicitud().toString(), jmoordb_user.getUsername(),
                        "create", "solicitud", repository.toDocument(solicitud).toString()));

            } else {
                JmoordbUtil.warningMessage(repository.getException().toString());
                valid=false;
            }
            valid = true;
        } catch (Exception e) {
            errorServices.errorMessage(nameOfClass(), nameOfMethod(), e.getLocalizedMessage(), e);
        }
       
        list.add(solicitud);
        list.add(solicitudGuardadasList);
         list.add(valid);
        return list;
    }// </editor-fold>

 
}
