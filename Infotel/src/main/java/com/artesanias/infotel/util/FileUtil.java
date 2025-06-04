package com.artesanias.infotel.util;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
@Slf4j
public class FileUtil {

    public static void createDirectoryIfNotExists(String directory) {
        try {
            Path path = Paths.get(directory);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("Directory created: {}", directory);
            }
        } catch (IOException e) {
            log.error("Error creating directory: {}", directory, e);
            throw new RuntimeException("Could not create directory: " + directory, e);
        }
    }

    public static boolean isValidImageFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }

        String lowerFilename = filename.toLowerCase();
        return lowerFilename.endsWith(".jpg") ||
                lowerFilename.endsWith(".jpeg") ||
                lowerFilename.endsWith(".png") ||
                lowerFilename.endsWith(".gif") ||
                lowerFilename.endsWith(".webp");
    }

    public static boolean isValid3DModelFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }

        String lowerFilename = filename.toLowerCase();
        return lowerFilename.endsWith(".glb") ||
                lowerFilename.endsWith(".gltf") ||
                lowerFilename.endsWith(".obj") ||
                lowerFilename.endsWith(".fbx") ||
                lowerFilename.endsWith(".blend");
    }

    public static String getFileExtension(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return "";
        }

        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex > 0 ? filename.substring(lastDotIndex + 1).toLowerCase() : "";
    }

    public static long getFileSizeInMB(long sizeInBytes) {
        return sizeInBytes / (1024 * 1024);
    }
}