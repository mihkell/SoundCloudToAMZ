package eu.scamzs3;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public interface AccessData {

    File getFile(URI url, String fileName, String fileExtentsion) throws IOException;
}
