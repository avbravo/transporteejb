/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.provider;

/**
 *
 * @author avbravo
 */
import com.avbravo.avbravoutils.JsfUtil;
import com.mongodb.MongoClient;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 * * * @author
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoClientTransporteejbProvider {

    private static final Logger LOG = Logger.getLogger(MongoClientTransporteejbProvider.class.getName());
    private MongoClient mongoClient = null;
    
 
    
    

    @Lock(LockType.READ)
    public MongoClient getMongoClient() {
        if (mongoClient == null) {
            init();
        }
        return mongoClient;
    }

    @PostConstruct
    public void init() {
        try {
            /* String database="";
            String username=""; 
            String password="";
            String host="localhost"; 
            String port="27017"; 
            
            char[] charArray = password.toCharArray(); 
            MongoCredential credential = MongoCredential.createCredential(username, database, charArray);
            mongoClient = new MongoClient(new ServerAddress(host,port), Arrays.asList(credential));
            */ 
            mongoClient = new MongoClient();
        } catch (Exception e) {
            JsfUtil.errorMessage("init() " + e.getLocalizedMessage());
            LOG.warning("MongoClientProvider init() " + e.getLocalizedMessage());
        }
    }
}
