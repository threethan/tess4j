/*
 * Copyright @ 2008 Quan Nguyen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.tess4j.util;

import com.recognition.software.jdeskew.ImageDeskew;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageIOHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(new LoggHelper().toString());
    private static final String TEST_RESOURCES_DATA_PATH = "src/test/resources/test-data/";
    private static final String TEST_RESOURCES_RESULTS_PATH = "src/test/resources/test-results/";
    private static final double MINIMUM_DESKEW_THRESHOLD = 0.05d;

    public ImageIOHelperTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }



    /**
     * Test of getImageFileFormat method, of class ImageIOHelper.
     */
    @Test
    public void testGetImageFileFormat() {
        logger.info("getImageFileFormat");
        File imageFile = new File(TEST_RESOURCES_DATA_PATH, "eurotext.png");
        String expResult = "png";
        String result = ImageIOHelper.getImageFileFormat(imageFile);
        assertEquals(expResult, result);
    }

    /**
     * Test of getImageFile method, of class ImageIOHelper.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetImageFile() throws Exception {
        logger.info("getImageFile");
        File inputFile = new File(TEST_RESOURCES_DATA_PATH, "eurotext.png");
        File expResult = new File(TEST_RESOURCES_DATA_PATH, "eurotext.png");
        File result = ImageIOHelper.getImageFile(inputFile);
        assertEquals(expResult, result);
    }


    /**
     * Test of getIIOImageList method, of class ImageIOHelper.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetIIOImageList_BufferedImage() throws Exception {
        logger.info("getIIOImageList");
        File imageFile = new File(TEST_RESOURCES_DATA_PATH, "eurotext.png");
        BufferedImage bi = ImageIO.read(imageFile);
        IIOImage result = ImageIOHelper.getIIOImage(bi);
        assertNotNull(result);
    }


}
