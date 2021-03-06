package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> NoteFiles(Integer UserId) {
        return noteMapper.usernotes(UserId);
    }

    public Integer insertNote(Note newnote) { return noteMapper.addnote(newnote); }

    public Integer updateNote(Note upnote) { return noteMapper.updatenote(upnote); }

    public Integer deleteNote(Integer noteId) { return noteMapper.deletenote(noteId); }


}
