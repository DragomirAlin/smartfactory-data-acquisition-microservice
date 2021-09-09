package ro.dragomiralin.data.acquisition.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.dragomiralin.data.acquisition.common.Data;

import java.util.List;

@Repository
public interface DataRepository extends MongoRepository<Data, String> {
    List<Data> findAllByTopic(String topic);
//    Page<Data> findAll(Pageable pageable);
}
