package tech.smartkit.istorybook.models.dto;

import tech.smartkit.istorybook.settings.StoryBookPageViewTypes;

public class StoryBookImageView extends StoryBookPageView {

    @Override
    public String getType() {
        return StoryBookPageViewTypes.IMAGE.toString();
    }
}
