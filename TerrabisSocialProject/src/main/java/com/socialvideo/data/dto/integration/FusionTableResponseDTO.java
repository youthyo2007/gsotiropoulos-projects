package com.socialvideo.data.dto.integration;

import java.util.List;

import com.google.api.client.util.Key;
import com.socialvideo.data.dto.PublicVideoPointDTO;

public class FusionTableResponseDTO {


  @Key("rows")
  private List<PublicVideoPointDTO> pins;

  public List<PublicVideoPointDTO> getPins() {
    return pins;
  }
}