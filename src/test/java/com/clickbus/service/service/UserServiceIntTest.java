package com.clickbus.service.service;

import com.clickbus.service.ClickbusApp;
import com.clickbus.service.config.Constants;
import com.clickbus.service.domain.User;
import com.clickbus.service.repository.search.UserSearchRepository;
import com.clickbus.service.repository.UserRepository;
import com.clickbus.service.service.dto.UserDTO;
import com.clickbus.service.service.util.RandomUtil;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClickbusApp.class)
@Transactional
public class UserServiceIntTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * This repository is mocked in the com.clickbus.service.repository.search test package.
     *
     * @see com.clickbus.service.repository.search.UserSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserSearchRepository mockUserSearchRepository;

    @Autowired
    private AuditingHandler auditingHandler;

    @Mock
    DateTimeProvider dateTimeProvider;

    private User user;

    @Before
    public void init() {
        user = new User();
        user.setLogin("johndoe");
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("johndoe@localhost");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setImageUrl("http://placehold.it/50x50");
        user.setLangKey("en");

        when(dateTimeProvider.getNow()).thenReturn(Optional.of(LocalDateTime.now()));
        auditingHandler.setDateTimeProvider(dateTimeProvider);
    }

    @Test
    @Transactional
    public void testFindNotActivatedUsersByCreationDateBefore() {
        Instant now = Instant.now();
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minus(4, ChronoUnit.DAYS)));
        user.setActivated(false);
        User dbUser = userRepository.saveAndFlush(user);
        dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        userRepository.saveAndFlush(user);
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minus(3, ChronoUnit.DAYS));
        assertThat(users).isNotEmpty();
        userService.removeNotActivatedUsers();
        users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minus(3, ChronoUnit.DAYS));
        assertThat(users).isEmpty();

        // Verify Elasticsearch mock
        verify(mockUserSearchRepository, times(1)).delete(user);
    }

    @Test
    @Transactional
    public void testRemoveNotActivatedUsers() {
        // custom "now" for audit to use as creation date
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(Instant.now().minus(30, ChronoUnit.DAYS)));

        user.setActivated(false);
        userRepository.saveAndFlush(user);

        assertThat(userRepository.findOneByLogin("johndoe")).isPresent();
        userService.removeNotActivatedUsers();
        assertThat(userRepository.findOneByLogin("johndoe")).isNotPresent();

        // Verify Elasticsearch mock
        verify(mockUserSearchRepository, times(1)).delete(user);
    }

}
