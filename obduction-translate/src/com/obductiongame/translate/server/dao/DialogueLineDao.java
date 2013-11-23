package com.obductiongame.translate.server.dao;

import java.util.List;

import com.obductiongame.translate.server.ValidationException;
import com.obductiongame.translate.server.entity.DialogueLine;

public interface DialogueLineDao {

	long count();
	DialogueLine get(String key);
	List<DialogueLine> getAll();
	void put(DialogueLine line) throws ValidationException;
	void delete(DialogueLine line);

}