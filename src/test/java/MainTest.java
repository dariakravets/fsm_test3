import org.example.FileReaderImpl;
import org.example.FileReaderInterface;
import org.example.Main;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    @Mock
    FileReaderInterface fileReader;

    @InjectMocks
    Main main;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFileReaderSuccess() throws IOException {
        // Define input parameters
        String filePath = "E://IdeaProjects//fsm_test3//src//main//resources//fsm.txt";

        // Define expected output
        ArrayList<String> expectedOutput = new ArrayList<>(Arrays.asList("2", "2", "0" , "1 1", "0 a 1", "0 b 1",
                "1 a 1", "1 b 1"));

        // Mock the fileReader object
        when(fileReader.readFile(filePath)).thenReturn(expectedOutput);

        // Call the method being tested
        ArrayList<String> actualOutput = fileReader.readFile(filePath);

        // Assert the result
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFileReaderFailure() throws IOException {
        String filePath = "invalid/file/path"; // path to non-existent file
        // Mock the fileReader object to throw an IOException
        when(fileReader.readFile(filePath)).thenThrow(new IOException());

        // Call the method being tested
        try {
            ArrayList<String> actualOutput = fileReader.readFile(filePath);
            fail("Expected an IOException to be thrown");
        } catch (IOException e) {
            // expected behavior, do nothing
        }
    }

    @Spy
    FileReaderImpl fileReader1;

    @Test
    public void testFileReaderEmpty() throws IOException {
        // Here, real method of fileReader is called
        ArrayList<String> actualOutput = fileReader1.readFile("E://IdeaProjects//fsm_test3//src//main//resources//fsm.txt");

        // Here, you define behaviour of method, making it a mock method
        doReturn(new ArrayList<>(Arrays.asList())).when(fileReader1).readFile(anyString());

        // Now, fileReader.readFile("path/to/file") will return [ ]
        actualOutput = fileReader1.readFile("E://IdeaProjects//fsm_test3//src//main//resources//fsm.txt");
        assertEquals(new ArrayList<>(), actualOutput);
    }

    @Test
    public void testFileReaderCalledOnce() throws IOException {
        // Define input parameters
        String filePath = "E://IdeaProjects//fsm_test3//src//main//resources//fsm.txt";

        // Define expected output
        ArrayList<String> expectedOutput = new ArrayList<>(Arrays.asList("2", "2", "0" , "1 1", "0 a 1", "0 b 1",
                "1 a 1", "1 b 1"));

        // Mock the fileReader object
        when(fileReader.readFile(filePath)).thenReturn(expectedOutput);

        // Call the method being tested
        ArrayList<String> actualOutput = fileReader.readFile(filePath);

        // Verify that the method was called only once
        verify(fileReader, times(1)).readFile(filePath);
    }
}