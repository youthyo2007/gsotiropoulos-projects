package com.socialvideo.data.services;


import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;



@Service
public class Base64WrapperService { 

    /**
     * Perform base64 binary data to string conversion.
     * If the data passed is null, then null will be returned as a result.
     *
     * @param bytes for processing
     * @return converted binary data string
     */
    public String encodeB64Bytes(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * Perform base64 string to binary data conversion.
     * If the data passed is null, then null will be returned as a result.
     *
     * @param encodedBytes string representation for processing
     * @return converted binary data
     */

    public byte[] decodeB64Bytes(String encodedBytes) {
        byte[] result = null;
        if (encodedBytes != null) {
            result = Base64.decodeBase64(encodedBytes);
        }
        return result;
    }
}