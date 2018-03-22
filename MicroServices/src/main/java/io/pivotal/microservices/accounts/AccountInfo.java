/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package io.pivotal.microservices.accounts;

import io.pivotal.microservices.base.ModelBase;

import javax.persistence.*;
import java.io.Serializable;
/**
 * Persistent accountInfo entity with JPA markup. AccountsInfo is One2One map
 * relationship with Account.
 * @see https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
 *
 * @author yangboz
 */
@Entity
@Table(name = "T_ACCOUNT_INFO")
public class AccountInfo extends ModelBase implements Serializable{

    //prototype:https://creator.ionic.io/share/html/de2372279d5e?ionicplatform=ios#/page0/page2

    private static final long serialVersionUID = 11L;

    @Id
    protected Long id;

    private String name;

    private String birthday;

    private String gender;

    private int age;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Account account;

    public AccountInfo() {
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", account=" + account +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
