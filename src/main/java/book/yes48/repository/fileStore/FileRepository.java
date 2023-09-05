package book.yes48.repository.fileStore;

import book.yes48.entity.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileStore, Long> {

}
