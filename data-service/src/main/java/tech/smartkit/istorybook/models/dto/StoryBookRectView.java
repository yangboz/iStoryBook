package tech.smartkit.istorybook.models.dto;

import tech.smartkit.istorybook.models.StoryView;
import tech.smartkit.istorybook.settings.StoryViewTypes;

public class StoryBookRectView extends StoryView {

    @Override
    public StoryViewTypes getType() {
        return StoryViewTypes.RECT;
    }
}
