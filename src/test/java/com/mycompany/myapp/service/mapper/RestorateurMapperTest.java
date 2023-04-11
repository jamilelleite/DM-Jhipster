package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestorateurMapperTest {

    private RestorateurMapper restorateurMapper;

    @BeforeEach
    public void setUp() {
        restorateurMapper = new RestorateurMapperImpl();
    }
}
