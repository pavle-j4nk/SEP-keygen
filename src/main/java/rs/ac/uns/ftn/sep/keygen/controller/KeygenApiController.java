package rs.ac.uns.ftn.sep.keygen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.sep.commons.crypto.dto.CertificateDto;
import rs.ac.uns.ftn.sep.commons.crypto.dto.GenerateRequest;
import rs.ac.uns.ftn.sep.commons.crypto.dto.SignRequest;
import rs.ac.uns.ftn.sep.commons.crypto.dto.SignedKeyDto;
import rs.ac.uns.ftn.sep.keygen.service.keygen.KeygenService;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KeygenApiController {
    private final KeygenService keygenService;

    @PostMapping("/sign")
    public CertificateDto sign(@RequestBody SignRequest signRequest, HttpServletRequest request) {
        signRequest.setIp(Set.of(request.getRemoteAddr()));
        return keygenService.sign(signRequest);
    }

    @PostMapping("/generate")
    public SignedKeyDto generate(@RequestBody GenerateRequest generateRequest, HttpServletRequest request) {
        generateRequest.setIp(Set.of(request.getRemoteAddr()));
        return keygenService.generate(generateRequest);
    }


}
