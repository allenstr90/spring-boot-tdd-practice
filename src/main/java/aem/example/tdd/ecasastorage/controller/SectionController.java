package aem.example.tdd.ecasastorage.controller;

import aem.example.tdd.ecasastorage.entity.SectionItem;
import aem.example.tdd.ecasastorage.service.SectionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/section")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectionItem> addProductToSection(@RequestBody SectionItem sectionItem) {
        return ResponseEntity.created(null).body(sectionService.addProductToSection(sectionItem));
    }
}
