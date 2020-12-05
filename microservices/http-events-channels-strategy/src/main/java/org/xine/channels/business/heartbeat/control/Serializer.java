package org.xine.channels.business.heartbeat.control;

import java.io.Writer;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.xine.channels.business.heartbeat.entity.Escalations;
import org.xine.channels.business.heartbeat.entity.Snapshot;

public class Serializer {
    private Marshaller marshaller;

    @PostConstruct
    public void initialize() {
        try {
            final JAXBContext jaxb = JAXBContext.newInstance(new Class[] { Snapshot.class, Escalations.class });
            this.marshaller = jaxb.createMarshaller();
        } catch (final JAXBException ex) {
            throw new IllegalStateException("Cannot initialize JAXB context " + ex);
        }
    }

    public void serialize(final Snapshot snapshot, final Writer writer) {
        try {
            synchronized (this.marshaller) {
                this.marshaller.marshal(snapshot, writer);
            }
        } catch (final JAXBException ex) {
            throw new RuntimeException("Cannot marshal Snapshot " + snapshot + " Reason: " + ex, ex);
        }
    }

    public void serialize(final Escalations snapshots, final Writer writer) {
        try {
            synchronized (this.marshaller) {
                this.marshaller.marshal(snapshots, writer);
            }
        } catch (final JAXBException ex) {
            throw new RuntimeException("Cannot marshal Snapshot " + snapshots + " Reason: " + ex, ex);
        }
    }

}
