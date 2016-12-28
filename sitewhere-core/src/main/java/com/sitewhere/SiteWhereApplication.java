/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.sitewhere.server.SiteWhereServer;
import com.sitewhere.server.lifecycle.LifecycleProgressMonitor;
import com.sitewhere.spi.ServerStartupException;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.server.ISiteWhereApplication;

/**
 * Root Spring Boot application that loads all other SiteWhere artifacts.
 * 
 * @author Derek
 */
@Configuration
@EnableAutoConfiguration(exclude = { ActiveMQAutoConfiguration.class, DataSourceAutoConfiguration.class,
	DataSourceTransactionManagerAutoConfiguration.class, ErrorMvcAutoConfiguration.class,
	GsonAutoConfiguration.class, HazelcastAutoConfiguration.class, HazelcastJpaDependencyAutoConfiguration.class,
	JmxAutoConfiguration.class, MongoAutoConfiguration.class, SolrAutoConfiguration.class,
	VelocityAutoConfiguration.class, WebSocketAutoConfiguration.class })
public class SiteWhereApplication implements ISiteWhereApplication {

    /** Static logger instance */
    private static Logger LOGGER = LogManager.getLogger();

    @PostConstruct
    public void start() {
	try {
	    LifecycleProgressMonitor monitor = new LifecycleProgressMonitor();
	    SiteWhere.start(this, monitor);
	    LOGGER.info("Server started successfully.");
	    SiteWhere.getServer().logState();
	} catch (ServerStartupException e) {
	    SiteWhere.getServer().setServerStartupError(e);
	    StringBuilder builder = new StringBuilder();
	    builder.append("\n!!!! SiteWhere Server Failed to Start !!!!\n");
	    builder.append("\n");
	    builder.append("Component: " + e.getDescription() + "\n");
	    builder.append("Error: " + e.getComponent().getLifecycleError().getMessage() + "\n");
	    LOGGER.info("\n" + builder.toString() + "\n");
	    System.exit(1);
	} catch (SiteWhereException e) {
	    LOGGER.error("Exception on server startup.", e);
	    StringBuilder builder = new StringBuilder();
	    builder.append("\n!!!! SiteWhere Server Failed to Start !!!!\n");
	    builder.append("\n");
	    builder.append("Error: " + e.getMessage() + "\n");
	    LOGGER.info("\n" + builder.toString() + "\n");
	    System.exit(2);
	} catch (Throwable e) {
	    LOGGER.error("Unhandled exception in server startup.", e);
	    StringBuilder builder = new StringBuilder();
	    builder.append("\n!!!! Unhandled Exception !!!!\n");
	    builder.append("\n");
	    builder.append("Error: " + e.getMessage() + "\n");
	    LOGGER.info("\n" + builder.toString() + "\n");
	    System.exit(3);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.server.ISiteWhereApplication#getServerClass()
     */
    @Override
    public Class<? extends SiteWhereServer> getServerClass() throws SiteWhereException {
	return SiteWhereServer.class;
    }

    public static void main(String[] args) {
	SpringApplication.run(SiteWhereApplication.class, args);
    }
}