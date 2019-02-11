package tech.smartkit.istorybook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import tech.smartkit.istorybook.settings.StoryBookModes;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

//https://github.com/kuckboy1994/mp_canvas_drawer#api
@Entity
@Table(name = "T_STORYBOOK")
@Data
//@JsonIgnoreProperties(value= {"pages"})
@ToString
@EqualsAndHashCode
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
//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "T_STORYBOOK_PAGE", joinColumns = @JoinColumn(name = "storybook_id"))
////@AttributeOverrides({
////        @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
////        @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
////})
//    private Collection<StoryPage> pages = new ArrayList<>();
//https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
//    @ManyToMany(cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//    })
//    @JoinTable(name = "BOOK_PAGE",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "page_id")
//    )
//https://www.objectdb.com/api/java/jpa/ManyToMany
//http://juhahinkula.github.io/2016-07-16-crudboot-manytomany/
//https://vladmihalcea.com/the-best-way-to-handle-the-lazyinitializationexception/
//@ManyToMany(targetEntity=tech.smartkit.istorybook.models.StoryPage.class,fetch = FetchType.EAGER)
//    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinTable(name = "book_publisher", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "page_id", referencedColumnName = "id"))
////    @JsonIgnore
//    private Set<StoryPage> pages = new HashSet<>();

    @OneToMany(mappedBy = "storyBook", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<StoryBookPage> storyBookPages = new HashSet<>();


    private String mode= StoryBookModes.PUBLIC.toString();//free for template, private property for trading.default is public.
}
