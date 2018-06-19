package edu.knoldus.demo.api.api;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize
public class UserInfo {
    //private static final long serialVersionUID = 1L;
    public int userId;

    public String userName;

    public String qualification;

    public String trackAssigned;
}
