package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NewsDto {

    private String title;
    private String content;
    private String date;
    private String image;
    private String reporter;
    private String link;
}
