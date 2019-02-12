package tech.smartkit.istorybook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
//https://www.callicoder.com/hibernate-spring-boot-jpa-element-collection-demo/
@Getter
@Setter
@ToString
@Table(name = "T_STORYPAGE_VIEW")
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoryPageView implements Serializable {
    @Id
    @GeneratedValue
    protected Long id;
    private String type;//: 'image',
    private String url;//: 'url',
    private double top;//: 0,
    private double left;//: 0,
    private double width;//: 375,
    private double height;//: 555
}
