package com.nure.Course.Project.saloon.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nure.Course.Project.saloon.Saloon;
import com.nure.Course.Project.saloon.SaloonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SaloonServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SaloonServiceImplDiffblueTest {
    @MockBean
    private SaloonRepository saloonRepository;

    @Autowired
    private SaloonServiceImpl saloonServiceImpl;

    /**
     * Method under test: {@link SaloonServiceImpl#getSaloonByInviteCode(String)}
     */
    @Test
    void testGetSaloonByInviteCode() {
        // Arrange
        Saloon saloon = new Saloon();
        saloon.setActive(true);
        saloon.setAddress("42 Main St");
        saloon.setCity("Oxford");
        saloon.setId(1L);
        saloon.setInviteCode("Invite Code");
        saloon.setName("Name");
        saloon.setOwnerId(1L);
        saloon.setRegion("us-east-2");
        saloon.setSchedule("Schedule");
        when(saloonRepository.getSaloonByInviteCode(Mockito.<String>any())).thenReturn(saloon);

        // Act
        Saloon actualSaloonByInviteCode = saloonServiceImpl.getSaloonByInviteCode("Invite Code");

        // Assert
        verify(saloonRepository).getSaloonByInviteCode(eq("Invite Code"));
        assertSame(saloon, actualSaloonByInviteCode);
    }
}
