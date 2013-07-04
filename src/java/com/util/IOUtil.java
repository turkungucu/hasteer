/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import com.api.Jsp;
import com.constants.HasteerConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author ecolak
 */
public class IOUtil {

    public static boolean uploadFile(FormFile file, String uploadPath) throws Exception {
        return uploadFile(file,uploadPath,true,null,false,null,0,0,false,false,null,0,0,false);
    }

    public static boolean uploadFile(FormFile file, String uploadPath, String[] allowedMimeTypes) throws Exception {
        return uploadFile(file,uploadPath,true,allowedMimeTypes,false,null,0,0,false,false,null,0,0,false);
    }

    public static boolean uploadAndResizeImage(FormFile file, String uploadPath, String[] allowedMimeTypes,
             String resizePath, int resizeWidth, int resizeHeight, boolean keepWidthConstantForResize) throws Exception
    {
        return uploadFile(file,uploadPath,true,allowedMimeTypes,true,resizePath, resizeWidth,resizeHeight,keepWidthConstantForResize,
                false,null,0,0,false);
    }

    public static boolean uploadImageAndCreateThumbnail(FormFile file, String uploadPath, String[] allowedMimeTypes,
            String thumbnailPath, int thumbWidth, int thumbHeight, boolean keepWidthConstantForThumb) throws Exception
    {
        return uploadFile(file,uploadPath,true,allowedMimeTypes,false,thumbnailPath, 0,0,false,
                true,thumbnailPath,thumbWidth,thumbHeight,keepWidthConstantForThumb);
    }

    /**
     * Uploads a FormFile to the specified upload path
     * Also creates a thumbnail of a picture if requested. 
     * @param file - FormFile coming from a struts form
     * @param uploadPath - path to upload the file under. it will be created if it doesn't exist
     * @param maxSize - maximum allowed size for the uploaded file
     * @param replaceExisting - new file will replace a file with the same name if one exists
     * @param allowedMimeTypes - mime types that are allowed. if null, all mime types are allowed
     * @param resizeImage - if true, will resize image
     * @param resizeUploadPath - destination path for the resized image
     * @param resizeWidth - only makes sense if <param>resizeImage</param> is true
     * @param resizeHeight - only makes sense if <param>resizeImage</param> is true
     * @param keepWidthConstantForResize - if <param>retainAspectRatioForResize</param> is set to false and this parameter
     *                            is set to true, then this method will first try to resize image by
     *                            keeping <param>resizeWidth</param> constant
     *                            else it will try to resize it by keeping <param>resizeHeight</param> constant
     * @param createThumbnail - if true, creates a thumnbail. Only applicable to image files
     * @param thumnbnailPath - path where the thumnbail is placed under.
     *                         Only used if <param>createThumbnail</param> is set to true
     *                       - if <param>createThumbnail</param> is true and this parameter is null
     *                         then this parameter will be set to <param>uploadPath</param>
     * @param thumbWidth - width of the thumnbnail
     * @param thumbHeight - height of the thumbnail
     * @param keepWidthConstantForThumb - if <param>aspectRatio</param> is set to false and this parameter
     *                            is set to true, then this method will first try to resize thumnbail by
     *                            keeping <param>thumbWidth</param> constant
     *                            else it will try to resize it by keeping <param>thumbHeight> constant
     * @return boolean - true if upload is successful, false otherwise
     * @throws Exception
     * @throws IllegalArgumentException - if file is null or if file's mime type is not included in the allowedMimeTypes
     */
    public static boolean uploadFile(FormFile file, String uploadPath, boolean replaceExisting, String[] allowedMimeTypes,
                                     boolean resizeImage, String resizeUploadPath, int resizeWidth, int resizeHeight,
                                     boolean keepWidthConstantForResize,
                                     boolean createThumbnail, String thumbnailPath,
                                     int thumbWidth, int thumbHeight, boolean keepWidthConstantForThumb)
    throws IOException {
        if(file == null)
            throw new IllegalArgumentException("Uploaded file cannot be null");

        int maxUploadSize = ConfigUtil.getInt(Jsp.getProperty(IOUtil.class, "maxUploadSize"),HasteerConstants.DEFAULT_MAX_UPLOAD_IMG_SIZE);
        if(file.getFileSize() > maxUploadSize)
            throw new IllegalArgumentException("Maximum allowed image size is " + maxUploadSize);

        if(allowedMimeTypes != null) {
            boolean allowed = false;
            for(String mt : allowedMimeTypes) {
                if(mt.equals(file.getContentType())) {
                    allowed = true;
                    break;
                }
            }
            if(!allowed) {
                StringBuffer sb = new StringBuffer(file.getContentType()).append("is not an allowed file type. Allowed types are ");
                for(int i = 0; i<allowedMimeTypes.length; i++) {
                    String mt = allowedMimeTypes[i];
                    sb.append(mt);
                    if(i < allowedMimeTypes.length-1)
                        sb.append(",");
                }

                throw new IllegalArgumentException(sb.toString());
            }
        }

        File uploadDir = new File(uploadPath); 
        if(!uploadDir.exists()) {
            uploadDir.mkdirs();
            System.out.println(uploadPath + " created");
        }

        File fileToCreate = new File(uploadDir, file.getFileName());

        if (replaceExisting || !fileToCreate.exists()) {
            FileOutputStream fileOutStream = null;

            try {
                fileOutStream = new FileOutputStream(fileToCreate);
                fileOutStream.write(file.getFileData());
            } finally {
                if(fileOutStream != null)
                    fileOutStream.close();
            }
        }

        if(resizeImage) {
            ImageUtil.resizeImage(uploadPath + "/" + file.getFileName(), new File(resizeUploadPath),
                    file.getFileName(), resizeWidth, resizeHeight, keepWidthConstantForResize);
        }

        if(createThumbnail) {
            if(thumbnailPath == null)
                thumbnailPath = uploadPath;
            
            ImageUtil.resizeImage(uploadPath + "/" + file.getFileName(), new File(thumbnailPath), file.getFileName(),
                    thumbWidth, thumbHeight, keepWidthConstantForThumb);
        }

        return true;
    }


}
