module tess4j {
    exports net.sourceforge.tess4j;
    exports net.sourceforge.tess4j.util;

    requires org.slf4j;
    requires transitive java.desktop;
    requires jai.imageio.core;
    requires com.sun.jna;
    requires lept4j;

    requires org.apache.commons.io;
    requires jboss.vfs;
}