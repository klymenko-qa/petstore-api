package test.store;

import endPoint.PetEndpoint;
import endPoint.StoreEndpoint;
import model.Order;
import model.OrderStatus;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(SerenityRunner.class)
public class DeleteOrderTest {

    @Steps
    private StoreEndpoint storeEndpoint;
    private int orderId;

    @Before
    public void createOrder() {
        Random random = new Random();
        Order order = Order.builder()
                .id(random.nextInt((10 - 1) + 1) + 1)
                .petId(56)
                .quantity(1)
                .shipDate(System.currentTimeMillis())
                .status(OrderStatus.PLACED)
                .complete(true)
                .build();
        orderId = storeEndpoint.createOrder(order);
    }

    @Test
    public void deleteOrder() {
        storeEndpoint.deleteOrder(orderId);
    }
}