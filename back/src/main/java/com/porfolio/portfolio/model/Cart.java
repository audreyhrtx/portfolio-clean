package com.porfolio.portfolio.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Paint> items = new ArrayList<>();

    public void addItem(Paint item) {
        // Vérifie si le produit existe déjà, sinon ajoute
        for (Paint i : items) {
            if (i.getIdPaint() == item.getIdPaint()) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(int productId) {
        items.removeIf(i -> i.getIdPaint() == productId);
    }

    public double getTotal() {
        return items.stream().mapToDouble(Paint::getTotalPrice).sum();
    }

    public List<Paint> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

}
