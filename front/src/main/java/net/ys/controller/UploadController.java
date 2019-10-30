package net.ys.controller;

import net.ys.component.SysConfig;
import net.ys.constant.GenResult;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

/**
 * 文件上传
 */
@Controller
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(required = true) MultipartFile file) {
        try {
            File desPath = new File(SysConfig.attachmentPath);
            if (!desPath.exists()) {
                desPath.mkdirs();
            }
            String fileName = file.getOriginalFilename();
            String targetFile = SysConfig.attachmentPath + fileName;

            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(targetFile));

            return GenResult.SUCCESS.genResult();
        } catch (Exception e) {
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }
}
