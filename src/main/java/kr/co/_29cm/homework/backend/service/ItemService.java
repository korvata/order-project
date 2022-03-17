package kr.co._29cm.homework.backend.service;

import kr.co._29cm.homework.backend.model.dto.ItemResponse;
import kr.co._29cm.homework.backend.model.entity.Item;
import kr.co._29cm.homework.backend.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public List<ItemResponse> getItem() {
        List<ItemResponse> itemResponses = new ArrayList<>();
        List<Item> items = itemRepository.findAll();
        for (Item item : items) {
            ItemResponse itemResponse = ItemResponse.builder()
                    .itemNo(item.getItemNo())
                    .name(item.getName())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .build();

            itemResponses.add(itemResponse);
        }

        logger.info("item list : {}", itemResponses);
        return itemResponses;
    }
}
