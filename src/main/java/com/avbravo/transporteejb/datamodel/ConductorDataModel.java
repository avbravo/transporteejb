/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Conductor;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class ConductorDataModel extends ListDataModel<Conductor> implements SelectableDataModel<Conductor>{

    public ConductorDataModel() {
    }
    public ConductorDataModel(List<Conductor>data) {
        super(data);
    }

    @Override
    public Conductor  getRowData(String rowKey) {
        List<Conductor> conductorList = (List<Conductor>) getWrappedData();
        for (Conductor conductor : conductorList) {
             if (conductor.getIdconductor().equals(rowKey)) {
                 return conductor;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Conductor conductor) {
         return conductor.getIdconductor();
     }


}
