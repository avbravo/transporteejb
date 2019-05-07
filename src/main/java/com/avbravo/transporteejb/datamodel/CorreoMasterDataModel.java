/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.CorreoMaster;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class CorreoMasterDataModel extends ListDataModel<CorreoMaster> implements SelectableDataModel<CorreoMaster>{

    public CorreoMasterDataModel() {
    }
    public CorreoMasterDataModel(List<CorreoMaster>data) {
        super(data);
    }

    @Override
    public CorreoMaster  getRowData(String rowKey) {
        List<CorreoMaster> correoMasterList = (List<CorreoMaster>) getWrappedData();
        for (CorreoMaster correoMaster : correoMasterList) {
             if (correoMaster.getEmail().equals(rowKey)) {
                 return correoMaster;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(CorreoMaster correoMaster) {
         return correoMaster.getEmail();
     }


}
