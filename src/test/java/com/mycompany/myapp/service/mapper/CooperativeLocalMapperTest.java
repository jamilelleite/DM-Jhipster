package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CooperativeLocalMapperTest {

    private CooperativeLocalMapper cooperativeLocalMapper;

    @BeforeEach
    public void setUp() {
        cooperativeLocalMapper = new CooperativeLocalMapperImpl();
    }
}
