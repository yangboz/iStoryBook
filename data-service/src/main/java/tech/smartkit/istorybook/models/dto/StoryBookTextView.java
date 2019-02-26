package tech.smartkit.istorybook.models.dto;

import tech.smartkit.istorybook.models.StoryPagesViews;
import tech.smartkit.istorybook.models.StoryView;
import tech.smartkit.istorybook.settings.StoryViewTypes;

public class StoryBookTextView extends StoryView {

    @Override
    public StoryViewTypes getType() {
        return StoryViewTypes.TEXT;
    }
}
