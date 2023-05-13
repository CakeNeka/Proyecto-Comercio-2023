package org.cakeneka.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Genera la documentación en formato html
 * 
 * @author Neka
 */
public final class DuaGenerator implements TextFileReader{

    private static final String TEMPLATE_PATH = "src/org/cakeneka/resources/DuaExportacionTemplate.html";

    /*
     * 1. Leer todo el texto de la plantilla y transformarlo en String
     * 2. Cambiar los signos [1] por el dato que les corresponda
     * 3. Guardar el resultado en output
     */
    public void generateDocuments(List<String> data, String outputPath) throws IOException {
        File template = new File(TEMPLATE_PATH);
        File output = new File(outputPath);
        // 1
        String text = readAllText(template);
        
        // 2
        for (int i = 0; i < data.size(); i++) {
            String field = data.get(i);
            field = field.replace("<", "&lt;");
            field = field.replace(">", "&gt;");
            text = text.replace("[" + (i + 1) + "]", field);
        }
        
        // 3
        writeText(output, text);
    }
    
    /**
     * Devuelve un String con todo el texto que hay en el archivo
     * que se pasa como parámetro
     * @param file
     * @return
     * @throws IOException 
     */
    @Override
    public String readAllText(File file) throws IOException {
        String text = "";
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String linea = bufferedReader.readLine();
        while (linea != null) {
            text += linea + '\n';
            System.out.println(linea); //TODO Eliminar
            linea = bufferedReader.readLine();
        }
        bufferedReader.close();
        return text;
    }
    
    /**
     * Escribe un String en un archivo. (crea el archivo si no existe)
     * @param output
     * @param text
     * @throws IOException 
     */
    private void writeText(File output, String text) throws IOException {
        output.createNewFile();
        FileWriter writer = new FileWriter(output);
        BufferedWriter bWriter = new BufferedWriter(writer);
        bWriter.write(text);
        bWriter.close();
    }
}
