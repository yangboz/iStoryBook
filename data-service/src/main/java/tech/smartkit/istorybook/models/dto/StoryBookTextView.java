package tech.smartkit.istorybook.models.dto;

import tech.smartkit.istorybook.models.StoryBookPageView;
import tech.smartkit.istorybook.settings.StoryBookPageViewTypes;

public class StoryBookTextView extends StoryBookPageView {

    @Override
    public String getType() {
        return StoryBookPageViewTypes.TEXT.toString();
    }
}
