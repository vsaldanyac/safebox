package com.securitish.safebox.com;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SafeboxControllerShould {

  static final String VALID_USERNAME = "VALID_USERNAME";
  static final String VALID_PASSWORD = "VALID_PASSWORD";
  static final String INVALID_USERNAME = "INVALID_USERNAME";
  static final String INVALID_PASSWORD = "INVALID_PASSWORD";
  static final String VALID_SAFEBOX = "{\"name\": \"VALID_USERNAME\", \"password\": \"VALID_PASSWORD\"}";
  static final String ITEMS_TO_INSERT = "[\"Safebox Item 1\", \"Safebox Item 2\",\"Safebox Item 3\"]";

  @Autowired
  private MockMvc mockMvc;

  @Before
  public void setup() {
    initMocks(this);
  }


  private String getSafeBoxIdForTest() throws Exception {
    ResultActions result
        = mockMvc.perform(post("/safebox")
        .content(VALID_SAFEBOX)
        .contentType("application/json;charset=UTF-8"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"));

    String resultString = result.andReturn().getResponse().getContentAsString();
    JacksonJsonParser jsonParser = new JacksonJsonParser();
    return jsonParser.parseMap(resultString).get("id").toString();
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
        .content(VALID_SAFEBOX)
        .contentType("application/json;charset=UTF-8"))
        .andExpect(status().is2xxSuccessful()
        );
  }

  @Test
  public void shouldExistGetItemsRequest() throws Exception {
    String safeboxId = getSafeBoxIdForTest();

    this.mockMvc.perform(get(String.format("/safebox/%s/items", safeboxId))
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is2xxSuccessful()
    );
  }

  @Test
  public void shouldReturnErrorOnGetItemsInvalidUsernameRequest() throws Exception {
    String safeboxId = getSafeBoxIdForTest();
    this.mockMvc.perform(get(String.format("/safebox/%s/items", safeboxId))
        .header("X-Auth-Name", INVALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is4xxClientError()
    );
  }

  @Test
  public void shouldReturnErrorOnGetItemsInvalidPasswordRequest() throws Exception {
    String safeboxId = getSafeBoxIdForTest();
    this.mockMvc.perform(get(String.format("/safebox/%s/items", safeboxId))
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", INVALID_PASSWORD)
    ).andExpect(status().is4xxClientError()
    );
  }

  @Test
  public void shouldExistPutItemsRequest() throws Exception {
    String safeboxId = getSafeBoxIdForTest();
    this.mockMvc.perform(put(String.format("/safebox/%s/items", safeboxId))
        .content(ITEMS_TO_INSERT)
        .contentType("application/json;charset=UTF-8")
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is2xxSuccessful()
    );
  }
}
