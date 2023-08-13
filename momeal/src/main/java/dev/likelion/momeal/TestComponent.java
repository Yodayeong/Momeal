package dev.likelion.momeal;

import dev.likelion.momeal.entity.MenuEntity;
import dev.likelion.momeal.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestComponent {
    public TestComponent(
            @Autowired MenuRepository menuRepository
            ) {
        MenuEntity menuEntity = new MenuEntity();

    }
}
