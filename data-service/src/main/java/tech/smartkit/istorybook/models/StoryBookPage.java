package tech.smartkit.istorybook.models;

import lombok.Data;
import tech.smartkit.istorybook.models.dto.StoryBookPageView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
@Entity
@Table(name = "T_STORYBOOK_PAGE")
@Data
public class StoryBookPage extends ModelBase implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    protected Long id;
    private double width;//
    private double height;
    private String mode;
    private List<StoryBookPageView> views;//

    @Override
    public String toString() {
        return "StoryBookPage{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", mode='" + mode + '\'' +
                ", views=" + views +
                '}';
    }
}
