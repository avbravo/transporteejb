/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Viaje;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class ViajeDataModel extends ListDataModel<Viaje> implements SelectableDataModel<Viaje>{

    public ViajeDataModel() {
    }
    public ViajeDataModel(List<Viaje>data) {
        super(data);
    }

    @Override
    public Viaje  getRowData(String rowKey) {
        List<Viaje> viajesList = (List<Viaje>) getWrappedData();
        for (Viaje viajes : viajesList) {
             if (viajes.getIdviaje().equals(rowKey)) {
                 return viajes;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Viaje viajes) {
         return viajes.getIdviaje();
     }


}
