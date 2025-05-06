package com.hfut.mihealth.controller;

import com.hfut.mihealth.util.RsaKeyPairGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PublicKeyController {

    private final RsaKeyPairGenerator rsaKeyPairGenerator;

    public PublicKeyController(RsaKeyPairGenerator rsaKeyPairGenerator) {
        this.rsaKeyPairGenerator = rsaKeyPairGenerator;
    }

    @GetMapping("/api/public-key")
    public ResponseEntity<Map<String, String>> getPublicKey() {
        PublicKey publicKey = rsaKeyPairGenerator.getPublicKey();
        String encodedKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        Map<String, String> response = new HashMap<>();
        response.put("publicKey", encodedKey);

        return ResponseEntity.ok(response);
    }
}