package rs.ac.uns.ftn.sep.keygen.utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import rs.ac.uns.ftn.sep.commons.crypto.utils.KeyUtils;
import rs.ac.uns.ftn.sep.keygen.Resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class KeyUtilsStripTest {

    @Test
    void certificateStrip() throws IOException {
        byte[] decoded = loadAndDecode(Resources.certificate);
        assertNotEquals(decoded.length, 0);
    }

    @Test
    void publicKeyStrip() throws IOException {
        byte[] decoded = loadAndDecode(Resources.examplePublicKey);
        assertNotEquals(decoded.length, 0);
    }

    @Test
    void privateKeyStrip() throws IOException {
        byte[] decoded = loadAndDecode(Resources.privateKey);
        assertNotEquals(decoded.length, 0);
    }

    private byte[] loadAndDecode(String resource) throws IOException {
        String raw = load(resource);
        String stripped = KeyUtils.strip(raw);
        return Base64.getDecoder().decode(stripped);
    }

    private String load(String resource) throws IOException {
        File file = ResourceUtils.getFile(resource);
        String content = Files.readString(file.toPath());
        return content;
    }

}
