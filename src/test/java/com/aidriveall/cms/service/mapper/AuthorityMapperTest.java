package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorityMapperTest {

    private AuthorityMapper authorityMapper;

    @BeforeEach
    public void setUp() {
        authorityMapper = new AuthorityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(authorityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(authorityMapper.fromId(null)).isNull();
    }
}
