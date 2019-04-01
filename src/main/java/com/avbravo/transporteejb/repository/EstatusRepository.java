/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.repository;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.transporteejb.entity.Estatus;
import javax.ejb.Stateless;

/**
 *
 * @author avbravo
 */
@Stateless
public class EstatusRepository extends Repository<Estatus> {

    public EstatusRepository(){
        super(Estatus.class,"transporte","estatus");
    }
   

}
