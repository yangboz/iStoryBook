/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.utils;
import org.im4java.core.*;
import org.im4java.process.ArrayListOutputConsumer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ImageMagickTools {

    protected static Logger logger = Logger.getLogger(ImageMagickTools.class
            .getName());
    /**
     * 是否使用 GraphicsMagick
     */
    private static final boolean USE_GRAPHICS_MAGICK_PATH = true;

    /**
     * ImageMagick安装路径
     */
    private static final String IMAGE_MAGICK_PATH = "D:\\software\\ImageMagick-6.2.7-Q8";

    /**
     * GraphicsMagick 安装目录
     */
    private static final String GRAPHICS_MAGICK_PATH = "D:\\software\\GraphicsMagick-1.3.23-Q8";

    /**
     * 水印图片路径
     */
    private static String watermarkImagePath = "/Users/yangboz/git/iStoryBook/MicroServices/src/main/resources/assets/watermark.png";

    /**
     * 水印图片
     */
    private static Image watermarkImage = null;
    static {
        try {
            //watermarkImage = ImageIO.read(new URL(watermarkImagePath));
            watermarkImage = ImageIO.read(new File(watermarkImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        /**
         *
         * ImageMagick的路径
         */
//         public static String imageMagickPath = null;
//        static {
            /**
             *
             * 获取ImageMagick的路径
             */
            // Properties prop = new PropertiesFile().getPropertiesFile();
            // linux下不要设置此值，不然会报错
            // imageMagickPath = prop.getProperty("imageMagickPath");
//        }

        /**
         *
         * 根据坐标裁剪图片
         *
         * @param srcPath   要裁剪图片的路径
         * @param newPath   裁剪图片后的路径
         * @param x         起始横坐标
         * @param y         起始挫坐标
         * @param x1                结束横坐标
         * @param y1                结束挫坐标
         */

        public static void cutImage(String srcPath, String newPath, int x, int y, int x1,	int y1) throws Exception {
            int width = x1 - x;
            int height = y1 - y;
            IMOperation op = new IMOperation();
            op.addImage(srcPath);
            /**
             * width：  裁剪的宽度
             * height：裁剪的高度
             * x：          裁剪的横坐标
             * y：          裁剪的挫坐标
             */
            op.crop(width, height, x, y);
            op.addImage(newPath);
            ConvertCmd convert = new ConvertCmd();

            // linux下不要设置此值，不然会报错
            // convert.setSearchPath(imageMagickPath);

            convert.run(op);
        }

        /**
         *
         * 根据尺寸缩放图片
         * @param width             缩放后的图片宽度
         * @param height            缩放后的图片高度
         * @param srcPath           源图片路径
         * @param newPath           缩放后图片的路径
         */
        public static void cutImage(int width, int height, String srcPath,	String newPath) throws Exception {
            IMOperation op = new IMOperation();
            op.addImage(srcPath);
            op.resize(width, height);
            op.addImage(newPath);
            ConvertCmd convert = new ConvertCmd();
            // linux下不要设置此值，不然会报错
            // convert.setSearchPath(imageMagickPath);
            convert.run(op);

        }

        /**
         * 根据宽度缩放图片
         *
         * @param width            缩放后的图片宽度
         * @param srcPath          源图片路径
         * @param newPath          缩放后图片的路径
         */
        public static void cutImage(int width, String srcPath, String newPath)	throws Exception {
            IMOperation op = new IMOperation();
            op.addImage(srcPath);
            op.resize(width, null);
            op.addImage(newPath);
            ConvertCmd convert = new ConvertCmd();
            // linux下不要设置此值，不然会报错
            // convert.setSearchPath(imageMagickPath);
            convert.run(op);
        }

        /**
         * 给图片加水印
         * @param srcPath            源图片路径
         */
        public static void addImgText(String srcPath) throws Exception {
            IMOperation op = new IMOperation();
            op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8")
                    .draw("text 5,5 juziku.com");
            op.addImage();
            op.addImage();
            ConvertCmd convert = new ConvertCmd();
            // linux下不要设置此值，不然会报错
            // convert.setSearchPath(imageMagickPath);
            convert.run(op, srcPath, srcPath);
        }

//        public static void main(String[] args) throws Exception {
//            // cutImage("D:\\apple870.jpg", "D:\\apple870eee.jpg", 98, 48, 370,
//            // 320);
//            // cutImage(200,300, "/home/steven/a.jpg", "/home/steven/aa.jpg");
//            addImgText("//home//steven//a.jpg");
//        }


    /**
     * Executes the Image Magick Identify command with format of Width x Height
     *
     * @param imagePath
     *            String
     * @return String format of Image's Width x Height
     */
    public static String getImageSize(String imagePath,long companyId,long siteId) {
        imagePath = imagePath.replaceAll("//", "/");
        String response = null;

        try {
            IdentifyCmd cmd = new IdentifyCmd();
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            cmd.setOutputConsumer(output);

            IMOperation imOperation = new IMOperation();
            imOperation.format("%[fx:w]x%[fx:h]");
            imOperation.addImage(imagePath);
			/*ProcessTask p = cmd.getProcessTask(imOperation);
			p.getProcessStarter().getSearchPath();*/
//            String globalSearchPath = ImageToolUtil.getGlobalSearchPath(companyId,siteId);
//            ProcessStarter.setGlobalSearchPath(globalSearchPath);

            cmd.run(imOperation);

            ArrayList<String> cmdOutput = output.getOutput();
            response = cmdOutput.get(0);
        } catch (Exception e) {
            logger.warning(e.toString());
        }
        return response;
    }

    /**
     * 命令类型
     *
     * @author hailin0@yeah.net
     * @createDate 2016年6月5日
     *
     */
    public enum CommandType {
        convert("转换处理"), identify("图片信息"), compositecmd("图片合成");
        private String name;

        CommandType(String name) {
            this.name = name;
        }
    }

    /**
     * 获取 ImageCommand
     *
     * @param command 命令类型
     * @return
     */
    public static ImageCommand getImageCommand(CommandType command) {
        ImageCommand cmd = null;
        switch (command) {
            case convert:
                cmd = new ConvertCmd(USE_GRAPHICS_MAGICK_PATH);
                break;
            case identify:
                cmd = new IdentifyCmd(USE_GRAPHICS_MAGICK_PATH);
                break;
            case compositecmd:
                cmd = new CompositeCmd(USE_GRAPHICS_MAGICK_PATH);
                break;
        }
        if (cmd != null && System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
            cmd.setSearchPath(USE_GRAPHICS_MAGICK_PATH ? GRAPHICS_MAGICK_PATH : IMAGE_MAGICK_PATH);
        }
        return cmd;
    }

    /**
     * 获取图片信息
     *
     * @param srcImagePath 图片路径
     * @return Map {height=, filelength=, directory=, width=, filename=}
     */
    public static Map<String, Object> getImageInfo(String srcImagePath) {
        IMOperation op = new IMOperation();
        op.format("%w,%h,%d,%f,%b");
        op.addImage(srcImagePath);
        IdentifyCmd identifyCmd = (IdentifyCmd) getImageCommand(CommandType.identify);
        ArrayListOutputConsumer output = new ArrayListOutputConsumer();
        identifyCmd.setOutputConsumer(output);
        try {
            identifyCmd.run(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> cmdOutput = output.getOutput();
        if (cmdOutput.size() != 1)
            return null;
        String line = cmdOutput.get(0);
        String[] arr = line.split(",");
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("width", Integer.parseInt(arr[0]));
        info.put("height", Integer.parseInt(arr[1]));
        info.put("directory", arr[2]);
        info.put("filename", arr[3]);
        info.put("filelength", Integer.parseInt(arr[4]));
        return info;
    }

    /**
     * 文字水印
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param content 文字内容（不支持汉字）
     * @throws Exception
     */
    public static void addTextWatermark(String srcImagePath, String destImagePath, String content)
            throws Exception {
        IMOperation op = new IMOperation();
        op.font("微软雅黑");
        // 文字方位-东南
        op.gravity("southeast");
        // 文字信息
        op.pointsize(18).fill("#BCBFC8").draw("text 10,10 " + content);
        // 原图
        op.addImage(srcImagePath);
        // 目标
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 图片水印
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param dissolve 透明度（0-100）
     * @throws Exception
     */
    public static void addImgWatermark(String srcImagePath, String destImagePath, Integer dissolve)
            throws Exception {
        // 原始图片信息
        BufferedImage buffimg = ImageIO.read(new File(srcImagePath));
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();

        IMOperation op = new IMOperation();
        // 水印图片位置
        op.geometry(watermarkImage.getWidth(null), watermarkImage.getHeight(null), w
                - watermarkImage.getWidth(null) - 10, h - watermarkImage.getHeight(null) - 10);
        // 水印透明度
        op.dissolve(dissolve);
        // 水印
        op.addImage(watermarkImagePath);
        // 原图
        op.addImage(srcImagePath);
        // 目标
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.compositecmd);
        cmd.run(op);
    }

    /**
     * jdk压缩图片-质量压缩
     *
     * @param destImagePath 被压缩文件路径
     * @param quality 压缩质量比例
     * @return
     * @throws Exception
     */
    public static void jdkResize(String destImagePath, float quality) throws Exception {
        // 目标文件
        File resizedFile = new File(destImagePath);
        // 压缩
        Image targetImage = ImageIO.read(resizedFile);
        int width = targetImage.getWidth(null);
        int height = targetImage.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.drawImage(targetImage, 0, 0, width, height, null);
        g.dispose();

        String ext = getFileType(resizedFile.getName());
//        if (ext.equals("jpg") || ext.equals("jpeg")) { // 如果是jpg
//            // jpeg格式的对输出质量进行设置
//            FileOutputStream out = new FileOutputStream(resizedFile);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(image);
//            jep.setQuality(quality, false);
//            encoder.setJPEGEncodeParam(jep);
//            encoder.encode(image);
//            out.close();
//        } else {
//            ImageIO.write(image, ext, resizedFile);
//        }
        ImageIO.write(image, ext, resizedFile);
    }

    /**
     * 压缩图片
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param width 压缩后的宽
     * @param height 压缩后的高
     * @param quality 压缩质量（0-100）
     * @param needWatermark 是否加水印
     * @return
     * @throws Exception
     */
    public static void resize(String srcImagePath, String destImagePath, int width, int height,
                              Double quality, boolean needWatermark) throws Exception {
        // 按照原有形状压缩（横图、竖图）
        BufferedImage buffimg = ImageIO.read(new File(srcImagePath));
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();
        if ((w > h && width < height) || (w < h && width > height)) {
            int temp = width;
            width = height;
            height = temp;
        }
        // 是否压缩
        if (w < width || h < height) {
            // 不压缩-是否加水印
            if (needWatermark) {
                // 不压缩，加水印
                addImgWatermark(srcImagePath, destImagePath, 100);
            }
            return;
        }
        // 压缩-是否加水印
        if (needWatermark) {
            // 压缩-加水印比例
            double cropRatio = 0f;
            if ((width + 0.0) / (w + 0.0) > (height + 0.0) / (h + 0.0)) {
                cropRatio = (height + 0.0) / (h + 0.0);
            } else {
                cropRatio = (width + 0.0) / (w + 0.0);
            }
            IMOperation op = new IMOperation();
            ImageCommand cmd = getImageCommand(CommandType.compositecmd);
            op.geometry(watermarkImage.getWidth(null), watermarkImage.getHeight(null),
                    (int) (w * cropRatio) - watermarkImage.getWidth(null) - 10,
                    (int) (h * cropRatio) - watermarkImage.getHeight(null) - 10);
            op.addImage(watermarkImagePath);
            op.addImage(srcImagePath);
            op.quality(quality);
            op.resize(width, height);
            op.addImage(createDirectory(destImagePath));
            cmd.run(op);
            return;
        }

        // 压缩-不加水印
        ImageCommand cmd = getImageCommand(CommandType.convert);
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.quality(quality);
        op.resize(width, height);
        op.addImage(createDirectory(destImagePath));
        cmd.run(op);
    }

    /**
     * 去除Exif信息，可减小文件大小
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @throws Exception
     */
    public static void removeProfile(String srcImagePath, String destImagePath) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.profile("*");
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 等比缩放图片（如果width为空，则按height缩放; 如果height为空，则按width缩放）
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     * @throws Exception
     */
    public static void scaleResize(String srcImagePath, String destImagePath, Integer width,
                                   Integer height) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.sample(width, height);
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 从原图中裁剪出新图
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param x 原图左上角
     * @param y 原图左上角
     * @param width 新图片宽度
     * @param height 新图片高度
     * @throws Exception
     */
    public static void crop(String srcImagePath, String destImagePath, int x, int y, int width,
                            int height) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.crop(width, height, x, y);
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 将图片分割为若干小图
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param width 指定宽度（默认为完整宽度）
     * @param height 指定高度（默认为完整高度）
     * @return 小图路径
     * @throws Exception
     */
    public static java.util.List<String> subsection(String srcImagePath, String destImagePath, Integer width,
                                          Integer height) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.crop(width, height);
        op.addImage(createDirectory(destImagePath));

        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);

        return getSubImages(destImagePath);
    }

    /**
     * 旋转图片
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param angle 旋转的角度
     * @return
     * @throws Exception
     */
    public static void rotate(String srcImagePath, String destImagePath, Double angle)
            throws Exception {
        File sourceFile = new File(srcImagePath);
        if (!sourceFile.exists() || !sourceFile.canRead() || !sourceFile.isFile()) {
            return;
        }

        BufferedImage buffimg = ImageIO.read(sourceFile);
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();
        // 目标图片
        // if (w > h) { //如果宽度不大于高度则旋转过后图片会变大
        ImageCommand cmd = getImageCommand(CommandType.convert);
        IMOperation operation = new IMOperation();
        operation.addImage(srcImagePath);
        operation.rotate(angle);
        operation.addImage(destImagePath);
        cmd.run(operation);
        // }
    }

    /**
     * 获取图片分割后的小图路径
     *
     * @param destImagePath 目标图片路径
     * @return 小图路径
     */
    private static java.util.List<String> getSubImages(String destImagePath) {
        // 文件所在目录
        String fileDir = destImagePath.substring(0, destImagePath.lastIndexOf(File.separatorChar));
        // 文件名称
        String fileName = destImagePath
                .substring(destImagePath.lastIndexOf(File.separatorChar) + 1);
        // 文件名（无后缀）
        String n1 = fileName.substring(0, fileName.lastIndexOf("."));
        // 后缀
        String n2 = fileName.replace(n1, "");

        java.util.List<String> fileList = new ArrayList<String>();
        String path = null;
        for (int i = 0;; i++) {
            path = fileDir + File.separatorChar + n1 + "-" + i + n2;
            if (new File(path).exists())
                fileList.add(path);
            else
                break;
        }
        return fileList;
    }

    /**
     * 创建目录
     *
     * @param path
     * @return path
     */
    public static String createDirectory(String path) {
        File file = new File(path);
        if (!file.exists())
            file.getParentFile().mkdirs();
        return path;
    }

    /**
     * 通过文件名获取文件类型
     *
     * @param fileName 文件名
     */
    private static String getFileType(String fileName) {
        if (fileName == null || "".equals(fileName.trim()) || fileName.lastIndexOf(".") == -1) {
            return null;
        }
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        return type.toLowerCase();
    }
}
