package tech.smartkit.istorybook.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_STORY_PAGEs_VIEWs")
@Data
@ToString
@NoArgsConstructor
public class StoryPagesViews implements Serializable {
    private int id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
//    @JoinColumn(name = "storybook_id")
//    private StoryBook storyBook;
    private long storyPage;
    @ManyToOne
//    @JoinColumn(name = "storypage_id")
//    private StoryPage storyPage;
    private long storyView;


    public StoryPagesViews(long storyPage, long storyView) {
        this.storyView = storyView;
        this.storyPage = storyPage;
    }
}
