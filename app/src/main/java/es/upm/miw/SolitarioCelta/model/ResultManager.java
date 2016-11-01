package es.upm.miw.SolitarioCelta.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class ResultManager {

    private static final String FILE_NAME = "resultados.txt";

    private FileOutputStream outFile;
    private FileInputStream inputFile;

    private static ResultManager resultManager;

    private Context _fileContext;

    public static ResultManager getResultManager(Context fileContext){
        if(resultManager == null)
            resultManager = new ResultManager(fileContext);

        return resultManager;

    }

    private ResultManager(Context fileContext){
        this._fileContext = fileContext;
    }

    public void saveResult(Result result){
        this.addLine2File(result.toString());
    }


    private void addLine2File(String text) {
        if(outFile == null){
            openOutFile();
        }
        try {
            outFile.write((text+"\n").getBytes());

            closeOutFile();
        } catch (IOException e) {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
    }

    private void openOutFile(){
        try {
            outFile = _fileContext.getApplicationContext().openFileOutput(FILE_NAME, MODE_APPEND);
        } catch (FileNotFoundException e) {
            Log.e("Ficheros", "Error al abrir fichero a memoria interna");
        }
    }

    private void closeOutFile(){
        try {
            outFile.close();
        } catch (IOException e) {
            Log.e("Ficheros", "Error al cerrar fichero a memoria interna");
        }
    }

    private void closeInputFile(){
        try {
            inputFile.close();
        } catch (IOException e) {
            Log.e("Ficheros", "Error al cerrar fichero a memoria interna");
        }
    }


    private void openInputFile(){
        try {
            inputFile = _fileContext.getApplicationContext().openFileInput(FILE_NAME);
        } catch (FileNotFoundException e) {
            Log.e("Ficheros", "Error al abrir fichero a memoria interna");
        }
    }

    public List<Result> getResults(){
        if(outFile == null){
            openInputFile();
        }
        InputStreamReader streamReader = new InputStreamReader(inputFile);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        List<Result> results = new ArrayList<Result>();
        try {
            while((line = reader.readLine()) != null ){
                String[] result = line.split(";");
                results.add(new Result(result[0], result[1], Integer.parseInt(result[2])));
            }
        } catch (IOException e) {
            Log.e("Ficheros", "Error al leer fichero a memoria interna");
        }

        Collections.sort(results);

        closeInputFile();

        return results;


    }

    public void resetResults() {
        try {
            outFile = _fileContext.getApplicationContext().openFileOutput(FILE_NAME, MODE_PRIVATE);
            closeOutFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
