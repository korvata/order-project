package kr.co._29cm.homework.backend.controller;

import kr.co._29cm.homework.backend.model.dto.ItemResponseDto;
import kr.co._29cm.homework.backend.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponseDto>> getItem(){
        return ResponseEntity.ok(itemService.getItem());
    }
}
