package ua.kpi.nc.persistence.dao;

import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import ua.kpi.nc.config.PropertiesReader;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class DataSourceSingleton {

    private static Logger log = LoggerFactory.getLogger(DataSourceSingleton.class.getName());

    private DataSource dataSource;

    private PropertiesReader propertiesReader = PropertiesReader.getInstance();

    private String databasePassword = propertiesReader.propertiesReader("db.password");

    private String databaseServerName = propertiesReader.propertiesReader("db.server.name");

    private String databaseUsername = propertiesReader.propertiesReader("db.username");

    private String databaseName = propertiesReader.propertiesReader("db.name");

    private int maxConnections = Integer.parseInt(propertiesReader.propertiesReader("db.connections"));

    private DataSourceSingleton() {
        try {
            dataSource = setUpDataSource();
            log.info("Data source has been created");
        } catch (NamingException e) {
            log.error("Cannot set up data source", e);
        }
    }

    private static class DataSourceSingletonHolder{
        public static final DataSourceSingleton HOLDER_INSTANCE = new DataSourceSingleton();
        private DataSourceSingletonHolder() {
        }
    }

    public static DataSource getInstance() {
        return DataSourceSingletonHolder.HOLDER_INSTANCE.getDataSource();
    }

    private DataSource setUpDataSource() throws NamingException {
        PGPoolingDataSource pgDataSource = new PGPoolingDataSource();
        pgDataSource.setDataSourceName("dataSource");
        pgDataSource.setServerName(databaseServerName);
        pgDataSource.setDatabaseName(databaseName);
        pgDataSource.setUser(databaseUsername);
        pgDataSource.setPassword(databasePassword);
        pgDataSource.setMaxConnections(maxConnections);
        return pgDataSource;
    }

    private DataSource getDataSource(){
        return dataSource;
    }
}
