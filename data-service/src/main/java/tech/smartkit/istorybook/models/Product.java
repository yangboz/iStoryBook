/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models;

import com.fasterxml.jackson.annotation.JsonRootName;
import tech.smartkit.istorybook.services.ProductService;

/**
 * Product DTO - used to interact with the {@link ProductService}.
 *
 * @author yangboz
 * @see https://imagemagick.org/script/montage.php
 * magick montage -label %f -frame 5 -background '#336699' -geometry +4+4 rose.jpg red-ball.png frame.jpg
 */
@JsonRootName("T_PRODUCT")
public class Product {
    protected Long id;
    protected Long groupId;
    protected Long userId;
    protected String name;
    protected String label;
    protected String url;
    protected String images;
    protected int frame;
    protected String backgroud;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                ", images='" + images + '\'' +
                ", frame=" + frame +
                ", backgroud='" + backgroud + '\'' +
                '}';
    }

    public Product(Long groupId, Long userId, String name) {
        this.groupId = groupId;
        this.userId = userId;
        this.name = name;
    }

    public Long getGroupId() {

        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public String getBackgroud() {
        return backgroud;
    }

    public void setBackgroud(String backgroud) {
        this.backgroud = backgroud;
    }
    //
}
