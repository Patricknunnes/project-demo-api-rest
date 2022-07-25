package br.com.pines.dev.controller;


import br.com.pines.dev.model.Product;
import br.com.pines.dev.model.dto.ProductDto;
import br.com.pines.dev.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public List<ProductDto> ListProducts(@PathVariable(required = false) String name){
        if (name!=null) {
            List<Product> products = productRepository.findByName(name);
            List<ProductDto> productsDto = new ArrayList<>();
            for (Product product: products) {
                productsDto.add(product.conversor());
            }
            return productsDto;
        } else {
            List<Product> products = productRepository.findAll();
            List<ProductDto> productsDto = new ArrayList<>();
            for (Product product : products) {
                productsDto.add(product.conversor());
            }
            return productsDto;
        }
    }

    @PostMapping
    @Transactional
    public void RegistrationProduct(@RequestBody ProductDto productDto) {
        Product product = productDto.conversor();
        productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void DeleteProduct(@PathVariable Long id){
        productRepository.findById(id);
    }

}
