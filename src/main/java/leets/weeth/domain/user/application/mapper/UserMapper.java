package leets.weeth.domain.user.application.mapper;

import leets.weeth.domain.user.application.dto.response.UserResponseDto.*;
import leets.weeth.domain.user.application.dto.response.UserResponseDto;
import leets.weeth.domain.user.application.dto.response.UserResponseDto.SummaryResponse;
import leets.weeth.domain.user.application.dto.response.UserResponseDto.UserResponse;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.enums.Department;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.Register;
import static leets.weeth.domain.user.application.dto.request.UserRequestDto.SignUp;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "cardinals", expression = "java( java.util.List.of(dto.cardinal()) )"),
            @Mapping(target = "password", expression = "java( passwordEncoder.encode(dto.password()) )"),
            @Mapping(target = "department", expression = "java( leets.weeth.domain.user.domain.entity.enums.Department.to(dto.department()) )")
    })
    User from(SignUp dto, @Context PasswordEncoder passwordEncoder);

    @Mappings({
            @Mapping(target = "cardinals", expression = "java( java.util.List.of(dto.cardinal()) )"),
            @Mapping(target = "department", expression = "java( leets.weeth.domain.user.domain.entity.enums.Department.to(dto.department()) )")
    })
    User from(Register dto);

    @Mapping(target = "department", expression = "java( toString(user.getDepartment()) )")
    Response to(User user);

    @Mappings({
            // 수정: 출석률, 출석 횟수, 결석 횟수 매핑 추후 추가 예정
    })
    AdminResponse toAdminResponse(User user);

    SummaryResponse toSummaryResponse(User user);

    SocialAuthResponse toSocialAuthResponse(Long kakaoId);

    @Mappings({
            @Mapping(target = "status", expression = "java(LoginStatus.LOGIN)"),
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "kakaoId", source = "user.kakaoId"),
    })
    SocialLoginResponse toLoginResponse(User user, JwtDto dto);

    @Mappings({
            @Mapping(target = "status", expression = "java(LoginStatus.INTEGRATE)"),
    })
    SocialLoginResponse toIntegrateResponse(Long kakaoId);

    @Mappings({
            // 상세 데이터 매핑
    })
    UserResponse toUserResponse(User user);

    UserResponseDto.UserInfo toUserInfoDto(User user);

    default String toString(Department department) {
        return department.getValue();
    }
}

