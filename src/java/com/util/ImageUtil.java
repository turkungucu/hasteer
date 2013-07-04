/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.api.Pair;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author ecolak
 */
public class ImageUtil {

    /**
     * Method taken from
     * http://www.loria.fr/~szathmar/pmwiki/index.php?n=Java.20090212ImageResizing
     */
    public static void resizeImage(File originalFile, File resizedFile, int newWidth, int newHeight, float quality) throws IOException {

        if (quality < 0 || quality > 1) {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

        resizedImage = i.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),
                                                        BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor, 1-(softenFactor*4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);

        // Encodes image as a JPEG data stream
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
        param.setQuality(quality, true);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);

        out.close();
    }

    /**
     * Implementation taken from
     * http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     *    scaling technique that provides higher quality than the usual
     *    one-step technique (only useful in downscaling cases, where
     *    {@code targetWidth} or {@code targetHeight} is
     *    smaller than the original dimensions, and generally only when
     *    the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@code BufferedImage}
     */
    public static BufferedImage getScaledInstance(BufferedImage img,
                                           int targetWidth,
                                           int targetHeight,
                                           Object hint,
                                           boolean higherQuality)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
            BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

    /**
     * @param oWidth - original width
     * @param oHeight - original height
     * @param height - desired resized height
     * @param maxWidth - maximum allowed width. if resized width is greater than this parameter,
     *                   resized height will be shrunk until maxWidth is reached by keeping the aspect ratio
     * @return - Pair of Integer and Integer. First is the resized width, Second is the resized height
     */
    public static Pair<Integer, Integer> getResizedDimensionsForConstantHeight(int oWidth, int oHeight, int height, int maxWidth) {
        Pair<Integer, Integer> result = new Pair<Integer, Integer>();
        double finalWidth;
        double finalHeight;
        double ratio = (double)height / (double)oHeight;
        finalHeight = height;
        finalWidth = new Double(oWidth * ratio).intValue();

        if(finalWidth > maxWidth) {
            double maxRatio = (double)maxWidth / (double)finalWidth;
            finalWidth = maxWidth;
            finalHeight = new Double(finalHeight * maxRatio).intValue();
        }

        result.setFirst((int)finalWidth);
        result.setSecond((int)finalHeight);
        return result;
    }

    public static Pair<Integer, Integer> getResizedDimensionsForConstantWidth(int oWidth, int oHeight, int width, int maxHeight) {
        Pair<Integer, Integer> result = new Pair<Integer, Integer>();
        double finalWidth;
        double finalHeight;

        double ratio = (double)width / (double)oWidth;
        finalWidth = width;
        finalHeight = new Double(oHeight * ratio).intValue();

        if(finalHeight > maxHeight) {
            double maxRatio = (double)maxHeight / (double)finalHeight;
            finalHeight = maxHeight;
            finalWidth = new Double(finalWidth * maxRatio).intValue();
        }

        result.setFirst((int)finalWidth);
        result.setSecond((int)finalHeight);
        return result;
    }

    public Pair<Integer, Integer> getResizedDimensions(int oWidth, int oHeight, int maxWidth, int maxHeight){
        Pair<Integer, Integer> result = new Pair<Integer, Integer>();
        double ratio = (double)oWidth / (double)oHeight;
        if(ratio < 1)
            result = getResizedDimensionsForConstantHeight(oWidth, oHeight, maxHeight, maxWidth);
        else
            result = getResizedDimensionsForConstantWidth(oWidth, oHeight, maxWidth, maxHeight);

        return result;
    }

    public static void resizeImage(String originalImagePath, File resizeDir, String resizeImageName,
            int resizeWidth, int resizeHeight, boolean keepWidthConstantForResize) throws IOException
    {
        File oFile = new File(originalImagePath);
        ImageIcon icon = new ImageIcon(originalImagePath);
        if(oFile == null || icon == null) {
            throw new IllegalArgumentException("Original image does not exist");
        }
        
        if(!resizeDir.exists())
            resizeDir.mkdirs();
        
        int finalWidth;
        int finalHeight;
        if(keepWidthConstantForResize) {
            Pair<Integer, Integer> finalDims = ImageUtil.getResizedDimensionsForConstantWidth(
                    icon.getIconWidth(), icon.getIconHeight(), resizeWidth, resizeHeight);
            finalWidth = finalDims.getFirst();
            finalHeight = finalDims.getSecond();
        } else {
            Pair<Integer, Integer> finalDims = ImageUtil.getResizedDimensionsForConstantHeight(
                    icon.getIconWidth(), icon.getIconHeight(), resizeHeight, resizeWidth);
            finalWidth = finalDims.getFirst();
            finalHeight = finalDims.getSecond();
        }
        
        //BufferedImage originalImage = ImageIO.read(oFile);
        //BufferedImage resizedImage = getScaledInstance(originalImage, finalWidth, finalHeight,
          //      RenderingHints.VALUE_INTERPOLATION_BICUBIC,true);
	//BufferedImage resizedImage = (BufferedImage)originalImage.getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
        //ImageIO.write(resizedImage, "png", new File(resizeDir, resizeImageName));

        resizeImage(oFile, new File(resizeDir, resizeImageName), finalWidth, finalHeight, 1.0f);

    }

}
