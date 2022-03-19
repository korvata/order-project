package kr.co._29cm.homework.backend.repository;

import kr.co._29cm.homework.backend.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, String> {
    Optional<Item> findByItemNo(long orderItemNo);
}
