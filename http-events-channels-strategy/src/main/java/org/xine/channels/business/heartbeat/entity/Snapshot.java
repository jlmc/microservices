package org.xine.channels.business.heartbeat.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Snapshot {

    @Id
    @GeneratedValue
    private long id;
    @Temporal(TemporalType.TIME)
    private Date monitoringTime;
    private long usedHeapSize;
    private int threadCount;
    private int peakThreadCount;

    @XmlTransient
    @Transient
    private String escalationChannel;

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Date getMonitoringTime() {
        return this.monitoringTime;
    }

    public void setMonitoringTime(final Date monitoringTime) {
        this.monitoringTime = monitoringTime;
    }

    public long getUsedHeapSize() {
        return this.usedHeapSize;
    }

    public void setUsedHeapSize(final long usedHeapSize) {
        this.usedHeapSize = usedHeapSize;
    }

    public int getThreadCount() {
        return this.threadCount;
    }

    public void setThreadCount(final int threadCount) {
        this.threadCount = threadCount;
    }

    public int getPeakThreadCount() {
        return this.peakThreadCount;
    }

    public void setPeakThreadCount(final int peakThreadCount) {
        this.peakThreadCount = peakThreadCount;
    }

    public String getEscalationChannel() {
        return this.escalationChannel;
    }

}
