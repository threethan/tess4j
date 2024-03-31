/**
 * Copyright @ 2008 Quan Nguyen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sourceforge.tess4j.util;

import org.w3c.dom.NodeList;

import javax.imageio.IIOImage;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageIOHelper {

    public static final String JAI_IMAGE_READER_MESSAGE = "ERROR";
    static final String OUTPUT_FILE_NAME = "Tesstmp";

    /**
     * Gets pixel data of an <code>IIOImage</code> object.
     *
     * @param image an <code>IIOImage</code> object
     * @return a byte buffer of pixel data
     */
    public static ByteBuffer getImageByteBuffer(IIOImage image) {
        return getImageByteBuffer(image.getRenderedImage());
    }

    /**
     * Gets pixel data of an <code>RenderedImage</code> object.
     *
     * @param image an <code>RenderedImage</code> object
     * @return a byte buffer of pixel data
     */
    public static ByteBuffer getImageByteBuffer(RenderedImage image) {
        ColorModel cm = image.getColorModel();
        WritableRaster wr = image.getData().createCompatibleWritableRaster(image.getWidth(), image.getHeight());
        image.copyData(wr);
        BufferedImage bi = new BufferedImage(cm, wr, cm.isAlphaPremultiplied(), null);
        return convertImageData(bi);
    }

    /**
     * Converts <code>BufferedImage</code> to <code>ByteBuffer</code>.
     *
     * @param bi Input image
     * @return pixel data
     */
    public static ByteBuffer convertImageData(BufferedImage bi) {
        DataBuffer buff = bi.getRaster().getDataBuffer();
        // ClassCastException thrown if buff not instanceof DataBufferByte because raster data is not necessarily bytes.
        // Convert the original buffered image to grayscale.
        if (!(buff instanceof DataBufferByte)) {
            BufferedImage grayscaleImage = ImageHelper.convertImageToGrayscale(bi);
            buff = grayscaleImage.getRaster().getDataBuffer();
        }
        byte[] pixelData = ((DataBufferByte) buff).getData();
        //        return ByteBuffer.wrap(pixelData);
        ByteBuffer buf = ByteBuffer.allocateDirect(pixelData.length);
        buf.order(ByteOrder.nativeOrder());
        buf.put(pixelData);
        ((Buffer) buf).flip();
        return buf;
    }

    /**
     * Gets image file format.
     *
     * @param imageFile input image file
     * @return image file format
     */
    public static String getImageFileFormat(File imageFile) {
        String imageFileName = imageFile.getName();
        String imageFormat = imageFileName.substring(imageFileName.lastIndexOf('.') + 1);
        if (imageFormat.matches("(pbm|pgm|ppm)")) {
            imageFormat = "pnm";
        } else if (imageFormat.matches("(jp2|j2k|jpf|jpx|jpm)")) {
            imageFormat = "jpeg2000";
        }
        return imageFormat;
    }

    /**
     * Gets image file. Convert to multi-page TIFF if given PDF.
     *
     * @param inputFile input file (common image or PDF)
     * @return image file
     * @throws IOException
     */
    public static File getImageFile(File inputFile) throws IOException {
        return inputFile;
    }

    /**
     * Gets a list of <code>IIOImage</code> objects for a
     * <code>BufferedImage</code>.
     *
     * @param bi input image
     * @return a list of <code>IIOImage</code> objects
     */
    @Deprecated
    public static List<IIOImage> getIIOImageList(BufferedImage bi) {
        List<IIOImage> iioImageList = new ArrayList<>();
        IIOImage oimage = new IIOImage(bi, null, null);
        iioImageList.add(oimage);
        return iioImageList;
    }
    
    /**
     * Gets an <code>IIOImage</code> object for a <code>BufferedImage</code>.
     *
     * @param bi input image
     * @return an <code>IIOImage</code> object
     */
    public static IIOImage getIIOImage(BufferedImage bi) {
        return new IIOImage(bi, null, null);
    }



    /**
     * Reads image meta data.
     *
     * @param oimage input image
     * @return a map of meta data
     */
    public static Map<String, String> readImageData(IIOImage oimage) {
        Map<String, String> dict = new HashMap<>();

        IIOMetadata imageMetadata = oimage.getMetadata();
        if (imageMetadata != null) {
            IIOMetadataNode dimNode = (IIOMetadataNode) imageMetadata.getAsTree("javax_imageio_1.0");
            NodeList nodes = dimNode.getElementsByTagName("HorizontalPixelSize");
            int dpiX;
            if (nodes.getLength() > 0) {
                float dpcWidth = Float.parseFloat(nodes.item(0).getAttributes().item(0).getNodeValue());
                dpiX = (int) Math.round(25.4f / dpcWidth);
            } else {
                dpiX = Toolkit.getDefaultToolkit().getScreenResolution();
            }
            dict.put("dpiX", String.valueOf(dpiX));

            nodes = dimNode.getElementsByTagName("VerticalPixelSize");
            int dpiY;
            if (nodes.getLength() > 0) {
                float dpcHeight = Float.parseFloat(nodes.item(0).getAttributes().item(0).getNodeValue());
                dpiY = (int) Math.round(25.4f / dpcHeight);
            } else {
                dpiY = Toolkit.getDefaultToolkit().getScreenResolution();
            }
            dict.put("dpiY", String.valueOf(dpiY));
        }

        return dict;
    }
}
