package org.xine.cdidemos.presentation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.cdidemos.business.resourcesmanager.boundary.FileManager;

@Named
@RequestScoped
public class HomeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fileName;
    private javax.servlet.http.Part photo;
    
    @Inject
    FileManager bo;

    public javax.servlet.http.Part getPhoto() {
        return this.photo;
    }

    public void setPhoto(final javax.servlet.http.Part photo) {
        this.photo = photo;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void save() {
        try (InputStream is = this.photo.getInputStream()) {
            System.out.println("hello saving");
            // final InputStream is = this.photo.getInputStream();
            final byte[] photoAsBytes = toByteArray(this.photo.getInputStream());

            System.out.println(this.photo.getName());
            System.out.println(this.photo.getContentType());
            System.out.println(this.photo.getHeaderNames());
            
            
            this.bo.deposit(this.fileName, photoAsBytes);

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] toByteArray(final InputStream is) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int reads = is.read();
        while (reads != -1) {
            baos.write(reads);
            reads = is.read();
        }
        return baos.toByteArray();

    }

}
