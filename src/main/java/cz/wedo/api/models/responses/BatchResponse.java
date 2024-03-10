package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * Batch response.
 */
@Data
public class BatchResponse {
	/**
	 * List of articles responses.
	 */
	@Expose
	@SerializedName("articles")
	private ArrayList<ArticleResponse> articles = new ArrayList<>();

	/**
	 * Batch.
	 */
	@Expose
	@SerializedName("batch")
	@Size(max = 20)
	private Batch batch;
}
