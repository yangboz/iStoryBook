package tech.smartkit.istorybook.models.dto;

import tech.smartkit.istorybook.settings.StoryBookPageViewTypes;

public class StoryBookRectView extends StoryBookPageView {

    @Override
    public String getType() {
        return StoryBookPageViewTypes.RECT.toString();
    }
}
