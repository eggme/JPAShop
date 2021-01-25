package com.example.demo.controllers;

import com.example.demo.domain.Book;
import com.example.demo.domain.Item;
import com.example.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /** 상품 수정 폼 */
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemService.findOne(itemId);
        model.addAttribute("item", item);
        return "items/updateItemForm";
    }

    @PostMapping("/items/updateItem/{itemId}")
    @Transactional
    public String updateItem(@PathVariable("itemId") Long itemId, Book newItem){
        Item item = itemService.findOne(itemId);
        item.setName(newItem.getName());
        item.setCategories(newItem.getCategories());
        item.setPrice(newItem.getPrice());
        item.setStockQuantity(newItem.getStockQuantity());
        return "redirect:/items";
    }

    /** 상품 등록 버튼 클릭 리다이렉트 */
    @GetMapping("/items/new")
    public String createFrom(){
        return "items/createItemForm";
    }

    /** 상품 등록 */
    @PostMapping("/items/new")
    public String create(Book item){
        itemService.saveItem(item);
        return "redirect:/items";
    }

    /** 전체 상품 조회 */
    @GetMapping("/items")
    public String getItemList(Model model){
        model.addAttribute("items", itemService.findItems());
        return "/items/itemList";
    }

    /** 타임리프 css 테스트용  */
    @GetMapping("/fragments/head")
    public String test(){
        return "commonHead";
    }
}
