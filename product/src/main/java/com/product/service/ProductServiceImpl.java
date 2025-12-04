package com.product.service;

import com.product.client.InventoryClient;
import com.product.dtos.InventoryInputDTO;
import com.product.dtos.ProductRequestDTO;
import com.product.dtos.ProductResponseDTO;
import com.product.entities.Product;
import com.product.exception.CategoryNotFoundException;
import com.product.exception.UserIdNotFoundException;
import com.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;


    @Override
    public List<ProductResponseDTO> getAll() {
        List<Product> product=productRepository.findAll();
        return product.stream().map(ele->modelMapper.map(ele,ProductResponseDTO.class)).toList();
    }

    @Override
    public ProductResponseDTO getByProductId(Long id) {
        Product product=productRepository.findById(id).orElseThrow(()->new UserIdNotFoundException("Product Id Not Found"));
        return modelMapper.map(product,ProductResponseDTO.class);
    }

    @Override
    public List<ProductResponseDTO> getById(Long id) {
        List<Product> product=productRepository.findBySellerId(id).orElseThrow(()->new UserIdNotFoundException("Seller Id not found"));

        return product.stream().map(ele->modelMapper.map(ele,ProductResponseDTO.class)).toList();
    }

    @Override
    public List<ProductResponseDTO> getByCategories(String categories) {
       List<Product> product =productRepository.findByCategory(categories)
               .orElseThrow(()->new CategoryNotFoundException("Such Category is not present"));
       return product.stream().map(ele->modelMapper.map(ele, ProductResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO addProduct(Long sellerId,ProductRequestDTO dto) {
        Product product=modelMapper.map(dto,Product.class);
        product.setSellerId(sellerId);
        Product product1 =productRepository.save(product);
        InventoryInputDTO inputDTO=new InventoryInputDTO();
        inputDTO.setProductid(product1.getId());
        inputDTO.setQuantity(product1.getStock());
        inventoryClient.change(inputDTO);
        return modelMapper.map(product1,ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO updateProduct(Long sellerId, ProductRequestDTO dto,Long id) {
        List<Product> product=productRepository.findBySellerId(sellerId).orElseThrow(()->new UserIdNotFoundException("UserId not found"));
       Product product1= product.stream().filter(ele->ele.getId().equals(id))
               .map(ele->{
            ele.setName(dto.getName());
            ele.setCategory(dto.getCategory());
            ele.setStock(dto.getStock());
            ele.setDescription(dto.getDescription());
            ele.setPrice(dto.getPrice());
            ele.setImageurl(dto.getImageurl());
            return productRepository.save(ele);
        }).findFirst()
               .orElseThrow(()->new UserIdNotFoundException("Product Id Not Found"));
        return modelMapper.map(product,ProductResponseDTO.class);
    }

    @Override
    public void deleteById(Long sellerId,Long id) {
        List<Product> product=productRepository.findBySellerId(sellerId).orElseThrow(()->new UserIdNotFoundException("SellerId not found"));
        Product product1= product.stream().filter(ele->ele.getId().equals(id))
                .findFirst().orElseThrow(()->new UserIdNotFoundException("ProductId not found"));
        productRepository.deleteById(id);

    }
    

}
