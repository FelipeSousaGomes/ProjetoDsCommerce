package services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.ResourceAccessException;
import tests.ProductFactory;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private String productName;

    private long existingId, nonExistingId;
    private Product product;

    private PageImpl<Product> page;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        productName = "Playstation";
        product = ProductFactory.createProductName(productName);

        page = new PageImpl<>(List.of(product));
        productDTO = new ProductDTO(product);



        Mockito.when(productRepository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(productRepository.save(any())).thenReturn(product);
        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.doThrow(ResourceNotFoundException.class).when(productRepository).findById(nonExistingId);
        Mockito.when(productRepository.searchByName(any(), (Pageable) any())).thenReturn(page);
        Mockito.doThrow(ResourceNotFoundException.class).when(productRepository).getReferenceById(nonExistingId);
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        ProductDTO productDTO = productService.findById(existingId);

        Assertions.assertNotNull(productDTO);
        Assertions.assertEquals(productName, productDTO.getName());
        Assertions.assertEquals(existingId, productDTO.getId());

    }

    @Test
    public void findByIdShouldResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.findById(nonExistingId));
    }


    @Test
    public void FindALLShouldReturnProductDTOWhenIdExists() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductMinDTO> result = productService.findAll(productName, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(productName, result.getContent().get(0).getName());
    }

    @Test
    public void InsertShouldReturnProductDTO() {
        ProductDTO result = productService.Insert(productDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(productName, result.getName());
        Assertions.assertEquals(existingId, result.getId());

    }

    @Test
    public void UpdateShouldReturnProductDTO() {
        ProductDTO updateDTO = productService.Update(existingId, productDTO);

        Assertions.assertNotNull(updateDTO);
        Assertions.assertEquals(productName, updateDTO.getName());

    }

    @Test
    public void UpdateShouldResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.Update(nonExistingId, productDTO));
    }
}
