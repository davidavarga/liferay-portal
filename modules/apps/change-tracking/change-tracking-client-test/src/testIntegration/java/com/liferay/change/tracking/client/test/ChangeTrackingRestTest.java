/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.client.test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.preemptive;

import com.liferay.change.tracking.client.test.internal.activator.ChangeTrackingRestTestActivator;
import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;

import io.restassured.RestAssured;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunAsClient
@RunWith(Arquillian.class)
public class ChangeTrackingRestTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			ChangeTrackingRestTestActivator.class);
	}

	@Before
	public void setUp() {
		RestAssured.port = 8080;
		RestAssured.basePath = "/o/change-tracking";
		RestAssured.baseURI = _url.toExternalForm();
		RestAssured.authentication =
			preemptive().basic("test@liferay.com", "test");
	}

	@Test
	public void testContentSpaceIsInContentSpaceCollections() {
		get("configurations/{companyId}", 20100).then().statusCode(200);
	}

	@ArquillianResource
	private URL _url;

}