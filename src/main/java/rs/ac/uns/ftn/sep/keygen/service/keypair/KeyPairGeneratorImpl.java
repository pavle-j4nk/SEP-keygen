package rs.ac.uns.ftn.sep.keygen.service.keypair;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.sep.keygen.constants.Algorithms;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

@Service
public class KeyPairGeneratorImpl implements KeyPairGenerator {
    private static final String SHA_1_PRNG = "SHA1PRNG";
    private static final String SUN = "SUN";
    private static final int KEYSIZE = 2048;

    @Override
    public KeyPair generateKeyPair() {
        java.security.KeyPairGenerator keyGen;
        SecureRandom random;
        try {
            keyGen = java.security.KeyPairGenerator.getInstance(Algorithms.RSA);
            random = SecureRandom.getInstance(SHA_1_PRNG, SUN);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new KeyGeneratorException(e);
        }

        keyGen.initialize(KEYSIZE, random);
        return keyGen.generateKeyPair();
    }

}
