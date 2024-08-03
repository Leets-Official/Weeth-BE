package leets.weeth.domain.board.application.mapper;

import leets.weeth.domain.board.application.dto.BoardDTO;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Post from(PostDTO.Save dto, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "user", source = "user")
    Post update(Long postId, PostDTO.Update dto, User user);

    BoardDTO.Response to(Post post);

}
