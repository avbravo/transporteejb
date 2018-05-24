/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.repository;
import com.avbravo.ejbjmoordb.mongodb.repository.Repository;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import com.avbravo.ejbjmoordb.pojos.AccessInfo;
import com.avbravo.transporteejb.provider.MongoClientTransporteejbProvider;
import com.mongodb.MongoClient;

/**
 *
 * @author avbravo
 */
@Stateless
public class AccessInfoTransporteejbRepository extends Repository<AccessInfo> {
    @EJB
    MongoClientTransporteejbProvider mongoClientProvider;
    @Override
    protected MongoClient getMongoClient() {
       return mongoClientProvider.getMongoClient();
    }
    public AccessInfoTransporteejbRepository(){
        super(AccessInfo.class,"transporte_history","accessinfo");
    }

    public AccessInfoTransporteejbRepository(Class<AccessInfo> entityClass, String database, String collection, Boolean... lazy) {
        super(entityClass, database, collection, lazy);
    }

    
    
    
    @Override
    public Object findById(String key, String value) {
       return search(key,value); 
    }
    @Override
    public Object findById(String key, Integer value) {
        return search(key,value);
    }

}
