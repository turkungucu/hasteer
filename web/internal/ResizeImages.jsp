<%@page import="com.util.*, com.api.*, com.constants.*" %>
<%@page import="java.io.*" %>

<%
    String cmd = request.getParameter("cmd");
    if("submit".equals(cmd)) {
        String parent = Jsp.getProductImagesUploadDirectory();
        File parentDir = new File(parent);
        int i = 0;
        if(parentDir.exists() && parentDir.isDirectory()) {
            File[] subDirs = parentDir.listFiles();
            for(File sd : subDirs) {
                if(sd.isDirectory()) {
                    File[] images = sd.listFiles();
                    for(File im : images) {
                        if(im.exists() && im.isFile()) {
                            File resizeDir = new File(Jsp.getResizedProductImagesUploadDirectory() + "/" + sd.getName());
                            ImageUtil.resizeImage(im.getAbsolutePath(), resizeDir, im.getName(),
                                    HasteerConstants.MAX_WIDTH_FOR_HOT_DEALS,
                                    HasteerConstants.MAX_HEIGHT_FOR_HOT_DEALS, true);

                            File thumbDir = new File(Jsp.getProductThumbnailsUploadDirectory() + "/" + sd.getName());
                            ImageUtil.resizeImage(im.getAbsolutePath(), thumbDir, im.getName(),
                                    HasteerConstants.MAX_WIDTH_FOR_THUMBNAIL,
                                    HasteerConstants.MAX_HEIGHT_FOR_THUMBNAIL, false);
                            i++;
                        }
                    }
                }
            }
        }

        out.println(i + " images resized under " + parent);
    }
%>

<html>
    <head></head>
    <body>
        <h1>Resize images</h1>
        <form method="GET">
            <input type="hidden" name="cmd" value="submit">
            <input type="submit" value="Resize">
        </form>
    </body>
</html>