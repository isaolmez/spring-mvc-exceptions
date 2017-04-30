package com.isa.spring.mvc.exceptions.global.controller;

import com.isa.spring.mvc.exceptions.global.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
public class HelloControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldHandleException_WithResponseStatus() throws Exception {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(status().reason("Handled by @ResponseStatus"));
    }

    @Test
    public void shouldHandleException_WithExceptionHandler() throws Exception {
        mockMvc.perform(get("/hello2"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Handled by @ExceptionHandler"));
    }

    @Test
    public void shouldHandleException_WithExceptionHandler_ForwardingWithoutModel() throws Exception {
        mockMvc.perform(get("/hello3"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(view().name("global/error"));
    }

    @Test
    public void shouldHandleException_WithExceptionHandler_ForwardingWithModel() throws Exception {
        mockMvc.perform(get("/hello4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("global/errorWithModel"))
                .andExpect(model().attributeExists("exception", "url"));
    }

    @Test
    public void shouldHandleException_WithExceptionHandler_WithResponseEntity() throws Exception {
        mockMvc.perform(get("/hello5"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Handled by @ExceptionHandler and written to response body"));
    }

    @Test
    public void shouldHandleException_WithCatchAllExceptionHandler() throws Exception {
        mockMvc.perform(get("/hello6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("global/error"))
                .andExpect(model().attributeExists("exception", "url"));
    }

    @Test
    public void shouldHandleException_WithCatchAllExceptionHandler_Again() throws Exception {
        mockMvc.perform(get("/hello7"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("global/error"))
                .andExpect(model().attributeExists("exception", "url"));
    }

    /**
     * If @ControllerAdvice extends ResponseEntityExceptionResovler, exceptions that are
     * being handled by Spring, are not overridden.
     * But right now, they are overridden.
     */
    @Test
    public void shouldOverride_HttpRequestMethodNotSupportedException_FromDefaultHandlerExceptionResolver() throws Exception {
        mockMvc.perform(post("/hello6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("global/error"))
                .andExpect(model().attributeExists("exception", "url"));
    }
}
