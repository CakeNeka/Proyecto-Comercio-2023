package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Genera la documentación en formato html
 * 
 * @author Neka
 */
public final class HtmlWriter {

    private static final String TEMPLATE_PATH = "src/resources/DuaExportacionTemplate.html";

    /*
     * 1. Leer todo el texto de la plantilla y transformarlo en String
     * 2. Cambiar los signos [1] por el dato que les corresponda
     * 3. Guardar el resultado en output
     */
    public static void generateDocuments(List<String> data, String outputPath) throws IOException {
        File template = new File(TEMPLATE_PATH);
        File output = new File(outputPath + "documents.html");
        // 1
        String text = readAllText(template);
        
        // 2
        for (int i = 0; i < data.size(); i++) {
            text = text.replace("[" + (i + 1) + "]", data.get(i));
        }
        System.out.println(text);
        
        // 3
        WriteAllText(output, text);
    }

    private static void WriteAllText(File output, String text) throws IOException {
        output.createNewFile();
        FileWriter writer = new FileWriter(output);
        BufferedWriter bWriter = new BufferedWriter(writer);
        bWriter.write(text);
        bWriter.close();
    }

    private static String readAllText(File template) throws IOException {
        String text = "";
        FileReader fileReader = new FileReader(template);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String linea = bufferedReader.readLine();
        while (linea != null) {
            text += linea + '\n';
            System.out.println(linea);
            linea = bufferedReader.readLine();
        }
        bufferedReader.close();
        return text;
    }
    
}
