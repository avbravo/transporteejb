/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.repository;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.transporteejb.entity.Rol;
import javax.ejb.Stateless;

/**
 *
 * @author avbravo
 */
@Stateless
public class RolRepository extends Repository<Rol> {

    public RolRepository(){
        super(Rol.class,"transporte","rol");
    }
   

}
