package com.shoppingMall.utils;

import com.shoppingMall.exception.AppException;
import com.shoppingMall.exception.BizException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * 上传文件工具类（因为文件没有单独放到一个文件夹或者放到单独的服务器，所以文件上传需要上传到两个路径）
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/5/7
 */
public class UploadFileUtil {
    /**
     * 最大允许的上传文件的大小,如果配置文件不配置，默认就是2mb
     */
    public static Long acceptMaxFileSize = 2097152L;

    /**
     * 获取文件的key
     */
    public static final String FILE_KEY = "files";

    /**
     * 项目文件路径（用于再次重启服务时，不会丢失文件）
     */
    private static final String PROJECT_PATH;

    /**
     * 运行后类生成的路径（用于运行时，不用重启，也可以访问此文件）
     */
    private static final String CLASS_PATH;

    /**
     * 如果是window系统，路径会带(:),要把第一位去掉，否者会写入失败
     */
    private static final String ERROR_CODE = ":/";

    static {
        PROJECT_PATH = new File("src/main/resources/static/img").getAbsolutePath();
        String path = URLDecoder.decode(UploadFileUtil.class.getResource("/static/img").getFile(), StandardCharsets.UTF_8);
        if (path.contains(ERROR_CODE)) {
            CLASS_PATH = path.substring(1);
        } else {
            CLASS_PATH = path;
        }
    }

    /**
     * 检查文件
     *
     * @param files
     */
    public static void check(List<MultipartFile> files) {
        if (files.isEmpty()) {
            throw new BizException("上传的内容缺少文件！");
        }
        for (MultipartFile file : files) {
            if (file.getSize() > acceptMaxFileSize) {
                throw new BizException("上传的文件大小不得超过【" + (acceptMaxFileSize / 1024 / 1024) + "MB】");
            }
        }
    }

    /**
     * 上传文件
     *
     * @param files
     */
    public static void uploadFile(List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                Files.write(Path.of(PROJECT_PATH, file.getOriginalFilename()), file.getBytes());
                Files.write(Path.of(CLASS_PATH, file.getOriginalFilename()), file.getBytes());
            }
        } catch (Exception e) {
            throw new AppException("上传文件失败！");
        }
    }
}
