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

    public Integer updateNote(Integer nId, String nTitle, String nDescription) { return noteMapper.updatenote(nId, nTitle, nDescription); }

    public Integer deleteNote(Integer noteId) { return noteMapper.deletenote(noteId); }

    public Integer nId(String nTitle) { return noteMapper.nid(nTitle); }

}
