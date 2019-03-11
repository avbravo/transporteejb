/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.producer;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import com.avbravo.jmoordb.pojos.ErrorInfo;
import com.avbravo.transporteejb.provider.MongoClientTransporteejbProvider;
import com.mongodb.MongoClient;

/**
 *
 * @author avbravo
 */
@Stateless
public class ErrorInfoTransporteejbRepository extends Repository<ErrorInfo> {
    @EJB
    MongoClientTransporteejbProvider mongoClientProvider;
    @Override
    protected MongoClient getMongoClient() {
       return mongoClientProvider.getMongoClient();
    }
    public ErrorInfoTransporteejbRepository(){
        super(ErrorInfo.class,"transporte_history","errorinfo");
    }

    public ErrorInfoTransporteejbRepository(Class<ErrorInfo> entityClass, String database, String collection, Boolean... lazy) {
        super(entityClass, database, collection, lazy);
    }

    
    
    
 

}
