package com.example.ms.user.mapper

import com.example.ms.user.dao.entity.UserEntity
import com.example.ms.user.model.criteria.PageCriteria
import com.example.ms.user.model.request.CreateUserRequest
import com.example.ms.user.model.update.UpdateUserRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static com.example.ms.user.mapper.UserMapper.USER_MAPPER
import static com.example.ms.user.model.enums.UserStatus.ACTIVE
import static com.example.ms.user.model.enums.UserStatus.IN_PROGRESS

class UserMapperTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestBuildUserEntity"() {
        given:
        def userRequest = random.nextObject(CreateUserRequest)

        when:
        def user = USER_MAPPER.buildUserEntity(userRequest);

        then:
        userRequest.firstName == user.firstName
        userRequest.lastName == user.lastName
        userRequest.age == user.age
        userRequest.email == user.email
        userRequest.profilePhoto == user.profilePhoto
        userRequest.password == user.password
        userRequest.birthDate == user.birthDate
        ACTIVE == user.status
    }

    def "TestBuildUserResponse"() {
        given:
        def user = random.nextObject(UserEntity)

        when:
        def userResponse = USER_MAPPER.buildUserResponse(user);

        then:
        user.id == userResponse.id
        user.firstName == userResponse.firstName
        user.lastName == userResponse.lastName
        user.status == userResponse.status
        user.age == userResponse.age
        user.email == userResponse.email
        user.password == userResponse.password
        user.userType == userResponse.userType
        user.birthDate == userResponse.birthDate
        user.profilePhoto == userResponse.profilePhoto
        user.createdAt == userResponse.createdAt
        user.updatedAt == userResponse.updatedAt
    }

    def "TestBuildPageableResponse"() {
        given:
        def total = 1L
        def pageCriteria = random.nextObject(PageCriteria)
        def pageable = PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        def entity = random.nextObject(UserEntity)
        def pageOfStudents = new PageImpl([entity], pageable, total)

        when:
        def pageableResponse = USER_MAPPER.buildPageableResponse(pageOfStudents);

        then:
        entity.id == pageableResponse.content[pageCriteria.getPage()].id
        entity.firstName == pageableResponse.content[pageCriteria.getPage()].firstName
        entity.lastName == pageableResponse.content[pageCriteria.getPage()].lastName
        entity.age == pageableResponse.content[pageCriteria.getPage()].age
        entity.updatedAt == pageableResponse.content[pageCriteria.getPage()].updatedAt
        entity.createdAt == pageableResponse.content[pageCriteria.getPage()].createdAt
        entity.profilePhoto == pageableResponse.content[pageCriteria.getPage()].profilePhoto
        entity.birthDate == pageableResponse.content[pageCriteria.getPage()].birthDate
        entity.userType == pageableResponse.content[pageCriteria.getPage()].userType
        ++pageCriteria.getPage() == pageableResponse.lastPageNumber
        total == pageableResponse.totalElements
        !pageableResponse.hasNext
    }

    def "TestUpdateUserFields"() {
        given:
        def user = random.nextObject(UserEntity)
        def updateUserRequest = random.nextObject(UpdateUserRequest)

        when:
        USER_MAPPER.updateUserFields(user, updateUserRequest);

        then:
        user.setProfilePhoto(updateUserRequest.getProfilePhoto())
        user.setFirstName(updateUserRequest.getFirstName())
        user.setLastName(updateUserRequest.getLastName())
        user.setBirthDate(updateUserRequest.getBirthDate())
        user.setStatus(IN_PROGRESS)
    }

}
