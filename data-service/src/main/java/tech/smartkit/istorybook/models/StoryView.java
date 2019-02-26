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
import tech.smartkit.istorybook.settings.StoryViewTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
@Entity
@Table(name = "T_STORY_VIEW")
@Data
//@JsonIgnoreProperties(value= {"views","books"})
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
public class StoryView extends ModelBase implements Serializable{
    @Id
    @GeneratedValue
    protected Long id;
    private StoryViewTypes type;//: 'image',
    private String url;//: 'url',
    private double top;//: 0,
    private double left;//: 0,
    private double width;//: 375,
    private double height;//: 555

    @OneToMany(mappedBy = "storyPage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<StoryBookPages> storyBookPages = new HashSet<>();
}
