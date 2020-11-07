package com.securitish.safebox.com;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SafeboxControllerShould {

  public static final String ID_SAFEBOX = "A";
  public static final String VALID_USERNAME = "VALID_USERNAME";
  public static final String VALID_PASSWORD = "VALID_PASSWORD";
  public static final String INVALID_USERNAME = "INVALID_USERNAME";
  public static final String INVALID_PASSWORD = "INVALID_PASSWORD";

  @Autowired
  private MockMvc mockMvc;

  @Before
  public void setup() {
    initMocks(this);
  }

  @Test
  public void shouldExistStatusRequest() throws Exception {
    this.mockMvc.perform(get("/safebox/status")
    ).andExpect(status().is2xxSuccessful()
    );
  }

  @Test
  public void shouldExistCreateSafeboxRequest() throws Exception {
    this.mockMvc.perform(post("/safebox")
    ).andExpect(status().is2xxSuccessful()
    );
  }

  @Test
  public void shouldExistGetItemsRequest() throws Exception {

    this.mockMvc.perform(get(String.format("/safebox/%s/items", ID_SAFEBOX))
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is2xxSuccessful()
    );
  }

  @Test
  public void shouldReturnErrorOnGetItemsInvalidUsernameRequest() throws Exception {

    this.mockMvc.perform(get(String.format("/safebox/%s/items", ID_SAFEBOX))
        .header("X-Auth-Name", INVALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is4xxClientError()
    );
  }

  @Test
  public void shouldReturnErrorOnGetItemsInvalidPasswordRequest() throws Exception {

    this.mockMvc.perform(get(String.format("/safebox/%s/items", ID_SAFEBOX))
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", INVALID_PASSWORD)
    ).andExpect(status().is4xxClientError()
    );
  }

  @Test
  public void shouldExistPutItemsRequest() throws Exception {
    this.mockMvc.perform(put(String.format("/safebox/%s/items", ID_SAFEBOX))
    ).andExpect(status().is2xxSuccessful()
    );
  }
}
