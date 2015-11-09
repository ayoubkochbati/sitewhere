/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.spi.device.event.processor.multicast;

import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.IDevice;

/**
 * Builds routes of a given type.
 * 
 * @author Derek
 *
 * @param <T>
 */
public interface IDeviceRouteBuilder<T> {

	/**
	 * Build route for a given device.
	 * 
	 * @param device
	 * @return
	 * @throws SiteWhereException
	 */
	public IRoute<T> build(IDevice device) throws SiteWhereException;
}