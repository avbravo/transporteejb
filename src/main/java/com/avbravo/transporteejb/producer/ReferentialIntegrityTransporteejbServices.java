/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.producer;

import com.avbravo.transporteejb.repository.ConductorRepository;
import com.avbravo.transporteejb.repository.EstatusRepository;
import com.avbravo.transporteejb.repository.RolRepository;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.TiposolicitudRepository;
import com.avbravo.transporteejb.repository.TipovehiculoRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @authoravbravo
 */
@Stateless
public class ReferentialIntegrityTransporteejbServices {

    @Inject
    ConductorRepository conductorRepository;
    @Inject
    EstatusRepository estatusRepository;
    @Inject
    RolRepository rolRepository;
    @Inject
    SolicitudRepository solicitudRepository;
    @Inject
    TiposolicitudRepository tiposolicitudRepository;
    @Inject
    TipovehiculoRepository tipovehiculoRepository;

}
