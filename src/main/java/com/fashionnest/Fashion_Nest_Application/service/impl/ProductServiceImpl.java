package com.fashionnest.Fashion_Nest_Application.service.impl;

import com.fashionnest.Fashion_Nest_Application.exception.ProductException;
import com.fashionnest.Fashion_Nest_Application.model.Category;
import com.fashionnest.Fashion_Nest_Application.model.Product;
import com.fashionnest.Fashion_Nest_Application.repository.CardItemRepository;
import com.fashionnest.Fashion_Nest_Application.repository.CategoryRepository;
import com.fashionnest.Fashion_Nest_Application.repository.ProductRepository;
import com.fashionnest.Fashion_Nest_Application.request.CreateProductRequest;
import com.fashionnest.Fashion_Nest_Application.service.ProductService;
import com.fashionnest.Fashion_Nest_Application.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CardItemRepository cartItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Product createProduct(CreateProductRequest req) {
        Category topLevel=categoryRepository.findByName(req.getTopLevelCategory());
        if(topLevel==null){
            Category topLevelCategory=new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel=categoryRepository.save(topLevelCategory);
        }
        Category secondLevel=categoryRepository.
                findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
        if(secondLevel == null) {

            Category secondLevelCategory=new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel= categoryRepository.save(secondLevelCategory);

        }
        Category thirdLevel=categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());
        if(thirdLevel == null) {

            Category thirdLavelCategory=new Category();
            thirdLavelCategory.setName(req.getThirdLevelCategory());
            thirdLavelCategory.setParentCategory(secondLevel);
            thirdLavelCategory.setLevel(3);

            thirdLevel=categoryRepository.save(thirdLavelCategory);

        }

        Product product=new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct=productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {

        Product product=findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product Deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {

        Product product=findProductById(productId);
        if(req.getQuantity()!=0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {

        Optional<Product> optionalProduct=productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }throw new ProductException("Product Not Fount With Id:-"+productId);

    }

    @Override
    public List<Product> findProductByCategory(String category) {

    return   productRepository.findByCategory(category);
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize);

        List<Product> products=productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);

      //for filter color
        if(!colors.isEmpty()){
            products=products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }

        //for filter stock
        if(stock!=null){
            if(stock.equals("in_stock")){
                products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        //for pagination
        int startIndex=(int) pageable.getOffset();
        int endIndex=Math.min(startIndex + pageable.getPageSize(), products.size() );

        List<Product> pageContent=products.subList(startIndex, endIndex);

        Page<Product> filteredProducts=new PageImpl<>(pageContent,pageable,products.size());

        return filteredProducts;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
