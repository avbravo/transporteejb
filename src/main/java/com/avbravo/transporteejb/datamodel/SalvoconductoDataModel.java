/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Salvoconducto;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class SalvoconductoDataModel extends ListDataModel<Salvoconducto> implements SelectableDataModel<Salvoconducto>{

    public SalvoconductoDataModel() {
    }
    public SalvoconductoDataModel(List<Salvoconducto>data) {
        super(data);
    }

    @Override
    public Salvoconducto  getRowData(String rowKey) {
        List<Salvoconducto> salvoconductoList = (List<Salvoconducto>) getWrappedData();
        for (Salvoconducto salvoconducto : salvoconductoList) {
             if (salvoconducto.getIdsalvoconducto().equals(rowKey)) {
                 return salvoconducto;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Salvoconducto salvoconducto) {
         return salvoconducto.getIdsalvoconducto();
     }


}
