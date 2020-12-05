package org.xine.cdidemos.business.resourcesmanager.control;

public class GoogleDriveRepository implements Repository {

    @Override
    public void put(final String fileName, final byte[] bytes) {
        System.out.println("Google Drive repository");
    }

}
