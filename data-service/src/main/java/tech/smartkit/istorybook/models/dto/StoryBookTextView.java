package tech.smartkit.istorybook.models.dto;

import tech.smartkit.istorybook.models.StoryPageView;
import tech.smartkit.istorybook.settings.StoryPageViewTypes;

public class StoryBookTextView extends StoryPageView {

    @Override
    public String getType() {
        return StoryPageViewTypes.TEXT.toString();
    }
}
