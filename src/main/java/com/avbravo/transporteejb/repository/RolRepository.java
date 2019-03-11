/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.repository;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.transporteejb.entity.Rol;
import com.avbravo.transporteejb.provider.MongoClientTransporteejbProvider;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import com.mongodb.MongoClient;

/**
 *
 * @author avbravo
 */
@Stateless
public class RolRepository extends Repository<Rol> {

    @EJB
    MongoClientTransporteejbProvider mongoClientProvider;
    @Override
    protected MongoClient getMongoClient() {
       return mongoClientProvider.getMongoClient();
    }
    public RolRepository(){
        super(Rol.class,"transporte","rol");
    }
   

}
