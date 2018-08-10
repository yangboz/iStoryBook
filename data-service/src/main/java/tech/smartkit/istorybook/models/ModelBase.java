/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class ModelBase {
    // @Version
    // @Temporal(TemporalType.DATE)
    // @Column(name = "created", nullable = false)
    // @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @Column(name = "CREATED", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    private Date updated;

    @PrePersist
    protected void onCreate() {
        updated = created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    // The item date
//	@NotNull
//	private Date date = new Date();
    protected Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}