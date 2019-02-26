package tech.smartkit.istorybook.models.dto;

import lombok.*;
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.StoryView;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookPagesResp implements Serializable {
    private StoryPage storyPage;
    private StoryView storyView;
}
