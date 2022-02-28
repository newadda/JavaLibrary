package org.onecell.common.misc;

import com.google.common.io.Files;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static boolean isChild(Path parent,Path child)
    {
        Path parentNormalize = parent.normalize();
        Path childNormalize = child.normalize();

        if (childNormalize.startsWith(parentNormalize) == false) {
            return false;
        }
        return true;

    }

    /// 이미지 여부 확인
    public static boolean isImage(InputStream inputStream)
    {
        boolean result = false;
        try {
            BufferedImage read = ImageIO.read(inputStream);
            if(read == null)
            {
                result=false;
            }else
            {
                result =true;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            inputStream.reset();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return result;
    }

    public static String mimeType(String fileExtension)
    {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        String mimeType = mappings.get(fileExtension);

        if(mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return mimeType;

    }


    public static ResponseEntity toResponseEntity(String file_path) throws MalformedURLException {

        String fileExtension = Files.getFileExtension(file_path);

        String mimeType = FileUtil.mimeType(fileExtension);

        Resource resource = new UrlResource(Paths.get(file_path).toFile().toURI());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }



    public static ResponseEntity toResponseEntity(String file_path,String file_name,String mimeType) throws MalformedURLException {

        Resource resource = new UrlResource(Paths.get(file_path).toFile().toURI());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file_name + "\"")
                .body(resource);
    }

}
