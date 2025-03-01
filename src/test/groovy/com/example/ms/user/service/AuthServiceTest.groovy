package com.example.ms.user.service

import com.example.ms.user.client.AuthClient
import com.example.ms.user.service.abstraction.AuthService
import com.example.ms.user.service.concrete.AuthServiceHandler
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

class AuthServiceTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    AuthClient authClient
    AuthService authService

    def setup() {
        authClient = Mock()
        authService = new AuthServiceHandler(authClient)
    }

    def "TestVerify"() {
        given:
        def accessToken = random.nextObject(String)

        when:
        authService.verify(accessToken)

        then:
        1 * authClient.verifyToken(accessToken)
    }

}
