package com.akarshans.controller;

import com.akarshans.utility.OcrUtility;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class OcrController {

    @Autowired
    OcrUtility ocrUtility;

    @GetMapping("/default")
    public String doFileOcr() throws TesseractException {
        //File image = new File("src/main/resources/images/img1.jpg");
        File image = new File("src/main/resources/images/pimg.pdf");
        return ocrUtility.tesseractOcr(image);
    }

    @PostMapping("/file")
    public String doFileOcr(@RequestParam("file")MultipartFile file) throws TesseractException, IOException {

        System.out.println("request received");
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
        File image = new File("src/main/resources/tempfiles/targetFile."+fileExtension);
        OutputStream os = new FileOutputStream(image);
        os.write(file.getBytes());

        return ocrUtility.tesseractOcr(image);
        //return null;
    }


}
