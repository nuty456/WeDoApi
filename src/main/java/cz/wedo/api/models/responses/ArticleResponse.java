package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Article response.
 */
@Slf4j
@Data
public class ArticleResponse implements Serializable {
  /**
   * Code.
   */
  @Expose
  @SerializedName("code")
  @Size(max = 255)
  private String code;

  /**
   * Order number.
   */
  @Expose
  @SerializedName("order_number")
  @Size(max = 255)
  private String orderNumber;

}
