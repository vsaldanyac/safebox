package com.securitish.safebox.com.controller;

import com.securitish.safebox.com.util.SecurityUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SafeboxControllerShould {

  static final String INVALID_SAFEBOX_ID = "INVALID_SAFEBOX_ID";
  static final String VALID_USERNAME = "VALID_USERNAME";
  static final String VALID_PASSWORD = "VALID_Password*1234";
  static final String INVALID_USERNAME = "INVALID_USERNAME";
  static final String INVALID_PASSWORD = "INVALID_Password*1234";
  static final String SAFEBOX_WITH_PWD_NOT_STRONG = "{\"name\": \"VALID_USERNAME\", \"password\": \"1234\"}";
  static final String VALID_SAFEBOX = "{\"name\": \"VALID_USERNAME\", \"password\": \"VALID_Password*1234\"}";
  static final String SAFEBOX_WITH_NO_NAME = "{\"password\": \"VALID_Password*1234\"}";
  static final String SAFEBOX_WITH_EMPTY_NAME = "{\"name\": \"\", \"password\": \"VALID_Password*1234\"}";
  static final String ITEMS_TO_INSERT = "[\"Safebox Item 1\", \"Safebox Item 2\",\"Safebox Item 3\"]";

  @Autowired
  private MockMvc mockMvc;

  @Before
  public void setup() {
    initMocks(this);
  }


  private String getSafeBoxIdForTest() throws Exception {
    ResultActions result
        = mockMvc.perform(post("/beta/safebox")
        .content(VALID_SAFEBOX)
        .contentType("application/json;charset=UTF-8"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"));

    String resultString = result.andReturn().getResponse().getContentAsString();
    JacksonJsonParser jsonParser = new JacksonJsonParser();
    return jsonParser.parseMap(resultString).get("id").toString();
  }

  private String getValidToken(String safeboxId) throws Exception {
    ResultActions result
        = this.mockMvc.perform(get(String.format("/v1/safebox/%s/auth", safeboxId))
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is2xxSuccessful()
    ).andExpect(content().contentType("application/json"));

    String resultString = result.andReturn().getResponse().getContentAsString();
    JacksonJsonParser jsonParser = new JacksonJsonParser();
    return jsonParser.parseMap(resultString).get("token").toString();
  }

  @Test
  public void shouldExistStatusRequest() throws Exception {
    this.mockMvc.perform(get("/beta/safebox/status")
    ).andExpect(status().is2xxSuccessful()
    );
  }

  @Test
  public void shouldExistCreateSafeboxRequest() throws Exception {
    this.mockMvc.perform(post("/beta/safebox")
        .content(VALID_SAFEBOX)
        .contentType("application/json;charset=UTF-8"))
        .andExpect(status().is2xxSuccessful()
        );
  }

  @Test
  public void shouldExistGetItemsRequest() throws Exception {
    String safeboxId = getSafeBoxIdForTest();

    this.mockMvc.perform(get(String.format("/beta/safebox/%s/items", safeboxId))
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is2xxSuccessful()
    );
  }

  @Test
  public void shouldReturnErrorOnGetItemsInvalidUsernameRequest() throws Exception {
    String safeboxId = getSafeBoxIdForTest();
    this.mockMvc.perform(get(String.format("/beta/safebox/%s/items", safeboxId))
        .header("X-Auth-Name", INVALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is4xxClientError()
    );
  }

  @Test
  public void shouldReturnErrorOnGetItemsInvalidPasswordRequest() throws Exception {
    String safeboxId = getSafeBoxIdForTest();
    this.mockMvc.perform(get(String.format("/beta/safebox/%s/items", safeboxId))
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", INVALID_PASSWORD)
    ).andExpect(status().is4xxClientError()
    );
  }

  @Test
  public void shouldExistPutItemsRequest() throws Exception {
    String safeboxId = getSafeBoxIdForTest();
    this.mockMvc.perform(put(String.format("/beta/safebox/%s/items", safeboxId))
        .content(ITEMS_TO_INSERT)
        .contentType("application/json;charset=UTF-8")
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is2xxSuccessful()
    );
  }

  @Test
  public void shouldReturnValidToken() throws Exception {
    String safeboxId = getSafeBoxIdForTest();
    String token = getValidToken(safeboxId);
    assertTrue(token.contains(SecurityUtils.PREFIX));
  }

  @Test
  public void shouldNotReturnValidTokenIfInvalidSafeboxId() throws Exception {
    this.mockMvc.perform(get(String.format("/v1/safebox/%s/auth", INVALID_SAFEBOX_ID))
        .header("X-Auth-Name", VALID_USERNAME)
        .header("X-Auth-Pwd", VALID_PASSWORD)
    ).andExpect(status().is4xxClientError()
    );
  }

  @Test
  public void shouldNotReturnValidTokenIfInvalidUser() throws Exception {
    String safeboxId = getSafeBoxIdForTest();
    this.mockMvc.perform(get(String.format("/v1/safebox/%s/auth", safeboxId))
        .header("X-Auth-Name", INVALID_USERNAME)
        .header("X-Auth-Pwd", INVALID_PASSWORD)
    ).andExpect(status().is4xxClientError()
    );
  }

  @Test
  public void shouldExistGetItemsRequestV1WithToken() throws Exception {
    String safeboxId = getSafeBoxIdForTest();
    String token = getValidToken(safeboxId);
    this.mockMvc.perform(get(String.format("/v1/safebox/%s/items", safeboxId))
        .header("Authorization", token)
    ).andExpect(status().is2xxSuccessful()
    );
  }

  @Test
  public void shouldNotAllowStoreSafeboxWithEmptyName() {
    try {
      this.mockMvc.perform(post("/beta/safebox")
          .content(SAFEBOX_WITH_EMPTY_NAME)
          .contentType("application/json;charset=UTF-8"))
          .andExpect(status().is5xxServerError()
          );
    } catch (
        Exception e) {
      assertEquals(e.getClass(), NestedServletException.class);
    }

  }

  @Test
  public void shouldNotAllowStoreSafeboxWithNullName() {
    try {
      this.mockMvc.perform(post("/beta/safebox")
          .content(SAFEBOX_WITH_NO_NAME)
          .contentType("application/json;charset=UTF-8"))
          .andExpect(status().is5xxServerError()
          );
    } catch (Exception e) {
      assertEquals(e.getClass(), NestedServletException.class);
    }
  }

  @Test
  public void shouldNotAllowPasswordNotStrongEnough() throws Exception {
    this.mockMvc.perform(post("/beta/safebox")
        .content(SAFEBOX_WITH_PWD_NOT_STRONG)
        .contentType("application/json;charset=UTF-8"))
        .andExpect(status().is4xxClientError()
        );
  }
}
