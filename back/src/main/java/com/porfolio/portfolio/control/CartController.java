package com.porfolio.portfolio.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.porfolio.portfolio.dto.AddToCartRequest;
import com.porfolio.portfolio.model.Cart;
import com.porfolio.portfolio.model.Paint;
import com.porfolio.portfolio.repository.PaintRepository;
import com.porfolio.portfolio.service.CartService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cart")

public class CartController {

    private final CartService cartService;

    @Autowired
    private PaintRepository paintRepository;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public Cart getCart(HttpSession session) {
        return cartService.getCart(session);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable int productId, HttpSession session) {
        cartService.removeProduct(productId, session);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(HttpSession session) {
        cartService.clearCart(session);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @PostMapping("/add")
    public ResponseEntity<Paint> addProduct(@RequestBody AddToCartRequest request, HttpSession session) {
        Optional<Paint> paintOpt = paintRepository.findById(request.getProductId());
        if (paintOpt.isEmpty()) {
            System.out.println("Paint not found with ID: " + request.getProductId());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Paint paint = paintOpt.get();
        System.out.println("Found paint: " + paint.getName());

        cartService.add(
                paint.getIdPaint(),
                paint.getName(),
                paint.getPrice(),
                request.getQuantity(),
                paint.getImagesUrl(), session);

        return new ResponseEntity<>(paint, HttpStatus.ACCEPTED);
    }

}
