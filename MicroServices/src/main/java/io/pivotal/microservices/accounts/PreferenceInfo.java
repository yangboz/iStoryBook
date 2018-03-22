/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package io.pivotal.microservices.accounts;

import io.pivotal.microservices.base.ModelBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_PREFERENCE_INFO")
public class PreferenceInfo extends ModelBase implements Serializable {


    private static final long serialVersionUID = 12L;

    @Id
    protected Long id;

    private String color;

    private String thing;//stage property，猫、狗、乌龟、鹦鹉等

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Account account;

    @Override
    public String toString() {
        return "PreferenceInfo{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", thing='" + thing + '\'' +
                ", account=" + account +
                '}';
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public PreferenceInfo() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }
}
