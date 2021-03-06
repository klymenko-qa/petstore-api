package test.store;

import endPoint.PetEndpoint;
import endPoint.StoreEndpoint;
import model.*;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(SerenityRunner.class)
public class CreateOrderTest {

    @Steps
    private StoreEndpoint storeEndpoint;
    private int orderId;

    @Steps
    private PetEndpoint petEndpoint;
    private long petId;

    @Before
    public void createPrecondition() {
        Pet pet = Pet.builder()
                .id(0)
                .name("Bob")
                .status(PetStatus.AVAILABLE)
                .category(Category
                        .builder()
                        .build())
                .build();
        petId = petEndpoint.createPet(pet);
    }

    @After
    public void deleteOrder() {
        storeEndpoint.deleteOrder(orderId);
    }

    @Test
    public void createOrder() {
        Random random = new Random();
        Order order = Order.builder()
                .id(random.nextInt((10 - 1) + 1) + 1)
                .petId(petId)
                .quantity(1)
                .shipDate(System.currentTimeMillis())
                .status(OrderStatus.PLACED)
                .complete(true)
                .build();
        orderId = storeEndpoint.createOrder(order);
    }
}
