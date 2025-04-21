package com.hfut.mihealth.controller;

import com.hfut.mihealth.util.RsaKeyPairGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.Base64;

@RestController
public class PublicKeyController {

    private final RsaKeyPairGenerator rsaKeyPairGenerator;

    public PublicKeyController(RsaKeyPairGenerator rsaKeyPairGenerator) {
        this.rsaKeyPairGenerator = rsaKeyPairGenerator;
    }

    @GetMapping("/api/public-key")
    public String getPublicKey() {
        PublicKey publicKey = rsaKeyPairGenerator.getPublicKey();
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
}