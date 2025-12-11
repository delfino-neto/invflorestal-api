package br.com.inv.florestal.api.service;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ExifMetadataService {

    public void addExifMetadata(
        String imagePath, 
        Double latitude, 
        Double longitude, 
        Long timestamp
    ) throws Exception {
        
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            throw new FileNotFoundException("Imagem não encontrada: " + imagePath);
        }

        TiffOutputSet outputSet = null;
        try {
            JpegImageMetadata jpegMetadata = (JpegImageMetadata) Imaging.getMetadata(imageFile);
            if (jpegMetadata != null && jpegMetadata.getExif() != null) {
                TiffOutputSet originalOutputSet = jpegMetadata.getExif().getOutputSet();
                
                if (originalOutputSet != null) {
                    outputSet = new TiffOutputSet();
                    
                    try {
                        TiffOutputDirectory rootDirectory = originalOutputSet.getRootDirectory();
                        if (rootDirectory != null) {
                            outputSet.addDirectory(rootDirectory);
                        }
                        
                        TiffOutputDirectory exifDirectory = originalOutputSet.getExifDirectory();
                        if (exifDirectory != null) {
                            outputSet.addDirectory(exifDirectory);
                        }                        
                    } catch (Exception ex) {
                        outputSet = null;
                    }
                }
            }
        } catch (Exception e) {}

        if (outputSet == null) {
            outputSet = new TiffOutputSet();
        }

        if (latitude != null && longitude != null) {
            TiffOutputDirectory gpsDirectory = outputSet.getOrCreateGPSDirectory();
            
            // Latitude
            gpsDirectory.removeField(GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
            gpsDirectory.removeField(GpsTagConstants.GPS_TAG_GPS_LATITUDE);
            gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF, 
                latitude >= 0 ? "N" : "S");
            gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LATITUDE, 
                toRationalArray(Math.abs(latitude)));
            
            // Longitude
            gpsDirectory.removeField(GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
            gpsDirectory.removeField(GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
            gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF, 
                longitude >= 0 ? "E" : "W");
            gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LONGITUDE, 
                toRationalArray(Math.abs(longitude)));
        }


        Path tempFile = Files.createTempFile("exif_", ".jpg");
        boolean success = false;
        
        try (
            FileOutputStream fos = new FileOutputStream(tempFile.toFile());
            BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            new ExifRewriter().updateExifMetadataLossless(
                Files.readAllBytes(imageFile.toPath()),
                bos,
                outputSet
            );
            success = true;
        } catch (Exception e) {
            
            TiffOutputSet cleanOutputSet = new TiffOutputSet();
            
            if (latitude != null && longitude != null) {
                TiffOutputDirectory gpsDirectory = cleanOutputSet.getOrCreateGPSDirectory();
                
                gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF, 
                    latitude >= 0 ? "N" : "S");
                gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LATITUDE, 
                    toRationalArray(Math.abs(latitude)));
                
                gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF, 
                    longitude >= 0 ? "E" : "W");
                gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LONGITUDE, 
                    toRationalArray(Math.abs(longitude)));
            }
            
            try (
                FileOutputStream fos = new FileOutputStream(tempFile.toFile());
                BufferedOutputStream bos = new BufferedOutputStream(fos)
            ) {
                new ExifRewriter().removeExifMetadata(
                    Files.readAllBytes(imageFile.toPath()),
                    bos
                );
                
                byte[] imageWithoutExif = Files.readAllBytes(tempFile);
                Files.delete(tempFile);
                
                try (
                    FileOutputStream fos2 = new FileOutputStream(tempFile.toFile());
                    BufferedOutputStream bos2 = new BufferedOutputStream(fos2)
                ) {
                    new ExifRewriter().updateExifMetadataLossless(
                        imageWithoutExif,
                        bos2,
                        cleanOutputSet
                    );
                    success = true;
                }
            }
        }
        
        if (!success) {
            throw new Exception("Falha ao adicionar GPS ao EXIF");
        }

        Files.move(tempFile, imageFile.toPath(), 
            java.nio.file.StandardCopyOption.REPLACE_EXISTING);        
    }

    private org.apache.commons.imaging.common.RationalNumber[] toRationalArray(double decimal) {
        int degrees = (int) decimal;
        double minutesDecimal = (decimal - degrees) * 60;
        int minutes = (int) minutesDecimal;
        double secondsDecimal = (minutesDecimal - minutes) * 60;
        
        int seconds = (int) (secondsDecimal * 1000000);
        
        return new org.apache.commons.imaging.common.RationalNumber[] {
            new org.apache.commons.imaging.common.RationalNumber(degrees, 1),
            new org.apache.commons.imaging.common.RationalNumber(minutes, 1),
            new org.apache.commons.imaging.common.RationalNumber(seconds, 1000000)
        };
    }
}
