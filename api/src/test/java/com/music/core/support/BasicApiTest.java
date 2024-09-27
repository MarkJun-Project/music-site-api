package com.music.core.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.core.security.component.JwtComponent;
import com.music.core.security.user.DefaultAuthenticationUser;
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

//    @Autowired
//    private MemberRepository memberRepository;

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

//    protected Member prepareLoggedInActiveUser() {
//        return prepareLoggedInUserInternal(createMember());
//    }
//
//    protected Member prepareLoggedInUser() {
//        return prepareLoggedInUserInternal(createMember());
//    }
//
//    private Member prepareLoggedInUserInternal(Member member) {
//        member = memberRepository.save(member);
//
//        memberRepository.flush();
//
//        login(member);
//
//        return member;
//    }
//
//    protected void login(Member member) {
//        login(member.getMemberCode());
//    }

    private void login(Long id) {
        DefaultAuthenticationUser user = (DefaultAuthenticationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setId(id);
    }
}
