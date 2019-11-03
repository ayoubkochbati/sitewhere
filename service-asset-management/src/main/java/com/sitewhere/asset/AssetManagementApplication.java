/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.asset;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.sitewhere.asset.spi.microservice.IAssetManagementMicroservice;
import com.sitewhere.microservice.MicroserviceApplication;

/**
 * Main application which runs the asset management microservice.
 */
@ApplicationScoped
public class AssetManagementApplication extends MicroserviceApplication<IAssetManagementMicroservice> {

    @Inject
    private IAssetManagementMicroservice microservice;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sitewhere.microservice.spi.IMicroserviceApplication#getMicroservice()
     */
    @Override
    public IAssetManagementMicroservice getMicroservice() {
	return microservice;
    }
}