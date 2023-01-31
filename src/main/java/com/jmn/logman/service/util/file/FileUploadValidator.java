
package com.jmn.logman.service.util.file;

/*
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;
import za.co.loc8.base.cloud.storage.GenerateImageUploadType;
import za.co.loc8.base.exception.BusinessRuleViolationException;

import java.io.IOException;
import java.util.Objects;

public class FileUploadValidator {

    public static void validate(MultipartFile file, GenerateImageUploadType type) throws IOException {
        String contentType = file.getContentType();
        assert contentType != null;
        switch (type) {
            case FEED:
            case DEALER:
                if (FileUtils.isExecutableFile(file.getInputStream()) || isValidImage(file) ||
                        (!FileUtils.isCSV(contentType) && !FileUtils.isJSON(contentType) && !FileUtils.isPlainText(contentType) && !FileUtils.isXML(contentType))) {
                    throw new BusinessRuleViolationException("System cannot complete request. Only CSV, JSON or XML files are " +
                            "allowed for the feeds.");
                }
                break;

            case DEALER_BANNER:
            case DEALER_LOGO:
            case LISTING_IMAGE:
            case ARTICLE_IMAGE:
            case PROMOTION_IMAGE:
            case USER_PROFILE:
            case BADGE_IMAGE:
            case CAR_SUB_BODY_TYPE:
                if (FileUtils.isExecutableFile(file.getInputStream()) || !isValidImage(file)) {
                    throw new BusinessRuleViolationException("System cannot complete request. Only PNG, JPG or JPEG files are " +
                            "allowed for the images.");
                }
                break;

        }
    }

    private static boolean isValidImage(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String detectedFileType = tika.detect(file.getInputStream());
        return FileUtils.isValidPNG(file.getInputStream())
                || FileUtils.isValidJPEG(file.getInputStream())
                || FileUtils.isSVG(Objects.requireNonNull(detectedFileType));
    }
}
*/
public class FileUploadValidator {
}