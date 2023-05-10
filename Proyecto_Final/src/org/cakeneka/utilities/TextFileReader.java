package org.cakeneka.utilities;

import java.io.File;
import java.io.IOException;

public interface TextFileReader {
    String readAllText(File file) throws IOException;
}
