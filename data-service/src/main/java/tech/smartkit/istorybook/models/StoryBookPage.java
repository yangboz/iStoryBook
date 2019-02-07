package tech.smartkit.istorybook.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
@Entity
@Table(name = "T_STORYBOOK_PAGE")
@Data
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
    private String mode;
//    private List<StoryBookPageView> views;//
//    @ElementCollection
    //@see: https://www.callicoder.com/hibernate-spring-boot-jpa-element-collection-demo/
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "T_STORYBOOK_PAGE_VIEW", joinColumns = @JoinColumn(name = "storybookpage_id"))
//@AttributeOverrides({
//        @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
//        @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
//})
//    private Collection<StoryBookPageView> views;
    private Set<StoryBookPageView> views = new HashSet<>();

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
