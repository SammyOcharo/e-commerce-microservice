package com.samdev.ecommerce.product;

import com.samdev.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Integer createProduct(@Valid ProductRequest request) {

        var product = mapper.toProduct(request);

        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(@Valid List<ProductPurchaseRequest> requests) {
        List<Integer> productIds = requests
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts = repository.findAllByIdInOrderById(productIds);

        System.out.println("This is the stored products: " + storedProducts.size());

        System.out.println("2. We are on the service controller...");

        if(productIds.size() != storedProducts.size()){
            throw new ProductPurchaseException("One or more product does not exist!");
        }
        var storedRequest = requests
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts= new ArrayList<ProductPurchaseResponse>();
        System.out.println("3. We are on the service controller...");

        for(int i=0; i < storedProducts.size(); i++){
            var product = storedProducts.get(i);
            System.out.println(product.getName());
            System.out.println(product.getAvailableQuantity());


            var productRequest = storedRequest.get(i);
            System.out.println(productRequest.quantity());

            if (product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with id: " + product.getId());
            }

            var newAvailableQuantity = product.getAvailableQuantity()-productRequest.quantity();

            product.setAvailableQuantity(newAvailableQuantity);

            repository.save(product);

            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));

            System.out.println("This is the purchased product: " + purchasedProducts);


        }
        System.out.println("4. We are on the service controller...");

        System.out.println("This are the purchased goods: " + purchasedProducts);

        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not found!"));
    }

    public List<ProductResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
