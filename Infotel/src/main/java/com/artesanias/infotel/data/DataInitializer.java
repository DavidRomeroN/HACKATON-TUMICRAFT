/*package com.artesanias.infotel.data;

import com.artesanias.infotel.model.ClothingItem;
import com.artesanias.infotel.repository.ClothingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClothingItemRepository clothingItemRepository;

    @Override
    public void run(String... args) throws Exception {
        if (clothingItemRepository.count() == 0) {
            initializeClothingData();
        }
    }

    private void initializeClothingData() {
        ClothingItem cardigan = ClothingItem.builder()
                .name("Cardigan de Punto Azul")
                .category("CARDIGAN")
                .color("Azul")
                .size("M")
                .brand("Fashion Brand")
                .description("Cardigan cómodo y elegante")
                .imageUrl("/images/cardigan-azul.jpg")
                .modelUrl("/models/cardigan-azul.glb")
                .textureUrl("/textures/cardigan-azul.jpg")
                .price(new BigDecimal("49.99"))
                .availableForAR(true)
                .clothingType("upper")
                .arScale(1.0)
                .anchorPoints("{\"shoulders\": [100, 150], \"chest\": [150, 200]}")
                .tags(Arrays.asList("casual", "invierno", "punto"))
                .build();

        ClothingItem poncho = ClothingItem.builder()
                .name("Poncho Tejido Beige")
                .category("PONCHO")
                .color("Beige")
                .size("One Size")
                .brand("Artisan Collection")
                .description("Poncho tradicional tejido a mano")
                .imageUrl("/images/poncho-beige.jpg")
                .modelUrl("/models/poncho-beige.glb")
                .textureUrl("/textures/poncho-beige.jpg")
                .price(new BigDecimal("79.99"))
                .availableForAR(true)
                .clothingType("upper")
                .arScale(1.2)
                .anchorPoints("{\"shoulders\": [100, 150], \"chest\": [150, 200]}")
                .tags(Arrays.asList("tradicional", "artesanal", "tejido"))
                .build();

        ClothingItem blazer = ClothingItem.builder()
                .name("Blazer Negro Formal")
                .category("BLAZER")
                .color("Negro")
                .size("L")
                .brand("Business Line")
                .description("Blazer elegante para ocasiones formales")
                .imageUrl("/images/blazer-negro.jpg")
                .modelUrl("/models/blazer-negro.glb")
                .textureUrl("/textures/blazer-negro.jpg")
                .price(new BigDecimal("129.99"))
                .availableForAR(true)
                .clothingType("upper")
                .arScale(1.0)
                .anchorPoints("{\"shoulders\": [100, 150], \"chest\": [150, 200]}")
                .tags(Arrays.asList("formal", "elegante", "trabajo"))
                .build();

        ClothingItem falda = ClothingItem.builder()
                .name("Falda Plisada Rosa")
                .category("SKIRT")
                .color("Rosa")
                .size("S")
                .brand("Young Fashion")
                .description("Falda plisada de estilo juvenil")
                .imageUrl("/images/falda-rosa.jpg")
                .modelUrl("/models/falda-rosa.glb")
                .textureUrl("/textures/falda-rosa.jpg")
                .price(new BigDecimal("34.99"))
                .availableForAR(true)
                .clothingType("lower")
                .arScale(1.0)
                .anchorPoints("{\"waist\": [150, 300], \"hips\": [150, 350]}")
                .tags(Arrays.asList("juvenil", "plisada", "casual"))
                .build();

        clothingItemRepository.saveAll(Arrays.asList(cardigan, poncho, blazer, falda));
    }
}*/