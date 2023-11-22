package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.entity.ColorEntity;
import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.repository.ProductRepository;
import com.cybersoft.cozastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ProductService implements ProductServiceImp {

    @Value("${root.folder}")
    private String folder;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean insertProduct(String name, MultipartFile file, double price, int quanity,
                                         int idColor, int idSize, int idCategory) throws IOException {

        String pathImage = folder+ "\\" + file.getOriginalFilename();

        Path path = Paths.get(folder);
        Path pathImageCopy = Paths.get(pathImage);

        if(!Files.exists(path)) {
            Files.createDirectory(path);
        }

        Files.copy(file.getInputStream(), Paths.get(pathImage), StandardCopyOption.REPLACE_EXISTING);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(name);
        productEntity.setImage(file.getOriginalFilename());
        productEntity.setPrice(price);
        productEntity.setQuanity(quanity);

        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setId(idColor);
        productEntity.setColor(colorEntity);

        CategoryEntity category = new CategoryEntity();
        category.setId(idCategory);


        return false;
    }

}
