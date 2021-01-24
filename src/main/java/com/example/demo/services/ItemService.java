package com.example.demo.services;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    ItemRepository repository;

    public void saveItem(Item item){
        repository.save(item);
    }

    public List<Item> findItems(){
        return repository.findAll();
    }

    public Item findOne(Long itemId){
        return repository.findById(itemId).get();
    }
}
