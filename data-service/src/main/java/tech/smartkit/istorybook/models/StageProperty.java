/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "T_STAGE_PROPERTY")
public class StageProperty extends ModelBase implements Serializable{

    private static final long serialVersionUID = 13L;

    @Id
    protected Long id;

    public StageProperty() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "StageProperty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    private String name;

    private String image;
}
