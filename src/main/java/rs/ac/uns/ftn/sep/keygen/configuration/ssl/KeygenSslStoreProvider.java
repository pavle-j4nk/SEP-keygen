package rs.ac.uns.ftn.sep.keygen.configuration.ssl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.boot.web.server.SslStoreProvider;
import rs.ac.uns.ftn.sep.commons.crypto.dto.GenerateRequest;
import rs.ac.uns.ftn.sep.commons.crypto.dto.SignedKeyDto;
import rs.ac.uns.ftn.sep.commons.crypto.utils.KeyUtils;
import rs.ac.uns.ftn.sep.keygen.constants.Algorithms;
import rs.ac.uns.ftn.sep.keygen.properties.KeygenProperties;
import rs.ac.uns.ftn.sep.keygen.service.keygen.KeygenService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class KeygenSslStoreProvider implements SslStoreProvider {
    public static final String KEY_ALIAS = "main";
    public static final char[] PASSWORD = "123456".toCharArray();
    public static final String CA = "ca";
    public static final String TRUSTSTORE_FILE = "truststore.jks";
    public static final String JKS = "JKS";

    private final KeygenService keygenService;
    private final KeygenProperties keygenProperties;

    @Override
    public KeyStore getKeyStore() throws Exception {
        String IP = InetAddress.getLocalHost().getHostAddress();

        GenerateRequest generateRequest = new GenerateRequest(null, getIps());
        SignedKeyDto generate = keygenService.generate(generateRequest, getName());

        PrivateKey privateKey = KeyUtils.decodeToPrivate(generate.getPrivateKey(), Algorithms.RSA);
        Certificate certificate = KeyUtils.decodeToCertificate(generate.getClientCertificate());
        Certificate caCertificate = KeyUtils.decodeToCertificate(generate.getCaCertificate());

        KeyStore keyStore = emptyStore();
        keyStore.setCertificateEntry(CA, caCertificate);
        keyStore.setKeyEntry(KEY_ALIAS, privateKey, PASSWORD, new Certificate[]{certificate, caCertificate});
        return keyStore;
    }

    @Override
    public KeyStore getTrustStore() throws Exception {
        KeyStore truststore = KeyStore.getInstance(JKS);
        truststore.load(new FileInputStream(new File(TRUSTSTORE_FILE)), PASSWORD);
        return truststore;
    }

    private X500Name getName() {
        return new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.CN, keygenProperties.getCommonName())
                .addRDN(BCStyle.O, keygenProperties.getOrganization())
                .addRDN(BCStyle.OU, keygenProperties.getOrganizationalUnit())
                .addRDN(BCStyle.C, keygenProperties.getCountryCode())
                .addRDN(BCStyle.UID, keygenProperties.getUid().toString())
                .build();
    }

    @SneakyThrows
    private Set<String> getIps() {
        Set<String> ips = new HashSet<>();
        ips.add(InetAddress.getLocalHost().getHostAddress());

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Set<String> interfaceAddress = networkInterface.getInterfaceAddresses().stream()
                    .filter(a -> a.getBroadcast() != null)  // IPv4 only
                    .map(InterfaceAddress::getAddress)
                    .map(InetAddress::getHostAddress)
                    .collect(Collectors.toSet());
            ips.addAll(interfaceAddress);
        }

        return ips;
    }

    private static KeyStore emptyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore ks = KeyStore.getInstance(JKS);
        ks.load(null, PASSWORD);
        return ks;
    }

}
