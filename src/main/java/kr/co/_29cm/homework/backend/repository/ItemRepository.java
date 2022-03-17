package kr.co._29cm.homework.backend.repository;

import kr.co._29cm.homework.backend.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
}
