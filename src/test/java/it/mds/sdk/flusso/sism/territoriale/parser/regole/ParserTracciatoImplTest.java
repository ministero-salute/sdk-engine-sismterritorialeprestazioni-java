/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.sism.territoriale.parser.regole;

import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.parser.ParserTracciato;
import it.mds.sdk.rest.exception.ParseCSVException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

class ParserTracciatoImplTest {

    private static final String FILE_TRACCIATO_SISM_TER = "tracciato-ter-test.csv";
    private ParserTracciatoImpl parserTracciato = new ParserTracciatoImpl();

    private FileReader fileReader = Mockito.mock(FileReader.class);
    private List<RecordDtoGenerico> list = Mockito.mock(List.class);
    private File file;
    private Properties prop;
    private ClassLoader classLoader;
    private MockedStatic<Files> files;
    private Reader reader = mock(Reader.class);

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        prop = loadPropertiesFromFile("config-flusso-test.properties");
        classLoader = getClass().getClassLoader();
    }

    @Test
    void validaTracciatoOK() {
        ParserTracciato parserTracciato = new ParserTracciatoImpl();
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        File tracciato = new File(absolutePath + FileSystems.getDefault().getSeparator() + FILE_TRACCIATO_SISM_TER);

        List<RecordDtoGenerico> listaRecord = parserTracciato.parseTracciato(tracciato);
        listaRecord.forEach(System.out::println);
        assertFalse(ArrayUtils.isEmpty(listaRecord.toArray()));
    }

    @Test
    void parseTracciatoTest() {
        file = new File(Objects.requireNonNull(classLoader.getResource(prop.getProperty("test.filecsv"))).getFile());
        parserTracciato.parseTracciato(file);
    }

    @Test
    void parseTracciatoTestKO() {
        file = null;
        Assertions.assertThrows(
                ParseCSVException.class,
                () -> parserTracciato.parseTracciato(file)
        );
    }

    @Test
    void parserTraciatoBloccoTestOk() {
        file = new File(Objects.requireNonNull(classLoader.getResource(prop.getProperty("test.filecsv"))).getFile());
        var list = parserTracciato.parseTracciatoBlocco(file, 0, 1);
        Assertions.assertEquals(1, list.size());
    }

    private Properties loadPropertiesFromFile(String fileName) {
        Properties prop = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(fileName);
            prop.load(stream);
            stream.close();
        } catch (Exception e) {
            String msg = String.format("Failed to load file '%s' - %s - %s", fileName, e.getClass().getName(),
                    e.getMessage());
            System.out.println(msg);
        }
        return prop;
    }


}