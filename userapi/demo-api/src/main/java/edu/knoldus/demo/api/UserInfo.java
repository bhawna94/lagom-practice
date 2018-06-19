package edu.knoldus.demo.api;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserInfo {
    //private static final long serialVersionUID = 1L;
    public int userId;

    public String userName;

    public String qualification;

    public String trackAssigned;
}
