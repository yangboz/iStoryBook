package tech.smartkit.istorybook.models.dto;

import lombok.Getter;
import lombok.Setter;
import tech.smartkit.istorybook.models.StoryPage;
import tech.smartkit.istorybook.models.StoryView;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class StoryPageResp extends StoryPage {
    private List<StoryView> views = new ArrayList<>();
}
