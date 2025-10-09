package com.porfolio.portfolio.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.porfolio.portfolio.model.Cart;
import com.porfolio.portfolio.model.Paint;
import com.porfolio.portfolio.model.PaintImage;

import jakarta.servlet.http.HttpSession;

@Service
// @SessionScope // Pour garder le panier en mémoire de session (marche pas)
public class CartService {

    private static final String CART_SESSION_KEY = "shopping_cart";

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        System.out.println("Getting cart with " + cart.getItems().size() + " items");
        return cart;
    }

    public void add(int productId, String name, double price, int quantity, List<PaintImage> images,
            HttpSession session) {
        Cart cart = getCart(session);

        Paint item = new Paint();
        item.setIdPaint(productId);
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setImagesUrl(images);
        cart.addItem(item);
        System.out.println("Cart after adding: " + cart.getItems().size() + " items");
    }

    public void removeProduct(int productId, HttpSession session) {
        Cart cart = getCart(session);
        cart.removeItem(productId);
    }

    public void clearCart(HttpSession session) {
        Cart cart = getCart(session);
        cart.clear();
    }
}
