package com.example.my.file;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件上传工具类
 *
 * @author sunhw
 * @date 2021/5/29
 */
public class FileTypeUtil {

    private static final List<String> TYPES = new ArrayList<String>(2) {{
        add("xls");
        add("xlsx");
    }};


    /**
     * 获取文件类型
     * <p>
     * 例如: hello.txt, 返回: txt
     *
     * @param file 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(File file) {
        if (Objects.isNull(file)) {
            return StringUtils.EMPTY;
        }
        return getFileType(file.getName());
    }


    /**
     * 获取文件类型
     * <p>
     * 例如: hello.txt, 返回: txt
     *
     * @param file 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(MultipartFile file) {
        if (Objects.isNull(file)) {
            return StringUtils.EMPTY;
        }
        return getFileType(Objects.requireNonNull(file.getOriginalFilename()));
    }

    /**
     * 获取文件类型
     * <p>
     * 例如: hello.txt, 返回: txt
     *
     * @param fileName 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(String fileName) {
        int separatorIndex = fileName.lastIndexOf(".");
        if (separatorIndex < 0) {
            return StringUtils.EMPTY;
        }
        return fileName.substring(separatorIndex + 1).toLowerCase();
    }


    /**
     * 是否为允许的类型
     *
     * @param type 类型
     * @return {@link Boolean}
     */
    public static Boolean isAllowedTypes(String type) {
        if (StringUtils.isBlank(type)) {
            return false;
        }
        return TYPES.contains(type);
    }

}
