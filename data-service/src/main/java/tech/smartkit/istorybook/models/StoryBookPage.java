package tech.smartkit.istorybook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import tech.smartkit.istorybook.settings.StoryBookModes;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
@Entity
@Table(name = "T_STORYBOOK_PAGE")
@Data
@JsonIgnoreProperties(value= {"views","books"})
public class StoryBookPage extends ModelBase implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    protected Long id;
    @NotNull
    @Size(max = 10)
    private double width;//
    @NotNull
    @Size(max = 10)
    private double height;
    private String mode= StoryBookModes.PUBLIC.toString();//free for template, private property for trading.default is public.
//    private List<StoryBookPageView> views;//
//    @ElementCollection
    //@see: https://www.callicoder.com/hibernate-spring-boot-jpa-element-collection-demo/
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "T_STORYBOOK_PAGE_VIEW", joinColumns = @JoinColumn(name = "storybookpage_id"))
//@AttributeOverrides({
//        @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
//        @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
//})
    private Collection<StoryBookPageView> views = new ArrayList<>();
//    private Set<StoryBookPageView> views = new HashSet<>();

//https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
    @ManyToMany(mappedBy = "pages")
    private Set<StoryBook> books = new HashSet<>();

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
