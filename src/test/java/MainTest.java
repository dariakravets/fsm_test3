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

import static net.bytebuddy.matcher.ElementMatchers.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
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
        String filePath = "E://IdeaProjects//fsm_test3//src//main//resources//fsm.txt";

        // Define expected output
        ArrayList<String> expectedOutput = new ArrayList<>(Arrays.asList("2", "2", "0" , "1 1", "0 a 1", "0 b 1",
                "1 a 1", "1 b 1"));

        // Mock the fileReader object
        when(fileReader.readFile(filePath)).thenReturn(expectedOutput);

        // Call the method being tested
        ArrayList<String> actualOutput = main.readFSMDeclaration(fileReader, filePath);

        // Verify the interaction
        verify(fileReader).readFile(filePath);

        // Assert the result
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFileReaderFailure() throws IOException {
        String filePath = "invalid/file/path"; // path to non-existent file

        // Mock the fileReader object to throw an IOException
        when(fileReader.readFile(filePath)).thenThrow(new IOException());

        // Call the method being tested and expect an IOException to be thrown
        try {
            ArrayList<String> actualOutput = main.readFSMDeclaration(fileReader, filePath);
            fail("Expected an IOException to be thrown");
        } catch (IOException e) {
            // Expected behavior, do nothing
        }
    }

    @Spy
    FileReaderImpl fileReader1;

    @Test
    public void testFileReaderEmpty() throws IOException {
        // Spy on FileReaderImpl and return an empty list for any file path
        doReturn(new ArrayList<>()).when(fileReader1).readFile(anyString());

        String filePath = "E://IdeaProjects//fsm_test3//src//main//resources//fsm.txt";

        // Call the method being tested
        ArrayList<String> actualOutput = main.readFSMDeclaration(fileReader1, filePath);

        // Verify the interaction
        verify(fileReader1).readFile(filePath);

        // Assert the result
        assertEquals(new ArrayList<>(), actualOutput);
    }

    @Test
    public void testFileReaderCalledOnce() throws IOException {
        String filePath = "E://IdeaProjects//fsm_test3//src//main//resources//fsm.txt";

        // Define expected output
        ArrayList<String> expectedOutput = new ArrayList<>(Arrays.asList("2", "2", "0" , "1 1", "0 a 1", "0 b 1",
                "1 a 1", "1 b 1"));

        // Mock the fileReader object
        when(fileReader.readFile(filePath)).thenReturn(expectedOutput);

        // Call the method being tested
        ArrayList<String> actualOutput = main.readFSMDeclaration(fileReader, filePath);

        // Verify that the method was called only once
        verify(fileReader, times(1)).readFile(filePath);
    }
}