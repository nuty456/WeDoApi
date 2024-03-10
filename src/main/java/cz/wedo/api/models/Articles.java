package cz.wedo.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * The Articles class represents a list of articles.
 */
@Slf4j
@Data
@AllArgsConstructor
public class Articles {
	/**
	 * List of articles.
	 */
	@Expose
	@SerializedName("articles")
	private ArrayList<String> articles;

	/**
	 * Adds an article to the list of articles.
	 *
	 * @param article the article to be added
	 */
	public void add(String article) {
		if (articles == null) {
			articles = new ArrayList<>();
		}
		articles.add(article);
	}
}
