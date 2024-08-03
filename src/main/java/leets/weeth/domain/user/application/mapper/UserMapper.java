package leets.weeth.domain.user.application.mapper;

import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.enums.Department;
import leets.weeth.domain.user.domain.entity.enums.Status;
import leets.weeth.global.common.error.exception.custom.DepartmentNotFoundException;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static leets.weeth.domain.user.application.dto.UserDTO.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "cardinals", expression = "java( java.util.List.of(dto.cardinal()) )"),
            @Mapping(target = "password", expression = "java( passwordEncoder.encode(dto.password()) )"),
            @Mapping(target = "department", expression = "java( toEnum(dto.department()) )")
    })
    User from(SignUp dto, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "department", expression = "java( toString(user.getDepartment()) )")
    Response to(User user);

    @Mappings({
            @Mapping(target = "absenceCount", ignore = true),
    })
    AdminResponse toAdminResponse(User user);

    default Department toEnum(String before) {
        return Department.to(before);
    }

    default String toString(Department department) {
        return department.getValue();
    }
}

