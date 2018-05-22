/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Solicitud;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class SolicitudDataModel extends ListDataModel<Solicitud> implements SelectableDataModel<Solicitud>{

    public SolicitudDataModel() {
    }
    public SolicitudDataModel(List<Solicitud>data) {
        super(data);
    }

    @Override
    public Solicitud  getRowData(String rowKey) {
        List<Solicitud> solicitudList = (List<Solicitud>) getWrappedData();
        for (Solicitud solicitud : solicitudList) {
             if (solicitud.getIdsolicitud().equals(rowKey)) {
                 return solicitud;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Solicitud solicitud) {
         return solicitud.getIdsolicitud();
     }


}
