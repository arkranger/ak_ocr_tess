package com.akarshans.utility;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

@Component
public class ImageProcessing {

    public BufferedImage increaseContrast(BufferedImage bufferedImage) throws IOException {

        BufferedImage imageAltered = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        int contrast = 50;
        for(int i = 0; i < bufferedImage.getWidth(); i++) {
            for(int j = 0; j < bufferedImage.getHeight(); j++) {
                Color c = new Color(bufferedImage.getRGB(i, j));
                int red = c.getRed() - contrast;
                int green = c.getGreen() - contrast;
                int blue = c.getBlue() - contrast;
                if(red > 255) {
                    red = 255;
                } else if(red < 0) {
                    red = 0;
                }
                if(green > 255) {
                    green = 255;
                } else if(green < 0) {
                    green = 0;
                }
                if(blue > 255) {
                    blue = 255;
                } else if(blue < 0) {
                    blue = 0;
                }
                imageAltered.setRGB(i, j, new Color(red, green, blue).getRGB());
            }
        }
        File image = new File("src/main/resources/tempfiles/contrast.jpg");
        ImageIO.write(imageAltered, "jpg", image);
        return imageAltered;
    }

    public BufferedImage imageBinarization(BufferedImage bufferedImage) throws IOException {

        double d = bufferedImage.getRGB(bufferedImage.getTileWidth() / 2,bufferedImage.getTileHeight() / 2);
        BufferedImage outputImage = new BufferedImage(1050,1024,bufferedImage.getType());
        Graphics2D graphic = outputImage.createGraphics();
        graphic.drawImage(bufferedImage, 0, 0,1050, 1024, null);
        graphic.dispose();
        RescaleOp rescale = new RescaleOp(.75f, 3.6f, null);
        BufferedImage fopimage = rescale.filter(outputImage, null);
        ImageIO.write(fopimage, "jpg", new File("src/main/resources/tempfiles/binarization.jpg"));

        return fopimage;
    }

    public BufferedImage RGB2GrayScaled(BufferedImage bufferedImage) throws IOException {

        BufferedImage image = bufferedImage;
        int width;
        int height;
        File output = new File("src/main/resources/tempfiles/greyscaled.jpg");
            width = image.getWidth();
            height = image.getHeight();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Color c = new Color(image.getRGB(j, i));
                    int red = (int) (c.getRed() * 0.299);
                    int green = (int) (c.getGreen() * 0.587);
                    int blue = (int) (c.getBlue() * 0.114);
                    Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }
            ImageIO.write(image, "jpg", output);
        return ImageIO.read(output);

    }
}
