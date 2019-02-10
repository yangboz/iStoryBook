package tech.smartkit.istorybook.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_STORYBOOK_PAGE")
@Data
@ToString
public class StoryBookPage extends ModelBase implements Serializable {
    @Id
    @GeneratedValue
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "storybook_id")
    private StoryBook storyBook;
    @ManyToOne
    @JoinColumn(name = "storypage_id")
    private StoryPage storyPage;
}
