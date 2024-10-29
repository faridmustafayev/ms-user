package com.example.ms.user.util


import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class FileUtilTest extends Specification {
    Base64.Encoder encoder = Mock()

    def "TestEncodeToBase64 success case"() {
        given:
        def multipartFile = Mock(MultipartFile)
        def fileUtil = new FileUtil(encoder)
        byte[] fileBytes = "test content".bytes
        multipartFile.getBytes() >> fileBytes

        when:
        def result = fileUtil.encodeToBase64(multipartFile)

        then:
        1 * encoder.encodeToString(fileBytes) >> "dGVzdCBjb250ZW50"
        result == "dGVzdCBjb250ZW50"
    }
}
