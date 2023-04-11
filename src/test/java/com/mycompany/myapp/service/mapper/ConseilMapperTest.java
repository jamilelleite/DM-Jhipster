package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConseilMapperTest {

    private ConseilMapper conseilMapper;

    @BeforeEach
    public void setUp() {
        conseilMapper = new ConseilMapperImpl();
    }
}
