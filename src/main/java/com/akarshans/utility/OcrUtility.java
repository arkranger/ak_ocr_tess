package com.akarshans.utility;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Component
public class OcrUtility {

    public String tesseractOcr(Image image) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setTessVariable("tessedit_write_images", "true");
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String whiteList = "0123456789.-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/' '/:=,";
        tesseract.setTessVariable("tessedit_char_whitelist",whiteList);
        //tesseract.setTessVariable("textord_tabfind_find_tables", "1");
        tesseract.setTessVariable("textord_heavy_nr", "1");
        String result = tesseract.doOCR((BufferedImage) image);
        System.out.println(result);
        return result;
    }

}
