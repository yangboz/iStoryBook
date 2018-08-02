/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.services;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.stereotype.Service;
import tech.smartkit.microservices.models.dto.IMConvertInfo;
import tech.smartkit.microservices.models.dto.IMMontageInfo;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Service
public class ImageMagickService {

    protected Logger logger = Logger.getLogger(ImageMagickService.class
            .getName());

    private ConvertCmd cmd;
    @PostConstruct
    public void main() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        // create command
        cmd = new ConvertCmd();
        logger.warning("hard coded imagemagick info: "+cmd.toString());
    }


    /**
     * Convert product's use IM montage.
     * @see http://im4java.sourceforge.net/tools/index.html
     * @see https://imagemagick.org/script/montage.php
     */
    public void montage(IMMontageInfo montageInfo) throws InterruptedException, IOException, IM4JavaException {
        logger.info(" montage info: "
                + montageInfo.toString());
        // create the operation, add images and operators/options
        IMOperation op = montageInfo.getIMOperation();

        String srcImage = montageInfo.getInput();
        String dstImage = montageInfo.getOutput();
        if(dstImage.equals(null)) {
            int lastDot = montageInfo.getInput().lastIndexOf(".");
            dstImage = montageInfo.getInput().substring(1, lastDot - 1) + "_montage.jpg";
        }
        cmd.run(op,srcImage,dstImage);
    }

    /**
     * Convert product's use IM convert.
     * @see http://im4java.sourceforge.net/tools/index.html
     * @see https://imagemagick.org/script/convert.php
     */
    public File convert(IMConvertInfo convertInfo) throws InterruptedException, IOException, IM4JavaException {

        logger.info(" montage info: " + convertInfo.toString());

        // create the operation, add images and operators/options
        IMOperation op = convertInfo.getIMOperation();

        String srcImage = convertInfo.getInput();
        String dstImage = convertInfo.getOutput();
        if(dstImage.equals(null)) {
            int lastDot = convertInfo.getInput().lastIndexOf(".");
            dstImage = convertInfo.getInput().substring(1, lastDot - 1) + "_convert.jpg";
        }
        cmd.run(op,srcImage,dstImage);
        //
        return new File(dstImage);
    }
    //TODO:Identify,Mogrify,Compare,Composite,
}
