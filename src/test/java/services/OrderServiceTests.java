package services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.services.AuthService;
import com.devsuperior.dscommerce.services.OrderService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tests.OrderFactory;
import tests.UserFactory;

import java.awt.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository repository;

    @Mock
    private AuthService authService;

    private Long existingOrderId, nonExistingOrderId;
    private Order order;
    private OrderDTO orderDTO;
    private User admin, client;

    @BeforeEach
    public void setUp() {
        existingOrderId = 1L;
        nonExistingOrderId = 2L;

        admin = UserFactory.createUserAdmin();
        client = UserFactory.createUserClient();
        order = OrderFactory.createOrder(client);
        orderDTO = new OrderDTO(order);

        Mockito.when(repository.findById(existingOrderId)).thenReturn(Optional.of(order));
        Mockito.when(repository.findById(nonExistingOrderId)).thenReturn(Optional.empty());


    }



    @Test
    public void findByIdShouldReturnOrderDTOWhenAdminLogged() {
        Mockito.doNothing().when(authService).validateSelfOrAdmin(any());

        OrderDTO result = orderService.findById(existingOrderId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingOrderId);

    }

    @Test
    public void findByIdShouldReturnOrderDTOWhenClientLogged() {
        Mockito.doNothing().when(authService).validateSelfOrAdmin(any());

        OrderDTO result = orderService.findById(existingOrderId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingOrderId);

    }

    @Test
    public void findByIDShouldThrowForbiddenExceptionWhenClientLogged() {
        Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(any());

        Assertions.assertThrows(ForbiddenException.class, () -> orderService.findById(existingOrderId));

    }

    @Test
    public void findByIDShouldThrowResourceNotFoundExceptionnWhenDoesNotExist() {
        Mockito.doNothing().when(authService).validateSelfOrAdmin(any());
       Mockito.doThrow(ResourceNotFoundException.class).when(authService).validateSelfOrAdmin(any());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> orderService.findById(nonExistingOrderId));

    }
}
