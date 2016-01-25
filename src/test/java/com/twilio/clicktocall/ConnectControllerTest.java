package com.twilio.clicktocall;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConnectControllerTest {

    private HttpServletRequest fakeServletRequest;
    private RequestValidator mockedRequestValidator;

    @Before
    public void setUp() {
        fakeServletRequest = mock(HttpServletRequest.class);
        mockedRequestValidator = mock(RequestValidator.class);
    }

    @Test
    public void shouldReturnResponseMessageWhenRequestIsValid() {
        ConnectController connectController = new ConnectController(mockedRequestValidator);
        when(mockedRequestValidator.validate(fakeServletRequest)).thenReturn(true);

        ResponseEntity<String> response = connectController.connect(fakeServletRequest);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), containsString(
                "If this were a real click to call implementation, you would be connected " +
                        "to an agent at this point."));
    }

    @Test
    public void shouldNotGenerateTwiMLWhenRequestIsNotValid() {
        when(mockedRequestValidator.validate(fakeServletRequest)).thenReturn(false);
        ConnectController connectController = new ConnectController(mockedRequestValidator);

        ResponseEntity<String> result = connectController.connect(fakeServletRequest);

        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody(), containsString("Invalid twilio request"));
    }
}
