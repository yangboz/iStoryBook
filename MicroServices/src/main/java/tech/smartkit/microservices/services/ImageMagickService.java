/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.services;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;
import org.im4java.process.StandardStream;
import org.springframework.stereotype.Service;
import tech.smartkit.microservices.models.Product;
import tech.smartkit.microservices.models.dto.IMConvertInfo;
import tech.smartkit.microservices.models.dto.IMMontageInfo;
import tech.smartkit.microservices.models.dto.ImageMagickInfo;
import tech.smartkit.microservices.utils.ImageMagickTools;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static tech.smartkit.microservices.utils.ImageMagickTools.createDirectory;
import static tech.smartkit.microservices.utils.ImageMagickTools.getImageCommand;

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
     * @see http://www.imagemagick.org/Usage/montage/#montage
     */
    public void montage(IMMontageInfo montageInfo) throws InterruptedException, IOException, IM4JavaException {
        logger.info(" montage info: "
                + montageInfo.toString());
        // create the operation, add images and operators/options
        IMOperation op = montageInfo.getIMOperation();

        String srcImage = montageInfo.getInput();
        String dstImage = montageInfo.getOutput();
//        if(dstImage.equals(null)) {
//            int lastDot = montageInfo.getInput().lastIndexOf(".");
//            dstImage = montageInfo.getInput().substring(1, lastDot - 1) + "_montage.jpg";
//        }
        cmd.run(op,srcImage,dstImage);
    }

    /**
     * Convert product's use IM convert.
     * @see http://im4java.sourceforge.net/tools/index.html
     * @see https://imagemagick.org/script/convert.php
     */
    public String convert(IMConvertInfo convertInfo) throws InterruptedException, IOException, IM4JavaException {

        logger.info(" convert info: " + convertInfo.toString());

        // create the operation, add images and operators/options
        IMOperation op = convertInfo.getIMOperation();

        String srcImage = convertInfo.getInput();
        String dstImage = convertInfo.getOutput();
//        if(dstImage.equals(null)) {//default
//            int lastDot = convertInfo.getInput().lastIndexOf(".");
//            dstImage = convertInfo.getInput().substring(1, lastDot - 1) + "_convert.jpg";
//        }
        cmd.run(op,srcImage,dstImage);
        //
        return dstImage;
    }
    /**
     * Convert product's use IM convert.
     * @see http://www.imagemagick.org/Usage/compose/
     */
    public void composite(IMConvertInfo convertInfo) throws InterruptedException, IOException, IM4JavaException {

        logger.info(" composite info: " + convertInfo.toString());

        ImageCommand commandComposite = getImageCommand(ImageMagickTools.CommandType.convert);
        // 增加水印
        IMOperation operation = new IMOperation();
// 原圖
        String destImagePath = convertInfo.getOutput();
        operation.addImage(destImagePath);

//        String nmzxPath = prefix   "/yellow_" sumBallot(curTeam.getBallot(),rivalItem.getBallot()) ".png";
        String nmzxPath = "prefix" +   "/yellow_"+ ".png";
// 第一個圖片
        operation.addImage(nmzxPath);
// 定位
        operation.geometry(134, 134, 30, 377);
// 追加命令 -composite
        operation.addRawArgs("-composite");

// 這裡將多個數字分成單個數字，計算位置，迴圈加入 例：1234 ---  1、2、3、4
//        List<String> stringList = this.converIntegerToStringList(rivalItem.getBallot(),false);
//        Integer count = 0;
//        for (String item : stringList) {
//            Integer width = 330   (stringList.size() * 10);
//            // 增加水印
//            String nmzxNubmerPath = prefix   "/red_ballot_"   item   ".png";
//            // 數字
//            operation.addImage(nmzxNubmerPath);
//            operation.geometry(28, 36, width - (25 * count), 426);
//            // 命令
//            operation.addRawArgs("-composite");
//            count  ;
//        }

// 目標圖片
        operation.addImage(createDirectory(destImagePath));
// Window 指定路徑，Linux 不需要哦
//        commandComposite.setSearchPath(imageMagickPath);

// 一次性合成圖片
        commandComposite.run(operation);
// 返回成路徑
    }
    //TODO:Identify,Mogrify,Compare,Composite,
//    //http://www.imagemagick.org/Usage/montage/#convert
    public String watermarkWithInfo(ImageMagickInfo imageMagickInfo) throws InterruptedException, IOException, IM4JavaException {
        logger.info(" watermark info: " + imageMagickInfo.toString());
        IMOperation op = new IMOperation();
// 字型路徑
        op.font("/Users/yangboz/git/iStoryBook/MicroServices/src/main/resources/assets/font/youyuan.TTF");
// 文字方位-東南
        op.gravity("NorthWest");
// 文字編碼
        op.encoding("UTF-8");
// 文字資訊
        op.pointsize(24).fill("#FBF6F6").draw("Watermark");
//        Integer index = 24 * 3;
//        op.pointsize(38).fill("#FBF6F6").draw("text " index ",22 "   "'584'");
//        Integer index2 = index * 20 * 3;
//        op.pointsize(24).fill("#FBF6F6").draw("text " index2 ",30 "   "'位為上中國際投票的粉絲'");
// 原圖
        op.addImage(imageMagickInfo.getInput());
// 目標
        op.addImage(createDirectory(imageMagickInfo.getOutput()));
        ImageCommand cmd = getImageCommand(ImageMagickTools.CommandType.convert);
        cmd.setErrorConsumer(StandardStream.STDERR);
        cmd.setCommand("/Users/yangboz/git/iStoryBook/MicroServices/target/classes/im-convert-proxy.sh");
        cmd.run(op);
        return imageMagickInfo.getOutput();
    }

    public void watermarkWithImage(String input,String image,String output){
        // List of commands that we want to execute
        List commands = new ArrayList();
        // Executable file
        commands.add("convert");
        commands.add(input);//"/Users/yangboz/git/iStoryBook/MicroServices/target/classes/assets/input/input.png
        // Executable file parameters
        commands.add("-gravity");
        commands.add("South-East");
        commands.add("-draw");
        commands.add(image);///Users/yangboz/git/iStoryBook/MicroServices/target/classes/assets/watermark.png
        commands.add(output);///Users/yangboz/git/iStoryBook/MicroServices/target/classes/assets/output/output.png
        //
        this.processCommand(commands);
    }
//

    public void watermarkWithText(String input,String label,String output){
        // List of commands that we want to execute
        List commands = new ArrayList();
        // Executable file
        commands.add("convert");
        commands.add(input);//"/Users/yangboz/git/iStoryBook/MicroServices/target/classes/assets/input/input.png
        commands.add(" -background Khaki");
        // Executable file parameters
        commands.add(" -gravity");
        commands.add(" center");//South-East
        commands.add(" label:"+label);
        commands.add(" -append");
        commands.add(output);///Users/yangboz/git/iStoryBook/MicroServices/target/classes/assets/output/output.png

        this.processCommand(commands);
    }

    private void processCommand(List commands){
        try {
            // I also tried to use Runtime.getRuntime().exec(...), but got the same result and it doesn't wonder
            // 'cause it use ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder();
            logger.info(commands.toString());
            processBuilder.command(commands);
            Process process = processBuilder.start();

            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Check if we have an error...
            if (error.ready()) {
                // ...then print them
                String line;
                while ((line = error.readLine()) != null) {
//                    System.out.println("error: " + line);
                    logger.warning("error: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
