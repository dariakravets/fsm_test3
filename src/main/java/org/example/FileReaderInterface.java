package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public interface FileReaderInterface {
    ArrayList<String> readFile(String path) throws IOException;
}