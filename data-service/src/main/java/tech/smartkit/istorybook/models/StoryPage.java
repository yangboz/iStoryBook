package tech.smartkit.istorybook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import tech.smartkit.istorybook.settings.StoryBookModes;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
@Entity
@Table(name = "T_STORYPAGE")
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
    private String mode= StoryBookModes.PUBLIC.toString();//free for template, private property for trading.default is public.
//    @ElementCollection
//@Embedded
//    private List<StoryPageView> views;//
//    @ElementCollection
    //@see: https://www.callicoder.com/hibernate-spring-boot-jpa-element-collection-demo/
//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "T_STORYPAGE_VIEW", joinColumns = @JoinColumn(name = "storypage_id"))
//@AttributeOverrides({
//        @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
//        @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
//})
//    @JsonIgnore
//@ElementCollection
//@Embedded
//@ElementCollection

//    private List<StoryPageView> views = new ArrayList<>();
//    @Embedded
//@ElementCollection
//@JsonUnwrapped
@Type(type = "jsonb")
@Column(columnDefinition = "jsonb")
private List<StoryPageView> views = new ArrayList<>();
//    private Set<StoryPageView> views = new HashSet<>();

//https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
//    @ManyToMany(mappedBy = "pages")
////@ManyToMany(targetEntity=tech.smartkit.istorybook.models.StoryBook.class, mappedBy="pages",fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "storyPage")
    @JsonIgnore
    private Set<StoryBookPage> storyBookPages = new HashSet<>();

    @Override
    public String toString() {
        return "StoryPage{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", mode='" + mode + '\'' +
                ", views=" + views +
                '}';
    }

    public StoryPage(Long id) {
        this.id = id;
    }
}
