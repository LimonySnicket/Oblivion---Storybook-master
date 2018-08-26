package storybook.oblivion.mainframe.gameData;

import android.app.Activity;

import java.io.*;
import java.util.ArrayList;



/**
 * Created by andrew on 11/22/17.
 */

public class FileDataManager {
    public static final String DATA_FILE_NAME = "filenames.txt";
    private FileReader fileReader;
    private FileWriter fileWriter;

    private ArrayList<String> cacheFileNames;
    private File cacheDirectory;

    //private ArrayList<String> assetFileNames;
    //private Activity activity;
    //private AssetManager assets;


    public FileDataManager(File dir, Activity a) {
        cacheFileNames = new ArrayList<>();
        cacheDirectory = dir;

        populateCacheFilesNames();
    }


    //
    //   Manage Data from Cache
    //
    public void populateCacheFilesNames() {
        try {
            fileReader = new FileReader(cacheDirectory + FileDataManager.DATA_FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                cacheFileNames.add(line);
            }
        } catch(FileNotFoundException inputException) {
            try {
                fileWriter = new FileWriter(cacheDirectory + FileDataManager.DATA_FILE_NAME);

                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.close();
                populateCacheFilesNames();
            } catch (IOException outputException) {
                System.out.println("Cannot Read or Write Files");
            }
        } catch(IOException readException) {
            System.out.println("Error reading file");
        }
    }

    public void writeCacheFile(String cacheFileName, String[] text) {
        try {
            fileWriter = new FileWriter(cacheDirectory + cacheFileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(String line : text) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            if(!cacheFileNames.contains(cacheFileName)) {
                cacheFileNames.add(cacheFileName);
            }
        } catch (IOException outputException) {
            System.out.println("Cannot write file");
        }
    }

    public ArrayList<String> readCacheFile(String cacheFileName) {
        if(cacheFileNames.contains(cacheFileName)){
            ArrayList<String> text = new ArrayList<>();
            try {
                fileReader = new FileReader(cacheDirectory + cacheFileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = null;
                while((line = bufferedReader.readLine()) != null) {
                    text.add(line);
                }
            } catch(FileNotFoundException inputException) {
                System.out.println("Cannot read file");
            } catch(IOException readException) {
                System.out.println("Error reading file");
            } finally {
                return text;
            }
        } else{
            System.out.println("Doesn't include Cache File");
            return null;
        }
    }



    //
    //   Manage Data from Assets
    //
    /*public void updateAssetNames() {
    }

    public void writeAssetFile(String assetFileName, String[] text) {
        try {
            fileWriter = new FileWriter(assetFileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(String line : text) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            if(!assetFileNames.contains(assetFileName)) {
                assetFileNames.add(assetFileName);
            }
        } catch (IOException outputException) {
            System.out.println("Cannot write file");
        }
    }

    public ArrayList<String> readAssetFile(String assetFileName) {
        if(cacheFileNames.contains(assetFileName)){
            ArrayList<String> text = new ArrayList<>();
            try {
                fileReader = new FileReader(assetFileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = null;
                while((line = bufferedReader.readLine()) != null) {
                    text.add(line);
                }
            } catch(FileNotFoundException inputException) {
                System.out.println("Cannot read file");
            } catch(IOException readException) {
                System.out.println("Error reading file");
            } finally {
                return text;
            }
        } else{
            System.out.println("Doesn't include Asset File");
            return null;
        }
    }*/


    //
    //    Return Methods
    //
    public String getCacheFileName(int i) {
        if(i < cacheFileNames.size()) {
            return cacheFileNames.get(i);
        } else {
            System.out.println("Array Out of Bounds");
            return null;
        }
    }
    public void printCacheFileNames() {
        for(String name : cacheFileNames) {
            System.out.println(name);
        }
    }

    /*public String getAssetFileName(int i) {
        if(i < assetFileNames.size()) {
            return assetFileNames.get(i);
        } else {
            System.out.println("Array Out of Bounds");
            return null;
        }
    }
    public void printAssetFileNames() {
        for(String name : assetFileNames) {
            System.out.println(name);
        }
    }*/

}
