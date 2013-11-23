package com.obductiongame.translate.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.obductiongame.translate.client.view.EditPlace;

@WithTokenizers({EditPlace.Tokenizer.class})
public interface MainPlaceHistoryMapper extends PlaceHistoryMapper {

}
