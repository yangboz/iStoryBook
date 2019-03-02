package tech.smartkit.istorybook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import tech.smartkit.istorybook.settings.StoryBookModes;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
@Entity
@Table(name = "T_STORY_PAGE")
@Data
@EqualsAndHashCode
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
        @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})
@AllArgsConstructor
@NoArgsConstructor
public class StoryPage extends ModelBase implements Serializable{
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
    private StoryBookModes mode= StoryBookModes.PUBLIC;//free for template, private property for trading.default is public.

    @OneToMany(mappedBy = "storyPage")
    @JsonIgnore
    private Set<StoryBookPages> storyBookPages = new HashSet<>();


    @OneToMany(mappedBy = "storyView",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<StoryPagesViews> storyPagesViews = new HashSet<>();


    @Override
    public String toString() {
        return "StoryPage{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", mode='" + mode + '\'' +
                ", storyBookPages=" + storyBookPages +
                ", storyPagesViews=" + storyPagesViews +
                '}';
    }

    public StoryPage(Long id) {
        this.id = id;
    }
}
