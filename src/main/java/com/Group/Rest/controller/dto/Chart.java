package com.Group.Rest.controller.dto;

import lombok.*;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chart {
    private String question;
    private String chartType;
    private List<String> answers;
    private Long[] results;
}
