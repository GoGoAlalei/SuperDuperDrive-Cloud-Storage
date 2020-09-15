package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> CredentialFiles(Integer UserId) {
        return credentialMapper.usercredentials(UserId);
    }

    public Integer insertCredential(Credential newcredential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(newcredential.getPassword(), encodedKey);
        newcredential.setKey(encodedKey);
        newcredential.setPassword(encryptedPassword);
        return credentialMapper.addcredential(newcredential);
    }

    public Integer updateCredential(Credential upcredential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedUpdateKey = Base64.getEncoder().encodeToString(key);
        String encryptedUpdatePassword = encryptionService.encryptValue(upcredential.getPassword(), encodedUpdateKey);
        upcredential.setKey(encodedUpdateKey);
        upcredential.setPassword(encryptedUpdatePassword);
        return credentialMapper.updatecredential(upcredential);
    }

    public Integer deleteCredential(Integer credentialId) { return credentialMapper.deletecredential(credentialId); }

    public String decryptPassword(Credential credential) {
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }



}
