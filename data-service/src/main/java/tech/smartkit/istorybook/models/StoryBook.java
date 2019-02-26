package tech.smartkit.istorybook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import tech.smartkit.istorybook.settings.StoryBookModes;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
@Entity
@Table(name = "T_STORY_BOOK")
@Data
//@JsonIgnoreProperties(value= {"pages"})
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StoryBook extends ModelBase implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    protected Long id;
    @NotNull
//    @Size(max = 10)
    private int width;//
    @NotNull
//    @Size(max = 10)
    private int height;
    @NotNull
    @Size(max = 10)
    private String title;//
    @NotNull
    @Size(max = 144)
    private String description;
    @NotNull
    @Size(max = 300)
    private String cover;

    private double rate;
    private int share;

    private String author;//

    @OneToMany(mappedBy = "storyBook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<StoryBookPages> storyBookPages = new HashSet<>();


    private StoryBookModes mode= StoryBookModes.PUBLIC;//free for template, private property for trading.default is public.

    public StoryBook(Long id) {
        this.id = id;
    }
}
