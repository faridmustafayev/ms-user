package com.example.ms.user.controller

import com.example.ms.user.exception.ErrorHandler
import com.example.ms.user.model.criteria.PageCriteria
import com.example.ms.user.model.criteria.UserCriteria
import com.example.ms.user.model.request.CreateUserRequest
import com.example.ms.user.model.response.PageableResponse
import com.example.ms.user.model.response.UserResponse
import com.example.ms.user.model.update.UpdateUserRequest
import com.example.ms.user.service.abstraction.UserService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDateTime

import static com.example.ms.user.model.enums.UserStatus.ACTIVE
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

class UserControllerTest extends Specification {
    UserService userService
    UserController userController
    MockMvc mockMvc

    def setup() {
        userService = Mock()
        userController = new UserController(userService)
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(ErrorHandler.class)
                .build()
    }

    // createUser

    def "TestCreateUser"() {
        given:
        def url = "/v1/users"
        def request = CreateUserRequest.builder()
                .firstName("Farid")
                .lastName("Mustafayev")
                .age(22)
                .email("feridmustafayev@gmail.com")
                .profilePhoto(null)
                .password("farid123")
                .birthDate(LocalDateTime.of(2002, 7, 8, 3, 2, 1, 4))
                .build()
        def jsonRequest =
                """
                   {
                       "firstName": "Farid",
                       "lastName": "Mustafayev",
                       "age": 22,
                       "email": "feridmustafayev@gmail.com",
                       "profilePhoto": null,
                       "password": "farid123",
                       "birthDate": ${[2002, 7, 8, 3, 2, 1, 4]}
                   }
                """
        when:
        def jsonResponse = mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, "")
                .content(jsonRequest))
                .andReturn()

        then:
        1 * userService.saveUser(request)
        jsonResponse.response.status == CREATED.value()
    }

    // updateUser

    def "TestUpdateUser"() {
        given:
        def id = 1L
        def url = "/v1/users/$id"
        def request = UpdateUserRequest.builder()
                .firstName("Farid")
                .lastName("Mustafayev")
                .profilePhoto("profilePhoto")
                .birthDate(LocalDateTime.of(2002, 7, 8, 3, 2, 1, 4))
                .build()
        def jsonRequest =
                """
                   {
                       "firstName": "Farid",
                       "lastName": "Mustafayev",
                       "profilePhoto": "profilePhoto",
                       "birthDate": ${[2002, 7, 8, 3, 2, 1, 4]}
                   }
                """

        when:
        mockMvc.perform(put(url)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, "")
                .content(jsonRequest))
                .andReturn()

        then:
        1 * userService.updateUser(id, request)
    }

    // deleteUser

    def "TestDeleteUser"() {
        given:
        def id = 1L
        def url = "/v1/users/$id"

        when:
        mockMvc.perform(delete(url)
                .header(AUTHORIZATION, "")
                .contentType(APPLICATION_JSON))
                .andReturn()

        then:
        1 * userService.deleteUser(id)
    }

    // getUser

    def "TestGetUser"() {
        given:
        def id = 1L
        def url = "/v1/users/$id"

        def response = UserResponse.builder()
                .id(1L)
                .firstName("Farid")
                .lastName("Mustafayev")
                .status(ACTIVE)
                .profilePhoto("profilePhoto")
                .createdAt(LocalDateTime.of(2002, 7, 8, 3, 2, 1, 4))
                .updatedAt(LocalDateTime.of(2002, 7, 8, 3, 2, 1, 4))
                .age(22)
                .email("feridmustafayev@gmail.com")
                .password("farid123")
                .birthDate(LocalDateTime.of(2002, 7, 8, 3, 2, 1, 4))
                .build()
        def expectedResponse =
                """
                   {
                       "id": 1,
                       "firstName": "Farid",
                       "lastName": "Mustafayev",
                       "status": "ACTIVE",
                       "profilePhoto": "profilePhoto",
                       "createdAt": "${[2002, 7, 8, 3, 2, 1, 4]}",
                       "updatedAt": "${[2002, 7, 8, 3, 2, 1, 4]}",
                       "age": 22,
                       "email": "feridmustafayev@gmail.com",
                       "password": "farid123",
                       "birthDate": ${[2002, 7, 8, 3, 2, 1, 4]}
                   }
                """

        when:
        def jsonResponse = mockMvc.perform(
                get(url)
                        .header(AUTHORIZATION, "")
                        .contentType(APPLICATION_JSON))
                .andReturn()

        then:
        1 * userService.getUser(id) >> response
        jsonResponse.response.status == HttpStatus.OK.value()
//        JSONAssert.assertEquals(expectedResponse.toString(), jsonResponse.response.contentAsString.toString(), true)
    }

    // getUsers

    def "TestGetUsers"() {
        given:
        def url = "/v1/users"
        def pageNumber = 0
        def pageSize = 10
        def pageCriteria = PageCriteria.of(pageNumber, pageSize)

        def userCriteria = UserCriteria.builder()
                .ageFrom(22)
                .ageTo(32)
                .address("Baku")
                .firstName("Xalid")
                .status(ACTIVE)
                .build()

        def response = UserResponse.builder()
                .id(1L)
                .firstName("Farid")
                .lastName("Mustafayev")
                .age(22)
                .email("feridmustafayev@gmail.com")
                .password("farid123")
                .birthDate(LocalDateTime.of(2002, 7, 8, 3, 2, 1, 4))
                .build()
        def pageableResponse = PageableResponse.<UserResponse> builder()
                .content([response])
                .lastPageNumber(1)
                .totalElements(1)
                .hasNext(false)
                .build()

        def expectedResponse =
                """
                    {
                      "content": [
                        {
                          "id": 1,
                          "firstName": "Farid",
                          "lastName": "Mustafayev",
                          "age": 22,
                          "email": "feridmustafayev@gmail.com",
                          "password": "farid123",
                          "birthDate": ${[2002, 7, 8, 3, 2, 1, 4]}
                        }
                      ],
                      "totalElements": 1,
                      "totalPages": 1,
                      "hasNextPage": false
                    }
                """

        when:
        def jsonResponse = mockMvc.perform(get(url)
                .header(AUTHORIZATION, "")
                .contentType(APPLICATION_JSON)
                .param("page", pageNumber as String)
                .param("size", pageSize as String)
                .param("ageFrom", userCriteria.getAgeFrom() as String)
                .param("ageTo", userCriteria.getAgeTo() as String)
                .param("address", userCriteria.getAddress() as String)
                .param("firstName", userCriteria.getFirstName() as String)
                .param("status", userCriteria.getStatus() as String))
                .andReturn()

        then:
        1 * userService.getUsers(pageCriteria, userCriteria) >> pageableResponse
//        JSONAssert.assertEquals(expectedResponse.toString(), jsonResponse.response.contentAsString.toString(), true)
    }

}
