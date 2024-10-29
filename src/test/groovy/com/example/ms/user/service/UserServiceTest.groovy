package com.example.ms.user.service

import com.example.ms.user.dao.entity.UserEntity
import com.example.ms.user.dao.repository.UserRepository
import com.example.ms.user.exception.NotFoundException
import com.example.ms.user.model.criteria.PageCriteria
import com.example.ms.user.model.criteria.UserCriteria
import com.example.ms.user.model.request.CreateUserRequest
import com.example.ms.user.model.update.UpdateUserRequest
import com.example.ms.user.service.abstraction.UserService
import com.example.ms.user.service.concrete.UserServiceHandler
import com.example.ms.user.service.specification.UserSpecification
import com.example.ms.user.util.FileUtil
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import static com.example.ms.user.mapper.UserMapper.USER_MAPPER
import static com.example.ms.user.model.enums.UserStatus.DELETED
import static com.example.ms.user.model.enums.UserStatus.IN_PROGRESS

class UserServiceTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    UserRepository userRepository
    UserService userService
    MultipartFile multipartFile

    def setup() {
        userRepository = Mock()
        multipartFile = Mock()
        userService = new UserServiceHandler(userRepository)
    }

    // saveUser

    def "TestSaveUser"() {
        given:
        def request = random.nextObject(CreateUserRequest)
        def user = USER_MAPPER.buildUserEntity(request)

        when:
        userService.saveUser(request)

        then:
        1 * userRepository.save(user)
    }

    // deleteUser

    def "TestDeleteUser success case"() {
        given:
        def id = random.nextObject(Long)
        def user = random.nextObject(UserEntity)

        when:
        userService.deleteUser(id)

        then:
        1 * userRepository.findById(id) >> Optional.of(user)
        user.status == DELETED
        1 * userRepository.save(user)
    }

    def "TestDeleteUser UserNotFound case"() {
        given:
        def id = random.nextObject(Long)

        when:
        userService.deleteUser(id)

        then:
        1 * userRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.code == "USER_NOT_FOUND"
        ex.message == "User not found"
        0 * userRepository.save(_)
    }

    // updateUser

    def "TestUpdateUser success case"() {
        given:
        def id = random.nextObject(Long)
        def updateRequest = random.nextObject(UpdateUserRequest)
        def user = random.nextObject(UserEntity)

        when:
        userService.updateUser(id, updateRequest)

        then:
        1 * userRepository.findById(id) >> Optional.of(user)
        updateRequest.birthDate == user.birthDate
        updateRequest.firstName == user.firstName
        updateRequest.lastName == user.lastName
        updateRequest.profilePhoto == user.profilePhoto
        IN_PROGRESS == user.status
        1 * userRepository.save(user)
    }

    def "TestUpdateUser UserNotFound case"() {
        given:
        def id = random.nextObject(Long)
        def updateRequest = random.nextObject(UpdateUserRequest)

        when:
        userService.updateUser(id, updateRequest)

        then:
        1 * userRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.code == "USER_NOT_FOUND"
        ex.message == "User not found"
        0 * userRepository.save(_)
    }

    // getUser

    def "TestGetUser success case"() {
        given:
        def id = random.nextObject(Long)
        def user = random.nextObject(UserEntity)

        when:
        def actual = userService.getUser(id)

        then:
        1 * userRepository.findById(id) >> Optional.of(user)
        actual.id == user.id
        actual.firstName == user.firstName
        actual.lastName == user.lastName
        actual.status == user.status
        actual.age == user.age
        actual.email == user.email
        actual.password == user.password
        actual.userType == user.userType
        actual.birthDate == user.birthDate
        actual.profilePhoto == user.profilePhoto
        actual.createdAt == user.createdAt
        actual.updatedAt == user.updatedAt
    }

    def "TestGetUser UserNotFound case"() {
        given:
        def id = random.nextObject(Long)

        when:
        userService.getUser(id)

        then:
        1 * userRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.code == "USER_NOT_FOUND"
        ex.message == "User not found"
    }

    // getUsers

    def "TestGetUsers"() {
        given:
        def pageCriteria = random.nextObject(PageCriteria)
        def userCriteria = random.nextObject(UserCriteria)

        def total = 1L
        def pageable = PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        def entity = random.nextObject(UserEntity)
        def pageOfUsers = new PageImpl([entity], pageable, total)

        when:
        def actual = userService.getUsers(pageCriteria, userCriteria)

        then:
        1 * userRepository.findAll(_ as UserSpecification, pageable) >> pageOfUsers

        entity.id == actual.content[pageCriteria.getPage()].id
        entity.firstName == actual.content[pageCriteria.getPage()].firstName
        entity.lastName == actual.content[pageCriteria.getPage()].lastName
        entity.birthDate == actual.content[pageCriteria.getPage()].birthDate
        entity.profilePhoto == actual.content[pageCriteria.getPage()].profilePhoto
        entity.updatedAt == actual.content[pageCriteria.getPage()].updatedAt
        entity.createdAt == actual.content[pageCriteria.getPage()].createdAt
        entity.userType == actual.content[pageCriteria.getPage()].userType
        ++pageCriteria.getPage() == actual.lastPageNumber
        total == actual.totalElements
        !actual.hasNext
    }

    // uploadUserProfilePhoto

    def "TestUploadUserProfilePhoto success upload case"() {
        given:
        def id = random.nextObject(Long)
        String base64Image = "someBase64EncodedString"
        byte[] fileBytes = "test image content".getBytes()
        multipartFile.getBytes() >> fileBytes

        def user = random.nextObject(UserEntity)
        user.setProfilePhoto(null)

        def encoder = Base64.getEncoder()
        new FileUtil(encoder).encodeToBase64(multipartFile) >> base64Image

        when:
        userService.uploadUserProfilePhoto(id, multipartFile)

        then:
        userRepository.findById(id) >> Optional.of(user)
        1 * userRepository.save(user)
    }

}
