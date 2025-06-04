package com.artesanias.infotel.model;


public enum ClothingCategory {
    // Tops
    SHIRT("Camisa"),
    T_SHIRT("Camiseta"),
    BLOUSE("Blusa"),
    SWEATER("Suéter"),
    CARDIGAN("Cardigan"),
    HOODIE("Sudadera"),
    JACKET("Chaqueta"),
    BLAZER("Blazer"),
    COAT("Abrigo"),

    // Bottoms
    PANTS("Pantalones"),
    JEANS("Jeans"),
    SHORTS("Shorts"),
    SKIRT("Falda"),
    LEGGINGS("Mallas"),

    // Dresses
    DRESS("Vestido"),
    JUMPSUIT("Mono"),

    // Outerwear
    PONCHO("Poncho"),
    CAPE("Capa"),
    VEST("Chaleco"),

    // Accessories
    HAT("Sombrero"),
    SCARF("Bufanda"),
    BELT("Cinturón"),

    // Footwear
    SHOES("Zapatos"),
    BOOTS("Botas"),
    SNEAKERS("Zapatillas"),
    SANDALS("Sandalias");

    private final String displayName;

    ClothingCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}