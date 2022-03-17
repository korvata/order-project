package kr.co._29cm.homework.backend.controller;

import kr.co._29cm.homework.backend.model.dto.ItemResponse;
import kr.co._29cm.homework.backend.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ItemController {

    private ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> getItem(){
        return ResponseEntity.ok(itemService.getItem());
    }
}
