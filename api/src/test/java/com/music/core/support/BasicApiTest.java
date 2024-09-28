package com.music.core.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import com.music.core.security.component.JwtComponent;
import com.music.core.security.user.DefaultAuthenticationUser;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.music.core.security.component.JwtType.ACCESS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public abstract class BasicApiTest {

    protected String token = "Bearer ";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtComponent jwtComponent;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.token += jwtComponent.issue(0L, ACCESS);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

    protected User prepareLoggedInActiveUser() {
        return prepareLoggedInUserInternal(UserFixture.create());
    }

    protected User prepareLoggedInUser() {
        return prepareLoggedInUserInternal(UserFixture.create());
    }

    private User prepareLoggedInUserInternal(User user) {
        user = userRepository.save(user);

        userRepository.flush();

        login(user);

        return user;
    }

    protected void login(User user) {
        login(user.getId());
    }

    private void login(Long id) {
        DefaultAuthenticationUser user = (DefaultAuthenticationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setId(id);
    }
}
