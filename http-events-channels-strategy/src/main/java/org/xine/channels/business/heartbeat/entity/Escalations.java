package org.xine.channels.business.heartbeat.entity;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Escalations {
    private Map<String, Snapshot> escalations;

    public Escalations() {
    }

    public Escalations(final Map<String, Snapshot> escalations) {
        this.escalations = escalations;
    }

    public Map<String, Snapshot> getEscalations() {
        return this.escalations;
    }

    public void setEscalations(final Map<String, Snapshot> escalations) {
        this.escalations = escalations;
    }
}