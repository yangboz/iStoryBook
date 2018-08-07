/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.models.dto;

import org.im4java.core.IMOperation;

abstract public class ImageMagickInfo {

    private String input="/Users/yangboz/git/iStoryBook/MicroServices/target/classes/assets/input/input.png";
    private String output="/Users/yangboz/git/iStoryBook/MicroServices/target/classes/assets/output/output.png";
    private int width=0;
    private int height=0;

    public IMOperation getIMOperation(){
        return new IMOperation();
    };

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageMagickInfo() {
    }

    @Override
    public String toString() {
        return "ImageMagickInfo{" +
                "input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
