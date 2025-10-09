package com.porfolio.portfolio.control;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.porfolio.portfolio.model.Paint;
import com.porfolio.portfolio.repository.PaintRepository;

@RestController

@RequestMapping("/paints")
public class PaintControl {

    @Autowired
    private PaintRepository paintRepository;

    @GetMapping("")
    public ResponseEntity<List<Paint>> getAll() {
        List<Paint> paints = (List<Paint>) paintRepository.findAll();
        if (paints.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paints, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Paint>> getAllbyCategory(@PathVariable String category) {
        try {
            Optional<List<Paint>> paintsOpt = paintRepository.findByCategory(category);
            if (paintsOpt.isPresent() && !paintsOpt.get().isEmpty()) {
                return new ResponseEntity<>(paintsOpt.get(), HttpStatus.OK);
            } else if (paintsOpt.isPresent()) {
                // Catégorie trouvée mais vide
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                // Catégorie non trouvée
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<List<String>> getCategory() {
        try {
            List<String> paintsOpt = paintRepository.findCategory();
            if (paintsOpt != null && !paintsOpt.isEmpty()) {
                return new ResponseEntity<>(paintsOpt, HttpStatus.OK);
            } else if (paintsOpt != null) {
                // Catégorie trouvée mais vide
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                // Catégorie non trouvée
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error fetching categories: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paint> getById(@PathVariable Long id) {
        try {
            Optional<Paint> paintOpt = paintRepository.findById(id);
            if (paintOpt.isPresent()) {
                return new ResponseEntity<>(paintOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/query/{query}")
    public ResponseEntity<List<Paint>> getByQuery(@PathVariable String query) {
        try {
            List<Paint> paints = paintRepository.searchByQuery(query);
            if (paints == null || paints.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(paints, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mayLike")
    public ResponseEntity<List<Paint>> getMayLike() {
        try {
            List<Paint> paints = (List<Paint>) paintRepository.findAll();
            Collections.shuffle(paints);
            paints = paints.stream().limit(4).toList();
            if (paints == null || paints.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(paints, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
