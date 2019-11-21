/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.factory;

import com.avbravo.transporteejb.entity.Viaje;

/**
 *
 * @author avbravo
 */
    interface ViajeFactory<P extends Viaje> {
    P create(Integer idviaje);
}

