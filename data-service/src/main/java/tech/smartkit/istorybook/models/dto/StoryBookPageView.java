package tech.smartkit.istorybook.models.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
@Getter
@Setter
@ToString
public class StoryBookPageView {
    private String type;//: 'image',
    private String url;//: 'url',
    private double top;//: 0,
    private double left;//: 0,
    private double width;//: 375,
    private double height;//: 555
}
