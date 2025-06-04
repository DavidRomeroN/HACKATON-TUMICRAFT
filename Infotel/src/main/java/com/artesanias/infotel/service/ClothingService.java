package com.artesanias.infotel.service;


import com.artesanias.infotel.dto.request.ClothingUploadRequest;
import com.artesanias.infotel.dto.response.ClothingResponse;
import com.artesanias.infotel.dto.response.PageResponse;
import com.artesanias.infotel.exception.ResourceNotFoundException;
import com.artesanias.infotel.model.Clothing;
import com.artesanias.infotel.model.ClothingCategory;
import com.artesanias.infotel.model.User;
import com.artesanias.infotel.repository.ClothingRepository;
import com.artesanias.infotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClothingService {

    private final ClothingRepository clothingRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public PageResponse<ClothingResponse> getAllPublicClothing(Pageable pageable) {
        Page<Clothing> clothingPage = clothingRepository.findByIsPublicTrueAndIsActiveTrue(pageable);
        return convertToPageResponse(clothingPage);
    }

    @Transactional(readOnly = true)
    public PageResponse<ClothingResponse> getClothingByCategory(ClothingCategory category, Pageable pageable) {
        Page<Clothing> clothingPage = clothingRepository.findByCategoryAndIsActiveTrue(category, pageable);
        return convertToPageResponse(clothingPage);
    }

    @Transactional(readOnly = true)
    public PageResponse<ClothingResponse> getUserClothing(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Page<Clothing> clothingPage = clothingRepository.findByUserAndIsActiveTrue(user, pageable);
        return convertToPageResponse(clothingPage);
    }

    @Transactional(readOnly = true)
    public ClothingResponse getClothingById(Long id) {
        Clothing clothing = clothingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prenda no encontrada"));

        return modelMapper.map(clothing, ClothingResponse.class);
    }

    @Transactional(readOnly = true)
    public PageResponse<ClothingResponse> searchClothing(
            String name,
            ClothingCategory category,
            Clothing.Size size,
            String color,
            Boolean isPublic,
            Pageable pageable) {

        Page<Clothing> clothingPage = clothingRepository.findClothingByFilters(
                category, size, color, name, isPublic, pageable
        );

        return convertToPageResponse(clothingPage);
    }

    @Transactional
    public ClothingResponse uploadClothing(
            ClothingUploadRequest request,
            MultipartFile imageFile,
            MultipartFile modelFile,
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Upload files
        String imageUrl = null;
        String modelUrl = null;

        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = fileUploadService.uploadFile(imageFile, "images");
        }

        if (modelFile != null && !modelFile.isEmpty()) {
            modelUrl = fileUploadService.uploadFile(modelFile, "models");
        }

        // Create clothing entity
        Clothing clothing = new Clothing();
        clothing.setName(request.getName());
        clothing.setDescription(request.getDescription());
        clothing.setCategory(request.getCategory());
        clothing.setColor(request.getColor());
        clothing.setSize(request.getSize());
        clothing.setIsPublic(request.getIsPublic());
        clothing.setPrice(request.getPrice());
        clothing.setImageUrl(imageUrl);
        clothing.setModelUrl(modelUrl);
        clothing.setUser(user);
        clothing.setIsActive(true);

        Clothing savedClothing = clothingRepository.save(clothing);

        log.info("Clothing {} uploaded by user {}", savedClothing.getName(), user.getEmail());
        return modelMapper.map(savedClothing, ClothingResponse.class);
    }

    @Transactional
    public ClothingResponse updateClothing(Long id, ClothingUploadRequest request, String userEmail) {
        Clothing clothing = clothingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prenda no encontrada"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Check if user owns the clothing
        if (!clothing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permisos para editar esta prenda");
        }

        // Update fields
        if (request.getName() != null) clothing.setName(request.getName());
        if (request.getDescription() != null) clothing.setDescription(request.getDescription());
        if (request.getCategory() != null) clothing.setCategory(request.getCategory());
        if (request.getColor() != null) clothing.setColor(request.getColor());
        if (request.getSize() != null) clothing.setSize(request.getSize());
        if (request.getIsPublic() != null) clothing.setIsPublic(request.getIsPublic());
        if (request.getPrice() != null) clothing.setPrice(request.getPrice());

        Clothing savedClothing = clothingRepository.save(clothing);
        return modelMapper.map(savedClothing, ClothingResponse.class);
    }

    @Transactional
    public void deleteClothing(Long id, String userEmail) {
        Clothing clothing = clothingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prenda no encontrada"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Check if user owns the clothing
        if (!clothing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permisos para eliminar esta prenda");
        }

        clothing.setIsActive(false);
        clothingRepository.save(clothing);

        log.info("Clothing {} deleted by user {}", clothing.getName(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public List<ClothingResponse> getSimilarClothing(Long clothingId, int limit) {
        Clothing clothing = clothingRepository.findById(clothingId)
                .orElseThrow(() -> new ResourceNotFoundException("Prenda no encontrada"));

        List<Clothing> similarClothing = clothingRepository.findSimilarClothing(
                clothing.getCategory(),
                clothing.getColor(),
                clothing.getId(),
                Pageable.ofSize(limit)
        );

        return similarClothing.stream()
                .map(c -> modelMapper.map(c, ClothingResponse.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public PageResponse<ClothingResponse> getPopularClothing(Pageable pageable) {
        Page<Object[]> popularClothingPage = clothingRepository.findPopularClothing(pageable);

        Page<Clothing> clothingPage = popularClothingPage.map(row -> (Clothing) row[0]);
        return convertToPageResponse(clothingPage);
    }

    private PageResponse<ClothingResponse> convertToPageResponse(Page<Clothing> clothingPage) {
        Page<ClothingResponse> responsePage = clothingPage.map(clothing ->
                modelMapper.map(clothing, ClothingResponse.class)
        );

        PageResponse<ClothingResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(responsePage.getContent());
        pageResponse.setPageNumber(responsePage.getNumber());
        pageResponse.setPageSize(responsePage.getSize());
        pageResponse.setTotalElements(responsePage.getTotalElements());
        pageResponse.setTotalPages(responsePage.getTotalPages());
        pageResponse.setFirst(responsePage.isFirst());
        pageResponse.setLast(responsePage.isLast());
        pageResponse.setEmpty(responsePage.isEmpty());

        return pageResponse;
    }
}