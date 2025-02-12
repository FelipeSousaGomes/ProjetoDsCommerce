package tests;

import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;

import java.time.LocalDate;

public class UserFactory {

    public static User createUserAdmin() {
        User user = new User(1L, "maria", "maria@gmail.com", "99999999", LocalDate.parse("2001-07-25"), "$2a$10$iqtVXxq6wC3AvfbCXwzw9ewb2h8uIh4gCmrd5ImV2Iv69M2MQYAhy");
        user.addRole(new Role(1L, "ROLE_ADMIN"));
        return user;
    }

    public static User createUserClient() {
        User user = new User(2L, "Alex", "alex@gmail.com", "99999999", LocalDate.parse("2001-07-25"), "$2a$10$iqtVXxq6wC3AvfbCXwzw9ewb2h8uIh4gCmrd5ImV2Iv69M2MQYAhy");
        user.addRole(new Role(2L, "ROLE_CLIENT"));
        return user;
    }

}
