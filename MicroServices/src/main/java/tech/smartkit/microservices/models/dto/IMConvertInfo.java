/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.models.dto;

import org.im4java.core.IMOperation;

//@see https://imagemagick.org/script/convert.php
//#http://www.imagemagick.org/Usage/
//        #montage，http://www.imagemagick.org/Usage/montage/
//        montage Begin00_face.png Begin00_role.png Begin00_UL.png Begin00_BG.png \
//        -geometry +2+2   montage_geom.png
//        #composite，http://www.imagemagick.org/Usage/compose/
//
//        convert -size 2600x1299 xc:skyblue \
//        Begin00_BG.png  -geometry  +0+0  -composite \
//        Begin00_UL.png  -geometry +35+725  -composite \
//        Begin00_role.png  -geometry +20+20  -composite \
//        Begin00_face.png  -geometry +30+30  -composite \
//        compose.png
//
//        #with lable，http://www.imagemagick.org/Usage/annotating/#anno_below
//
//        convert -size 2600x1299 xc:skyblue \
//        -pointsize 48 -font SimSun  \
//        label:'这就是我们勇敢的**骑士，他守护着和平的蓝色王国，保护着美丽的公主，一切都是那么的宁静祥和。' \
//        Begin00_BG.png  -geometry  +0+0  -composite \
//        Begin00_UL.png  -geometry +35+725  -composite \
//        Begin00_role.png  -geometry +20+20  -composite \
//        Begin00_face.png  -geometry +60+60  -composite \
//        compose_label.png
//
//        #with watermark，http://www.imagemagick.org/Usage/annotating/#wmark_text
//
//        convert -size 2600x1299 xc:skyblue \
//        -pointsize 60 -font SimSun  \
//        label:'这就是我们勇敢的**骑士，他守护着和平的蓝色王国，保护着美丽的公主，一切都是那么的宁静祥和。' \
//        Begin00_BG.png  -geometry  +0+0  -composite \
//        Begin00_UL.png  -geometry +35+725  -composite \
//        Begin00_role.png  -geometry +20+20  -composite \
//        Begin00_face.png  -geometry +30+30  -composite \
//        -draw "gravity south \
//        fill black  text 0,12 'cStoryBookAssets' \
//        fill white  text 1,11 'cStoryBookAssets' " \
//        compose_label_wmark_text_drawn.jpg
//More:TestScript.sh
public class IMConvertInfo extends ImageMagickInfo {

    private int pointsize;
    private String xc;
    private String font;
    private String label;
    private String geometry;
    private String draw;
    private String watermark;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    private String background;

    public IMConvertInfo() {
    }


    public int getPointsize() {
        return pointsize;
    }

    public void setPointsize(int pointsize) {
        this.pointsize = pointsize;
    }

    public String getXc() {
        return xc;
    }

    public void setXc(String xc) {
        this.xc = xc;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    @Override
    public IMOperation getIMOperation(){
        // create the operation, add images and operators/options
        IMOperation op = new IMOperation();
        op.addImage(this.getInput());
        op.resize(this.getWidth(),this.getHeight());
        op.background(this.getBackground());
        op.font(this.getFont());
        op.draw(this.getDraw());
        //
        return op;
    }

    @Override
    public String toString() {
        return "IMConvertInfo{" +
                "pointsize=" + pointsize +
                ", xc='" + xc + '\'' +
                ", font='" + font + '\'' +
                ", label='" + label + '\'' +
                ", geometry='" + geometry + '\'' +
                ", draw='" + draw + '\'' +
                ", watermark='" + watermark + '\'' +
                ", background='" + background + '\'' +
                '}';
    }
}
