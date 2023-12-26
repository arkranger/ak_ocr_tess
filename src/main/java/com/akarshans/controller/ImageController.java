package com.akarshans.controller;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class ImageController {


    @GetMapping("/image")
    public String imageProcess() throws IOException {
        String pathname = "src/main/resources/images/img1.jpg";
        File image = new File(pathname);
        //File image = new File("src/main/resources/images/pimg.pdf");

        MBFImage mbfImage = ImageUtilities.readMBF(new File(pathname));
        FImage fImage = ImageUtilities.readF(new File(pathname));
        System.out.println(mbfImage.colourSpace);
        System.out.println(fImage.getContentArea());


        return null;
    }


    @GetMapping("/face")
    public int faceDetection (@RequestParam("image") String ipimg) throws IOException {

        String pathname = "src/main/resources/images/"+ipimg;
        MBFImage image = ImageUtilities.readMBF(new File(pathname));
        // A simple Haar-Cascade face detector
        FaceDetector<DetectedFace, FImage> detector = new HaarCascadeDetector();
        List<DetectedFace> faces = detector.detectFaces(Transforms.calculateIntensity(image));
//        for (DetectedFace face : faces) {
//            Rectangle bounds = face.getBounds();
//            image.drawShape(face.getBounds(), RGBColour.RED);
//            System.out.println(bounds.x + ";" + bounds.y + ";" + bounds.width + ";" + bounds.height);
//        }
        return faces.size();
    }

}
