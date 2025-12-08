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
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class ExifMetadataService {

    private static final DateTimeFormatter EXIF_DATE_FORMAT = 
        DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");

    /**
     * Adiciona metadados GPS a uma imagem JPEG
     */
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

        System.out.println("📸 Adicionando GPS à imagem: " + imageFile.getName());
        if (latitude != null && longitude != null) {
            System.out.println("   📍 GPS: " + latitude + ", " + longitude);
        }

        // Tenta ler metadados existentes da câmera
        TiffOutputSet outputSet = null;
        try {
            JpegImageMetadata jpegMetadata = (JpegImageMetadata) Imaging.getMetadata(imageFile);
            if (jpegMetadata != null && jpegMetadata.getExif() != null) {
                TiffOutputSet originalOutputSet = jpegMetadata.getExif().getOutputSet();
                
                if (originalOutputSet != null) {
                    // Cria novo outputSet copiando apenas IFD0 (sem thumbnails)
                    outputSet = new TiffOutputSet();
                    
                    try {
                        // Copia o diretório ROOT (IFD0) - Make, Model, DateTime, Orientation, etc.
                        TiffOutputDirectory rootDirectory = originalOutputSet.getRootDirectory();
                        if (rootDirectory != null) {
                            outputSet.addDirectory(rootDirectory);
                        }
                        
                        // Copia o EXIF SubIFD - ISO, Aperture, Shutter Speed, Focal Length, etc.
                        TiffOutputDirectory exifDirectory = originalOutputSet.getExifDirectory();
                        if (exifDirectory != null) {
                            outputSet.addDirectory(exifDirectory);
                        }
                        
                        System.out.println("   ✅ Preservando EXIF completo da câmera (thumbnails excluídos)");
                    } catch (Exception ex) {
                        System.out.println("   ⚠️ Erro ao copiar EXIF: " + ex.getMessage());
                        outputSet = null;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("   ⚠️ Não foi possível ler EXIF original: " + e.getMessage());
        }

        // Se não conseguiu ler ou não existe, cria novo
        if (outputSet == null) {
            outputSet = new TiffOutputSet();
            System.out.println("   📝 Criando novo EXIF");
        }

        // Adiciona coordenadas GPS se disponíveis
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

        // Não adiciona timestamp - preserva data original da câmera

        // Escreve a imagem com EXIF atualizado
        Path tempFile = Files.createTempFile("exif_", ".jpg");
        boolean success = false;
        
        try (
            FileOutputStream fos = new FileOutputStream(tempFile.toFile());
            BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            // Tenta preservar EXIF original + adicionar GPS
            new ExifRewriter().updateExifMetadataLossless(
                Files.readAllBytes(imageFile.toPath()),
                bos,
                outputSet
            );
            success = true;
            System.out.println("   ✅ GPS adicionado preservando EXIF original da câmera");
        } catch (Exception e) {
            System.out.println("   ⚠️ Falha ao preservar EXIF original: " + e.getMessage());
            System.out.println("   🔄 Criando EXIF limpo apenas com GPS...");
            
            // Se falhar (APP1 too long), cria EXIF limpo apenas com GPS
            TiffOutputSet cleanOutputSet = new TiffOutputSet();
            
            // Re-adiciona apenas GPS
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
                // Remove EXIF existente completamente
                new ExifRewriter().removeExifMetadata(
                    Files.readAllBytes(imageFile.toPath()),
                    bos
                );
                
                // Reescreve com EXIF limpo
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
                    System.out.println("   ✅ GPS adicionado com sucesso");
                }
            }
        }
        
        if (!success) {
            throw new Exception("Falha ao adicionar GPS ao EXIF");
        }

        // Substitui arquivo original pelo arquivo com EXIF atualizado
        Files.move(tempFile, imageFile.toPath(), 
            java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("   💾 Arquivo atualizado com sucesso");
    }

    /**
     * Converte coordenada decimal para formato racional EXIF (graus, minutos, segundos)
     */
    private org.apache.commons.imaging.common.RationalNumber[] toRationalArray(double decimal) {
        int degrees = (int) decimal;
        double minutesDecimal = (decimal - degrees) * 60;
        int minutes = (int) minutesDecimal;
        double secondsDecimal = (minutesDecimal - minutes) * 60;
        
        // Multiplica por 1000000 para manter precisão
        int seconds = (int) (secondsDecimal * 1000000);
        
        return new org.apache.commons.imaging.common.RationalNumber[] {
            new org.apache.commons.imaging.common.RationalNumber(degrees, 1),
            new org.apache.commons.imaging.common.RationalNumber(minutes, 1),
            new org.apache.commons.imaging.common.RationalNumber(seconds, 1000000)
        };
    }
}
