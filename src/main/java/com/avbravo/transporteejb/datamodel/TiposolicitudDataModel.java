/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Tiposolicitud;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class TiposolicitudDataModel extends ListDataModel<Tiposolicitud> implements SelectableDataModel<Tiposolicitud>{

    public TiposolicitudDataModel() {
    }
    public TiposolicitudDataModel(List<Tiposolicitud>data) {
        super(data);
    }

    @Override
    public Tiposolicitud  getRowData(String rowKey) {
        List<Tiposolicitud> tiposolicitudList = (List<Tiposolicitud>) getWrappedData();
        for (Tiposolicitud tiposolicitud : tiposolicitudList) {
             if (tiposolicitud.getIdtiposolicitud().equals(rowKey)) {
                 return tiposolicitud;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Tiposolicitud tiposolicitud) {
         return tiposolicitud.getIdtiposolicitud();
     }


}
