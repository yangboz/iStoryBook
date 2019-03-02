package tech.smartkit.istorybook.models.dto;

import lombok.*;
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.StoryView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class StoryPageResp extends StoryPage {
    private List<StoryView> views = new ArrayList<>();
}
