package com.example.my.file;

import cn.hutool.core.lang.UUID;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author sunhw
 * @date 2021/5/31
 */
public class FileUploadUtil {


    public static String upload(String uploadPath, MultipartFile file) throws IOException {

        String fileName = extractFilename(file);

        File desc = getAbsoluteFile(uploadPath, fileName);

        file.transferTo(desc);

        return uploadPath + fileName;

    }

    private static File getAbsoluteFile(String uploadDir, String fileName) {

        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {

            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }

        }

        return desc;
    }

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {

        String extension = FileTypeUtil.getFileType(file);

        return datePath() + "/" + UUID.fastUUID() + "." + extension;

    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

}
