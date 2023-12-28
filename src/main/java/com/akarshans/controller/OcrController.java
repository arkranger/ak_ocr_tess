package com.akarshans.controller;

import com.akarshans.utility.ImageProcessing;
import com.akarshans.utility.OcrUtility;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
public class OcrController {

    @Autowired
    OcrUtility ocrUtility;
    @Autowired
    ImageProcessing imageProcessing;

    @GetMapping("/default")
    public String defaultOcr() throws TesseractException, IOException {
        File file = new File("src/main/resources/images/sample/color.jpg");
        BufferedImage bufferedImage = ImageIO.read(file);
        bufferedImage = imageProcessing.increaseContrast(bufferedImage);
        bufferedImage = imageProcessing.RGB2GrayScaled(bufferedImage);
        bufferedImage = imageProcessing.imageBinarization(bufferedImage);

        Raster raster = bufferedImage.getRaster();

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);

        String whiteList = "0123456789.-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/' '/:=,";
        tesseract.setTessVariable("tessedit_char_whitelist",whiteList);
        tesseract.setTessVariable("textord_heavy_nr", "1");
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setTessVariable("tessedit_write_images", "true");

        return tesseract.doOCR((BufferedImage) bufferedImage);
    }

    @GetMapping("/aadhaar")
    public String aadhaarOcr() throws TesseractException, IOException {
        File file = new File("src/main/resources/images/aadhaar/girl.png");
        BufferedImage bufferedImage = ImageIO.read(file);

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("eng+hin");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);

        //String whiteList = "0123456789.-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/' '/:=,";
        //tesseract.setTessVariable("tessedit_char_whitelist",whiteList);
        tesseract.setTessVariable("textord_heavy_nr", "1");
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setTessVariable("tessedit_write_images", "true");
        tesseract.setTessVariable("tessedit_create_tsv", "1");

        return tesseract.doOCR((BufferedImage) bufferedImage);
    }

    @GetMapping("/pan")
    public String panOcr() throws TesseractException, IOException {
        File file = new File("src/main/resources/images/pan/pan.png");
        BufferedImage bufferedImage = ImageIO.read(file);
        return ocrUtility.tesseractOcr(bufferedImage);
    }

    @GetMapping("/tax")
    public String taxOcr() throws TesseractException, IOException {
        File file = new File("src/main/resources/images/tax/1040.png");
        BufferedImage bufferedImage = ImageIO.read(file);
        return ocrUtility.tesseractOcr(bufferedImage);
    }

    @PostMapping("/file")
    public String fileOcr(@RequestParam("file")MultipartFile file) throws TesseractException, IOException {

        System.out.println("request received");
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
        File image = new File("src/main/resources/tempfiles/targetFile."+fileExtension);
        OutputStream os = new FileOutputStream(image);
        os.write(file.getBytes());

        BufferedImage bufferedImage = ImageIO.read(image);
        return ocrUtility.tesseractOcr(bufferedImage);
        //return null;
    }

}
