package tech.smartkit.istorybook.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_STORYBOOK_PAGE")
@Data
@ToString
@NoArgsConstructor
public class StoryBookPage implements Serializable {
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
    private long storyBook;
    @ManyToOne
//    @JoinColumn(name = "storypage_id")
//    private StoryPage storyPage;
    private long storyPage;


    public StoryBookPage(long storyBook, long storyPage) {
        this.storyBook = storyBook;
        this.storyPage = storyPage;
    }
}
