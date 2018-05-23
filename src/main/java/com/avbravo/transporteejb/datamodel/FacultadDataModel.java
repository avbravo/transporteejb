/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Facultad;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class FacultadDataModel extends ListDataModel<Facultad> implements SelectableDataModel<Facultad>{

    public FacultadDataModel() {
    }
    public FacultadDataModel(List<Facultad>data) {
        super(data);
    }

    @Override
    public Facultad  getRowData(String rowKey) {
        List<Facultad> facultadList = (List<Facultad>) getWrappedData();
        for (Facultad facultad : facultadList) {
             if (facultad.getIdfacultad().equals(rowKey)) {
                 return facultad;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Facultad facultad) {
         return facultad.getIdfacultad();
     }


}
