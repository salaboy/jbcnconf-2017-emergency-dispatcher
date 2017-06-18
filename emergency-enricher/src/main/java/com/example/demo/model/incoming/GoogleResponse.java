package com.example.demo.model.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by msalatino on 17/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleResponse {
    @JsonProperty("results")
    private GoogleAddress[] results;

    public GoogleResponse() {
    }

    public GoogleAddress[] getResults() {
        return results;
    }

    public void setResults(GoogleAddress[] results) {
        this.results = results;
    }
}
