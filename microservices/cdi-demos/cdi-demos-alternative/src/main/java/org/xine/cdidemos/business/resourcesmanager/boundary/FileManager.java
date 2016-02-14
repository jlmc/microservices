package org.xine.cdidemos.business.resourcesmanager.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.xine.cdidemos.business.resourcesmanager.control.Repository;

@Stateless
public class FileManager {

	@Inject
	Repository repository;
	
	public void deposit(final String fileName, final byte[] bytes) {
		this.repository.put(fileName, bytes);
	}
	
}
