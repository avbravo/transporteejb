/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.EstatusViaje;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class EstatusViajeDataModel extends ListDataModel<EstatusViaje> implements SelectableDataModel<EstatusViaje>{

    public EstatusViajeDataModel() {
    }
    public EstatusViajeDataModel(List<EstatusViaje>data) {
        super(data);
    }

    @Override
    public EstatusViaje  getRowData(String rowKey) {
        List<EstatusViaje> estatusViajeList = (List<EstatusViaje>) getWrappedData();
        for (EstatusViaje estatusViaje : estatusViajeList) {
             if (estatusViaje.getIdestatusviaje().equals(rowKey)) {
                 return estatusViaje;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(EstatusViaje estatusViaje) {
         return estatusViaje.getIdestatusviaje();
     }


}
