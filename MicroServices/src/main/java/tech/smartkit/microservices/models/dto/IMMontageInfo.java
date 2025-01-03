/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.models.dto;

//@see https://imagemagick.org/script/montage.php
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

import org.im4java.core.IMOperation;

public class IMMontageInfo extends ImageMagickInfo {

    @Override
    public IMOperation getIMOperation(){
        IMOperation op = new IMOperation();
        op.addImage(this.getInput());
        op.resize(this.getWidth(),this.getHeight());
        return new IMOperation();
    };
}
