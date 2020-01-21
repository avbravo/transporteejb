/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.SalvoConductoNotas;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class SalvoConductoNotasDataModel extends ListDataModel<SalvoConductoNotas> implements SelectableDataModel<SalvoConductoNotas>{

    public SalvoConductoNotasDataModel() {
    }
    public SalvoConductoNotasDataModel(List<SalvoConductoNotas>data) {
        super(data);
    }

    @Override
    public SalvoConductoNotas  getRowData(String rowKey) {
        List<SalvoConductoNotas> salvoConductoNotasList = (List<SalvoConductoNotas>) getWrappedData();
        for (SalvoConductoNotas salvoConductoNotas : salvoConductoNotasList) {
             if (salvoConductoNotas.getIdsalvoconductonotas().equals(rowKey)) {
                 return salvoConductoNotas;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(SalvoConductoNotas salvoConductoNotas) {
         return salvoConductoNotas.getIdsalvoconductonotas();
     }


}
