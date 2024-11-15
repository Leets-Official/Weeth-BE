package leets.weeth.domain.file.domain.repository;

import leets.weeth.domain.file.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
